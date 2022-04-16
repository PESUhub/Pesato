package i7.Models;

public class Restaurant extends User {

    public Restaurant(String username, String password) {
        this.username = username;
        this.password = password;
        this.type = UserType.RESTAURANT;
    }
}
