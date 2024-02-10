package client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button loginButton;
    @FXML
    private Button loginButtonFrontPage;
    @FXML
    private Button registerButtonFrontPage;
    @FXML
    private Button createAccountButton;

    @FXML
    private Button orderNowButton;
    @FXML
    private Button orderHistoryButton;

    @FXML
    private Button checkoutButton;

    @FXML
    private Button orderHistoryMenuButton;

    @FXML
    private Button placeOrderButton;

    @FXML
    private Button addToCartButton;

    @FXML
    private Button orderHistoryExit;


    public void showLoginUI (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/loginPage.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showRegistrationUI (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/signup.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showFrontPage (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/secondFrontPage.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showMenuUI (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/clientMenu.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showOrderHistoryUI (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/orderHistory.fxml"));
        root = loader.load();

        Stage selectVariationStage = new Stage();
        Scene selectVariationScene = new Scene(root);

        selectVariationStage.initOwner(stage);

        selectVariationStage.initModality(Modality.APPLICATION_MODAL);

        selectVariationStage.initStyle(StageStyle.DECORATED);

        selectVariationStage.setScene(selectVariationScene);
        selectVariationStage.show();
    }

    public void orderHistoryExit(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void showSelectVariationUI(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/selectVariation.fxml"));
        root = loader.load();

        Stage selectVariationStage = new Stage();
        Scene selectVariationScene = new Scene(root);

        selectVariationStage.initOwner(((Node)event.getSource()).getScene().getWindow()); // Get the owner window

        selectVariationStage.initModality(Modality.APPLICATION_MODAL);
        selectVariationStage.initStyle(StageStyle.DECORATED);

        selectVariationStage.setScene(selectVariationScene);
        selectVariationStage.showAndWait(); // Use showAndWait if you want to make it modal
    }

    public void addToCart(ActionEvent event) throws IOException {
        showSelectVariationUI(event);

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void showCheckoutUI (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/checkout.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void placeOrder (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/clientMenu.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void loginFrontPageButtonEntered(MouseEvent event){
        loginButtonFrontPage.setStyle("-fx-background-color: lightgray;");
        loginButtonFrontPage.setTextFill(Paint.valueOf("Black"));
    }
    public void loginFrontPageButtonExited(MouseEvent event){
        loginButtonFrontPage.setStyle("-fx-background-color:  #A38157;");
        loginButtonFrontPage.setTextFill(Paint.valueOf("White"));
    }
    public void registerFrontPageButtonEntered(MouseEvent event){
        registerButtonFrontPage.setStyle("-fx-background-color: lightgray;");
        registerButtonFrontPage.setTextFill(Paint.valueOf("Black"));
    }
    public void registerFrontPageButtonExited(MouseEvent event){
        registerButtonFrontPage.setStyle("-fx-background-color:  #A38157;");
        registerButtonFrontPage.setTextFill(Paint.valueOf("White"));
    }

    public void loginPageButtonEntered(MouseEvent event){
        loginButton.setStyle("-fx-background-color: lightgray;");
        loginButton.setTextFill(Paint.valueOf("Black"));
    }
    public void loginPageButtonExited(MouseEvent event){
        loginButton.setStyle("-fx-background-color:  #A38157;");
        loginButton.setTextFill(Paint.valueOf("White"));
    }

    public void createAccountButtonEntered(MouseEvent event){
        createAccountButton.setStyle("-fx-background-color: lightgray;");
        createAccountButton.setTextFill(Paint.valueOf("Black"));
    }
    public void createAccountButtonExited(MouseEvent event){
        createAccountButton.setStyle("-fx-background-color:  #A38157;");
        createAccountButton.setTextFill(Paint.valueOf("White"));
    }
    public void orderNowButtonEntered(MouseEvent event){
        orderNowButton.setStyle("-fx-background-color: lightgray;");
        orderNowButton.setTextFill(Paint.valueOf("Black"));
    }
    public void orderNowButtonExited(MouseEvent event){
        orderNowButton.setStyle("-fx-background-color:  #A38157;");
        orderNowButton.setTextFill(Paint.valueOf("White"));
    }
    public void orderHistoryButtonEntered(MouseEvent event){
        orderHistoryButton.setStyle("-fx-background-color: lightgray;");
        orderHistoryButton.setTextFill(Paint.valueOf("Black"));
    }
    public void orderHistoryButtonExited(MouseEvent event){
        orderHistoryButton.setStyle("-fx-background-color:  #A38157;");
        orderHistoryButton.setTextFill(Paint.valueOf("White"));
    }
    public void checkoutButtonEntered(MouseEvent event){
        checkoutButton.setStyle("-fx-background-color: lightgray;");
        checkoutButton.setTextFill(Paint.valueOf("Black"));
    }
    public void checkoutButtonExited(MouseEvent event){
        checkoutButton.setStyle("-fx-background-color:  #A38157;");
        checkoutButton.setTextFill(Paint.valueOf("White"));
    }
    public void orderHistoryMenuButtonEntered(MouseEvent event){
        orderHistoryMenuButton.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-radius: 3");
        orderHistoryMenuButton.setTextFill(Paint.valueOf("black"));
    }
    public void orderHistoryMenuButtonExited(MouseEvent event){
        orderHistoryMenuButton.setStyle("-fx-background-color:  #FFFFFF; -fx-border-color: #A38157; -fx-border-radius: 3");
        orderHistoryMenuButton.setTextFill(Paint.valueOf("#A38157"));
    }

    public void placeOrderButtonEntered(MouseEvent event){
        placeOrderButton.setStyle("-fx-background-color: lightgray;");
        placeOrderButton.setTextFill(Paint.valueOf("Black"));
    }
    public void placeOrderButtonExited(MouseEvent event){
        placeOrderButton.setStyle("-fx-background-color:  #A38157;");
        placeOrderButton.setTextFill(Paint.valueOf("White"));
    }

    public void addToCartButtonEntered(MouseEvent event){
        addToCartButton.setStyle("-fx-background-color: lightgray;");
        addToCartButton.setTextFill(Paint.valueOf("Black"));
    }
    public void addToCartButtonExited(MouseEvent event){
        addToCartButton.setStyle("-fx-background-color:  #A38157;");
        addToCartButton.setTextFill(Paint.valueOf("White"));
    }

    public void orderHistoryExitButtonEntered(MouseEvent event){
        orderHistoryExit.setStyle("-fx-background-color: white;");
        orderHistoryExit.setTextFill(Paint.valueOf("red"));
    }
    public void orderHistoryExitButtonExited(MouseEvent event){
        orderHistoryExit.setStyle("-fx-background-color:  #FFFFFF;");
        orderHistoryExit.setTextFill(Paint.valueOf("Black"));
    }
}
