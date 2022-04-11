package i7.Views;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class SignupView {
    Parent view;
    public TextField username;
    public TextField password;
    public Button signupButton;
    public Button closeButton;
    public ToggleGroup userTypeGroup;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;

    public SignupView() {
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

        userTypeGroup = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Customer");
        rb1.setToggleGroup(userTypeGroup);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("Restaurant");
        rb2.setToggleGroup(userTypeGroup);

        RadioButton rb3 = new RadioButton("DA");
        rb3.setToggleGroup(userTypeGroup);

        vBox.getChildren().add(rb1);
        vBox.getChildren().add(rb2);
        vBox.getChildren().add(rb3);

        vBox.getChildren().add(signupButton = new Button("Sign up"));
        vBox.getChildren().add(closeButton= new Button("go back to sign in"));
        
        return vBox;
    }
}
