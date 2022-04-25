package i7.Views;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class CustomerView {
    Parent view;


    public Button logoutButton;
    public Button reloadButton;
    public Button restartOrderingButton;

    public TabPane tabPane;

    public Tab placeOrderTab;
    public Tab walletTab;


    public CustomerView() {
        view = createView();
    }

    public Parent getView() {
        return view;
    }

    public VBox createView() {
        VBox vBox = new VBox();
        vBox.getChildren().add(logoutButton = new Button("Logout"));
        vBox.getChildren().add(reloadButton = new Button("Reload"));
        vBox.getChildren().add(restartOrderingButton = new Button("Restart Ordering"));

        placeOrderTab = new Tab("Place Order");
        placeOrderTab.setClosable(false);

        walletTab = new Tab("Wallet");
        walletTab.setClosable(false);

        tabPane = new TabPane();
        tabPane.getTabs().addAll(placeOrderTab, walletTab);

        vBox.getChildren().add(tabPane);
        return vBox;
    }

}
