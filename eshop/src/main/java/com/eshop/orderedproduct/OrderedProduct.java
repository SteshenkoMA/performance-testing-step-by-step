package com.eshop.orderedproduct;

import com.eshop.cart.Cart;
import com.eshop.cart.CartItem;
import com.eshop.customerorder.CustomerOrder;

import java.util.List;

/**
 * @author https://github.com/steshenkoma
 */
public class OrderedProduct {

    private CustomerOrder customerorder;
    private Cart cart;

    public OrderedProduct(CustomerOrder customerorder, Cart cart) {

        this.customerorder = customerorder;
        this.cart = cart;
    }

    public int getCustomerOrderId() {
        return customerorder.getId();
    }

    public List<CartItem> getCartItemCollection() {
        return cart.getItems();
    }
}
