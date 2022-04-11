package i7.Views;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class MainView {
    Parent view;
    public TextField username;
    public TextField password;
    public Button loginButton;
    public Button signupButton;
    public Button resetButton;

    public MainView() {
        view = createView();
    }

    public Parent getView() {
        return view;
    }

    public VBox createView() {
        VBox vBox = new VBox();
        vBox.getChildren().add(new Label("Username"));
        vBox.getChildren().add(username = new TextField());
        vBox.getChildren().add(new Label("Password"));
        vBox.getChildren().add(password = new TextField());
        vBox.getChildren().add(loginButton = new Button("Login"));
        vBox.getChildren().add(signupButton = new Button("Sign up"));
        vBox.getChildren().add(resetButton= new Button("send Reset to genie"));
        
        return vBox;
    }


}
