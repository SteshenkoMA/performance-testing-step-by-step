package com.eshop.orderedproduct;

import com.eshop.cart.CartItem;
import com.eshop.database.DatabaseConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 * @author https://github.com/steshenkoma
 */
public class OrderedProductService {

    private static String INSERT_QUERY = "INSERT INTO ordered_product (customer_order_id, product_id, quantity) VALUES (?,?,?)";
    private static final Logger logger = LogManager.getLogger(OrderedProductService.class);
    
    public boolean addToDB(OrderedProduct orderedProduct) throws SQLException {

        int customerOrderId = orderedProduct.getCustomerOrderId();
        List<CartItem> items = orderedProduct.getCartItemCollection();

        boolean result;
        try (Connection c = DatabaseConnector.getInstance().getConnection()) {
            PreparedStatement prs = c.prepareStatement(INSERT_QUERY);
            c.setAutoCommit(false);

            for (int i = 0; i < items.size(); i++) {

                int productId = items.get(i).getProduct().getId();
                int quantity = items.get(i).getQuantity();

                prs.setInt(1, customerOrderId);
                prs.setInt(2, productId);
                prs.setInt(3, quantity);

                prs.executeUpdate();
            }
            prs.close();
            c.commit();
            result = true;
        }catch (SQLException ex){
            logger.error("SQL insert failed {}", ex);
            result = false;
        }
        
        return result;
    }

}
