package i7.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import i7.Models.UserType;
import i7.Views.*;

public class MainController {

    Stage stage;
    Scene scene;
    Genie genie;
    MainView view;
    SignupView signupView;

    ArrayList<CustomerController> customerControllers = new ArrayList<CustomerController>();

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

                UserType userType = genie.verifyLogin(u, p);
                
                if (userType == UserType.CUSTOMER) {
                    Stage newWindow = new Stage();
                    CustomerView customerView = new CustomerView();
                    CustomerController customerController = new CustomerController(customerView, newWindow);
                    customerControllers.add(customerController);
                    newWindow.setTitle("Customer: Welcome " + u);
                    newWindow.setScene(new Scene(customerView.getView(), 800, 800));
                    newWindow.show();
                } else {
                    genie.showPopup("Invalid Login", "Username or password is incorrect", "close");
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
                System.out.println(userType);

                Boolean verify = genie.signupUser(u, p, UserType.valueOf(userType));

                if (verify) {
                    genie.showPopup("Success", "User " + u + " has been created. You may now close the signup window", "close");
                }
            }
        });
    }

}
