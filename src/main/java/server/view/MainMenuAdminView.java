package server.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import server.view.accounts.AccountListPageView;
import server.controller.temporarycontroller.AddProductsPageController;
import server.controller.temporarycontroller.AnalyticsPageController;
import server.controller.temporarycontroller.OrdersListPageController;
import server.view.inventory.InventoryPageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuAdminView {

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

    public void logout() {
        System.exit(0);
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

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void returnToHomePage() {
        borderPane.setCenter(anchorPane);
    }
} // end of MainMenuAdminView class
