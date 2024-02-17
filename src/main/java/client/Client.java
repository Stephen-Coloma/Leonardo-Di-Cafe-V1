package client;

import client.controller.ClientControllerNew;
import client.model.ClientModel;
import client.view.ClientView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Client extends Application {
    public static final String IP_ADDRESS = "localhost";
    public static final int PORT = 2000;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        ClientModel model = new ClientModel();
        ClientView view = new ClientView(stage);
        view.runInterface();
        ClientControllerNew controller = new ClientControllerNew(model, view);
    }
}
