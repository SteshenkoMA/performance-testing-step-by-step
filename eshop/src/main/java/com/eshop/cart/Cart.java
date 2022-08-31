package com.eshop.cart;

import com.eshop.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author https://github.com/steshenkoma
 */
public class Cart {

    List<CartItem> items;
    int numberOfItems;

    public Cart() {
        items = new ArrayList<>();
        numberOfItems = 0;
    }

    public double getSubtotal() {

        double amount = 0;

        for (CartItem scItem : items) {

            Product product = scItem.getProduct();
            amount += (scItem.getQuantity() * product.getPrice());
        }
        BigDecimal b = new BigDecimal(amount);
        amount = b.setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
        return amount;
    }

    public synchronized void addItem(Product product) {

        boolean newItem = true;

        for (CartItem cItem : items) {

            if (cItem.getProduct().getId() == product.getId()) {

                newItem = false;
                cItem.incrementQuantity();
            }
        }

        if (newItem) {
            CartItem cItem = new CartItem(product);
            items.add(cItem);
        }
    }

    public synchronized void update(Product product, String quantity) {

        short qty = Short.parseShort(quantity);

        if (qty >= 0) {

            CartItem item = null;

            for (CartItem scItem : items) {

                if (scItem.getProduct().getId() == product.getId()) {

                    if (qty != 0) {
                        scItem.setQuantity(qty);
                    } else {
                        item = scItem;
                        break;
                    }
                }
            }

            if (item != null) {
                items.remove(item);
            }
        }
    }

    public List<CartItem> getItems() {
        return items;
    }

    public synchronized int getNumberOfItems() {

        numberOfItems = 0;

        for (CartItem scItem : items) {
            numberOfItems += scItem.getQuantity();
        }

        return numberOfItems;
    }

}