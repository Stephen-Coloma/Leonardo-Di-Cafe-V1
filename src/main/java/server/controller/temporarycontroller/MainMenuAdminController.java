package server.controller.temporarycontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.util.logging.Level;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MainMenuAdminController implements Initializable{

    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    Button homeButton;
    @FXML
    Button viewOrderButton;
    @FXML
    Button viewAccountsButton;
    @FXML
    Button viewInventoryButton;
    @FXML
    Button viewAnalyticsButton;
    @FXML
    Button addProductsPageButton;
    @FXML
    Button logoutButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    private void loadPage(String page){
        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource(page+".fxml"));
        } catch (IOException e) {
            Logger.getLogger(MainMenuAdminController.class.getName()).log(Level.SEVERE,null, e);
        }

        borderPane.setCenter(root);
    }


    public void viewHomePage(MouseEvent mouseEvent) throws IOException{
        borderPane.setCenter(anchorPane);
    }
    public void viewOrderPage(MouseEvent actionEvent) throws IOException{
        loadPage("viewOrderPage");
    }

    public void viewAccountsPage(MouseEvent actionEvent) throws IOException{
        loadPage("viewAccountsPage");
    }
    public void viewAddProductsPage(MouseEvent mouseEvent) {
        loadPage("addProductsPage");
    }
    public void logout(MouseEvent actionEvent) {
        System.exit(0);
    }




}
