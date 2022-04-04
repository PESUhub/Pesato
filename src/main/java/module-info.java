module i7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens i7 to javafx.fxml;
    opens i7.Controllers to javafx.fxml;
    opens i7.Views to javafx.fxml;
    //opens i7.Models to javafx.base;
    exports i7;
}
