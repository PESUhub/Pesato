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
                String u = view.username.getText();
                String p = view.password.getText();

                User user = genie.verifyLogin(u, p);

                if (user != null) {
                    if (userControllers.containsKey(user.username)) {
                        genie.showPopup("Error", "You are already logged in!", "OK");
                    }
                    else {
                        Stage newWindow = new Stage();
                        newWindow.onCloseRequestProperty().set(event1 -> userControllers.remove(user.username));
                        if (user.type == UserType.CUSTOMER) {
                            CustomerView customerView = new CustomerView();
                            CustomerController customerController = new CustomerController((Customer) user, customerView, newWindow);
                            userControllers.put(user.username, customerController);
                            newWindow.setTitle("Customer: Welcome " + u);
                            newWindow.setScene(new Scene(customerView.getView(), 800, 800));
                            newWindow.show();
                        }
                        else if (user.type == UserType.RESTAURANT) {
                            RestaurantView restaurantView = new RestaurantView();
                            RestaurantController restaurantController = new RestaurantController((Restaurant) user, restaurantView, newWindow);
                            userControllers.put(user.username, restaurantController);
                            newWindow.setTitle("Restaurant: Welcome " + u);
                            newWindow.setScene(new Scene(restaurantView.getView(), 800, 800));
                            newWindow.show();
                        }
                        else if (user.type == UserType.DA) {
                            DAView daView = new DAView();
                            DAController daController = new DAController((DA) user, daView, newWindow);
                            userControllers.put(user.username, daController);
                            newWindow.setTitle("DA: Welcome " + u);
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
                String u = signupView.username.getText();
                String p = signupView.password.getText();
                String userType = ((javafx.scene.control.RadioButton) signupView.userTypeGroup.getSelectedToggle()).getText().toUpperCase();

                Boolean verify = genie.signupUser(u, p, UserType.valueOf(userType));

                if (verify) {
                    genie.showPopup("Success", "User " + u + " has been created. You may now close the signup window", "close");
                }
                else {
                    genie.showPopup("Error", "User " + u + " already exists", "close");
                }
            }
        });
    }

}
