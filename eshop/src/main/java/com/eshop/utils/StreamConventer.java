package com.eshop.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author https://github.com/steshenkoma
 */
public class StreamConventer {

    public static JSONObject getJsonFromInStream(InputStream inputStream) throws IOException, JSONException {
        BufferedReader bR = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";

        StringBuilder responseStrBuilder = new StringBuilder();
        while ((line = bR.readLine()) != null) {
            responseStrBuilder.append(line);
        }
        inputStream.close();

        return new JSONObject(responseStrBuilder.toString());
    }

}
