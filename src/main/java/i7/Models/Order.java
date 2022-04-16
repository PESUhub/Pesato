package i7.Models;

import i7.Controllers.Genie;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private int id;
    private String custname;
    private String daname;
    private Cart cart;
    private FoodStatus foodStatus;
    private LocalDateTime placedDateTime;
    private Double totalCost;

    public Order(String custname, int cartId, LocalDateTime placedDateTime)
    {
        Genie g = Genie.getInstance();
        this.id = g.getNextOrderId();
        this.custname = custname;
        this.daname = g.assignDA();
        this.cart = g.getCart(cartId);
        this.foodStatus = FoodStatus.ORDERED;
        this.placedDateTime = placedDateTime;
        cacluateTotalCost();
        payFromWallet();
    }

    public Order(Document doc) {
        Genie g = Genie.getInstance();
        this.id = doc.getInteger("id");
        this.custname = doc.getString("custname");
        this.daname = doc.getString("daname");
        this.foodStatus = FoodStatus.valueOf(doc.getString("foodStatus"));
        this.placedDateTime = LocalDateTime.parse(doc.getString("placedDateTime"));
        this.totalCost = doc.getDouble("totalCost");
        this.cart = g.getCart(doc.getInteger("cartId"));
    }

    public Map<String, Object> toDocument()
    {
        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("custname", custname);
        doc.put("daname", daname);
        doc.put("cartId", cart.getId());
        doc.put("foodStatus", foodStatus);
        doc.put("placedDateTime", placedDateTime);
        doc.put("totalCost", totalCost);

        return doc;
    }

    private void cacluateTotalCost()
    {
        totalCost = 0.0;
        for (CartItem item : cart.getCartItems()) {
            totalCost += item.getItem().getPrice() * item.getQuantity();
        }

        //standard delivery fee of 50.00
        totalCost += 50.00;
    }

    private void payFromWallet()
    {
        Genie g = Genie.getInstance();
        User u = g.getUser(custname);
        User d = g.getUser(daname);
        Boolean success = u.getWallet().transferBalanceTo(d.getWallet(), totalCost);
        g.showPopup("Error", "Insufficient funds in wallet", "close");
        g.updateUser(u);
        g.updateUser(d);
    }

    public int getId() {
        return id;
    }

    public String getCustname() {
        return custname;
    }

    public String getDaname() {
        return daname;
    }

    public Cart getCart() {
        return cart;
    }

    public FoodStatus getFoodStatus() {
        return foodStatus;
    }

    public LocalDateTime getPlacedDateTime() {
        return placedDateTime;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void changeFoodStatusToPreparing() {
        foodStatus = FoodStatus.PREPARING;
    }

    public void changeFoodStatusToReady() {
        foodStatus = FoodStatus.READY;
    }

    public void changeFoodStatusToPickedup() {
        foodStatus = FoodStatus.PICKEDUP;
    }

    public void changeFoodStatusToDelivered() {
        foodStatus = FoodStatus.DELIVERED;
    }
}
