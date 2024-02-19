package client.view.fxmlview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.io.IOException;

public class MainMenuClientPageView {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button logoutButton;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label accountNameLabel;
    @FXML
    private ImageView cartImage;
    @FXML
    private Button clearCartButton;

    @FXML
    private Label cartLabel1;

    @FXML
    private Label cartLabel2;

    @FXML
    private Button checkoutButton;

    @FXML
    private GridPane gridPaneCart;

    @FXML
    private ScrollPane scrollPaneCart;
    @FXML
    private GridPane gridPaneMenu;
    @FXML
    private ScrollPane scrollPaneMenu;

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
    private TextField productSearchBar;

    @FXML
    private Pane loadingIndicatorPanel;


    /**This method accepts the action listener implementation for foodButton from the main_menu_client_page controller*/
    public void setActionMainMenuFoodButton(EventHandler<ActionEvent> event){
        mainMenuFoodButton.setOnAction(event);
    }
    /**This method accepts the action listener implementation for beverageButton from the main_menu_client_page controller*/
    public void setActionMainMenuBeverageButton(EventHandler<ActionEvent> event){
        mainMenuBeveragesButton.setOnAction(event);
    }
    public void setActionClearCartButton(EventHandler<ActionEvent> event){
        clearCartButton.setOnAction(event);
    }

    public void setUpActionCheckoutButton(EventHandler<ActionEvent> event){
        checkoutButton.setOnAction(event);
    }

    public void setUpActionOrderHistoryButton(EventHandler<ActionEvent> event){
        orderHistoryMenuButton.setOnAction(event);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/select_food.fxml"));
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

    public Button getOrderHistoryButton() {
        return orderHistoryMenuButton;
    }

    public void setOrderHistoryButton(Button orderHistoryMenuButton) {
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

    public Button getClearCartButton() {
        return clearCartButton;
    }

    public void setClearCartButton(Button clearCartButton) {
        this.clearCartButton = clearCartButton;
    }

    public Label getAccountNameLabel() {
        return accountNameLabel;
    }

    public void setAccountNameLabel(Label accountNameLabel) {
        this.accountNameLabel = accountNameLabel;
    }

    public void setDateLabel(String value) {
        dateLabel.setText(value);
    }

    public void setTimeLabel(String value) {
        timeLabel.setText(value);
    }

    public Label getDateLabel() {
        return dateLabel;
    }

    public void setDateLabel(Label dateLabel) {
        this.dateLabel = dateLabel;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }

    public void setTimeLabel(Label timeLabel) {
        this.timeLabel = timeLabel;
    }

    public TextField getProductSearchBar() {
        return productSearchBar;
    }

    public Pane getLoadingIndicatorPanel() {
        return loadingIndicatorPanel;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }

    public void setLogoutButton(Button logoutButton) {
        this.logoutButton = logoutButton;
    }

    public Button getOrderHistoryMenuButton() {
        return orderHistoryMenuButton;
    }

    public void setOrderHistoryMenuButton(Button orderHistoryMenuButton) {
        this.orderHistoryMenuButton = orderHistoryMenuButton;
    }

    public void setProductSearchBar(TextField productSearchBar) {
        this.productSearchBar = productSearchBar;
    }

    public void setLoadingIndicatorPanel(Pane loadingIndicatorPanel) {
        this.loadingIndicatorPanel = loadingIndicatorPanel;
    }
}
