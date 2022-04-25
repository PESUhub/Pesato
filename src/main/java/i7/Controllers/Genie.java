package i7.Controllers;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;

import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.*;
import i7.Models.MenuItem;
import org.bson.Document;
import org.bson.types.ObjectId;

import i7.Models.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.print.Doc;
import java.util.*;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class Genie {
    private static Genie instance = new Genie();
    private MongoClient mongoClient;
    private MongoDatabase database;

    private Genie() {
        connectToMongodb();

    }

    public static Genie getInstance() {
        return instance;
    }

    public void connectToMongodb() {
        mongoClient = MongoClients.create();
        database = mongoClient.getDatabase("pesato");
    }

    public void reset() {
        database.drop();
        connectToMongodb();
    }

    public Boolean signupUser(User user) {
        MongoCollection<Document> collection = database.getCollection("users");
        if (collection.find(eq("username", user.getUsername())).first() != null) {
            return false;
        }
        collection.insertOne(new Document(user.toDocument()));
        return true;
    }

    public User verifyLogin(String username, String password) {
        MongoCollection<Document> collection = database.getCollection("users");
        Document doc = collection.find(eq("username", username)).first();
        if (doc == null) {
            return null;
        }
        if (doc.getString("password").equals(password)) {
            UserType type = UserType.valueOf(doc.getString("type").toUpperCase(Locale.ROOT));
            if (type == UserType.CUSTOMER) {
                return new Customer(doc);
            } else if (type == UserType.RESTAURANT) {
                return new Restaurant(doc);
            } else if (type == UserType.DA) {
                return new DA(doc);
            } else {
                return null;
            }
        }
        return null;
    }

    public void showPopup(String title, String message, String buttonText) {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(title);

        Label label1= new Label(message);
        Button button1= new Button(buttonText);
        button1.setOnAction(e -> popupWindow.close());

        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1, button1);
        layout.setAlignment(Pos.CENTER);

        Scene scene1= new Scene(layout, 500, 250);

        popupWindow.setScene(scene1);
        popupWindow.showAndWait();
    }

    public User getUser(String username) {
        MongoCollection<Document> collection = database.getCollection("users");
        Document doc = collection.find(eq("username", username)).first();
        if (doc == null) {
            return null;
        }
        UserType type = UserType.valueOf(doc.getString("type").toUpperCase(Locale.ROOT));
        if (type == UserType.CUSTOMER) {
            return new Customer(doc);
        } else if (type == UserType.RESTAURANT) {
            return new Restaurant(doc);
        } else if (type == UserType.DA) {
            return new DA(doc);
        } else {
            return null;
        }
    }

    public ArrayList<User> getUsersByType(UserType type) {
        MongoCollection<Document> collection = database.getCollection("users");
        //get all users with type
        FindIterable<Document> iterable = collection.find(eq("type", type.toString()));
        ArrayList<User> users = new ArrayList<>();
        iterable.forEach(doc -> {
            if (type == UserType.CUSTOMER) {
                users.add(new Customer(doc));
            } else if (type == UserType.RESTAURANT) {
                users.add(new Restaurant(doc));
            } else if (type == UserType.DA) {
                users.add(new DA(doc));
            }
        });
        return users;
    }

    public void updateUser(User user) {
        MongoCollection<Document> collection = database.getCollection("users");
        collection.replaceOne(eq("username", user.getUsername()), new Document(user.toDocument()));
    }

    public Boolean addMenuItem(MenuItem item) {
        MongoCollection<Document> collection = database.getCollection("menuitems");
        collection.insertOne(new Document(item.toDocument()));
        return true;
    }

    public int getNextMenuItemId() {
        MongoCollection<Document> collection = database.getCollection("menuitems");
        Document max = collection.find().sort(new Document("id", -1)).limit(1).first();
        if (max == null) {
            return 1;
        }
        return (max.getInteger("id") + 1);
    }

    public HashMap<Integer, MenuItem> getMenuItems(String restaurantName) {
        MongoCollection<Document> collection = database.getCollection("menuitems");

        HashMap<Integer, MenuItem> menuItems = new HashMap<>();
        try {
            collection.find(eq("restaurantName", restaurantName)).into(new ArrayList<>()).forEach((item) -> menuItems.put(item.getInteger("id"), new MenuItem(item)));
        } catch (Exception e) {
            Genie g = Genie.getInstance();
            g.showPopup("Error", "Error loading menu items. Contact Dev.", "close");
        }

        return menuItems;
    }

    public MenuItem getMenuItem(int id) {
        MongoCollection<Document> collection = database.getCollection("menuitems");
        Document doc = collection.find(eq("id", id)).first();
        if (doc == null) {
            return null;
        }
        return new MenuItem(doc);
    }

    public ArrayList<MenuItem> getMenuItemsFromRestaurant(String restaurantName) {
        MongoCollection<Document> collection = database.getCollection("menuitems");
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        try {
            collection.find(eq("restaurantName", restaurantName)).into(new ArrayList<>()).forEach((item) -> menuItems.add(new MenuItem(item)));
        } catch (Exception e) {
            Genie g = Genie.getInstance();
            g.showPopup("Error", "Error loading menu items. Contact Dev." + e, "close");
        }
        return menuItems;
    }

    public Boolean updateMenuItem(MenuItem item) {
        MongoCollection<Document> collection = database.getCollection("menuitems");
        collection.replaceOne(eq("id", item.getId()), new Document(item.toDocument()));
        return true;
    }

    public Boolean removeMenuItem(int id) {
        MongoCollection<Document> collection = database.getCollection("menuitems");
        collection.deleteOne(eq("id", id));
        return true;
    }


    public int getNextCartId() {
        MongoCollection<Document> collection = database.getCollection("carts");
        Document max = collection.find().sort(new Document("id", -1)).limit(1).first();
        if (max == null) {
            return 1;
        }
        return (max.getInteger("id") + 1);
    }

    public void addCart(Cart cart) {
        MongoCollection<Document> collection = database.getCollection("carts");
        collection.insertOne(new Document(cart.toDocument()));
    }

    public Cart getCart(int id) {
        MongoCollection<Document> collection = database.getCollection("carts");
        Document doc = collection.find(eq("id", id)).first();
        if (doc == null) {
            return null;
        }
        return new Cart(doc);
    }

    public int getNextOrderId() {
        MongoCollection<Document> collection = database.getCollection("orders");
        Document max = collection.find().sort(new Document("id", -1)).limit(1).first();
        if (max == null) {
            return 1;
        }
        return (max.getInteger("id") + 1);
    }

    public Order getOrder(int id) {
        MongoCollection<Document> collection = database.getCollection("orders");
        Document doc = collection.find(eq("id", id)).first();
        if (doc == null) {
            return null;
        }
        return new Order(doc);
    }

    public String assignDA(Integer orderID) {
        MongoCollection<Document> collection = database.getCollection("users");
        Document doc = collection.find(eq("occupied", false)).first();
        if (doc == null) {
            showPopup("Error", "No available delivery agents. Errors will propagate.", "close");
            return null;
        }
        DA da = new DA(doc);
        String username = da.assignJob(orderID);
        return username;
    }

    public void setDAOccupancy(String username, Boolean occupied, Integer orderID) {
        MongoCollection<Document> collection = database.getCollection("users");
        collection.updateOne(eq("username", username), new Document("$set", new Document("occupied", occupied)));
        collection.updateOne(eq("username", username), new Document("$set", new Document("orderID", orderID)));
    }

    public Wallet getWallet(String username) {
        MongoCollection<Document> collection = database.getCollection("users");
        Document doc = collection.find(eq("username", username)).first();
        if (doc == null) {
            return null;
        }
        return new Wallet(doc.get("wallet", Document.class));
    }

    public void updateWallet(Wallet wallet, String username) {
        MongoCollection<Document> collection = database.getCollection("users");
        collection.updateOne(eq("username", username), new Document("$set", new Document("wallet", wallet.toDocument())));
    }

    public void addOrder(Order order) {
        MongoCollection<Document> collection = database.getCollection("orders");
        collection.insertOne(new Document(order.toDocument()));
    }

    public void updateOrder(Order order) {
        MongoCollection<Document> collection = database.getCollection("orders");
        collection.updateOne(eq("id", order.getId()), new Document("$set", new Document(order.toDocument())));
    }

    public ArrayList<Order> getRestaurantPendingOrders(String restaurant) {
        ArrayList<Order> orders = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection("orders");
        FindIterable<Document> iterable = collection.find(and(eq("resname", restaurant), or(eq("foodStatus", "ORDERED"), eq("foodStatus", "PREPARING"))));
        iterable.forEach(item -> orders.add(new Order(item)));
        return orders;
    }

    public ArrayList<Order> getRestaurantOrdersHistory(String restaurant) {
        ArrayList<Order> orders = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection("orders");
        FindIterable<Document> iterable = collection.find(and(eq("resname", restaurant), or(not(eq("foodStatus", "ORDERED")), not(eq("foodStatus", "PREPARING")))));
        iterable.forEach(item -> orders.add(new Order(item)));
        return orders;
    }

    public ArrayList<Order> getDAOrdersHistory(String da) {
        ArrayList<Order> orders = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection("orders");
        FindIterable<Document> iterable = collection.find(and(eq("daname", da), eq("foodStatus", "DELIVERED")));
        iterable.forEach(item -> orders.add(new Order(item)));
        return orders;
    }

    public ArrayList<Order> getCustomerCurrentOrders(String username) {
        ArrayList<Order> orders = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection("orders");
        FindIterable<Document> iterable = collection.find(and(eq("custname", username), not(eq("foodStatus", "DELIVERED"))));
        iterable.forEach(item -> orders.add(new Order(item)));
        return orders;
    }

    public ArrayList<Order> getCustomerOrdersHistory(String username) {
        ArrayList<Order> orders = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection("orders");
        FindIterable<Document> iterable = collection.find(and(eq("custname", username), eq("foodStatus", "DELIVERED")));
        iterable.forEach(item -> orders.add(new Order(item)));
        return orders;
    }
}
