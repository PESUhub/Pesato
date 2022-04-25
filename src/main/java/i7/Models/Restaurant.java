package i7.Models;

import i7.Controllers.Genie;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Restaurant extends User {
    private String cuisine;
    private int rating;
    private Genie genie;
    private ObservableList<MenuItem> menuItems;

    public Restaurant(String username, String password, String email, String phone, String address, double balance, String cuisine, int rating) {
        super(UserType.RESTAURANT, username, password, email, phone, address, balance);
        this.cuisine = cuisine;
        this.rating = rating;
    }

    public Restaurant(Document document) {
        super(document);
        this.cuisine = document.getString("cuisine");
        this.rating = document.getInteger("rating");
        this.menuItems = FXCollections.observableArrayList();
        this.genie = Genie.getInstance();

        updateMenuItems();
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

    private void updateMenuItems() {
        menuItems.clear();
        menuItems.addAll((genie.getMenuItemsFromRestaurant(this.getUsername())));
    }

    public ObservableList<MenuItem> getMenuItemsObservableList() {
        return menuItems;
    }

    public Boolean addMenuItem(MenuItem menuItem) {
        Boolean result = genie.addMenuItem(menuItem);
        updateMenuItems();
        return result;
    }

    public Boolean removeMenuItem(MenuItem menuItem) {
        Boolean result = genie.removeMenuItem(menuItem.getId());
        updateMenuItems();
        return result;
    }
}
