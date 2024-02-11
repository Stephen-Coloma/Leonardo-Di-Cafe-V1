package server.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerView {
    //private FXMLLoader loader;
    private Stage stage;

    public ServerView(Stage stage) {
        this.stage = stage;
    } // end of constructor

    public void runInterface() {
        Platform.runLater(() -> {
            try {

                System.out.println("Loading Admin Interface");
                BorderPane root = FXMLLoader.load(getClass().getResource("/fxml/server/MainMenuAdminPage.fxml"));
                Scene scene = new Scene(root);
                stage.setTitle("LEONARDO D' Cafe [ADMIN]");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

    }

    public boolean UIExit() {
        return stage.isShowing();
    }

    /*
    @Override
    public void start(Stage stage) {
        try {
            System.out.println("Loading Admin Interface");
            BorderPane root = FXMLLoader.load(getClass().getResource("/fxml/server/MainMenuAdminPage.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("LEONARDO D' Cafe [ADMIN]");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

     */
} // end of ServerView class
