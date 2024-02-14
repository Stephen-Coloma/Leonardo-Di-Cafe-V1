package server.view.fxmlcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class YesNoPopupController {
    @FXML
    private TextArea questionPromptLabel;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;

    public void setQuestionPromptLabel(String value) {
        questionPromptLabel.setText(value);
    }

    public Button getYesButton() {
        return yesButton;
    }

    public Button getNoButton() {
        return noButton;
    }

    public void setYesButtonMouseEntered() {
        yesButton.setStyle("-fx-background-color: #71d079");
    }

    public void setYesButtonMouseExited() {
        yesButton.setStyle("-fx-background-color: #5dae65");
    }

    public void setNoButtonMouseEntered() {
        noButton.setStyle("-fx-background-color: #d07171");
    }

    public void setNoButtonMouseExited() {
        noButton.setStyle("-fx-background-color: #ae5d5d");
    }
} // end of YesNoPopupController class
