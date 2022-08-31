package com.eshop.checkout;

import com.eshop.cart.Cart;
import com.eshop.customer.Customer;
import com.eshop.customer.CustomerService;
import com.eshop.customerorder.CustomerOrder;
import com.eshop.customerorder.CustomerOrderService;
import com.eshop.orderedproduct.OrderedProduct;
import com.eshop.orderedproduct.OrderedProductService;
import com.eshop.session.Session;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.logging.Level;

/**
 * @author https://github.com/steshenkoma
 */
public class CheckoutService {

    private CustomerService customerService;
    private CustomerOrderService customerOrderService;
    private OrderedProductService orderedProductService;

    public CheckoutService(CustomerService customerService,
                           CustomerOrderService customerOrderService,
                           OrderedProductService orderedProductService) {
        this.customerService = customerService;
        this.customerOrderService = customerOrderService;
        this.orderedProductService = orderedProductService;
    }

    public JSONObject submitPurchase(JSONObject json, Session session) {

        JSONObject result;
        try {
            Customer customer = new Customer(json);
            Cart cart = (Cart) session.getAttribute("cart");

            CustomerOrder customerOrder = new CustomerOrder(customer, cart);
            OrderedProduct orderedProduct = new OrderedProduct(customerOrder, cart);
            customerService.addToDB(customer);
            customerOrderService.addToDB(customerOrder);
            orderedProductService.addToDB(orderedProduct);

            result = getConfirmationInformation(customerOrder);

        } catch (SQLException ex) {
            result = null;
            java.util.logging.Logger.getLogger(CheckoutService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public JSONObject getConfirmationInformation(CustomerOrder customerOrder) {
        JSONObject result = new JSONObject();
        JSONObject customer = customerOrder.getCustomer().toJSONObject();
        JSONObject order = customerOrder.getOrderSummary();
        result.put("customer", customer);
        result.put("order", order);
        return result;
    }

}
