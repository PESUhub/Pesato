package i7.Views.CustomerViews;

import i7.Models.Restaurant;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ListRestaurantView {
    Parent view;
    public Button nextButton;

    public TableView<Restaurant> rTable;
    public TableColumn<Restaurant, String> nameCol;
    public TableColumn<Restaurant, String> addrCol;
    public TableColumn<Restaurant, String> cuisineCol;
    public TableColumn<Restaurant, Integer> ratingCol;
    public TableColumn<Restaurant, Button> selectCol;

    public VBox vbox;

    public ListRestaurantView() {
        view = createView();
    }

    public Parent getView() {
        return view;
    }

    public VBox createView() {
        vbox = new VBox();
        nextButton = new Button("Next");

        rTable = new TableView<>();
        rTable.setEditable(false);

        nameCol = new TableColumn<>("Name");
        addrCol = new TableColumn<>("Address");
        cuisineCol = new TableColumn<>("Cuisine");
        ratingCol = new TableColumn<>("Rating");
        selectCol = new TableColumn<>("Select");

        nameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
        addrCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        cuisineCol.setCellValueFactory(new PropertyValueFactory<>("Cuisine"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("Rating"));

        nameCol.setMinWidth(75);
        addrCol.setMinWidth(150);
        cuisineCol.setMinWidth(40);
        ratingCol.setMinWidth(20);
        selectCol.setMinWidth(70);

        rTable.getColumns().addAll(nameCol, addrCol, cuisineCol, ratingCol, selectCol);
        rTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        vbox = new VBox();
        vbox.getChildren().addAll(nextButton, rTable);
        return vbox;
    }
}
