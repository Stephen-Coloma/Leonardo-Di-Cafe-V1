package client.view.fxmlview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

public class SelectBeverageVariationView {
    @FXML
    private Label smallTotalPriceLabel;
    @FXML
    private CheckBox smallCheckBox;

    @FXML
    private CheckBox mediumCheckBox;

    @FXML
    private CheckBox largeCheckBox;

    @FXML
    private Label mediumTotalPriceLabel;
    @FXML
    private Label largeTotalPriceLabel;
    @FXML
    private Label noticeLabel;
    @FXML
    private Button addToCartButton;

    @FXML
    private Button largeDecrementButton;

    @FXML
    private Button largeIncrementButton;

    @FXML
    private Label largeQuantityLabel;

    @FXML
    private Label largeStockLabel;

    @FXML
    private Button mediumDecrementButton;

    @FXML
    private Button mediumIncrementButton;

    @FXML
    private Label mediumQuantityLabel;

    @FXML
    private Label mediumStockLabel;

    @FXML
    private Label productAvailabilityLabel;

    @FXML
    private Label productDetailsLabel;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label productPriceLabel;

    @FXML
    private Button smallDecrementButton;

    @FXML
    private Button smallIncrementButton;


    @FXML
    private Label smallQuantityLabel;

    @FXML
    private Label smallStockLabel;

    public void setActionAddToCartButton(EventHandler<ActionEvent> event){
        addToCartButton.setOnAction(event);
    }

    public void setActionSmallDecrementButton(EventHandler<ActionEvent> event){
        smallDecrementButton.setOnAction(event);
    }
    public void setActionSmallIncrementButton(EventHandler<ActionEvent> event){
        smallIncrementButton.setOnAction(event);
    }

    public void setActionMediumDecrementButton(EventHandler<ActionEvent> event){
        mediumDecrementButton.setOnAction(event);
    }
    public void setActionMediumIncrementButton(EventHandler<ActionEvent> event){
        mediumIncrementButton.setOnAction(event);
    }

    public void setActionLargeDecrementButton(EventHandler<ActionEvent> event){
        largeDecrementButton.setOnAction(event);
    }
    public void setActionLargeIncrementButton(EventHandler<ActionEvent> event){
        largeIncrementButton.setOnAction(event);
    }


    public Button getAddToCartButton() {
        return addToCartButton;
    }

    public void setAddToCartButton(Button addToCartButton) {
        this.addToCartButton = addToCartButton;
    }

    public Button getLargeDecrementButton() {
        return largeDecrementButton;
    }

    public void setLargeDecrementButton(Button largeDecrementButton) {
        this.largeDecrementButton = largeDecrementButton;
    }

    public Button getLargeIncrementButton() {
        return largeIncrementButton;
    }

    public void setLargeIncrementButton(Button largeIncrementButton) {
        this.largeIncrementButton = largeIncrementButton;
    }

    public Label getLargeQuantityLabel() {
        return largeQuantityLabel;
    }

    public void setLargeQuantityLabel(Label largeQuantityLabel) {
        this.largeQuantityLabel = largeQuantityLabel;
    }

    public Label getLargeStockLabel() {
        return largeStockLabel;
    }

    public void setLargeStockLabel(Label largeStockLabel) {
        this.largeStockLabel = largeStockLabel;
    }

    public Button getMediumDecrementButton() {
        return mediumDecrementButton;
    }

    public void setMediumDecrementButton(Button mediumDecrementButton) {
        this.mediumDecrementButton = mediumDecrementButton;
    }

    public Button getMediumIncrementButton() {
        return mediumIncrementButton;
    }

    public void setMediumIncrementButton(Button mediumIncrementButton) {
        this.mediumIncrementButton = mediumIncrementButton;
    }

    public Label getMediumQuantityLabel() {
        return mediumQuantityLabel;
    }

    public void setMediumQuantityLabel(Label mediumQuantityLabel) {
        this.mediumQuantityLabel = mediumQuantityLabel;
    }

