package i7.Models;

import i7.Controllers.Genie;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Restaurant extends User {
    private String cuisine;
    private int rating;
    private ArrayList<MenuItem> menuItems;

    public Restaurant(String username, String password, String email, String phone, String address, double balance, String cuisine, int rating) {
        super(UserType.RESTAURANT, username, password, email, phone, address, balance);
        this.cuisine = cuisine;
        this.rating = rating;
        this.menuItems = new ArrayList<>();
    }

    public Restaurant(Document document) {
        super(document);
        this.cuisine = document.getString("cuisine");
        this.rating = document.getInteger("rating");
        Genie g = Genie.getInstance();
        this.menuItems = g.getMenuItemsFromRestaurant(this.getUsername());
    }

    public Map<String, Object> toDocument() {
        Map<String, Object> document = super.toDocument();
        document.put("cuisine", cuisine);
        document.put("rating", rating);
        return document;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }
}
