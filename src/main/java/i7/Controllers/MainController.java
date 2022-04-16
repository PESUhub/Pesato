package i7.Controllers;

import java.io.IOException;
import javafx.event.EventHandler;
import java.util.HashMap;

import i7.Models.*;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import i7.Views.*;

public class MainController {

    Stage stage;
    Scene scene;
    Genie genie;
    MainView view;
    SignupView signupView;

    HashMap<String, UserController> userControllers = new HashMap<>();

    public MainController(Stage stage, Scene scene, MainView view, Genie genie) {
        this.stage = stage;
        this.genie = genie;
        this.scene = scene;
        this.view = view;
        setView();
    }

    private void setView() {
        view.loginButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = view.username.getText();
                String password = view.password.getText();

                User user = genie.verifyLogin(username, password);

                if (user != null) {
                    if (userControllers.containsKey(user.getUsername())) {
                        genie.showPopup("Error", "You are already logged in!", "OK");
                    }
                    else {
                        Stage newWindow = new Stage();
                        newWindow.onCloseRequestProperty().set(event1 -> userControllers.remove(user.getUsername()));
                        if (user.getType() == UserType.CUSTOMER) {
                            CustomerView customerView = new CustomerView();
                            CustomerController customerController = new CustomerController((Customer) user, customerView, newWindow);
                            userControllers.put(user.getUsername(), customerController);
                            newWindow.setTitle("Customer: Welcome " + username);
                            newWindow.setScene(new Scene(customerView.getView(), 800, 800));
                            newWindow.show();
                        }
                        else if (user.getType() == UserType.RESTAURANT) {
                            RestaurantView restaurantView = new RestaurantView();
                            RestaurantController restaurantController = new RestaurantController((Restaurant) user, restaurantView, newWindow);
                            userControllers.put(user.getUsername(), restaurantController);
                            newWindow.setTitle("Restaurant: Welcome " + username);
                            newWindow.setScene(new Scene(restaurantView.getView(), 800, 800));
                            newWindow.show();
                        }
                        else if (user.getType() == UserType.DA) {
                            DAView daView = new DAView();
                            DAController daController = new DAController((DA) user, daView, newWindow);
                            userControllers.put(user.getUsername(), daController);
                            newWindow.setTitle("DA: Welcome " + username);
                            newWindow.setScene(new Scene(daView.getView(), 800, 800));
                            newWindow.show();
                        } else {
                            genie.showPopup("Error", "Invalid user type! Contact Dev", "OK");
                        }
                    }
                }
                else {
                    genie.showPopup("Error", "Invalid username or password!", "OK");
                }
            }
        });
        view.signupButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                signupView = new SignupView();
                setSignupView();
                scene.setRoot(signupView.getView());
            }
        });

        view.resetButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                genie.reset();
            }
        });
    }

    private void setSignupView() {
        signupView.closeButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scene.setRoot(view.getView());
            }
        }); 

        signupView.signupButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = signupView.username.getText();
                String password = signupView.password.getText();
                String confirmPassword = signupView.confirmPassword.getText();

                if (username.equals("") || password.equals("") || confirmPassword.equals("")) {
                    genie.showPopup("Error", "Please fill in all fields!", "OK");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    genie.showPopup("Error", "Passwords do not match!", "OK");
                    return;
                }

                String email = signupView.email.getText();
                String phone = signupView.phone.getText();
                String address = signupView.address.getText();
                String initalWalletBalance = signupView.initalWalletBalance.getText();

                if (email.equals("") || phone.equals("") || address.equals("") || initalWalletBalance.equals("")) {
                    genie.showPopup("Error", "Please fill in all fields!", "OK");
                    return;
                }

                double balance;
                try {
                    balance = Double.parseDouble(initalWalletBalance);
                }
                catch (NumberFormatException e) {
                    genie.showPopup("Error", "Initial wallet balance must be a number!", "OK");
                    return;
                }

                UserType userType = UserType.valueOf(((javafx.scene.control.RadioButton) signupView.userTypeGroup.getSelectedToggle()).getText().toUpperCase());

                User user;

                if (userType == UserType.CUSTOMER) {
                    user = new Customer(username, password, email, phone, address, balance);
                } else if (userType == UserType.RESTAURANT) {
                    String cuisine = signupView.cuisine.getText();
                    String rating = signupView.rating.getText();

                    if (cuisine.equals("") || rating.equals("")) {
                        genie.showPopup("Error", "Please fill in all fields!", "OK");
                        return;
                    }

                    int ratingInt;
                    try {
                        ratingInt = Integer.parseInt(rating);
                    } catch (NumberFormatException e) {
                        genie.showPopup("Error", "Rating must be an Integer!", "OK");
                        return;
                    }

                    user = new Restaurant(username, password, email, phone, address, balance, cuisine, ratingInt);
                } else if (userType == UserType.DA) {
                    user = new DA(username, password, email, phone, address, balance);
                } else {
                    genie.showPopup("Error", "Invalid user type! contact dev", "OK");
                    return;
                }

                Boolean verify = genie.signupUser(user);

                if (verify) {
                    genie.showPopup("Success", "User " + username + " has been created. You may now close the signup window", "close");
                }
                else {
                    genie.showPopup("Error", "User " + username + " already exists!", "close");
                }
            }
        });
    }

}
