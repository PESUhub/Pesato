package i7.Models;

import i7.Controllers.Genie;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class CartItem {
    private int quantity;
    private MenuItem item;

    public CartItem(MenuItem item) {
        this.quantity = 0;
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public MenuItem getItem() {
        return item;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void removeQuantity(int quantity) {
        this.quantity -= quantity;
    }
}


public class Cart {
    private int id;
    private String custname;
    private String restaurantSelected;
    private Map<Integer, CartItem> cartItems;

    public Cart(String custname, String restaurantSelected) {
        Genie g = Genie.getInstance();
        this.id = g.getNextCartId();
        this.custname = custname;
        this.restaurantSelected = restaurantSelected;
    }

    public Cart(Document document) {
        Genie g = Genie.getInstance();
        this.id = document.getInteger("id");
        this.custname = document.getString("custname");
        this.restaurantSelected = document.getString("restaurantSelected");

        cartItems = new HashMap<>();
        try {
            document.get("cartItems", Map.class).forEach((k, v) -> {
                CartItem cartItem = new CartItem(g.getMenuItem((Integer)k));
                cartItem.addQuantity((Integer)v);
                cartItems.put((Integer) k, cartItem);
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

        Map<Integer, Integer> cartItemsDocuments = new HashMap<>();
        for (Map.Entry<Integer, CartItem> entry : cartItems.entrySet()) {
            cartItemsDocuments.put(entry.getKey(), entry.getValue().getQuantity());
        }

        document.put("cartItems", cartItemsDocuments);
        return document;
    }

    public String getRestaurantSelected() {
        return restaurantSelected;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public Integer getItemQuantity(Integer itemId) {
        return cartItems.get(itemId).getQuantity();
    }

    public void addItem(MenuItem item) {
        CartItem cartItem = cartItems.get(item.getId());
        if (cartItem == null) {
            cartItem = new CartItem(item);
            cartItems.put(item.getId(), cartItem);
        }
        cartItem.addQuantity(1);
    }

    public void removeItem(MenuItem item) {
        CartItem cartItem = cartItems.get(item.getId());
        if (cartItem != null) {
            cartItem.removeQuantity(1);
            if (cartItem.getQuantity() == 0) {
                cartItems.remove(item.getId());
            }
        }
    }

    public ArrayList<CartItem> getCartItems() {
        return new ArrayList<>(cartItems.values());
    }

    public int getId() {
        return id;
    }

    public String getCustname() {
        return custname;
    }
}
