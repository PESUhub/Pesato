package i7.Models;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class Wallet {
    private Double balance;

    public Wallet(Double balance) {
        this.balance = balance;
    }

    public Wallet(Document document) {
        this.balance = document.getDouble("balance");
    }

    public Double getBalance() {
        return balance;
    }

    public void addBalance(Double balance) {
        this.balance += balance;
    }

    private Boolean subtractBalance(Double balance) {
        if (this.balance >= balance) {
            this.balance -= balance;
            return true;
        }
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
        return document;
    }
}
