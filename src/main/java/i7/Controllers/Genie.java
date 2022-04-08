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
        MongoIterable<String> dbNames = mongoClient.listDatabaseNames();
        for (String dbName : dbNames) {
            System.out.println(dbName);
        }
        database = mongoClient.getDatabase("pesato");
    }

    public Boolean verifyLogin(String username, String password) {
        MongoCollection<Document> collection = database.getCollection("customers");
        Document doc = collection.find(eq("username", username)).first();
        if (doc == null) {
            return false;
        }
        if (doc.getString("password").equals(password)) {
            return true;
        }
        return false;
    }
}
