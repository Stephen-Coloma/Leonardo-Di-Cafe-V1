package client.controller;

import client.model.ClientModel;
import client.view.*;
import javafx.application.Platform;
import shared.Beverage;
import shared.Customer;
import shared.Food;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.*;

public class ClientController {
    private static final int PORT = 2000;
    private static final String IP_ADDRESS = "localhost";
    private ClientView view;
    private ClientModel model;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private LandingPageController landingPageController;
    private LoginPageController loginPageController;
    private SignUpPageController signUpPageController;
    private MainMenuClientPageController mainMenuClientPageController;
    private static boolean isLoginSuccessful = false;
    public ClientController(ClientModel model, ClientView view){
        this.model = model;
        this.view = view;

        Platform.runLater(() -> {
            System.out.println("Obtained Main Menu Controller");
            landingPageController = view.getLoader().getController();

            setUpLandingPageComponentActions();
            System.out.println("Successfully added actions");
        });
    }

    /**This method sets the action listeners for the components under landing_page.fxml*/
    private void setUpLandingPageComponentActions() {
        //action listener for login button
        landingPageController.getLoginButtonFrontPage().setOnAction(actionEvent -> {
            try {
                landingPageController.showLoginUI(actionEvent);
                loginPageController = landingPageController.getLoader().getController();
                setUpLoginPageComponentActions(); //if login page is loaded, load also it's component actions
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //action listener for sign up button
        landingPageController.getSignupButtonFrontPage().setOnAction(actionEvent -> {
            try {
                landingPageController.showRegistrationUI(actionEvent);
                signUpPageController = landingPageController.getLoader().getController();
                setUpSignUpPageComponentActions(); //if sign up is loaded, load also it's component actions
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**TThis method sets up the components actions for login_page.fxml*/
    private void setUpLoginPageComponentActions() {
        loginPageController.getLoginButton().setOnAction(actionEvent -> {
            try {
                //get the credentials
                String[] credentials = loginPageController.getCredentials();
                if (credentials != null){
                    Object[] request = new Object[]{"LOGIN", credentials};
                    processServerResponse(authenticate(request)); //process the authentication to server

                    //loading the main menu if login is successful
                    if (isLoginSuccessful){
                        loginPageController.showMainMenu(actionEvent);
                        mainMenuClientPageController = loginPageController.getLoader().getController();
                    }else {
                        loginPageController.incorrectDetails();
                    }
                }else {
                    loginPageController.emptyField();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }


    /**TThis method sets up the components actions for signup_page.fxml*/
    private void setUpSignUpPageComponentActions() {
        signUpPageController.getCreateAccountButton().setOnAction(actionEvent -> {
            try {
                signUpPageController.showLoginPage(actionEvent);
                loginPageController = signUpPageController.getLoader().getController(); //this connects to login set ups
                setUpLoginPageComponentActions(); //this connects back to login setups
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    /**This method will only be utilized by Login and SignUp actions. This method is separated since the idea is
     * to close the socket connections every time logging in or signing up fails.*/
    public Object[] authenticate(Object[] request) throws IOException, ClassNotFoundException {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            String code = (String) request[0];
            Object data = request[1];

            // Sending to server
            sendToServer(code, data);

            // Receiving from server
            Object[] response = (Object[]) in.readObject();


            //close the socket if login failed or after sign up
            String serverCode = (String) response[0];
            if (!serverCode.equals("LOGIN_SUCCESSFUL")){
                in.close();
                out.close();
                socket.close();
            }
            return response;
        } catch (UnknownHostException e) {
            // TODO: Pop up that host is unknown
            return null;
        } catch (IOException e) {
            // TODO: Pop up that host is down
            return null;
        }
    }

    /**This method will be used for processing all server responses.*/
    private void processServerResponse(Object[] response) {
        //Guide response[0] = code, response[1] = data
        String serverCode = (String) response[0];

        switch (serverCode){
            case "LOGIN_SUCCESSFUL":
                Object[] data = (Object[]) response[1]; //separating to customerAccount,foodMenu, beverageMenu
                Customer customer = (Customer) data[0];
                HashMap<String, Food> foodMenu = (HashMap<String, Food>) data[1];
                HashMap<String, Beverage> beverageMenu = (HashMap<String, Beverage>) data[2];

                //setting up the model
                model.setCustomer(customer);
                model.setFoodMenu(foodMenu);
                model.setBeverageMenu(beverageMenu);

                //login successful
                isLoginSuccessful = true;
                break;
            case "LOGIN_FAILED":
                //handled by if-else statement using the isLoginSuccessful
                break;
        }
    }

    /**This is a helper method that sends data to the server.*/
    public void sendToServer(String code, Object data){
        Object[] request = {code, data};
        try {
            out.writeObject(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
