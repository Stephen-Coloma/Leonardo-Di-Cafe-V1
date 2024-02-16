package client.view.fxmlview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

public class SelectFoodView {
    @FXML
    private Label noticeLabel;

    @FXML
    private Button addToCartButton;

    @FXML
    private Button decrementButton;

    @FXML
    private Button incrementButton;

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
    private Label quantityLabel;

    @FXML
    private Label totalPriceLabel;


    public void setActionAddToCartButton(EventHandler<ActionEvent> event){
        addToCartButton.setOnAction(event);
    }

    public void setActionIncrementButton(EventHandler<ActionEvent> event){
        incrementButton.setOnAction(event);
    }
    public void setActionDecrementButton(EventHandler<ActionEvent> event){
        decrementButton.setOnAction(event);
    }

    public Button getAddToCartButton() {
        return addToCartButton;
    }

    public void setAddToCartButton(Button addToCartButton) {
        this.addToCartButton = addToCartButton;
    }

    public Button getDecrementButton() {
        return decrementButton;
    }

    public void setDecrementButton(Button decrementButton) {
        this.decrementButton = decrementButton;
    }

    public Button getIncrementButton() {
        return incrementButton;
    }

    public void setIncrementButton(Button incrementButton) {
        this.incrementButton = incrementButton;
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

    public Label getQuantityLabel() {
        return quantityLabel;
    }

    public void setQuantityLabel(Label quantityLabel) {
        this.quantityLabel = quantityLabel;
    }

    public Label getTotalPriceLabel() {
        return totalPriceLabel;
    }

    public void setTotalPriceLabel(Label totalPriceLabel) {
        this.totalPriceLabel = totalPriceLabel;
    }

    public Label getNoticeLabel() {
        return noticeLabel;
    }

    public void setNoticeLabel(Label noticeLabel) {
        this.noticeLabel = noticeLabel;
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
