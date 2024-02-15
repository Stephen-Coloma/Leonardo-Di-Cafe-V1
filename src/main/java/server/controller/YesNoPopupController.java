package server.controller;

import server.view.YesNoPopupView;

public class YesNoPopupController {
    YesNoPopupView view;

    public YesNoPopupController(YesNoPopupView view) {
        this.view = view;
    }

    public void setQuestionPromptMessage(String message) {
        view.setQuestionPromptLabel(message);
    }

    public void setComponentActions() {
        view.getNoButton().setOnAction(actionEvent -> view.closePopupStage());
    }
} // end of YesNoPopupController
