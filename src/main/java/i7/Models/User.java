package i7.Models;

import i7.Controllers.Genie;
import org.bson.Document;

import java.util.Locale;
import java.util.Map;

public abstract class User {
    private UserType type;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private Wallet wallet;

    public User(UserType type, String username, String password, String email, String phone, String address, double balance) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.type = type;
        this.wallet = new Wallet(balance, this.username);
    }

    public User(Document document) {
        this.username = document.getString("username");
        this.password = document.getString("password");
        this.email = document.getString("email");
        this.phone = document.getString("phone");
        this.address = document.getString("address");
        this.type = UserType.valueOf(document.getString("type").toUpperCase(Locale.ROOT));
        this.wallet = Genie.getInstance().getWallet(this.username);
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getAddress() {
        return this.address;
    }

    public UserType getType() {
        return this.type;
    }

    public Wallet getWallet() {
        return this.wallet;
    }
}
