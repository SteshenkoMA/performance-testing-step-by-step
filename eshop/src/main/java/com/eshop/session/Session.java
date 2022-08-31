package com.eshop.session;

import com.eshop.cart.Cart;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author https://github.com/steshenkoma
 */
public class Session {

    private final String SID;
    private Map<String, Object> attributes;

    public Session() {
        this.SID = buildUID();
        this.attributes = new ConcurrentHashMap<>();
        addAttribute("cart", new Cart());
    }

    private String buildUID() {
        UUID result = UUID.randomUUID();
        return result.toString();
    }

    public String getSID() {
        return SID;
    }

    public void addAttribute(String attributeName, Object attributeEntity) {
        attributes.put(attributeName, attributeEntity);
    }

    public Object getAttribute(String attributeName) {
        Object attributeEntity = attributes.get(attributeName);
        return attributeEntity;
    }

}
