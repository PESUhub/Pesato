package i7.Controllers;

import i7.Models.MenuItem;
import i7.Models.Restaurant;
import i7.Views.CustomerView;
import i7.Views.RestaurantView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

public class RestaurantController extends UserController {

    private Genie genie;
    private Restaurant restaurant;

    public RestaurantController(Restaurant user, RestaurantView view, Stage stage) {
        this.stage = stage;
        this.genie = Genie.getInstance();
        this.restaurant = user;
        setView(view);
    }

    private void setView(RestaurantView view) {
        view.logoutButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.getOnCloseRequest().handle(null);
                stage.close();
            }
        });

        EventHandler<TableColumn.CellEditEvent<MenuItem, String>> nameEditControl = new EventHandler<TableColumn.CellEditEvent<MenuItem, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<MenuItem, String> t) {
                ((MenuItem) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setName(t.getNewValue());
            }
        };

        EventHandler<TableColumn.CellEditEvent<MenuItem, String>> descEditControl = new EventHandler<TableColumn.CellEditEvent<MenuItem, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<MenuItem, String> t) {
                ((MenuItem) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setDescription(t.getNewValue());
            }
        };

        EventHandler<TableColumn.CellEditEvent<MenuItem, Double>> priceEditControl = new EventHandler<TableColumn.CellEditEvent<MenuItem, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<MenuItem, Double> t) {
                try {
                    ((MenuItem) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setPrice(t.getNewValue());
                } catch (Exception e) {
                    genie.showPopup("Error", "Please enter valid price", "close");
                }
            }
        };

        EventHandler<TableColumn.CellEditEvent<MenuItem, Integer>> ratingEditControl = new EventHandler<TableColumn.CellEditEvent<MenuItem, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<MenuItem, Integer> t) {
                try {
                    ((MenuItem) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setRating(t.getNewValue());
                    System.out.println(t.getNewValue());
                } catch (Exception e) {
                    genie.showPopup("Error", "Please enter valid rating", "close");
                }
            }
        };

        view.vegCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MenuItem, CheckBox>, ObservableValue<CheckBox>>() {

            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<MenuItem, CheckBox> arg0) {
                MenuItem item = arg0.getValue();
                CheckBox checkBox = new CheckBox();
                checkBox.selectedProperty().setValue(item.getVeg());
                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                        item.setVeg(new_val);
                    }
                });
                return new SimpleObjectProperty<CheckBox>(checkBox);
            }
        });

        view.deleteCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MenuItem, Button>, ObservableValue<Button>>() {
            @Override
            public ObservableValue<Button> call(TableColumn.CellDataFeatures<MenuItem, Button> arg0) {
                MenuItem item = arg0.getValue();
                Button button = new Button("Delete");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        restaurant.removeMenuItem(item);
                    }
                });
                return new SimpleObjectProperty<Button>(button);
            }
        });

        view.nameCol.setOnEditCommit(nameEditControl);
        view.descCol.setOnEditCommit(descEditControl);
        view.priceCol.setOnEditCommit(priceEditControl);
        view.ratingCol.setOnEditCommit(ratingEditControl);

        view.miTable.setItems(restaurant.getMenuItemsObservableList());

        view.addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(view.nameField.getText().isEmpty() || view.descField.getText().isEmpty() || view.priceField.getText().isEmpty() || view.ratingField.getText().isEmpty()) {
                    genie.showPopup("Error", "Please fill all the fields", "close");
                } else {
                    try {
                        restaurant.addMenuItem(new MenuItem(restaurant.getUsername(), view.nameField.getText(), view.descField.getText(), Double.parseDouble(view.priceField.getText()), Integer.parseInt(view.ratingField.getText()), view.vegCheckBox.isSelected()));
                    } catch (Exception e) {
                        genie.showPopup("Error", "Please enter valid price and rating OR some other error", "close");
                    }
                }
            }
        });
    }
}
