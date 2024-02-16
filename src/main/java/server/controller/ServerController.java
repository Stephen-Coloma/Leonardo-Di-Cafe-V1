package server.controller;

import javafx.application.Platform;
import server.model.MainMenuAdminModel;
import server.model.ServerModel;
import server.model.listeners.MainMenuAdminObserver;
import server.view.MainMenuAdminView;
import server.view.ServerView;
import shared.Customer;
import util.exception.AccountExistsException;
import util.exception.InvalidCredentialsException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerController implements MainMenuAdminObserver {
    private final ServerModel model;
    private final ServerView view;
    private Socket clientSocket;
    private ObjectInputStream streamReader;
    private ObjectOutputStream streamWriter;

    private MainMenuAdminModel mainMenuAdminModel;

    public ServerController(ServerModel model, ServerView view) {
        this.model = model;
        this. view = view;

        Platform.runLater(() -> {
            System.out.println("Obtained Main Menu Controller");
            setComponentActions();
            System.out.println("Successfully added actions");
        });
    } // end of constructor

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    } // end of setClientSocket

    // TODO
    private void setComponentActions() {
        Platform.runLater(() -> {
            mainMenuAdminModel = new MainMenuAdminModel();
            mainMenuAdminModel.setCustomerAccountList(model.getCustomerAccountList());
            mainMenuAdminModel.setOrderList(model.getOrderList());
            mainMenuAdminModel.setFoodMenu(model.getFoodMenu());
            mainMenuAdminModel.setBeverageMenu(model.getBeverageMenu());
            mainMenuAdminModel.registerObserver(this);
            MainMenuAdminView mainMenuAdminView = view.getLoader().getController();

            MainMenuAdminController mainMenuAdminController = new MainMenuAdminController(mainMenuAdminModel, mainMenuAdminView);
            mainMenuAdminController.start();
        });
    } // end of setComponentActions

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
    } // end of listenToClient

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
    } // end of handleClientRequest

    @Override
    public void notifyMenuChanges(String code, boolean menuChanges) {
        if (menuChanges) {
            if ("STATUS_CHANGE".equals(code)) {
                model.setOrderList(mainMenuAdminModel.getOrderList());
                System.out.println(model.getOrderList());
            } else if ("INVENTORY_CHANGE".equals(code)) {
                model.setFoodMenu(mainMenuAdminModel.getFoodMenu());
                model.setBeverageMenu(mainMenuAdminModel.getBeverageMenu());
                System.out.println(model.getFoodMenu());
                System.out.println(model.getBeverageMenu());
            } else if ("NEW_FOOD_PRODUCT".equals(code)) {
                model.setFoodMenu(mainMenuAdminModel.getFoodMenu());
                System.out.println(model.getFoodMenu());
            } else if ("NEW_BEVERAGE_PRODUCT".equals(code)) {
                model.setBeverageMenu(mainMenuAdminModel.getBeverageMenu());
                System.out.println(model.getBeverageMenu());
            }

            /*
            TODO: Move this part into a method that executs once the server closes so that the writing of data will
                  only be done once the server closes

               XMLUtility.saveFoodMenu(model.getFoodMenu());
               XMLUtility.saveBeverageMenu(model.getBeverageMenu());
                XMLUtility.saveOrders(model.getOrderList());
             */
        }
    } // end of notifyMenuChanges

    private void sendData(String code, Object data) {
        Object[] response = {code, data};
        try {
            streamWriter.writeObject(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of sendData
} // end of ServerController
