package server.view.inventory;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FoodInventoryPopupVIew {

    @FXML
    private Label quantity;
    @FXML
    private Label totalQuantity;
    @FXML
    private Button incrementButton;
    @FXML
    private Button decrementButton;
    @FXML
    private Button acceptButton;

    public Label getQuantity() {
        return quantity;
    }

    public void setQuantity(String value) {
        quantity.setText(value);
    }

    public void setTotalQuantity(String value) {
        totalQuantity.setText(value);
    }

    public Button getIncrementButton() {
        return incrementButton;
    }

    public Button getDecrementButton() {
        return decrementButton;
    }

    public Button getAcceptButton() {
        return acceptButton;
    }

    public void setIncrementButtonMouseEntered() {
        incrementButton.setStyle("-fx-background-color: #9a7133;");
    }

    public void setDecrementButtonMouseEntered() {
        decrementButton.setStyle("-fx-background-color: #9a7133;");
    }

    public void setAcceptButtonMouseEntered() {
        acceptButton.setStyle("-fx-background-color: #71d079");
    }

    public void setIncrementButtonMouseExited() {
        incrementButton.setStyle("-fx-background-color: #634921;");
    }

    public void setDecrementButtonMouseExited() {
        decrementButton.setStyle("-fx-background-color: #634921;");
    }

    public void setAcceptButtonMouseExited() {
        acceptButton.setStyle("-fx-background-color: #5dae65");
    }
} // end of FoodInventoryPopupController class
