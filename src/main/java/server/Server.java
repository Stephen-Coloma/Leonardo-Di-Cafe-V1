package server;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import server.model.ServerModel;
import shared.Order;
import util.XMLUtility;

import java.io.File;
import java.util.List;

public class Server extends Application {
    public static void main(String[] args) throws Exception {
       launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ServerModel serverModel = new ServerModel();

        List<Order> test = (List<Order>) XMLUtility.loadXMLData(new File("src/main/java/server/model/order_list.xml"));

        XMLUtility.saveOrders(test);
    }
}
