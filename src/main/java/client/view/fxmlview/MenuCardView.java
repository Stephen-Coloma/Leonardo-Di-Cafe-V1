package client.view.fxmlview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import shared.Beverage;
import shared.Food;
import shared.Product;

import java.util.HashMap;

public class MenuCardView {
    @FXML
    private Button addProductButton;

    @FXML
    private Label productDetailsLabel;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label productPriceLabel;

    @FXML
    private Label productRatingLabel;
    @FXML
    private Label sizeLabel;
    //the data to be passed

    /*This method implements the action listener for the button*/
    public void setActionAddProductButton(EventHandler<ActionEvent> event){
        addProductButton.setOnAction(event);
    }

    public Button getAddProductButton() {
        return addProductButton;
    }

    public void setAddProductButton(Button addProductButton) {
        this.addProductButton = addProductButton;
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

    public Label getProductRatingLabel() {
        return productRatingLabel;
    }

    public void setProductRatingLabel(Label productRatingLabel) {
        this.productRatingLabel = productRatingLabel;
    }

    public Label getSizeLabel() {
        return sizeLabel;
    }

    public void setSizeLabel(Label sizeLabel) {
        this.sizeLabel = sizeLabel;
    }
}
