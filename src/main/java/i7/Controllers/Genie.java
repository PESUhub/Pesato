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
import org.bson.Document;
import org.bson.types.ObjectId;

import i7.Models.UserType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

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
        //list databases
        //MongoIterable<String> dbNames = mongoClient.listDatabaseNames();
        database = mongoClient.getDatabase("pesato");
    }

    public void reset() {
        database.drop();
        connectToMongodb();
    }

    public Boolean signupUser(String username, String password, UserType Type) {
        MongoCollection<Document> collection = database.getCollection("users");
        if (collection.find(eq("username", username)).first() != null) {
            return false;
        }
        Document doc = new Document("username", username).append("password", password).append("type", Type.toString());
        collection.insertOne(doc);
        return true;
    }

    public UserType verifyLogin(String username, String password) {
        MongoCollection<Document> collection = database.getCollection("users");
        Document doc = collection.find(eq("username", username)).first();
        if (doc == null) {
            return null;
        }
        if (doc.getString("password").equals(password)) {
            return UserType.valueOf(doc.getString("type"));
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

        Scene scene1= new Scene(layout, 300, 250);

        popupWindow.setScene(scene1);
        popupWindow.showAndWait();
    }
}
