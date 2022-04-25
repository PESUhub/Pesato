package i7.Controllers;

import i7.Models.FoodStatus;
import i7.Models.MenuItem;
import i7.Models.Order;
import i7.Models.Restaurant;
import i7.Views.CustomerView;
import i7.Views.RestaurantView;
import i7.Views.WalletView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

public class RestaurantController extends UserController {

    private Genie genie;
    private Restaurant restaurant;

    private RestaurantView restaurantView;

    public RestaurantController(Restaurant user, RestaurantView view, Stage stage) {
        this.stage = stage;
        this.genie = Genie.getInstance();
        this.restaurant = user;
        this.restaurantView = view;
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

        view.reloadButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setView(restaurantView);
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

        view.prepareCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Order, Button>, ObservableValue<Button>>() {
            @Override
            public ObservableValue<Button> call(TableColumn.CellDataFeatures<Order, Button> arg0) {
                Order item = arg0.getValue();
                Button button = new Button("Prepare");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        item.changeFoodStatusToPreparing();
                        setView(restaurantView);
                    }
                });
                if (item.getFoodStatus() == FoodStatus.PREPARING) {
                    button.setDisable(true);
                } else if (item.getFoodStatus() == FoodStatus.ORDERED) {
                    button.setDisable(false);
                }
                return new SimpleObjectProperty<Button>(button);
            }
        });

        view.doneCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Order, Button>, ObservableValue<Button>>() {
            @Override
            public ObservableValue<Button> call(TableColumn.CellDataFeatures<Order, Button> arg0) {
                Order item = arg0.getValue();
                Button button = new Button("Ready");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        item.changeFoodStatusToReady();
                        setView(restaurantView);
                    }
                });
                if (item.getFoodStatus() == FoodStatus.PREPARING) {
                    button.setDisable(false);
                } else if (item.getFoodStatus() == FoodStatus.ORDERED) {
                    button.setDisable(true);
                }
                return new SimpleObjectProperty<Button>(button);
            }
        });

        ObservableList<Order> orders = FXCollections.observableArrayList();
        Genie.getInstance().getRestaurantPendingOrders(restaurant.getUsername()).forEach(item -> orders.add(item));
        view.oTable.setItems(orders);



        WalletView walletView = new WalletView();

        walletView.walletBalance.setText(String.valueOf(restaurant.getWallet().getBalance()));
        walletView.addMoneyButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    restaurant.getWallet().addBalance(Double.parseDouble(walletView.amount.getText()));
                    walletView.walletBalance.setText(String.valueOf(restaurant.getWallet().getBalance()));
                } catch (Exception e) {
                    Genie.getInstance().showPopup("Invalid amount", "Please enter a valid amount", "OK");
                }
            }
        });

        view.walletTab.setContent(walletView.getView());
    }
}
