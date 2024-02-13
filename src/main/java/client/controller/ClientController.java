package client.controller;

import client.model.ClientModel;
import client.view.*;
import javafx.application.Platform;

import java.io.IOException;

public class ClientController {
    private ClientView view;
    private ClientModel model;
    private LandingPageController landingPageController;
    private LoginPageController loginPageController;
    private SignUpPageController signUpPageController;
    private MainMenuClientPageController mainMenuClientPageController;
    public ClientController(ClientModel model, ClientView view){
        this.model = model;
        this.view = view;

        Platform.runLater(() -> {
            System.out.println("Obtained Main Menu Controller");
            landingPageController = view.getLoader().getController();

            setFrontPageComponentActions();
            System.out.println("Successfully added actions");
        });
    }

    private void setFrontPageComponentActions() {
        //action event for login button in frontpageUI
        landingPageController.getLoginButtonFrontPage().setOnAction(actionEvent -> {
            try {
                landingPageController.showLoginUI(actionEvent);
                loginPageController = landingPageController.getLoader().getController();
                setUpLoginActions();
                //set up also the loginPage controller
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //action event for signup button in frontpageUI
        landingPageController.getSignupButtonFrontPage().setOnAction(actionEvent -> {
            try {
                landingPageController.showRegistrationUI(actionEvent);
                signUpPageController = landingPageController.getLoader().getController();
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
                mainMenuClientPageController = loginPageController.getLoader().getController();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setUpSignUpActions() {
        signUpPageController.getCreateAccountButton().setOnAction(actionEvent -> {
            try {
                signUpPageController.showLoginPage(actionEvent);
                loginPageController = signUpPageController.getLoader().getController(); //this connects to login set ups
                setUpLoginActions(); //this connects back to login setups
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
