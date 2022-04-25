package i7.Models;

import i7.Controllers.Genie;
import org.bson.Document;

import java.util.Map;

public class Customer extends User {

    private Cart currentCart;

    public Customer(String username, String password, String email, String phone, String address, double balance) {
        super(UserType.CUSTOMER, username, password, email, phone, address, balance);
    }

    public Customer(Document userMap) {
        super(userMap);
        Genie g = Genie.getInstance();
    }

    public Map<String, Object> toDocument() {
        Map<String, Object> document = super.toDocument();
        return document;
    }

    public Cart getCurrentCart() {
        return currentCart;
    }

    public void initCart(String restaurantSelected) {
        this.currentCart = new Cart(this.getUsername(), restaurantSelected);
    }
}
