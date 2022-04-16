package i7.Controllers;

import i7.Models.Restaurant;
import i7.Views.CustomerView;
import i7.Views.RestaurantView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class RestaurantController extends UserController {

    public RestaurantController(Restaurant user, RestaurantView view, Stage stage) {
        setView(view);
        this.stage = stage;
        this.user = user;
    }

    private void setView(RestaurantView view) {
        view.logoutButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.getOnCloseRequest().handle(null);
                stage.close();
            }
        });
    }
}
