package i7.Views;

import i7.Models.UserType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class SignupView {
    Parent view;
    public TextField username;
    public TextField password;
    public TextField confirmPassword;

    public TextField email;
    public TextField phone;
    public TextField address;
    public TextField initalWalletBalance;

    public TextField cuisine;
    public TextField rating;

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

        userTypeGroup = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Customer");
        rb1.setUserData(UserType.CUSTOMER);
        rb1.setToggleGroup(userTypeGroup);

        RadioButton rb2 = new RadioButton("Restaurant");
        rb2.setUserData(UserType.RESTAURANT);
        rb2.setToggleGroup(userTypeGroup);

        RadioButton rb3 = new RadioButton("Delivery Agent");
        rb3.setUserData(UserType.DA);
        rb3.setToggleGroup(userTypeGroup);

        vBox.getChildren().add(rb1);
        vBox.getChildren().add(rb2);
        vBox.getChildren().add(rb3);

        vBox.getChildren().add(new Separator());

        vBox.getChildren().add(new Label("Username"));
        vBox.getChildren().add(username = new TextField(""));
        vBox.getChildren().add(new Label("Password"));
        vBox.getChildren().add(password = new TextField("1"));
        vBox.getChildren().add(new Label("Confirm Password"));
        vBox.getChildren().add(confirmPassword = new TextField("1"));
        vBox.getChildren().add(new Label("Email"));
        vBox.getChildren().add(email = new TextField("this@email.com"));
        vBox.getChildren().add(new Label("Phone"));
        vBox.getChildren().add(phone = new TextField("7777222212"));
        vBox.getChildren().add(new Label("Address"));
        vBox.getChildren().add(address = new TextField("35/4 street"));
        vBox.getChildren().add(new Label("Initial Wallet Balance"));
        vBox.getChildren().add(initalWalletBalance = new TextField("50.00"));

        vBox.getChildren().add(new Separator());

        vBox.getChildren().add(new Label("Cuisine"));
        vBox.getChildren().add(cuisine = new TextField("Indian"));
        vBox.getChildren().add(new Label("Rating"));
        vBox.getChildren().add(rating = new TextField("4"));

        vBox.getChildren().add(new Separator());

        vBox.getChildren().add(signupButton = new Button("Sign up"));
        vBox.getChildren().add(closeButton= new Button("go back to sign in"));

        userTypeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (userTypeGroup.getSelectedToggle() != null) {
                    UserType type = (UserType) userTypeGroup.getSelectedToggle().getUserData();
                    if (type == UserType.RESTAURANT) {
                        cuisine.setDisable(false);
                        rating.setDisable(false);
                    } else {
                        cuisine.setDisable(true);
                        rating.setDisable(true);
                    }
                }
            }
        });
        rb1.setSelected(true);
        
        return vBox;
    }
}
