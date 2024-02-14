package server.controller.temporarycontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import server.controller.temporarycontroller.inventory.InventoryPageController;

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
    private Button homeButton;
    @FXML
    private Button viewOrderButton;
    @FXML
    private Button viewAccountsButton;
    @FXML
    private Button viewInventoryButton;
    @FXML
    private Button viewAnalyticsButton;
    @FXML
    private Button addProductsPageButton;
    @FXML
    private Button logoutButton;
    @FXML
    private AccountsListPageController accountsListPageController;
    @FXML
    private AddProductsPageController addProductsPageController;
    @FXML
    private AnalyticsPageController analyticsPageController;
    @FXML
    private InventoryPageController inventoryPageController;
    @FXML
    private OrdersListPageController ordersListPageController;
    private FXMLLoader loader;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private FXMLLoader loadPage(String page){
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("/fxml/server/"+page+".fxml"));
            root = loader.load();
            //Parent root = FXMLLoader.load(getClass().getResource("/fxml/server/"+page+".fxml"));
        } catch (IOException e) {
            Logger.getLogger(MainMenuAdminController.class.getName()).log(Level.SEVERE,null, e);
        }
        borderPane.setCenter(root);
        return loader;
    }

    public void viewHomePage() {
        borderPane.setCenter(anchorPane);
    }
    public void viewOrderPage() {
        loader = loadPage("OrdersListPage");
        ordersListPageController = loader.getController();
    }

    public void viewAccountsPage() {
        loader = loadPage("AccountsListPage");
        accountsListPageController = loader.getController();
    }
    public void viewAddProductsPage() {
       loader = loadPage("AddProductsPage");
       addProductsPageController = loader.getController();
    }
    public void viewInventoryPage() {
        loader = loadPage("inventory/InventoryPage");
        inventoryPageController = loader.getController();
    }
    public void viewAnalyticsPage() {
        loader = loadPage("AnalyticsPage");
        analyticsPageController = loader.getController();
    }
    public void logout() {
        System.exit(0);
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public AccountsListPageController getAccountsListPageController() {
        return accountsListPageController;
    }

    public AddProductsPageController getAddProductsPageController() {
        return addProductsPageController;
    }

    public AnalyticsPageController getAnalyticsPageController() {
        return analyticsPageController;
    }

    public InventoryPageController getInventoryPageController() {
        return inventoryPageController;
    }

    public OrdersListPageController getOrdersListPageController() {
        return ordersListPageController;
    }

    public Button getHomeButton() {
        return homeButton;
    }

    public Button getViewAccountsButton() {
        return viewAccountsButton;
    }

    public Button getViewOrderButton() {
        return viewOrderButton;
    }

    public Button getViewInventoryButton() {
        return viewInventoryButton;
    }

    public Button getViewAnalyticsButton() {
        return viewAnalyticsButton;
    }

    public Button getAddProductsPageButton() {
        return addProductsPageButton;
    }

    public void viewOrdersButtonEntered() {
        viewOrderButton.setStyle("-fx-background-color: lightgray;");
    }

    public void viewOrdersButtonExited() {
        viewOrderButton.setStyle("-fx-background-color: transparent;");
    }
    public void viewAccountsButtonEntered() {
        viewAccountsButton.setStyle("-fx-background-color: lightgray;");
    }
    public void viewAccountsButtonExited() {
        viewAccountsButton.setStyle("-fx-background-color: transparent;");
    }
    public void viewInventoryButtonEntered() {
        viewInventoryButton.setStyle("-fx-background-color: lightgray;");
    }
    public void viewInventoryButtonExited() {
        viewInventoryButton.setStyle("-fx-background-color: transparent;");
    }
    public void addProductsPageButtonEntered() {
        addProductsPageButton.setStyle("-fx-background-color: lightgray;");
    }
    public void addProductsPageButtonExited() {
        addProductsPageButton.setStyle("-fx-background-color: transparent;");
    }
    public void viewAnalyticsButtonEntered() {
        viewAnalyticsButton.setStyle("-fx-background-color: lightgray;");
    }
    public void viewAnalyticsButtonExited() {
        viewAnalyticsButton.setStyle("-fx-background-color: transparent;");
    }

    public void logoutButtonEntered() {
        logoutButton.setStyle("-fx-background-color: #B83B26;");
    }
    public void logoutButtonExited() {
        logoutButton.setStyle("-fx-background-color:  #dc472d;");
    }
    public void homeButtonEntered() {
        homeButton.setStyle("-fx-background-color: lightgray;");
    }

    public void homeButtonExited() {
        homeButton.setStyle("-fx-background-color: transparent;");
    }
}
