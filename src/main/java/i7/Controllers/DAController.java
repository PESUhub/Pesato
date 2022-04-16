package i7.Controllers;

import i7.Models.*;
import i7.Views.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class DAController extends UserController{

    private DA da;

    public DAController(DA user, DAView view, Stage stage) {
        setView(view);
        this.stage = stage;
        this.da = user;
    }

    private void setView(DAView view) {
        view.logoutButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.getOnCloseRequest().handle(null);
                stage.close();
            }
        });
    }
}
