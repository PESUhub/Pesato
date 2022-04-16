package i7.Models;

public class Customer extends User {

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
        this.type = UserType.CUSTOMER;
    }
}
