package i7.Controllers;

import i7.Models.*;
import i7.Views.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class DAController extends UserController{

    private DA da;

    private DAView daView;

    public DAController(DA user, DAView view, Stage stage) {
        this.stage = stage;
        this.da = user;
        this.daView = view;
        setView(view);
    }

    private void setView(DAView view) {
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
                da = (DA) Genie.getInstance().getUser(da.getUsername());
                setView(daView);
            }
        });

        if (da.isOccupied()) {
            FoodStatus status = Genie.getInstance().getOrder(da.getOrderID()).getFoodStatus();
            view.deliveryInfo.setText("Delivery: " + da.getOrderID() + " Status: " + status.toString());
            if (status == FoodStatus.READY) {
                view.pickupButton.setDisable(false);
                view.deliverButton.setDisable(true);
            } else if (status == FoodStatus.PICKEDUP) {
                view.pickupButton.setDisable(true);
                view.deliverButton.setDisable(false);
            } else {
                view.deliveryInfo.setText("Delivery: " + da.getOrderID() + " Status: " + status.toString());
                view.pickupButton.setDisable(true);
                view.deliverButton.setDisable(true);
            }
        } else {
            view.deliveryInfo.setText("No delivery");
            view.pickupButton.setDisable(true);
            view.deliverButton.setDisable(true);
        }

        view.pickupButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Genie.getInstance().getOrder(da.getOrderID()).changeFoodStatusToPickedup();
                setView(view);
            }
        });

        view.deliverButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Genie.getInstance().getOrder(da.getOrderID()).changeFoodStatusToDelivered();
                da.unassignJob();
                setView(view);
            }
        });

        WalletView walletView = new WalletView();

        walletView.walletBalance.setText(String.valueOf(da.getWallet().getBalance()));
        walletView.addMoneyButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    da.getWallet().addBalance(Double.parseDouble(walletView.amount.getText()));
                    walletView.walletBalance.setText(String.valueOf(da.getWallet().getBalance()));
                } catch (Exception e) {
                    Genie.getInstance().showPopup("Invalid amount", "Please enter a valid amount", "OK");
                }
            }
        });

        view.walletTab.setContent(walletView.getView());
    }
}
