package client.view;

import client.controller.FrontPageController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientView {
    private FXMLLoader loader;
    private Stage stage;
    public ClientView(Stage stage) {
        this.stage = stage;
    }
    public void runInterface() {
        Platform.runLater(() -> {
            try {
                System.out.println("Loading Client Interface");
                loader = new FXMLLoader(getClass().getResource("/fxml/client/frontPage.fxml"));
                AnchorPane root = loader.load();
                Scene scene = new Scene(root);
                stage.setTitle("LEONARDO D' Cafe [CLIENT]");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    public FXMLLoader getLoader() {
        return loader;
    }
}
