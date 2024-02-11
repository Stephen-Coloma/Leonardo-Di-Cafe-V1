package server;

import javafx.application.Application;
import javafx.stage.Stage;
import server.model.ServerModel;

public class Server extends Application {
    public static void main(String[] args) throws Exception {
       launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ServerModel serverModel = new ServerModel();
    }
}
