package i7.Models;

import i7.Controllers.Genie;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private String restaurantSelected;
    //ItemName, quantity
    private Map<String, Integer> cartItems;

    public Cart(Document document) {
        this.restaurantSelected = document.getString("restaurantSelected");
        try {
            document.get("cartItems", Map.class).forEach((k, v) -> cartItems.put((String) k, (Integer) v));
        } catch (Exception e) {
            Genie g = Genie.getInstance();
            g.showPopup("Error", "Error loading menu items. Contact Dev.", "close");
        }
    }

    private void updateCartItemsDocuments() {
        Genie g = Genie.getInstance();
        Map<String, Map<String, Object>> menuItemsDocuments = new HashMap<>();
        for (Map.Entry<String, MenuItem> entry : cartItems.entrySet()) {
            menuItemsDocuments.put(entry.getKey(), entry.getValue().toDocument());
        }
        g.updateMenuItemsInRestaurant(menuItemsDocuments, this.username);
    }

    public void setRestaurantSelected(String restaurantSelected) {
        this.restaurantSelected = restaurantSelected;
    }

    public String getRestaurantSelected() {
        return restaurantSelected;
    }

    public Boolean isItemInCart(String itemName) {
        return cartItems.containsKey(itemName);
    }

    public void addItemToCart(String itemName, Integer quantity) {
        if (cartItems.containsKey(itemName)) {
            cartItems.put(itemName, cartItems.get(itemName) + quantity);
        } else {
            cartItems.put(itemName, quantity);
        }
    }

    public void removeItemFromCart(String itemName) {
        cartItems.remove(itemName);
    }

    public void clearCart() {
        cartItems.clear();
    }

    public Integer getItemQuantity(String itemName) {
        return cartItems.get(itemName);
    }

    public Map<String, Integer> getCartItems() {
        return cartItems;
    }
}
