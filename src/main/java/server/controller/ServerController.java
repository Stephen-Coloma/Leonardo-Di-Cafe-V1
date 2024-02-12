package server.controller;

import javafx.application.Platform;
import server.controller.temporarycontroller.*;
import server.model.ServerModel;
import server.view.ServerView;
import shared.Beverage;
import shared.Customer;
import shared.Food;
import util.XMLUtility;
import util.exception.AccountExistsException;
import util.exception.InvalidCredentialsException;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ServerController {
    private final ServerModel model;
    private final ServerView view;
    private Socket clientSocket;
    private ObjectInputStream streamReader;
    private ObjectOutputStream streamWriter;
    private AccountsListPageController accountsListPageController;
    private AddProductsPageController addProductsPageController;
    private AnalyticsPageController analyticsPageController;
    private InventoryPageController inventoryPageController;
    private MainMenuAdminController mainMenuAdminController;
    private OrdersListPageController ordersListPageController;

    public ServerController(ServerModel model, ServerView view) {
        this.model = model;
        this. view = view;

        Platform.runLater(() -> {
            System.out.println("Obtained Main Menu Controller");
            mainMenuAdminController = view.getLoader().getController();

            setComponentActions();
            System.out.println("Successfully added actions");
        });
    } // end of constructor

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    // TODO
    private void setComponentActions() {
        mainMenuAdminController.getViewInventoryButton().setOnAction(actionEvent -> {
            HashMap<String, Beverage> beverageMenu = (HashMap<String, Beverage>) XMLUtility.loadXMLData(new File("src/main/java/server/model/beverage_menu.xml"));
            HashMap<String, Food> foodMenu = (HashMap<String, Food>) XMLUtility.loadXMLData(new File("src/main/java/server/model/food_menu.xml"));

            Platform.runLater(() -> {
                inventoryPageController = mainMenuAdminController.getInventoryPageController();
                inventoryPageController.populateTableFromMap(foodMenu, beverageMenu);
            });
        });
    }

    // TODO
    public void run() {
        try {
            streamReader = new ObjectInputStream(clientSocket.getInputStream());
            streamWriter = new ObjectOutputStream(clientSocket.getOutputStream());

            listenToClient();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    } // end of run

    private void listenToClient() throws IOException, ClassNotFoundException {
        while (!clientSocket.isClosed()) {
            Object[] data = (Object[]) streamReader.readObject();
            if (data != null) {
                handleClientRequest(data);
            }
        }
    }

    // TODO
    private void handleClientRequest(Object[] message) {
        String requestCode = (String) message[0];
        switch (requestCode) {
            case "LOGIN" -> {
                try {
                    String[] information = (String[]) message[1];
                    String username = information[0];
                    String password = information[1];

                    Object[] client = model.processLogin(username, password);
                    sendData("LOGIN_SUCCESSFUL", client);
                } catch (InvalidCredentialsException exception) {
                    sendData("LOGIN_FAILED", null);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "SIGN_UP" -> {
                try {
                    Customer client = (Customer) message[1];
                    model.processSignUp(client);
                    sendData("SIGN_UP_SUCCESSFUL", true);
                } catch (AccountExistsException accountExistsException) {
                    sendData("SIGN_UP_FAILED", false);
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            }
        }
    }

    private void sendData(String code, Object data) {
        Object[] response = {code, data};
        try {
            streamWriter.writeObject(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
} // end of ServerController
