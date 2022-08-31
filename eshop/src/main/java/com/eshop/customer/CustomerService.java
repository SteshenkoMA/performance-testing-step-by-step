package com.eshop.customer;

import com.eshop.database.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author https://github.com/steshenkoma
 */
public class CustomerService {

    private static String INSERT_QUERY = "INSERT INTO customer (name, email, phone, address, cc_number) VALUES (?,?,?,?,?)";
    private static String SELECT_BY_UNIQUE_QUERY = "SELECT * FROM customer WHERE name=? "
            + "AND email=? "
            + "AND phone=? "
            + "AND address=?"
            + "AND cc_number=?";

    public CustomerService() {
    }

    public boolean addToDB(final Customer customer) throws SQLException {
        boolean result;
        try (Connection c = DatabaseConnector.getInstance().getConnection()) {
            PreparedStatement prs = c.prepareStatement(INSERT_QUERY);
            c.setAutoCommit(false);
            prs.setString(1, customer.getName());
            prs.setString(2, customer.getEmail());
            prs.setString(3, customer.getPhone());
            prs.setString(4, customer.getAddrress());
            prs.setString(5, customer.getCcNumber());

            prs.executeUpdate();
            prs.close();
            c.commit();

            prs = c.prepareStatement(SELECT_BY_UNIQUE_QUERY);
            prs.setString(1, customer.getName());
            prs.setString(2, customer.getEmail());
            prs.setString(3, customer.getPhone());
            prs.setString(4, customer.getAddrress());
            prs.setString(5, customer.getCcNumber());
            ResultSet rs = prs.executeQuery();
            if (rs.next()) {
                customer.setId(rs.getInt("id"));
                result = true;
            } else {
                result = false;
            }
            rs.close();
            prs.close();
        }

        return result;
    }

}
