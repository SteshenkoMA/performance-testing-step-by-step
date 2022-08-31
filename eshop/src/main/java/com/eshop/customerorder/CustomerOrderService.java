package com.eshop.customerorder;

import com.eshop.database.DatabaseConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author https://github.com/steshenkoma
 */
public class CustomerOrderService {

    private static String INSERT_QUERY = "INSERT INTO customer_order (amount, date_created, confirmation_number, customer_id) VALUES (?,?,?,?)";
    private static String SELECT_BY_UNIQUE_QUERY = "SELECT * FROM customer_order WHERE date_created=? "
            + "AND confirmation_number=? "
            + "AND customer_id=?";
    private static final Logger logger = LogManager.getLogger(CustomerOrderService.class);


    public boolean addToDB(CustomerOrder customerOrder) throws SQLException {
        boolean result;
        try (Connection c = DatabaseConnector.getInstance().getConnection()) {
            PreparedStatement prs = c.prepareStatement(INSERT_QUERY);
            c.setAutoCommit(false);
            prs.setDouble(1, customerOrder.getAmount());
            prs.setString(2, customerOrder.getGeneratedDate());
            prs.setInt(3, customerOrder.getConfirmationNumber());
            prs.setInt(4, customerOrder.getCustomerId());

            prs.executeUpdate();

            prs.close();
            c.commit();

            prs = c.prepareStatement(SELECT_BY_UNIQUE_QUERY);
            prs.setString(1, customerOrder.getGeneratedDate());
            prs.setInt(2, customerOrder.getConfirmationNumber());
            prs.setInt(3, customerOrder.getCustomerId());
            ResultSet rs = prs.executeQuery();
            if (rs.next()) {
                customerOrder.setId(rs.getInt("id"));
                result = true;
            } else {
                result = false;
                logger.error("Sustomer was not created");
            }
            rs.close();
            prs.close();
        }
        result = true;
        return result;
    }

}
