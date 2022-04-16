package i7.Models;

import org.bson.Document;

import java.util.Map;

public class DA extends User {
    public DA(String username, String password, String email, String phone, String address, double balance) {
        super(UserType.DA, username, password, email, phone, address, balance);
    }

    public DA(Document map) {
        super(map);
    }

    public Map<String, Object> toDocument() {
        Map<String, Object> document = super.toDocument();

        return document;
    }
}
