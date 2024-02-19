package server.view;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerView {
    private FXMLLoader loader;
    private final Stage stage;

    public ServerView(Stage stage) {
        this.stage = stage;
    } // end of constructor

    public void runInterface() {
        Platform.runLater(() -> {
            try {
                System.out.println("Loading Admin Interface");
                loader = new FXMLLoader(getClass().getResource("/fxml/server/main_menu_admin_page.fxml"));
                BorderPane root = loader.load();
                Scene scene = new Scene(root);
                stage.setTitle("LEONARDO D' Cafe [ADMIN]");
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

    public boolean UIExit() {
        return stage.isShowing();
    }

    public Stage getStage() {
        return stage;
    }
} // end of ServerView class
