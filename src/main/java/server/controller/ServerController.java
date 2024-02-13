package server.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import server.controller.temporarycontroller.*;
import server.model.ServerModel;
import server.view.ServerView;
import shared.*;
import util.XMLUtility;
import util.exception.AccountExistsException;
import util.exception.InvalidCredentialsException;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    } // end of setClientSocket

    // TODO
    private void setComponentActions() {
        mainMenuAdminController.getViewInventoryButton().setOnAction(actionEvent -> {
            HashMap<String, Beverage> beverageMenu = (HashMap<String, Beverage>) XMLUtility.loadXMLData(new File("src/main/java/server/model/beverage_menu.xml"));
            HashMap<String, Food> foodMenu = (HashMap<String, Food>) XMLUtility.loadXMLData(new File("src/main/java/server/model/food_menu.xml"));

            Platform.runLater(() -> {
                inventoryPageController = mainMenuAdminController.getInventoryPageController();
                inventoryPageController.populateTableFromMap(foodMenu, beverageMenu);

                inventoryPageController.getSaveChangesButton().setOnAction(actionEvent1 -> {
                    ObservableList<Object> productList = inventoryPageController.getProductList();
                    model.updateMenuFromInventory(productList);
                });
            });
        });

        mainMenuAdminController.getViewOrderButton().setOnAction(actionEvent -> {
            List<Order> orderList = (ArrayList<Order>) XMLUtility.loadXMLData(new File("src/main/java/server/model/order_list.xml"));

            Platform.runLater(() -> {
                ordersListPageController = mainMenuAdminController.getOrdersListPageController();
                ordersListPageController.populateTableFromList(orderList);
            });
        });

        mainMenuAdminController.getViewAccountsButton().setOnAction(actionEvent -> {
            List<Customer> accountList = (ArrayList<Customer>) XMLUtility.loadXMLData(new File("src/main/java/server/model/customer_account_list.xml"));

            Platform.runLater(() -> {
                accountsListPageController = mainMenuAdminController.getAccountsListPageController();
                accountsListPageController.populateTableFromList(accountList);
            });
        });

        mainMenuAdminController.getAddProductsPageButton().setOnAction(actionEvent -> {
            Platform.runLater(() -> {
                addProductsPageController = mainMenuAdminController.getAddProductsPageController();
                addProductsPageController.getAddProductButton().setOnAction(actionEvent1 -> {
                    addProduct();
                });
            });
        });
    } // end of setComponentActions

    public void addProduct() {
        if (addProductsPageController.getTypeOfProductMenuButton().getText().equals("food")) {

        } else {
            String name = addProductsPageController.getProductNameTextField().getText().trim();
            char type = 'b';
            double review = 0.0;
            int reviewCount = 0;
            String description = addProductsPageController.getProductDescriptionTextField().getText();
            int sQuantity = Integer.parseInt(addProductsPageController.getMainQuantityTextField().getText());
            int mQuantity = Integer.parseInt(addProductsPageController.getMediumQuantityTextField().getText());
            int lQuantity = Integer.parseInt(addProductsPageController.getLargeQuantityTextField().getText());
            double sPrice = Double.parseDouble(addProductsPageController.getMainPriceTextField().getText());
            double mPrice = Double.parseDouble(addProductsPageController.getMediumPriceTextField().getText());
            double lPrice = Double.parseDouble(addProductsPageController.getLargePriceTextField().getText());

            String absolutePath = new File(addProductsPageController.getImageTextField().getText()).getAbsolutePath();
            String extension = absolutePath.substring(absolutePath.lastIndexOf('.'));
            String copiedImagePath = model.copyImage(absolutePath, name + extension);

            Image image = new Image("file:" + copiedImagePath);

            Beverage beverage = new Beverage(name, type, review, reviewCount, image, description, sQuantity, mQuantity, lQuantity, sPrice, mPrice, lPrice);
            System.out.println(beverage);
            //model.getBeverageMenu().put(name, beverage);
        }
    } // end of addProduct

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

    private void sendData(String code, Object data) {
        Object[] response = {code, data};
        try {
            streamWriter.writeObject(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of sendData
} // end of ServerController
