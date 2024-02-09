package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerTest extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/server/MainMenuAdminPage.fxml"));

        stage.setTitle("Sample");
        stage.setScene(new Scene(root, 807, 455));
        stage.setResizable(false);
        stage.show();
    }
}
