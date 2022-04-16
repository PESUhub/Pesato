package i7.Views;

import i7.Models.MenuItem;
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

    public Tab editMenuItemsTab;
    public VBox editMenuItemsTabContent;

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


    public RestaurantView() {
        view = createView();
    }

    public Parent getView() {
        return view;
    }

    public VBox createView() {
        VBox vBox = new VBox();
        vBox.getChildren().add(logoutButton = new Button("Logout"));

        editMenuItemsTabContent = new VBox();

        editMenuItemsTab = new Tab("Edit Menu Items");
        editMenuItemsTab.setContent(editMenuItemsTabContent);
        editMenuItemsTab.setClosable(false);

        editMenuItemsTabContent.getChildren().add(createMiTable());
        editMenuItemsTabContent.getChildren().add(createAddMenuItemBox());

        tabPane = new TabPane();
        tabPane.getTabs().addAll(editMenuItemsTab);

        vBox.getChildren().add(tabPane);
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

        nameCol.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("Name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        descCol.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("Description"));
        descCol.setCellFactory(TextFieldTableCell.forTableColumn());

        priceCol.setCellValueFactory(new PropertyValueFactory<MenuItem, Double>("Price"));
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        ratingCol.setCellValueFactory(new PropertyValueFactory<MenuItem, Integer>("Rating"));
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
}
