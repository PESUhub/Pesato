package i7.Models;

import i7.Controllers.Genie;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class Wallet {
    private Double balance;
    private String username;

    public Wallet(Double balance, String username) {
        this.balance = balance;
        this.username = username;
    }

    public Wallet(Document document) {
        this.balance = document.getDouble("balance");
        this.username = document.getString("username");
    }

    private void updateWallet() {
        Genie.getInstance().updateWallet(this, username);
    }

    public Double getBalance() {
        return balance;
    }

    public void addBalance(Double balance) {
        this.balance += balance;
        updateWallet();
    }

    private Boolean subtractBalance(Double balance) {
        if (this.balance >= balance) {
            this.balance -= balance;
            return true;
        }
        updateWallet();
        return false;
    }

    public Boolean transferBalanceTo(Wallet wallet, Double balance) {
        Boolean status = this.subtractBalance(balance);
        if (status) {
            wallet.addBalance(balance);
            return true;
        } else {
            return false;
        }
    }

    public Map<String, Object> toDocument() {
        Map<String, Object> document = new HashMap<>();
        document.put("balance", this.balance);
        document.put("username", this.username);
        return document;
    }
}
