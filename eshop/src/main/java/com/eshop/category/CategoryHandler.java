package com.eshop.category;

import com.eshop.product.Product;
import com.eshop.utils.TemplateMessages;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

/**
 * @author https://github.com/steshenkoma
 */
public class CategoryHandler implements HttpHandler {

    private static final Logger logger = LogManager.getLogger(CategoryHandler.class);

    private final String path;
    private final CategoryService categoryService;

    public CategoryHandler(final String path, CategoryService categoryService) {
        this.path = path;
        this.categoryService = categoryService;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {

        String requestURI = t.getRequestURI().getPath().substring(path.length() + 1).toLowerCase();
        String method = t.getRequestMethod();

        int responseCode;
        JSONObject responseBody;

        try {
            switch (method) {
                case "GET":
                    JSONObject getResult = handleGet(requestURI);
                    responseBody = getResult.getJSONObject("body");
                    responseCode = getResult.getInt("code");
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

    private JSONObject handleGet(String requestURI) {

        JSONObject result = new JSONObject();

        int responseCode = 200;
        JSONObject responseBody;

        String rgx = "(\\d+)";

        if (requestURI.matches("all")) {
            responseBody = getCategories();
        } else if (requestURI.matches(rgx)) {
            int categoryId = Integer.parseInt(requestURI);
            responseBody = getSelectedCategory(categoryId);
        } else if (requestURI.matches(rgx + "/products")) {
            int categoryId = Integer.parseInt(requestURI.replaceAll("/products", ""));
            responseBody = getSelectedCategoryProducts(categoryId);
        } else {
            responseCode = 501;
            responseBody = TemplateMessages.uriNotImplemented(requestURI);
        }

        result.put("code", responseCode);
        result.put("body", responseBody);

        return result;
    }

    private JSONObject getCategories() {
        JSONObject result = new JSONObject();
        JSONArray categoiesJsonArr = new JSONArray();
        Collection<Category> categories = categoryService.getCategories().values();
        categories.forEach(category -> categoiesJsonArr.put(category.toJSONObject()));
        result.put("categories", categoiesJsonArr);
        return result;
    }

    private JSONObject getSelectedCategory(int categoryId) {
        JSONObject result = new JSONObject();
        Category category = categoryService.getSelectedCategory(categoryId);
        result.put("category", category.toJSONObject());
        return result;
    }

    private JSONObject getSelectedCategoryProducts(int categoryId) {
        JSONObject result = new JSONObject();
        JSONArray productsJsonArr = new JSONArray();
        Map<Integer, Product> products = categoryService.getCategories().get(categoryId).getCategoryProducts();

        products.forEach((k, v) -> productsJsonArr.put(v.toJSONObject()));

        result.put("products", productsJsonArr);
        return result;
    }

}
