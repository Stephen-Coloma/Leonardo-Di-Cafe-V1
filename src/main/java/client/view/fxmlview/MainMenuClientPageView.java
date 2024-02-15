package client.view.fxmlview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import shared.Beverage;
import shared.Food;

import java.io.IOException;
import java.util.HashMap;

public class MainMenuClientPageView {
    private Stage stage;
    private Scene scene;
    private Parent root;



    //this holds the data to be displayed
    private HashMap<String, Food> foodMenu;
    private HashMap<String, Beverage> beverageMenu;

    private ObservableList<Food> foodObservableList;
    private ObservableList<Beverage> beverageObservableList;



    @FXML
    private ImageView cartImage;

    @FXML
    private Label cartLabel1;

    @FXML
    private Label cartLabel2;

    @FXML
    private Button checkoutButton;

    @FXML
    private GridPane gridPaneCart;

    @FXML
    private GridPane gridPaneMenu;

    @FXML
    private Button mainMenuBeveragesButton;

    @FXML
    private Button mainMenuFoodButton;

    @FXML
    private Button orderHistoryMenuButton;

    @FXML
    private Label priceLabel;

    @FXML
    private Label productTypeLabel;

    @FXML
    private ScrollPane scrollPaneCart;

    @FXML
    private ScrollPane scrollPaneMenu;


    /**This method initializes the model to be used in displaying the content of each card in the menu pane*/
    public void initializeFoodMenu(HashMap<String, Food> foodMenu, HashMap<String, Beverage> beverageMenu) {
        this.foodMenu = foodMenu;
        this.beverageMenu = beverageMenu;

        foodObservableList = FXCollections.observableList(foodMenu.values().stream().toList()) ;
        beverageObservableList = FXCollections.observableList(beverageMenu.values().stream().toList()) ;

        int column = 0;
        int row = 1;

        //initializing the food menu cards from the hashmap
        try {
            for (Food product : foodObservableList) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/client/menu_card.fxml"));

                //putting the card on the anchorPane
                AnchorPane card = loader.load();

                MenuCardView menuCardView = loader.getController();
                menuCardView.setData(product);

                if (column == 2){
                    column = 0;
                    row++;
                }
                gridPaneMenu.add(card, column++, row);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**THis loads the beverage menu when the button is clicked*/
    public void loadFoodMenu() {
        int column = 0;
        int row = 1;

        gridPaneMenu.getChildren().clear();

        //initializing the food menu cards from the hashmap
        try {
            for (Food product : foodObservableList) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/client/menu_card.fxml"));

                //putting the card on the anchorPane
                AnchorPane card = loader.load();

                MenuCardView menuCardView = loader.getController();
                menuCardView.setData(product);

                if (column == 2){
                    column = 0;
                    row++;
                }
                gridPaneMenu.add(card, column++, row);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**THis loads the beverage menu when the button is clicked*/
    public void loadBeverageMenu() {

        gridPaneMenu.getChildren().clear();
        int column = 0;
        int row = 1;
        try {
            //initializing the beverage menu cards from the hashmap
            for (Beverage product : beverageObservableList) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/client/menu_card.fxml"));

                //putting the card on the anchorPane
                AnchorPane card = loader.load();

                MenuCardView menuCardView = loader.getController();
                menuCardView.setData(product);


                if (column == 2){
                    column = 0;
                    row++;
                }
                gridPaneMenu.add(card, column++, row);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void showMenuUI (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/main_menu_client_page.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showOrderHistoryUI (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/order_history_page.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/select_variation.fxml"));
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/checkout_page.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
//    public void addToCartButtonEntered(MouseEvent event){
//        addToCartButton.setStyle("-fx-background-color: lightgray;");
//        addToCartButton.setTextFill(Paint.valueOf("Black"));
//    }
//    public void addToCartButtonExited(MouseEvent event){
//        addToCartButton.setStyle("-fx-background-color:  #A38157;");
//        addToCartButton.setTextFill(Paint.valueOf("White"));
//    }

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

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public HashMap<String, Food> getFoodMenu() {
        return foodMenu;
    }

    public void setFoodMenu(HashMap<String, Food> foodMenu) {
        this.foodMenu = foodMenu;
    }

    public HashMap<String, Beverage> getBeverageMenu() {
        return beverageMenu;
    }

    public void setBeverageMenu(HashMap<String, Beverage> beverageMenu) {
        this.beverageMenu = beverageMenu;
    }

    public ImageView getCartImage() {
        return cartImage;
    }

    public void setCartImage(ImageView cartImage) {
        this.cartImage = cartImage;
    }

    public Label getCartLabel1() {
        return cartLabel1;
    }

    public void setCartLabel1(Label cartLabel1) {
        this.cartLabel1 = cartLabel1;
    }

    public Label getCartLabel2() {
        return cartLabel2;
    }

    public void setCartLabel2(Label cartLabel2) {
        this.cartLabel2 = cartLabel2;
    }

    public Button getCheckoutButton() {
        return checkoutButton;
    }

    public void setCheckoutButton(Button checkoutButton) {
        this.checkoutButton = checkoutButton;
    }

    public GridPane getGridPaneCart() {
        return gridPaneCart;
    }

    public void setGridPaneCart(GridPane gridPaneCart) {
        this.gridPaneCart = gridPaneCart;
    }

    public GridPane getGridPaneMenu() {
        return gridPaneMenu;
    }

    public void setGridPaneMenu(GridPane gridPaneMenu) {
        this.gridPaneMenu = gridPaneMenu;
    }

    public Button getMainMenuBeveragesButton() {
        return mainMenuBeveragesButton;
    }

    public void setMainMenuBeveragesButton(Button mainMenuBeveragesButton) {
        this.mainMenuBeveragesButton = mainMenuBeveragesButton;
    }

    public Button getMainMenuFoodButton() {
        return mainMenuFoodButton;
    }

    public void setMainMenuFoodButton(Button mainMenuFoodButton) {
        this.mainMenuFoodButton = mainMenuFoodButton;
    }

    public Button getOrderHistoryMenuButton() {
        return orderHistoryMenuButton;
    }

    public void setOrderHistoryMenuButton(Button orderHistoryMenuButton) {
        this.orderHistoryMenuButton = orderHistoryMenuButton;
    }

    public Label getPriceLabel() {
        return priceLabel;
    }

    public void setPriceLabel(Label priceLabel) {
        this.priceLabel = priceLabel;
    }

    public Label getProductTypeLabel() {
        return productTypeLabel;
    }

    public void setProductTypeLabel(Label productTypeLabel) {
        this.productTypeLabel = productTypeLabel;
    }

    public ScrollPane getScrollPaneCart() {
        return scrollPaneCart;
    }

    public void setScrollPaneCart(ScrollPane scrollPaneCart) {
        this.scrollPaneCart = scrollPaneCart;
    }

    public ScrollPane getScrollPaneMenu() {
        return scrollPaneMenu;
    }

    public void setScrollPaneMenu(ScrollPane scrollPaneMenu) {
        this.scrollPaneMenu = scrollPaneMenu;
    }
}
