package server;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import server.model.ServerModel;
import shared.Food;
import util.XMLUtility;

import java.io.File;
import java.util.HashMap;

public class Server extends Application {
    public static void main(String[] args) throws Exception {
       launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ServerModel serverModel = new ServerModel();

        HashMap<String, Food> test = (HashMap<String, Food>) XMLUtility.loadXMLData(new File("src/main/java/server/model/food_menu.xml"));

        XMLUtility.saveFoodMenu(test);

    }
}
