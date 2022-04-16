package i7.Controllers;


import java.io.IOException;

import i7.Models.Customer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import i7.Views.*;


public class CustomerController extends UserController {

    private Customer customer;
    
    public CustomerController(Customer user, CustomerView view, Stage stage) {
        setView(view);
        this.stage = stage;
        this.customer = user;
    }

    private void setView(CustomerView view) {
        view.logoutButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.getOnCloseRequest().handle(null);
                stage.close();
            }
        });
    }
}
