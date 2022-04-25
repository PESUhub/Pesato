package i7.Models;

import i7.Controllers.Genie;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Cart {
    private int id;
    private String custname;
    private String restaurantSelected;
    private ObservableList<CartItem> cartItems;

    public Cart(String custname, String restaurantSelected) {
        Genie g = Genie.getInstance();
        this.id = g.getNextCartId();
        this.custname = custname;
        this.restaurantSelected = restaurantSelected;
        cartItems = FXCollections.observableArrayList();
    }

    public Cart(Document document) {
        Genie g = Genie.getInstance();
        this.id = document.getInteger("id");
        this.custname = document.getString("custname");
        this.restaurantSelected = document.getString("restaurantSelected");

        cartItems = FXCollections.observableArrayList();
        try {
            document.get("cartItems", Document.class).forEach((k, v) -> {
                CartItem cartItem = new CartItem(g.getMenuItem(Integer.parseInt(k)));
                cartItem.setQuantity((Integer)v);
                cartItems.add(cartItem);
            });
        } catch (Exception e) {
            g.showPopup("Error", "Error loading cart menu items. Contact Dev.", "close");
        }
    }

    public Map<String,Object> toDocument() {
        Map<String,Object> document = new HashMap<>();
        document.put("id", id);
        document.put("custname", custname);
        document.put("restaurantSelected", restaurantSelected);

        Map<String, Object> cartItemsDocuments = new HashMap<>();
        cartItems.forEach(item -> cartItemsDocuments.put(Integer.toString(item.getItem().getId()), item.getQuantity()));

        document.put("cartItems", cartItemsDocuments);
        return document;
    }

    public String getRestaurantSelected() {
        return restaurantSelected;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public void addItems(ObservableList<MenuItem> items) {
        items.forEach(item -> {
            CartItem cartItem = new CartItem(item);
            cartItems.add(cartItem);
        });
    }

    public ObservableList<CartItem> getCartItemsObservableList() {
        return cartItems;
    }

    public int getId() {
        return id;
    }

    public String getCustname() {
        return custname;
    }
}
