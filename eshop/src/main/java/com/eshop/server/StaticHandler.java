package com.eshop.server;

import com.eshop.session.Session;
import com.eshop.session.SessionService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author https://github.com/steshenkoma
 */
public class StaticHandler implements HttpHandler {

    private static String path;
    private SessionService sessionService;

    public StaticHandler(String path, SessionService sessionService) {
        this.path = path;
        this.sessionService = sessionService;
    }

    private static final Logger logger = LogManager.getLogger(StaticHandler.class);

    public void handle(HttpExchange t) throws IOException{
        try {

            String initPath = "web";
            String uri = t.getRequestURI().getPath().substring(path.length());
            String filePath = initPath + uri;

            List<String> req_cookies = null;

            if (t.getRequestHeaders().containsKey("Cookie")) {
                req_cookies = t.getRequestHeaders().get("Cookie");

                String SID = parseSessionID(req_cookies);
                if (SID != null) {
                    if (sessionService.getSession(SID) != null) {

                    } else {
                        req_cookies = null;
                        logger.debug("Session is old");
                    }
                } else {
                    req_cookies = null;
                    logger.debug("Cookies have no sid");
                }
            }

            if (uri.equals("")) {
                filePath = initPath + "/index.html";
            }

            switch (filePath) {
                case ("web/category"):
                    filePath = initPath + "/category.html";
                    break;
                case ("web/cart"):
                    filePath = initPath + "/cart.html";
                    break;
                case ("web/checkout"):
                    filePath = initPath + "/checkout.html";
                    break;
                case ("web/confirmation"):
                    filePath = initPath + "/confirmation.html";
                    break;
                case ("web/index"):
                    filePath = initPath + "/index.html";
                    break;
            }

            ClassLoader classLoader = getClass().getClassLoader();
            InputStream in = classLoader.getResourceAsStream(filePath);

            if (in == null) {
                String response = "404 (Not Found)\n";
                t.sendResponseHeaders(404, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                switch (filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase()) {
                    case "js":
                        t.getResponseHeaders().add("Content-Type", "application/javascript;charset=utf-8");
                        break;
                    case "css":
                        t.getResponseHeaders().add("Content-Type", "text/css;charset=utf-8");
                        break;
                    case "html":
                        t.getResponseHeaders().add("Content-Type", "text/html;charset=utf-8");
                        break;
                    default:
                        t.getResponseHeaders().add("Content-Type", "text/plain;charset=utf-8");
                        break;
                }
                OutputStream os = t.getResponseBody();

                if (req_cookies == null && filePath.equals("web/index.html")) {

                    List<String> res_cookies = new ArrayList<>();

                    Session session = new Session();
                    sessionService.addSession(session);
                    res_cookies.add("sid=" + session.getSID() + "; Max-Age=3600; HttpOnly; Path=/;");
                    t.getResponseHeaders().put("Set-Cookie", res_cookies);

                }
                t.sendResponseHeaders(200, 0);

                final byte[] buffer = new byte[0x10000];
                int count;
                while ((count = in.read(buffer)) >= 0) {
                    os.write(buffer, 0, count);
                }
                in.close();

                os.close();
            }
        } catch (Exception ex) {
            logger.error("Unknown error: {}", ex);
        }
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

}
