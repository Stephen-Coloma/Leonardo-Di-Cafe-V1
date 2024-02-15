package client.controller;

import client.model.ClientModel;
import client.view.*;
import client.view.fxmlview.*;
import javafx.application.Platform;
import shared.Beverage;
import shared.Customer;
import shared.Food;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientController {
//    private static final int PORT = 2000;
//    private static final String IP_ADDRESS = "localhost";
//    private ClientView view;
//    private ClientModel model;
//    private Socket socket;
//    private ObjectInputStream in;
//    private ObjectOutputStream out;
//
//    private LandingPageView landingPageView;
//    private LoginPageView loginPageView;
//    private SignUpPageView signUpPageView;
//    private MainMenuClientPageView mainMenuClientPageView;
//    private ServerErrorView serverErrorView;
//    private static boolean isLoginSuccessful = false;
//    private static boolean isSignUpSuccessful = false;
//    public ClientController(ClientModel model, ClientView view){
//        this.model = model;
//        this.view = view;
//
//        Platform.runLater(() -> {
//            System.out.println("Obtained Main Menu Controller");
//            landingPageView = view.getLoader().getController();
//
//            setUpLandingPageComponentActions();
//            System.out.println("Successfully added actions");
//        });
//    }
//
//    /**This method sets the action listeners for the components under landing_page.fxml*/
//    private void setUpLandingPageComponentActions() {
//        //action listener for login button
//        landingPageView.getLoginButtonFrontPage().setOnAction(actionEvent -> {
//            try {
//                landingPageView.showLoginUI(actionEvent);
//                loginPageView = landingPageView.getLoader().getController();
//                setUpLoginPageComponentActions(); //if login page is loaded, load also it's component actions
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        //action listener for sign up button
//        landingPageView.getSignupButtonFrontPage().setOnAction(actionEvent -> {
//            try {
//                landingPageView.showRegistrationUI(actionEvent);
//                signUpPageView = landingPageView.getLoader().getController();
//                setUpSignUpPageComponentActions(); //if sign up is loaded, load also it's component actions
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
//
//    /**TThis method sets up the components actions for login_page.fxml*/
//    private void setUpLoginPageComponentActions() {
//        //action for login button
//        loginPageView.getLoginButton().setOnAction(actionEvent -> {
//            try {
//                //get the credentials
//                String[] credentials = loginPageView.getCredentials();
//                if (credentials != null){
//                    Object[] request = new Object[]{"LOGIN", credentials};
//                    Object[] serverResponse = authenticate(request);
//                    if (serverResponse != null){ //it returns null if server is down
//                        processServerResponse(serverResponse); //process the authentication to server
//
//                        //loading the main menu if login is successful
//                        if (isLoginSuccessful){
//                            loginPageView.showMainMenu(actionEvent);
//                            mainMenuClientPageView = loginPageView.getLoader().getController();
//                            mainMenuClientPageView.initializeFoodMenu(model.getFoodMenu(), model.getBeverageMenu()); //--------------> IMPORTANT! setting up the menu in the scrollpane
//                            setUpMainMenuClientPageComponentActions();
//                        }else {
//                            loginPageView.incorrectDetails();
//                        }
//                    }
//                }else {
//                    loginPageView.emptyField();
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        //action for home button
//        loginPageView.getBackButton().setOnAction(actionEvent ->{
//            try {
//                loginPageView.showLandingPage(actionEvent);
//                landingPageView = loginPageView.getLoader().getController();
//                setUpLandingPageComponentActions();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
//
//    /**TThis method sets up the components actions for signup_page.fxml*/
//    private void setUpSignUpPageComponentActions() {
//        //action for sign up button
//        signUpPageView.getCreateAccountButton().setOnAction(actionEvent -> {
//            try {
//                //get the credentials
//                Customer customer = signUpPageView.getCredentials();
//                if (customer != null){
//                    Object[] request = new Object[]{"SIGN_UP", customer};
//                    Object[] serverResponse = authenticate(request); //process the authentication to server
//                    if (serverResponse != null){ //it returns null if server is down
//                        processServerResponse(serverResponse);
//                        //loading the main menu if login is successful
//                        if (isSignUpSuccessful){
//                            signUpPageView.showLoginPage(actionEvent);
//                            loginPageView = signUpPageView.getLoader().getController(); //this connects to login set ups
//                            setUpLoginPageComponentActions(); //this connects back to login setups
//                        }else {
//                            signUpPageView.accountExist();
//                        }
//                    }
//                }else {
//                    signUpPageView.emptyField();
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        //action for home button
//        signUpPageView.getBackButton().setOnAction(actionEvent ->{
//            try {
//                signUpPageView.showLandingPage(actionEvent);
//                landingPageView = signUpPageView.getLoader().getController();
//                setUpLandingPageComponentActions();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
//
//    /**TThis method sets up the components actions for main_menu_client_page.fxml*/
//    private void setUpMainMenuClientPageComponentActions() {
//        //actions for food button
//        mainMenuClientPageView.getMainMenuFoodButton().setOnAction(actionEvent->{
//            mainMenuClientPageView.loadFoodMenu();
//        });
//
//        //action for beverage button
//        mainMenuClientPageView.getMainMenuBeveragesButton().setOnAction(actionEvent->{
//            mainMenuClientPageView.loadBeverageMenu();
//        });
//    }
//
//    /**This method will only be utilized by Login and SignUp actions. This method is separated since the idea is
//     * to close the socket connections every time logging in or signing up fails.*/
//    public Object[] authenticate(Object[] request) throws IOException, ClassNotFoundException {
//        try {
//            socket = new Socket(IP_ADDRESS, PORT);
//            out = new ObjectOutputStream(socket.getOutputStream());
//            in = new ObjectInputStream(socket.getInputStream());
//
//            String code = (String) request[0];
//            Object data = request[1];
//
//            // Sending to server
//            sendToServer(code, data);
//
//            // Receiving from server
//            Object[] response = (Object[]) in.readObject();
//
//
//            //close the socket if login failed or after sign up
//            String serverCode = (String) response[0];
//            if (!serverCode.equals("LOGIN_SUCCESSFUL")){
//                in.close();
//                out.close();
//                socket.close();
//                System.out.println("here");
//            }
//            return response;
//        } catch (UnknownHostException e) {
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            //either of the two will call the server error
//            if (loginPageView != null){
//                loginPageView.serverError();
//            }else if (signUpPageView != null){
//                signUpPageView.serverError();
//            }
//            return null;
//        }
//
////        HashMap<String, Food> foodMenu = (HashMap<String, Food>) XMLUtility.loadXMLData(new File("src/main/java/server/model/food_menu.xml"));
////        HashMap<String, Beverage> beverageMenu = (HashMap<String, Beverage>) XMLUtility.loadXMLData(new File("src/main/java/server/model/beverage_menu.xml"));
////        List<Customer> customerList = (List<Customer>) XMLUtility.loadXMLData(new File("src/main/java/server/model/customer_account_list.xml"));
////
////        Customer customer = customerList.get(0);
////        Object[] sendToClient = new Object[]{customer, foodMenu, beverageMenu};
////
////        Object[] response = {"LOGIN_SUCCESSFUL", sendToClient};
////
////        return response;
//    }
//    /**This method will be used for processing all server responses.*/
//    private void processServerResponse(Object[] response) {
//        //Guide response[0] = code, response[1] = data
//        String serverCode = (String) response[0];
//        System.out.println(socket.isClosed());
//
//        switch (serverCode){
//            case "LOGIN_SUCCESSFUL":
//                Object[] data = (Object[]) response[1]; //separating to customerAccount,foodMenu, beverageMenu
//                Customer customer = (Customer) data[0];
//                HashMap<String, Food> foodMenu = (HashMap<String, Food>) data[1];
//                HashMap<String, Beverage> beverageMenu = (HashMap<String, Beverage>) data[2];
//
//                //setting up the model
//                model.setCustomer(customer);
//                model.setFoodMenu(foodMenu);
//                model.setBeverageMenu(beverageMenu);
//                model.setCart(new ArrayList<>());
//                model.setOrder(null);
//
//                System.out.println("model is ready");
//                //login successful
//                isLoginSuccessful = true;
//                break;
//            case "LOGIN_FAILED":
//                isLoginSuccessful = false;
//                //handled by if-else statement using the isLoginSuccessful
//                break;
//            case "SIGN_UP_SUCCESSFUL":
//                isSignUpSuccessful = true;
//                break;
//            case "SIGN_UP_FAILED":
//                isSignUpSuccessful = false;
//                //handled by if-else statement using the isLoginSuccessful
//                break;
//
//        }
//    }
//
//    /**This is a helper method that sends data to the server.*/
//    public void sendToServer(String code, Object data){
//        Object[] request = {code, data};
//        try {
//            out.writeObject(request);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
