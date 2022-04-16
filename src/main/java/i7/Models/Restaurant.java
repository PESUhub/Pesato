package i7.Models;

import i7.Controllers.Genie;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class Restaurant extends User {
    private String cuisine;
    private int rating;
    private Map<String, MenuItem> menuItems = new HashMap<>();

    public Restaurant(String username, String password, String email, String phone, String address, double balance, String cuisine, int rating) {
        super(UserType.RESTAURANT, username, password, email, phone, address, balance);
        this.cuisine = cuisine;
        this.rating = rating;
    }

    public Restaurant(Document document) {
        super(document);
        this.cuisine = document.getString("cuisine");
        this.rating = document.getInteger("rating");
        try {
            document.get("menuItems", Map.class).forEach((k, v) -> menuItems.put((String) k, new MenuItem((Document) v)));
        } catch (Exception e) {
            Genie g = Genie.getInstance();
            g.showPopup("Error", "Error loading menu items. Contact Dev.", "close");
        }
    }

    public Map<String, Object> toDocument() {
        Map<String, Object> document = super.toDocument();
        document.put("cuisine", cuisine);
        document.put("rating", rating);

        Map<String, Map<String, Object>> menuItemsDocuments = new HashMap<>();
        for (Map.Entry<String, MenuItem> entry : menuItems.entrySet()) {
            menuItemsDocuments.put(entry.getKey(), entry.getValue().toDocument());
        }
        document.put("menuItems", menuItemsDocuments);

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

    public Map<String, MenuItem> getMenuItems() {
        return menuItems;
    }

    private void updateMenuItemsDocuments() {
        Genie g = Genie.getInstance();
        Map<String, Map<String, Object>> menuItemsDocuments = new HashMap<>();
        for (Map.Entry<String, MenuItem> entry : menuItems.entrySet()) {
            menuItemsDocuments.put(entry.getKey(), entry.getValue().toDocument());
        }
        g.updateMenuItemsInRestaurant(menuItemsDocuments, this.username);
    }

    public void addMenuItem(String name, String description, double price, int rating, Boolean isVeg) {
        if (menuItems.containsKey(name)) {
            Genie g = Genie.getInstance();
            g.showPopup("Error", "Menu item already exists", "close");
        } else {
            MenuItem item = new MenuItem(name, description, price, rating, isVeg);
            menuItems.put(name, item);
            updateMenuItemsDocuments();
            //g.insertMenuItemInRestaurant(item, this.username);

        }
    }

    public void removeMenuItem(String name) {
        if (menuItems.containsKey(name)) {
            menuItems.remove(name);
            updateMenuItemsDocuments();
        }
    }
}
