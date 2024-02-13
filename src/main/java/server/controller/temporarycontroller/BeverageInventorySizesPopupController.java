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
    private Button smallDecrementButton;
    @FXML
    private Button mediumIncrementButton;
    @FXML
    private Button mediumDecrementButton;
    @FXML
    private Button largeIncrementButton;
    @FXML
    private Button largeDecrementButton;
    @FXML
    private Label smallQuantity;
    @FXML
    private Label mediumQuantity;
    @FXML
    private Label largeQuantity;
    @FXML
    private Label totalSmallQuantityLabel;
    @FXML
    private Label totalMediumQuantityLabel;
    @FXML
    private Label totalLargeQuantityLabel;

    public Label getSmallQuantity() {
        return smallQuantity;
    }

    public Label getMediumQuantity() {
        return mediumQuantity;
    }

    public Label getLargeQuantity() {
        return largeQuantity;
    }

    public void setTotalSmallQuantityLabel(String value) {
        totalSmallQuantityLabel.setText(value);
    }

    public void setTotalMediumQuantityLabel(String value) {
        totalMediumQuantityLabel.setText(value);
    }

    public void setTotalLargeQuantityLabel(String value) {
        totalLargeQuantityLabel.setText(value);
    }

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

    public Button getSmallDecrementButton() {
        return smallDecrementButton;
    }

    public Button getMediumDecrementButton() {
        return mediumDecrementButton;
    }

    public Button getLargeDecrementButton() {
        return largeDecrementButton;
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

    public void setSmallIncrementButtonMouseEntered() {
        smallIncrementButton.setStyle("-fx-background-color: #9a7133;");
    }

    public void setSmallDecrementButtonMouseEntered() {
        smallDecrementButton.setStyle("-fx-background-color: #9a7133;");
    }

    public void setMediumIncrementButtonMouseEntered() {
        mediumIncrementButton.setStyle("-fx-background-color: #9a7133;");
    }

    public void setMediumDecrementButtonMouseEntered() {
        mediumDecrementButton.setStyle("-fx-background-color: #9a7133;");
    }

    public void setLargeIncrementButtonMouseEntered() {
        largeIncrementButton.setStyle("-fx-background-color: #9a7133;");
    }

    public void setLargeDecrementButtonMouseEntered() {
        largeDecrementButton.setStyle("-fx-background-color: #9a7133;");
    }

    public void setAcceptButtonMouseEntered() {
        acceptButton.setStyle("-fx-background-color: #71d079");
    }

    public void setSmallIncrementButtonMouseExited() {
        smallIncrementButton.setStyle("-fx-background-color: #634921;");
    }

    public void setSmallDecrementButtonMouseExited() {
        smallDecrementButton.setStyle("-fx-background-color: #634921;");
    }

    public void setMediumIncrementButtonMouseExited() {
        mediumIncrementButton.setStyle("-fx-background-color: #634921;");
    }

    public void setMediumDecrementButtonMouseExited() {
        mediumDecrementButton.setStyle("-fx-background-color: #634921;");
    }

    public void setLargeIncrementButtonMouseExited() {
        largeIncrementButton.setStyle("-fx-background-color: #634921;");
    }

    public void setLargeDecrementButtonMouseExited() {
        largeDecrementButton.setStyle("-fx-background-color: #634921;");
    }

    public void setAcceptButtonMouseExited() {
        acceptButton.setStyle("-fx-background-color: #5dae65");
    }
} // end of BeverageInventorySizesPopupController
