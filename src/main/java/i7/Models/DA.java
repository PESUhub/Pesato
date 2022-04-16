package i7.Models;

public class DA extends User {
    public DA(String username, String password) {
        this.username = username;
        this.password = password;
        this.type = UserType.DA;
    }
}
