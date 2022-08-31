package com.eshop.product;

import com.eshop.category.Category;
import org.json.JSONObject;

/**
 * @author https://github.com/steshenkoma
 */
public class Product {

    private final int ID;
    private final String NAME;
    private final double PRICE;
    private final String DESCRIPTION;
    private Category category;

    public Product(int id,
                   String name,
                   double price,
                   String description,
                   Category category) {
        this.ID = id;
        this.NAME = name;
        this.PRICE = price;
        this.DESCRIPTION = description;
        this.category = category;
    }

    @Override
    public String toString() {
        return "{"
                + "id = " + ID
                + ", name = " + NAME
                + ", price = " + PRICE
                + ", description = " + DESCRIPTION
                + ", category = " + category
                + "}";
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return ID;
    }

    public String getName() {
        return NAME;
    }

    public double getPrice() {
        return PRICE;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    public Category getCategory() {
        return category;
    }

    public JSONObject toJSONObject() {
        JSONObject result = new JSONObject();
        result.put("id", ID);
        result.put("name", NAME);
        result.put("price", PRICE);
        result.put("description", DESCRIPTION);
        return result;
    }
}
