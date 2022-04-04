package i7.Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import i7.Views.*;

public class MainController {

    public MainController(MainView view) {
        setView(view);
    }

    private void setView(MainView view) {
        view.loginButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String u = view.username.getText();
                String p = view.password.getText();
                
                //Create Stage
                Stage newWindow = new Stage();
                CustomerView customerView = new CustomerView();
                CustomerController customerController = new CustomerController(customerView, newWindow);
                newWindow.setTitle("Customer: Welcome " + u);
                //Set view in window
                newWindow.setScene(new Scene(customerView.getView(), 640, 480));
                //Launch
                newWindow.show();
            }
        });
    }
}
