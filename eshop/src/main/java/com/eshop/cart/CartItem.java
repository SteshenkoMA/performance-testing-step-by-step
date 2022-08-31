package com.eshop.cart;

import com.eshop.product.Product;

import java.math.BigDecimal;

/**
 * @author https://github.com/steshenkoma
 */
public class CartItem {

    Product product;
    short quantity;

    public CartItem(Product product) {
        this.product = product;
        quantity = 1;
    }

    public Product getProduct() {
        return product;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public double getTotal() {
        double amount = (this.getQuantity() * product.getPrice());
        BigDecimal b = new BigDecimal(amount);
        amount = b.setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
        return amount;
    }
}
