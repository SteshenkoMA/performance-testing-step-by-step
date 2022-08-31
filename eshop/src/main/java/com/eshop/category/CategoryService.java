package com.eshop.category;

import com.eshop.database.DatabaseConnector;
import com.eshop.product.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author https://github.com/steshenkoma
 */
public class CategoryService {

    private static String SELECT_QUERY = "SELECT * FROM category";
    private static String SELECT_CATEGORY_PRODUCTS = "SELECT * FROM product WHERE category_id=?";

    private Map<Integer, Category> categories;

    private static final Logger logger = LogManager.getLogger(CategoryService.class);

    public CategoryService() throws SQLException {
        try {
            categories = readFromDB();
        } catch (SQLException ex) {
            categories = null;
            logger.error("Could not read categories from DB {}", ex);
            throw ex;
        }
    }

    private Map<Integer, Category> readFromDB() throws SQLException {
        Map<Integer, Category> categoryMap = new ConcurrentHashMap<>();
        try (Connection c = DatabaseConnector.getInstance().getConnection();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_QUERY)) {
            while (rs.next()) {
                int categoryId = rs.getInt("id");
                String categoryName = rs.getString("name");
                Category category = new Category(categoryId, categoryName);
                categoryMap.put(categoryId, category);
            }
        }

        for (Map.Entry<Integer, Category> entry : categoryMap.entrySet()) {
            Category category = entry.getValue();
            Map<Integer, Product> categoryProducts = readCategoryProductsFromDB(category);
            category.setCategoryProducts(categoryProducts);
        }

        return categoryMap;
    }

    private Map<Integer, Product> readCategoryProductsFromDB(Category category) throws SQLException {

        Map<Integer, Product> categoryProducts = new ConcurrentHashMap<>();

        try (Connection c = DatabaseConnector.getInstance().getConnection();
             PreparedStatement prs = c.prepareStatement(SELECT_CATEGORY_PRODUCTS)) {
            prs.setInt(1, category.getId());

            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");

                Product product;
                if (category.getId() == rs.getInt("category_id")) {
                    product = new Product(id,
                            name,
                            price,
                            description,
                            category);
                } else {
                    product = null;
                }

                categoryProducts.put(id, product);
            }
        }

        return categoryProducts;
    }

    public void refresh() throws SQLException {
        categories = readFromDB();
    }

    public Map<Integer, Category> getCategories() {
        return categories;
    }

    public Category getSelectedCategory(Integer id) {
        Category result = categories.get(id);
        return result;
    }

}
