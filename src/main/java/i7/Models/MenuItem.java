package i7.Models;

import i7.Controllers.Genie;
import org.bson.Document;

import java.util.Map;

public class MenuItem {
    private int id;
    private String restaurantName;
    private String name;
    private String description;
    private double price;
    private int rating;
    private Boolean Veg;

    public MenuItem(String restaurantName, String name, String description, Double price, int rating, Boolean veg) {
        Genie g = Genie.getInstance();
        this.id = g.getNextMenuItemId();
        this.restaurantName = restaurantName;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.Veg = veg;
    }

    public MenuItem(Document document) {
        this.id = document.getInteger("id");
        this.restaurantName = document.getString("restaurantName");
        this.name = document.getString("name");
        this.description = document.getString("description");
        this.price = document.getDouble("price");
        this.rating = document.getInteger("rating");
        this.Veg = document.getBoolean("veg");
    }

    public Map<String, Object> toDocument() {
        Map<String, Object> menuItem = new java.util.HashMap<>();
        menuItem.put("id", this.id);
        menuItem.put("restaurantName", this.restaurantName);
        menuItem.put("name", this.name);
        menuItem.put("description", this.description);
        menuItem.put("price", this.price);
        menuItem.put("rating", this.rating);
        menuItem.put("veg", this.Veg);
        return menuItem;
    }

    public int getId() {
        return id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public int getRating() {
        return rating;
    }

    public Boolean getVeg() {
        return Veg;
    }

    private void updateMenuItem() {
        Genie g = Genie.getInstance();
        g.updateMenuItem(this);
    }

    public void setName(String name) {
        this.name = name;
        updateMenuItem();
    }

    public void setDescription(String description) {
        this.description = description;
        updateMenuItem();
    }

    public void setPrice(Double price) {
        this.price = price;
        updateMenuItem();
    }

    public void setRating(int rating) {
        this.rating = rating;
        updateMenuItem();
    }

    public void setVeg(Boolean veg) {
        this.Veg = veg;
        updateMenuItem();
    }
}
