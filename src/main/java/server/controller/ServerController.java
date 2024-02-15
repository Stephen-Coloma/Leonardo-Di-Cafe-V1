package server.controller;

import javafx.application.Platform;
import server.model.MainMenuAdminModel;
import server.view.MainMenuAdminView;
import server.model.ServerModel;
import server.view.ServerView;
import shared.*;
import util.ImageCopier;
import util.exception.AccountExistsException;
import util.exception.InvalidCredentialsException;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerController {
    private final ServerModel model;
    private final ServerView view;
    private Socket clientSocket;
    private ObjectInputStream streamReader;
    private ObjectOutputStream streamWriter;

    public ServerController(ServerModel model, ServerView view) {
        this.model = model;
        this. view = view;

        Platform.runLater(() -> {
            System.out.println("Obtained Main Menu Controller");
            //mainMenuAdminView = view.getLoader().getController();

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
            MainMenuAdminModel mainMenuAdminModel = new MainMenuAdminModel();
            MainMenuAdminView mainMenuAdminView = view.getLoader().getController();

            MainMenuAdminController mainMenuAdminController = new MainMenuAdminController(model, mainMenuAdminModel, mainMenuAdminView);
            mainMenuAdminController.start();
        });
        /*
        mainMenuAdminView.getViewInventoryButton().setOnAction(actionEvent -> {
            InventoryPageModel inventoryPageModel = new InventoryPageModel();
            InventoryPageView inventoryPageView;
            InventoryPageController inventoryPageController = new InventoryPageController(inventoryPageModel, inventoryPageView = mainMenuAdminView.getInventoryPageController());


            HashMap<String, Beverage> beverageMenu = (HashMap<String, Beverage>) XMLUtility.loadXMLData(new File("src/main/java/server/model/beverage_menu.xml"));
            HashMap<String, Food> foodMenu = (HashMap<String, Food>) XMLUtility.loadXMLData(new File("src/main/java/server/model/food_menu.xml"));

            Platform.runLater(() -> {
                InventoryPageModel inventoryPageModel = new InventoryPageModel();

                inventoryPageView = mainMenuAdminView.getInventoryPageController();
                inventoryPageView.populateTableFromMap(foodMenu, beverageMenu);
                InventoryPageController inventoryPageController = new InventoryPageController(inventoryPageModel, inventoryPageView);

                inventoryPageView.getSaveChangesButton().setOnAction(actionEvent1 -> {
                    ObservableList<Object> productList = inventoryPageView.getProductList();
                    model.updateMenuFromInventory(productList);
                });
            });
        });

        mainMenuAdminView.getViewOrderButton().setOnAction(actionEvent -> {
            List<Order> orderList = (ArrayList<Order>) XMLUtility.loadXMLData(new File("src/main/java/server/model/order_list.xml"));

            Platform.runLater(() -> {
                ordersListPageController = mainMenuAdminView.getOrdersListPageController();
                ordersListPageController.populateTableFromList(orderList);
            });
        });

        mainMenuAdminView.getViewAccountsButton().setOnAction(actionEvent -> {
            List<Customer> accountList = (ArrayList<Customer>) XMLUtility.loadXMLData(new File("src/main/java/server/model/customer_account_list.xml"));

            Platform.runLater(() -> {
                accountsListPageController = mainMenuAdminView.getAccountsListPageController();
                accountsListPageController.populateTableFromList(accountList);
            });
        });

        mainMenuAdminView.getAddProductsPageButton().setOnAction(actionEvent -> Platform.runLater(() -> {
            addProductsPageController = mainMenuAdminView.getAddProductsPageController();
            addProductsPageController.getAddProductButton().setOnAction(actionEvent1 -> addProduct());
        }));

         */
    } // end of setComponentActions

    /*
    public void addProduct() {
        if (addProductsPageController.getTypeOfProductMenuButton().getText().equalsIgnoreCase("food")) {
            String name = addProductsPageController.getProductNameTextField().getText().trim();
            String description = addProductsPageController.getProductDescriptionTextField().getText().trim();
            int quantity = Integer.parseInt(addProductsPageController.getMainQuantityTextField().getText().trim());
            double price = Double.parseDouble(addProductsPageController.getMainPriceTextField().getText().trim());

            String absolutePath = new File(addProductsPageController.getImageTextField().getText()).getAbsolutePath();
            String extension = absolutePath.substring(absolutePath.lastIndexOf('.'));
            String copiedImagePath = ImageCopier.copyImage(absolutePath, name + extension);

            SerializableImage image = new SerializableImage("file:" + copiedImagePath);

            Food food = new Food(name, 'f', 0.0, 0, image, description, quantity, price);
            System.out.println(food);
            //model.getFoodMenu().put(name, food);
        } else {
            String name = addProductsPageController.getProductNameTextField().getText().trim();
            String description = addProductsPageController.getProductDescriptionTextField().getText().trim();
            int sQuantity = Integer.parseInt(addProductsPageController.getMainQuantityTextField().getText().trim());
            int mQuantity = Integer.parseInt(addProductsPageController.getMediumQuantityTextField().getText().trim());
            int lQuantity = Integer.parseInt(addProductsPageController.getLargeQuantityTextField().getText().trim());
            double sPrice = Double.parseDouble(addProductsPageController.getMainPriceTextField().getText().trim());
            double mPrice = Double.parseDouble(addProductsPageController.getMediumPriceTextField().getText().trim());
            double lPrice = Double.parseDouble(addProductsPageController.getLargePriceTextField().getText().trim());

            String absolutePath = new File(addProductsPageController.getImageTextField().getText()).getAbsolutePath();
            String extension = absolutePath.substring(absolutePath.lastIndexOf('.'));
            String copiedImagePath = ImageCopier.copyImage(absolutePath, name + extension);

            SerializableImage image = new SerializableImage("file:" + copiedImagePath);

            Beverage beverage = new Beverage(name, 'b', 0.0, 0, image, description, sQuantity, mQuantity, lQuantity, sPrice, mPrice, lPrice);
            System.out.println(beverage);
            //model.getBeverageMenu().put(name, beverage);
        }
    } // end of addProduct

     */

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
