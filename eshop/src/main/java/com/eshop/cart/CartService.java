package com.eshop.cart;

import com.eshop.product.Product;
import com.eshop.session.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * @author https://github.com/steshenkoma
 */
public class CartService {

    public CartService() {
    }

    public int addItemToSessionCart(Product product, Session session) {

        int result = 0;
        Cart cart = (Cart) session.getAttribute("cart");
        int numberOfItemsInCartBefore = cart.getNumberOfItems();
        cart.addItem(product);

        int numberOfItemsInCartAfter = cart.getNumberOfItems();
        if (numberOfItemsInCartAfter > numberOfItemsInCartBefore) {
            result = numberOfItemsInCartAfter;
        }

        return result;

    }

    public JSONObject getCartInformation(Session session) {
        JSONObject result = new JSONObject();
        Cart cart = (Cart) session.getAttribute("cart");
        List<CartItem> items = cart.getItems();

        JSONArray itemsArray = new JSONArray();

        for (CartItem e : items) {
            JSONObject itemObj = new JSONObject();

            JSONObject productObj = e.getProduct().toJSONObject();
            int quantity = e.getQuantity();
            itemObj.put("product", productObj);
            itemObj.put("quantity", quantity);
            itemObj.put("totalPrice", e.getTotal());

            itemsArray.put(itemObj);
        }

        result.put("cartItems", itemsArray);
        result.put("subtotalPrice", cart.getSubtotal());
        result.put("itemsInCart", cart.getNumberOfItems());

        return result;
    }

    public void updateQuantity(Product product, int quantity, Session session) {

        String q = quantity + "";
        Cart cart = (Cart) session.getAttribute("cart");
        cart.update(product, q);

    }
}
