module i7 {
    requires javafx.controls;
    requires javafx.fxml;

    opens i7 to javafx.fxml;
    exports i7;
}
