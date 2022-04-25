package i7.Controllers;


import i7.Models.*;
import i7.Views.CustomerViews.ListMenuItemsView;
import i7.Views.CustomerViews.ListRestaurantView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import i7.Views.*;
import javafx.util.Callback;
import jdk.jfr.Unsigned;

import java.time.LocalDateTime;


public class CustomerController extends UserController {

    private Customer customer;

    Stage stage;

    Scene scene;

    CustomerView customerView;

    ListRestaurantView listRestaurantView;

    ListMenuItemsView listMenuItemsView;
    
    public CustomerController(Customer user, Stage stage, Scene scene, CustomerView view) {
        this.stage = stage;
        this.customer = user;
        this.scene = scene;
        this.customerView = view;
        setCustomerView(view);
    }

    private void setCustomerView(CustomerView view) {
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
                setCustomerView(customerView);
            }
        });

        view.restartOrderingButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        listRestaurantView = new ListRestaurantView();
                        setListRestaurantView(listRestaurantView);
                    }
                });

        listRestaurantView = new ListRestaurantView();
        setListRestaurantView(listRestaurantView);

        WalletView walletView = new WalletView();

        walletView.walletBalance.setText(String.valueOf(customer.getWallet().getBalance()));
        walletView.addMoneyButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    customer.getWallet().addBalance(Double.parseDouble(walletView.amount.getText()));
                    walletView.walletBalance.setText(String.valueOf(customer.getWallet().getBalance()));
                } catch (Exception e) {
                    Genie.getInstance().showPopup("Invalid amount", "Please enter a valid amount", "OK");
                }
            }
        });

        view.walletTab.setContent(walletView.getView());
    }

    private void setListRestaurantView(ListRestaurantView view) {
        view.selectCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Restaurant, Button>, ObservableValue<Button>>() {
            @Override
            public ObservableValue<Button> call(TableColumn.CellDataFeatures<i7.Models.Restaurant, Button> arg0) {
                Restaurant item = arg0.getValue();
                Button button = new Button("Select");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String selectedRestaurant = item.getUsername();
                        customer.initCart(selectedRestaurant);
                        listMenuItemsView = new ListMenuItemsView();
                        setListMenuItemsView(listMenuItemsView, item);
                    }
                });
                return new SimpleObjectProperty<Button>(button);
            }
        });

        ObservableList<Restaurant> restaurants = FXCollections.observableArrayList();
        Genie.getInstance().getUsersByType(UserType.RESTAURANT).forEach(restaurant -> restaurants.add((Restaurant) restaurant));
        view.rTable.setItems(restaurants);

        customerView.placeOrderTab.setContent(view.getView());
    }

    private void setListMenuItemsView(ListMenuItemsView view, Restaurant restaurant) {

        view.nextButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Genie g = Genie.getInstance();
                Order order = new Order(customer.getCurrentCart(), customer.getUsername(), LocalDateTime.now());
                g.addOrder(order);
                g.showPopup("Order Placed", "Your order has been placed!", "OK");

                setCustomerView(customerView);
            }
        });

        view.vegCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CartItem, CheckBox>, ObservableValue<CheckBox>>() {
            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<CartItem, CheckBox> arg0) {
                CartItem item = arg0.getValue();
                CheckBox checkBox = new CheckBox();
                checkBox.selectedProperty().setValue(item.getItem().getVeg());
                checkBox.setDisable(true);
                return new SimpleObjectProperty<CheckBox>(checkBox);
            }
        });

        view.qtCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CartItem, TextField>, ObservableValue<TextField>>() {
            @Override
            public ObservableValue<TextField> call( TableColumn.CellDataFeatures<CartItem, TextField> arg0) {
                CartItem item = arg0.getValue();
                TextField textField = new TextField();

                textField.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            Integer qt = Integer.parseInt(textField.getText());
                            if (qt > 0) {
                                item.setQuantity(qt);
                            } else {
                                Genie.getInstance().showPopup("Error", "Please enter a valid quantity", "OK");
                                textField.setText(item.getQuantity() + "");
                            }
                        } catch (NumberFormatException e) {
                            Genie.getInstance().showPopup("Error", "Please enter a valid quantity", "OK");
                        }
                    }
                });
                textField.setText("0");
                return new SimpleObjectProperty<TextField>(textField);
            }
        });

        customer.getCurrentCart().addItems(restaurant.getMenuItemsObservableList());

        view.miTable.setItems(customer.getCurrentCart().getCartItemsObservableList());

        customerView.placeOrderTab.setContent(view.getView());
    }
}
