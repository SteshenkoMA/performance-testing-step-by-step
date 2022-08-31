package com.eshop.utils;

import org.json.JSONObject;

/**
 * @author https://github.com/steshenkoma
 */
public class TemplateMessages {

    //Http code 400
    public static JSONObject requestBodyWrongFormat() {
        String template = "Request body wrong format";
        JSONObject result = new JSONObject().append("message", template);
        return result;
    }

    //Http code 500
    public static JSONObject inputStreamError() {
        String template = "Failed to read input stream";
        JSONObject result = new JSONObject().append("message", template);
        return result;
    }

    //Http code 500
    public static JSONObject internalServerError() {
        String template = "Internal server error";
        JSONObject result = new JSONObject().append("message", template);
        return result;
    }

    //Http code 501
    public static JSONObject methodNotImplemented(String method) {
        String template = "Method " + method + " is not implemented";
        JSONObject result = new JSONObject().append("message", template);
        return result;
    }

    //Http code 501
    public static JSONObject uriNotImplemented(String requestURI) {
        String template = "URI " + requestURI + " is not implemented";
        JSONObject result = new JSONObject().append("message", template);
        return result;
    }

}
