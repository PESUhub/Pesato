package i7.Controllers;

import com.mongodb.*;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
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
        if (collection.find(eq("username", user.username)).first() != null) {
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

    /*public Boolean insertMenuItemInRestaurant(MenuItem menuItem, String restaurantName) {
        MongoCollection<Document> collection = database.getCollection("users");
        Document doc = collection.find(eq("username", restaurantName)).first();
        if (doc == null) {
            return false;
        }
        //doc.get("menuItems", Map.class).put(menuItem.getName(), menuItem.toDocument());
        collection.updateOne(eq("username", restaurantName), new Document("$push", new Document("menuItems", menuItem.toDocument())));
        System.out.println(doc.get("menuItems", Map.class));
        return true;
    }*/

    public Boolean updateMenuItemsInRestaurant(Map<String, Map<String, Object>> menuItems, String restaurantName) {
        MongoCollection<Document> collection = database.getCollection("users");
        Document doc = collection.find(eq("username", restaurantName)).first();
        if (doc == null) {
            return false;
        }
        collection.updateOne(eq("username", restaurantName), new Document("$set", new Document("menuItems", menuItems)));
        return true;
    }

    public Boolean addMenuItem(MenuItem item) {
        MongoCollection<Document> collection = database.getCollection("menuitems");
    }
}
