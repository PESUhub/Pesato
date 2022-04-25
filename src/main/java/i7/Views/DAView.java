package i7.Views;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class DAView {
    Parent view;
    public Button logoutButton;
    public Button reloadButton;


    public VBox deliverTabContent;

    public Button pickupButton;
    public Button deliverButton;

    public Label deliveryInfo;

    public TabPane tabPane;

    public Tab walletTab;
    public Tab deliverTab;

    public DAView() {
        view = createView();
    }

    public Parent getView() {
        return view;
    }

    public VBox createView() {
        VBox vBox = new VBox();
        vBox.getChildren().add(logoutButton = new Button("Logout"));
        vBox.getChildren().add(reloadButton = new Button("Reload"));

        deliveryInfo = new Label("Delivery Info: ");
        pickupButton = new Button("Pickup");
        deliverButton = new Button("Deliver");

        deliverTabContent = new VBox();
        deliverTabContent.getChildren().addAll(deliveryInfo, pickupButton, deliverButton);

        deliverTab = new Tab("Deliver");
        deliverTab.setClosable(false);
        deliverTab.setContent(deliverTabContent);

        walletTab = new Tab("Wallet");
        walletTab.setClosable(false);

        tabPane = new TabPane();
        tabPane.getTabs().addAll(walletTab, deliverTab);

        vBox.getChildren().add(tabPane);
        return vBox;
    }
}
