package i7.Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import i7.Views.*;

public class MainController {

    Stage stage;
    Genie genie;

    public MainController(MainView view, Stage stage, Genie genie) {
        setView(view);
        this.stage = stage;
        this.genie = genie;
    }

    private void setView(MainView view) {
        view.loginButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String u = view.username.getText();
                String p = view.password.getText();

                Boolean verify = genie.verifyLogin(u, p);
                
                if (verify) 
                //Create Stage
                {
                    Stage newWindow = new Stage();
                    CustomerView customerView = new CustomerView();
                    CustomerController customerController = new CustomerController(customerView, newWindow);
                    newWindow.setTitle("Customer: Welcome " + u);
                    //Set view in window
                    newWindow.setScene(new Scene(customerView.getView(), 800, 800));
                    //Launch
                    newWindow.show();
                }
            }
        });
    }
}
