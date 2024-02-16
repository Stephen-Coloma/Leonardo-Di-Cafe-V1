package client.view.fxmlview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CartItemCardView {

    @FXML
    private Label priceLabel;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label quantityLabel;


    @FXML
    private Label sizeLabel;

    public Label getPriceLabel() {
        return priceLabel;
    }

    public void setPriceLabel(Label priceLabel) {
        this.priceLabel = priceLabel;
    }

    public Label getProductNameLabel() {
        return productNameLabel;
    }

    public void setProductNameLabel(Label productNameLabel) {
        this.productNameLabel = productNameLabel;
    }

    public Label getQuantityLabel() {
        return quantityLabel;
    }

    public void setQuantityLabel(Label quantityLabel) {
        this.quantityLabel = quantityLabel;
    }


    public Label getSizeLabel() {
        return sizeLabel;
    }

    public void setSizeLabel(Label sizeLabel) {
        this.sizeLabel = sizeLabel;
    }
}
