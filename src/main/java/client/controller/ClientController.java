package client.controller;

import client.Client;
import client.model.ClientModel;
import client.view.ClientView;
import javafx.application.Platform;

import java.io.IOException;

public class ClientController {
    private ClientView view;
    private ClientModel model;
    private FrontPageController frontPageController;
    private LoginPageController loginPageController;
    private SignUpController signUpController;
    private MainController mainController;
    public ClientController(ClientModel model, ClientView view){
        this.model = model;
        this.view = view;

        Platform.runLater(() -> {
            System.out.println("Obtained Main Menu Controller");
            frontPageController = view.getLoader().getController();

            setComponentActions();
            System.out.println("Successfully added actions");
        });
    }

    private void setComponentActions() {
        //action event for login button in frontpageUI
        frontPageController.getLoginButtonFrontPage().setOnAction(actionEvent -> {
            try {
                frontPageController.showLoginUI(actionEvent);
                loginPageController = frontPageController.getLoader().getController();
                setUpLoginActions();
                //set up also the loginPage controller
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //action event for signup button in frontpageUI
        frontPageController.getRegisterButtonFrontPage().setOnAction(actionEvent -> {
            try {
                frontPageController.showRegistrationUI(actionEvent);
                signUpController = frontPageController.getLoader().getController();
                setUpSignUpActions();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setUpLoginActions() {
        loginPageController.getLoginButton().setOnAction(actionEvent -> {
            try {
                loginPageController.showMainMenu(actionEvent);
                mainController = loginPageController.getLoader().getController();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setUpSignUpActions() {
        signUpController.getCreateAccountButton().setOnAction(actionEvent -> {
            try {
                signUpController.showLoginPage(actionEvent);
                loginPageController = signUpController.getLoader().getController(); //this connects to login set ups
                setUpLoginActions(); //this connects back to login setups
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
