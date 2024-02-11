package server;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import server.model.ServerModel;
import shared.Customer;
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
        List<Customer> listOfCustomerTest = (List<Customer>) XMLUtility.loadXMLData(new File("src/main/java/server/model/customer_account_list.xml"));
        System.out.println(listOfCustomerTest);
        XMLUtility.saveCustomerAccounts(listOfCustomerTest);
    }
}
