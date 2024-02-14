package client;

import client.controller.ClientController;
import client.model.ClientModel;
import client.view.ClientView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Client extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        ClientModel model = new ClientModel();
        ClientView view = new ClientView(stage);
        view.runInterface();
        ClientController controller = new ClientController(model, view);
    }
}
