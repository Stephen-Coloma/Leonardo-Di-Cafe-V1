package client.view.fxmlview;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CheckoutItemCardView {

    @FXML
    private Pane checkoutItemCard;

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemName;

    @FXML
    private Label itemPriceLabel;

    @FXML
    private Label itemQuantity;

    @FXML
    private Label itemSizeLabel;

    public Pane getCheckoutItemCard() {
        return checkoutItemCard;
    }

    public void setCheckoutItemCard(Pane checkoutItemCard) {
        this.checkoutItemCard = checkoutItemCard;
    }

    public ImageView getItemImage() {
        return itemImage;
    }

    public void setItemImage(ImageView itemImage) {
        this.itemImage = itemImage;
    }

    public Label getItemName() {
        return itemName;
    }

    public void setItemName(Label itemName) {
        this.itemName = itemName;
    }

    public Label getItemPriceLabel() {
        return itemPriceLabel;
    }

    public void setItemPriceLabel(Label itemPriceLabel) {
        this.itemPriceLabel = itemPriceLabel;
    }

    public Label getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Label itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Label getItemSizeLabel() {
        return itemSizeLabel;
    }

    public void setItemSizeLabel(Label itemSizeLabel) {
        this.itemSizeLabel = itemSizeLabel;
    }
}
