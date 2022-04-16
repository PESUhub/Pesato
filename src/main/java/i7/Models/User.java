package i7.Models;

import org.bson.Document;

import java.util.Locale;
import java.util.Map;

public abstract class User {
    public UserType type;
    public String username;
    public String password;
    public String email;
    public String phone;
    public String address;
    public Wallet wallet;

    public User(UserType type, String username, String password, String email, String phone, String address, double balance) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.type = type;
        this.wallet = new Wallet(balance);
    }

    public User(Document document) {
        this.username = document.getString("username");
        this.password = document.getString("password");
        this.email = document.getString("email");
        this.phone = document.getString("phone");
        this.address = document.getString("address");
        this.type = UserType.valueOf(document.getString("type").toUpperCase(Locale.ROOT));
        this.wallet = new Wallet(document.get("wallet", Document.class));
    }

    public Map<String, Object> toDocument() {
        Map<String, Object> document = new java.util.HashMap<>();
        document.put("username", this.username);
        document.put("password", this.password);
        document.put("type", this.type.toString());
        document.put("email", this.email);
        document.put("phone", this.phone);
        document.put("address", this.address);
        document.put("wallet", this.wallet.toDocument());
        return document;
    }
}
