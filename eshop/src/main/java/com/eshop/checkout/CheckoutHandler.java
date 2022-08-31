package com.eshop.checkout;

import com.eshop.cart.CartService;
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
public class CheckoutHandler implements HttpHandler {

    private static final Logger logger = LogManager.getLogger(CheckoutHandler.class);

    private final String path;
    private final CheckoutService checkoutService;
    private SessionService sessionService;
    private CartService cartService;

    public CheckoutHandler(final String path, CheckoutService checkoutService, SessionService sessionService, CartService cartService) {
        this.path = path;
        this.checkoutService = checkoutService;
        this.sessionService = sessionService;
        this.cartService = cartService;
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

        String data = responseBody.toString();
        t.sendResponseHeaders(responseCode, data.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(data.getBytes());
        os.close();
    }

    private JSONObject handlePost(String requestURI, InputStream requestBody, Session session) {

        JSONObject result = new JSONObject();

        int responseCode = 200;
        JSONObject responseBody;

        try {
            JSONObject json = StreamConventer.getJsonFromInStream(requestBody);

            switch (requestURI) {
                case "purchase/submit":
                    responseBody = submitPurchase(json, session);
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

    JSONObject submitPurchase(JSONObject json, Session session) {
        JSONObject result = new JSONObject();
        JSONObject confirmationInfo = checkoutService.submitPurchase(json, session);
        result.put("confirmationInfo", confirmationInfo);
        sessionService.deleteSession(session);
        return result;
    }
}
