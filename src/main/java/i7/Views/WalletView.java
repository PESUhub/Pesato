package i7.Views;

import i7.Models.CartItem;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class WalletView {
    Parent view;
    public Button addMoneyButton;

    public TextField amount;

    public VBox vbox;

    public Label walletBalance;

    public WalletView() {
        view = createView();
    }

    public Parent getView() {
        return view;
    }

    public VBox createView() {
        addMoneyButton = new Button("Add Money");
        amount = new TextField();

        Label walletBalanceLabel = new Label("Wallet Balance");
        walletBalance = new Label("0");

        vbox = new VBox();
        vbox.getChildren().addAll(walletBalanceLabel, walletBalance, amount, addMoneyButton);
        return vbox;
    }
}
