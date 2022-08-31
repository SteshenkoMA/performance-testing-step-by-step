package com.eshop.server;

import com.eshop.cart.CartHandler;
import com.eshop.cart.CartService;
import com.eshop.category.CategoryHandler;
import com.eshop.category.CategoryService;
import com.eshop.checkout.CheckoutHandler;
import com.eshop.checkout.CheckoutService;
import com.eshop.customer.CustomerService;
import com.eshop.customerorder.CustomerOrderService;
import com.eshop.orderedproduct.OrderedProductService;
import com.eshop.product.ProductService;
import com.eshop.session.SessionService;
import com.eshop.utils.ConfigLoader;
import com.sun.net.httpserver.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

/**
 * @author https://github.com/steshenkoma
 */
public class Server {

    private static final Logger LOGGER = LogManager.getLogger(Server.class);

    private static int port;

    private static final String INDEX = "/eshop";

    private static final String CONTROL = INDEX + "/control";
    private static final String CATEGORY = CONTROL + "/category";
    private static final String CART = CONTROL + "/cart";
    private static final String CHECKOUT = CONTROL + "/checkout";

    private static CategoryService categoryService;
    private static SessionService sessionService;
    private static CartService cartService;
    private static ProductService productService;
    private static CheckoutService checkoutService;
    private static CustomerService customerService;
    private static CustomerOrderService customerOrderService;
    private static OrderedProductService orderedProductService;

    public Server() {
        init();
    }

    private static void init() {
        try {
            port = Integer.parseInt(ConfigLoader.properties.getProperty("port"));
            categoryService = new CategoryService();
            sessionService = new SessionService();
            productService = new ProductService();
            cartService = new CartService();
            customerService = new CustomerService();
            customerOrderService = new CustomerOrderService();
            orderedProductService = new OrderedProductService();
            checkoutService = new CheckoutService(customerService, customerOrderService, orderedProductService);
        } catch (SQLException e) {
            LOGGER.fatal("Database connection error", e);
        }
    }

    public void start() {

        try {
            if (port != 0) {
                HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
                server.createContext(INDEX, new StaticHandler(INDEX, sessionService));
                server.createContext(CATEGORY, new CategoryHandler(CATEGORY, categoryService));
                server.createContext(CART, new CartHandler(CART, cartService, sessionService, productService));
                server.createContext(CHECKOUT, new CheckoutHandler(CHECKOUT, checkoutService, sessionService, cartService));

                server.setExecutor(null);
                server.start();
                LOGGER.info("HTTP Server started at port {}", port);
            }
        } catch (IOException ex) {
            LOGGER.fatal("Unnable start the server. Check that this port is available for use {}", port, ex);
        }
    }

}