    public Label getMediumStockLabel() {
        return mediumStockLabel;
    }

    public void setMediumStockLabel(Label mediumStockLabel) {
        this.mediumStockLabel = mediumStockLabel;
    }

    public Label getProductAvailabilityLabel() {
        return productAvailabilityLabel;
    }

    public void setProductAvailabilityLabel(Label productAvailabilityLabel) {
        this.productAvailabilityLabel = productAvailabilityLabel;
    }

    public Label getProductDetailsLabel() {
        return productDetailsLabel;
    }

    public void setProductDetailsLabel(Label productDetailsLabel) {
        this.productDetailsLabel = productDetailsLabel;
    }

    public ImageView getProductImage() {
        return productImage;
    }

    public void setProductImage(ImageView productImage) {
        this.productImage = productImage;
    }

    public Label getProductNameLabel() {
        return productNameLabel;
    }

    public void setProductNameLabel(Label productNameLabel) {
        this.productNameLabel = productNameLabel;
    }

    public Label getProductPriceLabel() {
        return productPriceLabel;
    }

    public void setProductPriceLabel(Label productPriceLabel) {
        this.productPriceLabel = productPriceLabel;
    }

    public Button getSmallDecrementButton() {
        return smallDecrementButton;
    }

    public void setSmallDecrementButton(Button smallDecrementButton) {
        this.smallDecrementButton = smallDecrementButton;
    }

    public Button getSmallIncrementButton() {
        return smallIncrementButton;
    }

    public void setSmallIncrementButton(Button smallIncrementButton) {
        this.smallIncrementButton = smallIncrementButton;
    }

    public Label getSmallQuantityLabel() {
        return smallQuantityLabel;
    }

    public void setSmallQuantityLabel(Label smallQuantityLabel) {
        this.smallQuantityLabel = smallQuantityLabel;
    }

    public Label getSmallStockLabel() {
        return smallStockLabel;
    }

    public void setSmallStockLabel(Label smallStockLabel) {
        this.smallStockLabel = smallStockLabel;
    }

    public Label getNoticeLabel() {
        return noticeLabel;
    }

    public void setNoticeLabel(Label noticeLabel) {
        this.noticeLabel = noticeLabel;
    }

    public Label getSmallTotalPriceLabel() {
        return smallTotalPriceLabel;
    }

    public void setSmallTotalPriceLabel(Label smallTotalPriceLabel) {
        this.smallTotalPriceLabel = smallTotalPriceLabel;
    }

    public Label getMediumTotalPriceLabel() {
        return mediumTotalPriceLabel;
    }

    public void setMediumTotalPriceLabel(Label mediumTotalPriceLabel) {
        this.mediumTotalPriceLabel = mediumTotalPriceLabel;
    }

    public Label getLargeTotalPriceLabel() {
        return largeTotalPriceLabel;
    }

    public void setLargeTotalPriceLabel(Label largeTotalPriceLabel) {
        this.largeTotalPriceLabel = largeTotalPriceLabel;
    }

    public CheckBox getSmallCheckBox() {
        return smallCheckBox;
    }

    public void setSmallCheckBox(CheckBox smallCheckBox) {
        this.smallCheckBox = smallCheckBox;
    }

    public CheckBox getMediumCheckBox() {
        return mediumCheckBox;
    }

    public void setMediumCheckBox(CheckBox mediumCheckBox) {
        this.mediumCheckBox = mediumCheckBox;
    }

    public CheckBox getLargeCheckBox() {
        return largeCheckBox;
    }

    public void setLargeCheckBox(CheckBox largeCheckBox) {
        this.largeCheckBox = largeCheckBox;
    }

    public void addToCartButtonEntered(MouseEvent event){
        addToCartButton.setStyle("-fx-background-color: lightgray;");
        addToCartButton.setTextFill(Paint.valueOf("Black"));
    }
    public void addToCartButtonExited(MouseEvent event){
        addToCartButton.setStyle("-fx-background-color:  #A38157;");
        addToCartButton.setTextFill(Paint.valueOf("White"));
    }
}
