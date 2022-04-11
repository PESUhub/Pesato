package i7.Models;

public class Customer extends User {
    public final UserType type = UserType.CUSTOMER;

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
