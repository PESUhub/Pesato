package i7.Models;

import org.bson.Document;

import java.util.Map;

public class Customer extends User {

    private Cart currentCart;

    public Customer(String username, String password, String email, String phone, String address, double balance) {
        super(UserType.CUSTOMER, username, password, email, phone, address, balance);
    }

    public Customer(Document userMap) {
        super(userMap);
    }

    public Map<String, Object> toDocument() {
        Map<String, Object> document = super.toDocument();
        document.put("currentCart", currentCart.toDocument());
        return document;
    }
}
