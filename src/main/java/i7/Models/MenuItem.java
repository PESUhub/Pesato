package i7.Models;

import org.bson.Document;

import java.util.Map;

public class MenuItem {
    private int id;
    private String restaurantName;
    private String name;
    private String description;
    private Double price;
    private int rating;
    private Boolean veg;

    public MenuItem(int id, String restaurantName, String name, String description, Double price, int rating, Boolean veg) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.veg = veg;
    }

    public MenuItem(Document document) {
        this.id = document.getInteger("id");
        this.restaurantName = document.getString("restaurantName");
        this.name = document.getString("name");
        this.description = document.getString("description");
        this.price = document.getDouble("price");
        this.rating = document.getInteger("rating");
        this.veg = document.getBoolean("veg");
    }

    public Map<String, Object> toDocument() {
        Map<String, Object> menuItem = new java.util.HashMap<>();
        menuItem.put("id", this.id);
        menuItem.put("restaurantName", this.restaurantName);
        menuItem.put("name", this.name);
        menuItem.put("description", this.description);
        menuItem.put("price", this.price);
        menuItem.put("rating", this.rating);
        menuItem.put("veg", this.veg);
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
        return veg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setVeg(Boolean veg) {
        this.veg = veg;
    }
}
