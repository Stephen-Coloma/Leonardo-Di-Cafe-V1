package client;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUITest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);

        Button button = new Button("Click Me");
        Label label = new Label("");

        button.setOnAction(actionEvent -> label.setText("Hello World!"));

        layout.getChildren().addAll(label, button);

        stage.setScene(new Scene(layout, 400, 400));
        stage.setTitle("GUI TEST");
        stage.show();
    }
}
