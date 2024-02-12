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

public class MainController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public Button mainMenuFoodButton;
    @FXML
    public Button mainMenuBeveragesButton;
    @FXML
    private Button orderNowButton;
    @FXML
    private Button orderHistoryButton;

    @FXML
    private Button checkoutButton;

    @FXML
    private Button orderHistoryMenuButton;


    @FXML
    private Button addToCartButton;




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
    public void addToCartButtonEntered(MouseEvent event){
        addToCartButton.setStyle("-fx-background-color: lightgray;");
        addToCartButton.setTextFill(Paint.valueOf("Black"));
    }
    public void addToCartButtonExited(MouseEvent event){
        addToCartButton.setStyle("-fx-background-color:  #A38157;");
        addToCartButton.setTextFill(Paint.valueOf("White"));
    }

    public void foodButtonEntered(MouseEvent event){
        mainMenuFoodButton.setStyle("-fx-background-color: lightgray;");
    }
    public void foodButtonExited(MouseEvent event){
        mainMenuFoodButton.setStyle("-fx-background-color:  #FFFFFF;");
    }

    public void beverageButtonEntered(MouseEvent event){
        mainMenuBeveragesButton.setStyle("-fx-background-color: lightgray;");
    }
    public void beverageButtonExited(MouseEvent event){
        mainMenuBeveragesButton.setStyle("-fx-background-color:  #FFFFFF;");
    }
}
