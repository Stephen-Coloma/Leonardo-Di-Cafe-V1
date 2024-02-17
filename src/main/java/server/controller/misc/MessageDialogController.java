package server.controller.misc;

import server.view.misc.MessageDialogView;

public class MessageDialogController {
    MessageDialogView view;

    public MessageDialogController(MessageDialogView view) {
        this.view = view;
    }

    public void setMessagePromptMessage(String message) {
        view.setMessagePromptLabel(message);
    }

    public void setComponentActions() {
        view.getOkButton().setOnAction(actionEvent -> view.closePopupStage());
    }
} // end of MessageDialogController
