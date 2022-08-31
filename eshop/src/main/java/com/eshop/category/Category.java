package com.eshop.category;

import com.eshop.product.Product;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author https://github.com/steshenkoma
 */
public class Category {

    private final int ID;
    private final String NAME;
    private Map<Integer, Product> categoryProducts;

    public Category(int id, String name) {
        this.ID = id;
        this.NAME = name;
    }

    public int getId() {
        return ID;
    }

    public String getName() {
        return NAME;
    }

    public Map<Integer, Product> getCategoryProducts() {
        return categoryProducts;
    }

    public void setCategoryProducts(Map<Integer, Product> categoryProducts) {
        this.categoryProducts = categoryProducts;
    }

    @Override
    public String toString() {
        return "{id=" + ID + ", name=" + NAME + "}";
    }

    public JSONObject toJSONObject() {
        JSONObject result = new JSONObject();
        result.put("id", ID);
        result.put("name", NAME);
        return result;
    }

}
