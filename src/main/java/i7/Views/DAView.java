package i7.Views;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class DAView {
    Parent view;
    public Button logoutButton;

    public DAView() {
        view = createView();
    }

    public Parent getView() {
        return view;
    }

    public VBox createView() {
        VBox vBox = new VBox();

        vBox.getChildren().add(logoutButton = new Button("Logout"));
        return vBox;
    }
}
