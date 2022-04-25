package i7.Models;

import i7.Controllers.Genie;
import org.bson.Document;

import java.util.Map;

public class DA extends User {
    private Boolean occupied;
    private int orderID;

    public DA(String username, String password, String email, String phone, String address, double balance) {
        super(UserType.DA, username, password, email, phone, address, balance);
        this.occupied = false;
    }

    public DA(Document map) {
        super(map);
        this.occupied = map.getBoolean("occupied");
        this.orderID = map.getInteger("orderID");
    }

    public Map<String, Object> toDocument() {
        Map<String, Object> document = super.toDocument();
        document.put("occupied", occupied);
        document.put("orderID", orderID);

        return document;
    }

    public Boolean isOccupied() {
        return occupied;
    }

    public String assignJob(Integer orderID) {
        if (occupied) { return null; }
        Genie g = Genie.getInstance();
        g.setDAOccupancy(this.getUsername(), true, orderID);
        this.orderID = orderID;
        occupied = true;
        return this.getUsername();
    }

    public void unassignJob() {
        Genie g = Genie.getInstance();
        orderID = -1;
        occupied = false;
        g.setDAOccupancy(this.getUsername(), false, -1);
    }

    public int getOrderID() {
        return orderID;
    }
}
