package i7.Views.CustomerViews;

import i7.Models.CartItem;
import i7.Models.MenuItem;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;

public class ListMenuItemsView {
    Parent view;
    public Button nextButton;

    public VBox vbox;

    public TableView<CartItem> miTable;
    public TableColumn<CartItem, String> nameCol;
    public TableColumn<CartItem, String> descCol;
    public TableColumn<CartItem, Double> priceCol;
    public TableColumn<CartItem, Integer> ratingCol;
    public TableColumn<CartItem, CheckBox> vegCol;
    public TableColumn<CartItem, TextField> qtCol;


    public ListMenuItemsView() {
        view = createView();
    }

    public Parent getView() {
        return view;
    }

    public VBox createView() {
        nextButton = new Button("Place Order");

        miTable = new TableView<CartItem>();
        miTable.setEditable(true);

        // Column headings in the tableChron.
        nameCol = new TableColumn<CartItem, String>("Name");
        descCol = new TableColumn<CartItem, String>("Description");
        priceCol = new TableColumn<CartItem, Double>("Price");
        ratingCol = new TableColumn<CartItem, Integer>("Rating");
        vegCol = new TableColumn<CartItem, CheckBox>("Veg");
        qtCol = new TableColumn<CartItem, TextField>("Quantity");

        nameCol.setCellValueFactory(cellData -> cellData.getValue().getItem().nameProperty());
        descCol.setCellValueFactory(cellData -> cellData.getValue().getItem().descriptionProperty());
        priceCol.setCellValueFactory(cellData -> cellData.getValue().getItem().priceProperty());
        ratingCol.setCellValueFactory(cellData -> cellData.getValue().getItem().ratingProperty());

        nameCol.setMinWidth(75);
        descCol.setMinWidth(180);
        priceCol.setMinWidth(30);
        ratingCol.setMinWidth(30);
        vegCol.setMinWidth(30);
        qtCol.setMinWidth(30);

        miTable.getColumns().addAll(nameCol, descCol, priceCol, ratingCol, vegCol, qtCol);
        miTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        vbox = new VBox();
        vbox.getChildren().addAll(nextButton, miTable);
        return vbox;
    }
}
