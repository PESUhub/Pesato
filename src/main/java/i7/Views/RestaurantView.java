package i7.Views;

import i7.Models.FoodStatus;
import i7.Models.MenuItem;
import i7.Models.Order;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class RestaurantView {
    Parent view;
    public Button logoutButton;
    public Button reloadButton;

    public Tab editMenuItemsTab;
    public Tab walletTab;
    public Tab ordersTab;
    public VBox editMenuItemsTabContent;
    public VBox ordersTabContent;

    public TableView<MenuItem> miTable;
    public TableColumn<MenuItem, String> nameCol;
    public TableColumn<MenuItem, String> descCol;
    public TableColumn<MenuItem, Double> priceCol;
    public TableColumn<MenuItem, Integer> ratingCol;
    public TableColumn<MenuItem, CheckBox> vegCol;
    public TableColumn<MenuItem, Button> deleteCol;


    public TabPane tabPane;

    public TextField nameField;
    public TextField descField;
    public TextField priceField;
    public TextField ratingField;
    public CheckBox vegCheckBox;
    public Button addButton;


    public TableView<Order> oTable;
    public TableColumn<Order, String> cnameCol;
    public TableColumn<Order, FoodStatus> statusCol;
    public TableColumn<Order, Button> prepareCol;
    public TableColumn<Order, Button> doneCol;


    public RestaurantView() {
        view = createView();
    }

    public Parent getView() {
        return view;
    }

    public VBox createView() {
        VBox vBox = new VBox();
        vBox.getChildren().add(logoutButton = new Button("Logout"));
        vBox.getChildren().add(reloadButton = new Button("Reload"));

        ordersTabContent = new VBox();
        ordersTabContent.getChildren().add(createOrdersTable());

        editMenuItemsTabContent = new VBox();
        editMenuItemsTabContent.getChildren().addAll(createMiTable(), createAddMenuItemBox());

        editMenuItemsTab = new Tab("Edit Menu Items");
        editMenuItemsTab.setContent(editMenuItemsTabContent);
        editMenuItemsTab.setClosable(false);

        walletTab = new Tab("Wallet");
        walletTab.setClosable(false);

        ordersTab = new Tab("Orders");
        ordersTab.setContent(ordersTabContent);
        ordersTab.setClosable(false);

        tabPane = new TabPane();
        tabPane.getTabs().addAll(ordersTab, editMenuItemsTab, walletTab);

        vBox.getChildren().addAll(tabPane);
        return vBox;
    }

    public TableView<MenuItem> createMiTable() {
        miTable = new TableView<MenuItem>();
        miTable.setEditable(true);

        // Column headings in the tableChron.
        nameCol = new TableColumn<MenuItem, String>("Name");
        descCol = new TableColumn<MenuItem, String>("Description");
        priceCol = new TableColumn<MenuItem, Double>("Price");
        ratingCol = new TableColumn<MenuItem, Integer>("Rating");
        vegCol = new TableColumn<MenuItem, CheckBox>("Veg");
        deleteCol = new TableColumn<MenuItem, Button>("Delete");

        nameCol.setCellValueFactory(item -> item.getValue().nameProperty());
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        descCol.setCellValueFactory(item -> item.getValue().descriptionProperty());
        descCol.setCellFactory(TextFieldTableCell.forTableColumn());

        priceCol.setCellValueFactory(item -> item.getValue().priceProperty());
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        ratingCol.setCellValueFactory(item -> item.getValue().ratingProperty());
        ratingCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        nameCol.setMinWidth(75);
        descCol.setMinWidth(180);
        priceCol.setMinWidth(30);
        ratingCol.setMinWidth(30);
        vegCol.setMinWidth(30);
        deleteCol.setMinWidth(50);

        miTable.getColumns().addAll(nameCol, descCol, priceCol, ratingCol, vegCol, deleteCol);
        miTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return miTable;
    }

    public HBox createAddMenuItemBox() {
        HBox hBox = new HBox();
        hBox.setSpacing(10);

        nameField = new TextField();
        descField = new TextField();
        priceField = new TextField();
        ratingField = new TextField();
        vegCheckBox = new CheckBox();
        addButton = new Button("Add");

        nameField.setPromptText("Name");
        descField.setPromptText("Description");
        priceField.setPromptText("Price");
        ratingField.setPromptText("Rating");

        hBox.getChildren().addAll(nameField, descField, priceField, ratingField, vegCheckBox, addButton);
        return hBox;
    }

    public TableView<Order> createOrdersTable() {
        oTable = new TableView<Order>();
        oTable.setEditable(false);

        // Column headings in the tableChron.
        cnameCol = new TableColumn<Order, String>("Customer Name");
        statusCol = new TableColumn<Order, FoodStatus>("Status");
        prepareCol = new TableColumn<Order, Button>("Prepare");
        doneCol = new TableColumn<Order, Button>("Done");

        cnameCol.setCellValueFactory(new PropertyValueFactory<>("custname"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("foodStatus"));


        cnameCol.setMinWidth(75);
        statusCol.setMinWidth(100);
        prepareCol.setMinWidth(50);
        doneCol.setMinWidth(50);

        oTable.getColumns().addAll(cnameCol, statusCol, prepareCol, doneCol);
        oTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return oTable;
    }
}
