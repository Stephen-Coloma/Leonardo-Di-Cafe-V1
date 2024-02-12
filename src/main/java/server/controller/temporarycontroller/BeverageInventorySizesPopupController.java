package server.controller.temporarycontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BeverageInventorySizesPopupController {

    @FXML
    private Button acceptButton;
    @FXML
    private Button smallIncrementButton;
    @FXML
    private Button mediumIncrementButton;
    @FXML
    private Button largeIncrementButton;
    @FXML
    private Label smallQuantity;
    @FXML
    private Label mediumQuantity;
    @FXML
    private Label largeQuantity;

    public Button getAcceptButton() {
        return acceptButton;
    }

    public Button getSmallIncrementButton() {
        return smallIncrementButton;
    }

    public Button getMediumIncrementButton() {
        return mediumIncrementButton;
    }

    public Button getLargeIncrementButton() {
        return largeIncrementButton;
    }

    public void setSmallQuantity(String amount) {
        smallQuantity.setText(amount);
    }

    public void setMediumQuantity(String amount) {
        mediumQuantity.setText(amount);
    }

    public void setLargeQuantity(String amount) {
        largeQuantity.setText(amount);
    }
} // end of BeverageInventorySizesPopupController
