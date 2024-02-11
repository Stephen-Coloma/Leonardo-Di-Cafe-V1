package server;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import server.model.ServerModel;
import shared.Beverage;
import shared.Customer;
import shared.Food;
import util.XMLUtility;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class Server extends Application {
    public static void main(String[] args) throws Exception {
       launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ServerModel serverModel = new ServerModel();
        List<Customer> listOfCustomerTest = (List<Customer>) XMLUtility.loadXMLData(new File("src/main/java/server/model/customer_account_list.xml"));
        XMLUtility.saveCustomerAccounts(listOfCustomerTest);

        HashMap<String, Food> foodHashMap = (HashMap<String, Food>) XMLUtility.loadXMLData(new File("src/main/java/server/model/food_menu.xml"));
        XMLUtility.saveFoodMenu(foodHashMap);

        HashMap<String, Beverage> beverageHashMap = (HashMap<String, Beverage>) XMLUtility.loadXMLData(new File("src/main/java/server/model/beverage_menu.xml"));
        XMLUtility.saveBeverageMenu(beverageHashMap);

    }
}
