package i7.Models;

import i7.Controllers.Genie;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.bson.Document;

import java.util.Map;

public class MenuItem {
    private int id;
    private String restaurantName;
    private ObjectProperty<String> name = new SimpleObjectProperty<>();
    private ObjectProperty<String> description = new SimpleObjectProperty<>();
    private ObjectProperty<Double> price = new SimpleObjectProperty<>();
    private ObjectProperty<Integer> rating = new SimpleObjectProperty<>();
    private ObjectProperty<Boolean> Veg = new SimpleObjectProperty<>();

    public MenuItem(String restaurantName, String name, String description, Double price, int rating, Boolean veg) {
        Genie g = Genie.getInstance();
        this.id = g.getNextMenuItemId();
        this.restaurantName = restaurantName;
        this.name.set(name);
        this.description.set(description);
        this.price.set(price);
        this.rating.set(rating);
        this.Veg.set(veg);
    }

    public MenuItem(Document document) {
        this.id = document.getInteger("id");
        this.restaurantName = document.getString("restaurantName");
        this.name.set(document.getString("name"));
        this.description.set(document.getString("description"));
        this.price.set(document.getDouble("price"));
        this.rating.set(document.getInteger("rating"));
        this.Veg.set(document.getBoolean("veg"));
    }

    public Map<String, Object> toDocument() {
        Map<String, Object> menuItem = new java.util.HashMap<>();
        menuItem.put("id", this.id);
        menuItem.put("restaurantName", this.restaurantName);
        menuItem.put("name", this.name.get());
        menuItem.put("description", this.description.get());
        menuItem.put("price", this.price.get());
        menuItem.put("rating", this.rating.get());
        menuItem.put("veg", this.Veg.get());
        return menuItem;
    }

    public int getId() {
        return id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getName() {
        return name.get();
    }

    public ObjectProperty<String> nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
        updateMenuItem();
    }

    public String getDescription() {
        return description.get();
    }

    public ObjectProperty<String> descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
        updateMenuItem();
    }

    public double getPrice() {
        return price.get();
    }

    public ObjectProperty<Double> priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
        updateMenuItem();
    }

    public int getRating() {
        return rating.get();
    }

    public ObjectProperty<Integer> ratingProperty() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating.set(rating);
        updateMenuItem();
    }

    public Boolean getVeg() {
        return Veg.get();
    }

    public ObjectProperty<Boolean> vegProperty() {
        return Veg;
    }

    public void setVeg(Boolean veg) {
        this.Veg.set(veg);
        updateMenuItem();
    }

    private void updateMenuItem() {
        Genie g = Genie.getInstance();
        g.updateMenuItem(this);
    }
}
