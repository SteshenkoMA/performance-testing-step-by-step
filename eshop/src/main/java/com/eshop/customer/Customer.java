package com.eshop.customer;

import org.json.JSONObject;

/**
 * @author https://github.com/steshenkoma
 */
public class Customer {

    private int id;

    private String name;
    private String email;
    private String phone;
    private String address;
    private String ccNumber;

    public Customer(JSONObject json) {
        this.name = json.getString("name");
        this.email = json.getString("email");
        this.phone = json.getString("phone");
        this.address = json.getString("address");
        this.ccNumber = json.getString("ccNumber");
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddrress() {
        return address;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public JSONObject toJSONObject() {
        JSONObject result = new JSONObject();
        result.put("name", name);
        result.put("address", address);
        result.put("email", email);
        result.put("phone", phone);
        return result;
    }

}