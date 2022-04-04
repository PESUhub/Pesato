package i7.Controllers;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import i7.Views.*;


public class CustomerController {

    Stage stage;
    
    public CustomerController(CustomerView view, Stage stage) {
        setView(view);
        this.stage = stage;
    }

    private void setView(CustomerView view) {
        /*view.loginButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String u = view.username.getText();
                String p = view.password.getText();
                
                CustomerView customerView = new CustomerView();
                CustomerController customerController = new CustomerController(customerView);
                //Create Stage
                Stage newWindow = new Stage();
                newWindow.setTitle("New Scene");
                //Set view in window
                newWindow.setScene(new Scene());
                //Launch
                newWindow.show();
            }
        });*/
        view.logoutButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Create Stage
                stage.close();
            }
        });
    }    
}
