package server.view.misc;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MessageDialogView {
    @FXML
    private TextArea messagePromptLabel;
    @FXML
    private Button okButton;
    private static Stage popupStage;

    public static <T> T loadMessageDialogView() {
        try {
            FXMLLoader loader = new FXMLLoader(YesNoPopupView.class.getResource("/fxml/server/misc/message_dialog.fxml"));
            popupStage = new Stage();
            popupStage.getIcons().add(new Image(MessageDialogView.class.getResource("/images/server/server_app_logo.png").toExternalForm()));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.show();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of loadYesNoPopupView

    public void closePopupStage() {
        popupStage.close();
    }

    public void setTitle(String value) {
        popupStage.setTitle(value);
    }

    public void setMessagePromptLabel(String value) {
        messagePromptLabel.setText(value);
    }

    public Button getOkButton() {
        return okButton;
    }

    public void setOkButtonMouseEntered() {
        okButton.setStyle("-fx-background-color: #71d079");
    }

    public void setOkButtonMouseExited() {
        okButton.setStyle("-fx-background-color: #5dae65");
    }
} // end of MessageDialogView class
