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

    public Order(Cart cart, String custname, LocalDateTime placedDateTime)
    {
        Genie g = Genie.getInstance();
        g.addCart(cart);
        this.id = g.getNextOrderId();
        this.custname = custname;
        this.daname = g.assignDA(id);
        this.cart = cart;
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

        //TODO: datetime this.placedDateTime = LocalDateTime.parse(doc.getDate("placedDateTime").toString());
        this.placedDateTime = LocalDateTime.now();

        this.totalCost = doc.getDouble("totalCost");
        this.cart = g.getCart(doc.getInteger("cartId"));
    }

    public Map<String, Object> toDocument()
    {
        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("custname", custname);
        doc.put("daname", daname);
        doc.put("resname", cart.getRestaurantSelected());
        doc.put("cartId", cart.getId());
        doc.put("foodStatus", foodStatus.toString());
        doc.put("placedDateTime", placedDateTime);
        doc.put("totalCost", totalCost);

        return doc;
    }

    private void cacluateTotalCost()
    {
        totalCost = 0.0;
        for (CartItem item : cart.getCartItemsObservableList()) {
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
        if(!success) g.showPopup("Error", "Insufficient funds in wallet", "close");
        g.updateUser(u);
        g.updateUser(d);
    }

    private void updateOrder() {
        Genie g = Genie.getInstance();
        g.updateOrder(this);
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
        updateOrder();
    }

    public void changeFoodStatusToReady() {
        foodStatus = FoodStatus.READY;
        updateOrder();
    }

    public void changeFoodStatusToPickedup() {
        foodStatus = FoodStatus.PICKEDUP;
        updateOrder();
    }

    public void changeFoodStatusToDelivered() {
        foodStatus = FoodStatus.DELIVERED;
        updateOrder();
    }
}
