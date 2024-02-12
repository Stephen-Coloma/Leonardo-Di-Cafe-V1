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

    public void viewHomePage(MouseEvent mouseEvent) throws IOException{
        borderPane.setCenter(anchorPane);
    }
    public void viewOrderPage(MouseEvent actionEvent) throws IOException{
        loader = loadPage("OrdersListPage");
    }

    public void viewAccountsPage(MouseEvent actionEvent) throws IOException{
        loader = loadPage("AccountsListPage");
        accountsListPageController = loader.getController();
    }
    public void viewAddProductsPage(MouseEvent mouseEvent) throws IOException{
       loader = loadPage("AddProductsPage");
       addProductsPageController = loader.getController();
    }
    public void viewInventoryPage(MouseEvent mouseEvent) throws IOException{
        loader = loadPage("InventoryPage");
        inventoryPageController = loader.getController();
    }
    public void viewAnalyticsPage(MouseEvent mouseEvent) throws IOException{
        loader = loadPage("AnalyticsPage");
        analyticsPageController = loader.getController();
    }
    public void logout(MouseEvent actionEvent) {
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

    public void viewOrdersButtonEntered(MouseEvent mouseEvent) {
        viewOrderButton.setStyle("-fx-background-color: lightgray;");
    }

    public void viewOrdersButtonExited(MouseEvent mouseEvent) {
        viewOrderButton.setStyle("-fx-background-color: transparent;");
    }
    public void viewAccountsButtonEntered(MouseEvent mouseEvent) {
        viewAccountsButton.setStyle("-fx-background-color: lightgray;");
    }
    public void viewAccountsButtonExited(MouseEvent mouseEvent) {
        viewAccountsButton.setStyle("-fx-background-color: transparent;");
    }
    public void viewInventoryButtonEntered(MouseEvent mouseEvent) {
        viewInventoryButton.setStyle("-fx-background-color: lightgray;");
    }
    public void viewInventoryButtonExited(MouseEvent mouseEvent) {
        viewInventoryButton.setStyle("-fx-background-color: transparent;");
    }
    public void addProductsPageButtonEntered(MouseEvent mouseEvent) {
        addProductsPageButton.setStyle("-fx-background-color: lightgray;");
    }
    public void addProductsPageButtonExited(MouseEvent mouseEvent) {
        addProductsPageButton.setStyle("-fx-background-color: transparent;");
    }
    public void viewAnalyticsButtonEntered(MouseEvent mouseEvent) {
        viewAnalyticsButton.setStyle("-fx-background-color: lightgray;");
    }
    public void viewAnalyticsButtonExited(MouseEvent mouseEvent) {
        viewAnalyticsButton.setStyle("-fx-background-color: transparent;");
    }

    public void logoutButtonEntered(MouseEvent mouseEvent) {
        logoutButton.setStyle("-fx-background-color: #B83B26;");
    }
    public void logoutButtonExited(MouseEvent mouseEvent) {
        logoutButton.setStyle("-fx-background-color:  #dc472d;");
    }
    public void homeButtonEntered(MouseEvent mouseEvent) {
        homeButton.setStyle("-fx-background-color: lightgray;");
    }

    public void homeButtonExited(MouseEvent mouseEvent) {
        homeButton.setStyle("-fx-background-color: transparent;");
    }
}
