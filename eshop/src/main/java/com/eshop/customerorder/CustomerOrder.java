package com.eshop.customerorder;

import com.eshop.cart.Cart;
import com.eshop.cart.CartItem;
import com.eshop.customer.Customer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author https://github.com/steshenkoma
 */
public class CustomerOrder {

    private int id;
    private String generatedDate;
    private int confirmationNumber;
    private Customer customer;
    private Cart cart;

    public CustomerOrder(Customer customer, Cart cart) {
        this.customer = customer;
        this.cart = cart;
        this.confirmationNumber = generateRandom();
        this.generatedDate = generateDate();
    }

    public void setId(int id) {
        this.id = id;
    }

    private int generateRandom() {
        Random random = new Random();
        int i = random.nextInt(999999999);
        return i;
    }

    private String generateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
        Date currDate = new Date(System.currentTimeMillis());
        String date = sdf.format(currDate);
        return date;
    }

    public String getGeneratedDate() {
        return generatedDate;
    }

    public int getConfirmationNumber() {
        return confirmationNumber;
    }

    public double getAmount() {
        return cart.getSubtotal();
    }

    public int getCustomerId() {
        return customer.getId();
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Cart getCart() {
        return cart;
    }

    public JSONObject getOrderSummary() {

        JSONObject result = new JSONObject();

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
        result.put("totalItemsAmount", cart.getNumberOfItems());
        result.put("subtotalPrice", cart.getSubtotal());
        result.put("confirmationNumber", confirmationNumber);
        result.put("date", generatedDate);
        return result;
    }

}
