package com.eshop.cart;

import com.eshop.product.Product;
import com.eshop.product.ProductService;
import com.eshop.session.Session;
import com.eshop.session.SessionService;
import com.eshop.utils.StreamConventer;
import com.eshop.utils.TemplateMessages;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author https://github.com/steshenkoma
 */
public class CartHandler implements HttpHandler {

    private static final Logger logger = LogManager.getLogger(CartHandler.class);

    private final String path;
    private final CartService cartService;
    private final SessionService sessionService;
    private final ProductService productService;

    public CartHandler(final String path, CartService cartService, SessionService sessionService, ProductService productService) {
        this.path = path;
        this.cartService = cartService;
        this.sessionService = sessionService;
        this.productService = productService;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {

        String requestURI = t.getRequestURI().getPath().substring(path.length() + 1).toLowerCase();
        String method = t.getRequestMethod();

        List<String> req_cookies = null;
        if (t.getRequestHeaders().containsKey("Cookie")) {
            req_cookies = t.getRequestHeaders().get("Cookie");
        }

        String SID = parseSessionID(req_cookies);
        Session session = sessionService.getSession(SID);

        int responseCode;
        JSONObject responseBody;

        try {
            switch (method) {
                case "GET":
                    JSONObject getResult = handleGet(requestURI, session);
                    responseBody = getResult.getJSONObject("body");
                    responseCode = getResult.getInt("code");
                    break;
                case "POST":
                    JSONObject postResult = handlePost(requestURI, t.getRequestBody(), session);
                    responseBody = postResult.getJSONObject("body");
                    responseCode = postResult.getInt("code");
                    break;
                default:
                    responseCode = 501;
                    responseBody = TemplateMessages.methodNotImplemented(method);
                    break;
            }
        } catch (RuntimeException ex) {
            logger.error("Unknown error: {}", ex);
            responseCode = 500;
            responseBody = TemplateMessages.internalServerError();
        }

        t.getResponseHeaders().add("content-type", "application/json; charset=utf-8");

        String data = responseBody.toString();
        t.sendResponseHeaders(responseCode, data.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(data.getBytes());
        os.close();
    }

    private JSONObject handleGet(String requestURI, Session session) {

        JSONObject result = new JSONObject();

        int responseCode = 200;
        JSONObject responseBody;

        switch (requestURI) {
            case "getinfo":
                responseBody = getCartInfo(session);
                break;
            default:
                responseCode = 501;
                responseBody = TemplateMessages.uriNotImplemented(requestURI);
                break;
        }

        result.put("code", responseCode);
        result.put("body", responseBody);

        return result;
    }

    private JSONObject handlePost(String requestURI, InputStream requestBody, Session session) {

        JSONObject result = new JSONObject();

        int responseCode = 200;
        JSONObject responseBody;

        try {
            JSONObject json = StreamConventer.getJsonFromInStream(requestBody);

            switch (requestURI) {
                case "additem":
                    Product productToAdd = parseProduct(json);
                    responseBody = addItem(productToAdd, session);
                    break;
                case "updatequantity":
                    Product productToUpdate = parseProduct(json);
                    int quantity = parseQuantity(json);
                    responseBody = updateQuantity(productToUpdate, quantity, session);
                    break;
                default:
                    responseCode = 501;
                    responseBody = TemplateMessages.uriNotImplemented(requestURI);
                    break;
            }
        } catch (JSONException ex) {
            logger.error("Request body wrong format: {}", ex);
            responseCode = 400;
            responseBody = TemplateMessages.requestBodyWrongFormat();
        } catch (IOException ex) {
            logger.error("Failed to read inputStream: {}", ex);
            responseCode = 500;
            responseBody = TemplateMessages.inputStreamError();
        }

        result.put("code", responseCode);
        result.put("body", responseBody);

        return result;
    }

    private JSONObject addItem(Product product, Session session) {
        JSONObject result = new JSONObject();
        int itemsInCart = cartService.addItemToSessionCart(product, session);
        result.put("itemsInCart", itemsInCart);
        return result;
    }

    private JSONObject getCartInfo(Session session) {
        JSONObject result = new JSONObject();

        JSONObject cartinfo = cartService.getCartInformation(session);
        result.put("cartInfo", cartinfo);
        return result;
    }

    private String parseSessionID(List<String> cookies) {

        String SID = null;

        for (String e : cookies) {

            if (e.contains("sid")) {
                String[] rawCookieParams = e.split(";");
                for (String v : rawCookieParams) {
                    if (v.contains("sid")) {
                        SID = v.replaceAll("sid=", "");
                    }
                }
            }
        }

        return SID;
    }

    private Product parseProduct(JSONObject json) {
        Integer productId = json.getInt("id");
        Product product = productService.getProducts().get(productId);
        return product;
    }

    private Integer parseQuantity(JSONObject json) {
        Integer quantity = json.getInt("quantity");
        return quantity;
    }

    private JSONObject updateQuantity(Product product, int quantity, Session session) {
        JSONObject result = new JSONObject();
        cartService.updateQuantity(product, quantity, session);
        JSONObject cartInfo = cartService.getCartInformation(session);
        result.put("cartInfo", cartInfo);
        return result;
    }

}