package client.controller.fxmlcontroller;


import client.model.fxmlmodel.*;
import client.view.fxmlview.CheckoutPageView;
import client.view.fxmlview.MainMenuClientPageView;
import client.view.fxmlview.MenuCardView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import shared.*;
import util.ImageUtility;
import util.LoadingScreenUtility;
import util.PushNotification;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MainMenuClientPageController {
    private char currentLoadedMenu = 'f';
    private MainMenuClientPageView mainMenuView;
    private MainMenuClientPageModel mainMenuModel;
    private FXMLLoader loader;
    private Parent root;
    private Object[] serverResponse;
    private Socket socket; // to be passed to client
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private int cartColumn = 0; //for cart scrollPane
    private int cartRow = 1; //for cart scrollPane
    private double cartTotalPrice = 0; //for cart purposes
    private static final long DEBOUNCE_DELAY = 500;
    private Timer debounceTimer;

    public MainMenuClientPageController(MainMenuClientPageModel mainMenuModel, MainMenuClientPageView mainMenuView) {
        this.mainMenuView = mainMenuView;
        this.mainMenuModel = mainMenuModel;

        // setting up the time and date labels
        setupClock();
        setupDate();

        //initialize the account name
        String accountName = this.mainMenuModel.getClientModel().getCustomer().getName();
        this.mainMenuView.getAccountNameLabel().setText(accountName);

        // initial menu
        loadFoodMenu();

        setComponentActions();

        debounceTimer = new Timer();
    }

    private void setComponentActions() {
        // set up action listener for food category button
        mainMenuView.getMainMenuFoodButton().setOnAction(actionEvent -> {
            mainMenuView.getGridPaneMenu().getChildren().clear(); // remove existing menu from the grid before switching menus
            currentLoadedMenu = 'f';
            loadFoodMenu();
        });

        // set up action listener for beverages category button
        mainMenuView.getMainMenuBeveragesButton().setOnAction(actionEvent -> {
            mainMenuView.getGridPaneMenu().getChildren().clear(); // remove existing menu from the grid before switching menus
            currentLoadedMenu = 'b';
            loadBeverageMenu();
        });

        //setting up the action for setUpActionClearCartButtonButton
        setUpActionClearCartButton();

        //set up the action for checking out
        setUpActionCheckoutButton();

        mainMenuView.getProductSearchBar().textProperty().addListener((observable, oldValue, newValue) -> {
            debounceFilterMenuItems(newValue);
        });
    } // end of setComponentActions

    public void run() {
        try {
            listenToHost();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    } // end of run

    private void listenToHost() throws IOException, ClassNotFoundException {
        while (true) {
            Object[] data = (Object[]) in.readObject();
            if (data != null) {
                handleIncomingData(data);
            }
        }
    } // end of listenToHost

    // TODO: guide from incoming data from server <Object[]{String clientId, String dataCode, Object[] data}
    private void handleIncomingData(Object[] data) {
        String dataCode = (String) data[1];
        System.out.println("received data from server");
        System.out.println(data[0]);
        System.out.println(data[1]);
        System.out.println(data[2]);
        switch (dataCode) {
            case "PRODUCT_CHANGES" -> {
            }
            case "PROCESS_ORDER_SUCCESSFUL" -> {
                mainMenuModel.getClientModel().orderProcessSuccessful((Order) data[2]);
                PushNotification.toastSuccess("Checkout Status", "Your order has been placed");
            }
        }
    } // end of handleIncomingData

    private void sendData(String clientID, String code, Object data) {
        Object[] response = {clientID, code, data};
        System.out.println("before sending back to client");
        System.out.println(response[0]);
        System.out.println(response[1]);
        System.out.println(response[2]);
        try {
            out.writeObject(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of sendData

    public void setupClock() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> updateClock()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    } // end of setDateAndTime

    public void updateClock() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        String formattedTime = currentTime.format(formatter);
        mainMenuView.setTimeLabel(formattedTime);
    } // end of updateClock

    private void setupDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = currentDate.format(formatter);
        mainMenuView.setDateLabel(formattedDate);
    } // end of setupDate

    private void loadFoodMenu() {
        mainMenuView.getProductTypeLabel().setText("Food Category");

        LoadingScreenUtility.showLoadingIndicator(mainMenuView.getLoadingIndicatorPanel());

        HashMap<String, Food> foodMenu = mainMenuModel.getClientModel().getFoodMenu();
        List<Food> foodProducts = new ArrayList<>(foodMenu.values());

        Task<Void> loadingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);

                Platform.runLater(() -> {
                    int columnIndex = 0;
                    int rowIndex = 1;

                    for (Food food : foodProducts) {
                        Node productCard = createProductCard(food);

                        mainMenuView.getGridPaneMenu().getChildren().add(productCard);
                        GridPane.setConstraints(productCard, columnIndex, rowIndex);

                        if (columnIndex == 1) {
                            columnIndex = 0;
                            rowIndex++;
                        } else {
                            columnIndex++;
                        }
                    }

                    LoadingScreenUtility.hideLoadingIndicator(mainMenuView.getLoadingIndicatorPanel());
                });
                return null;
            }
        };

        Thread loadingThread = new Thread(loadingTask);
        loadingThread.start();
    } // end of loadFoodMenu

    private void loadBeverageMenu() {
        mainMenuView.getProductTypeLabel().setText("Beverage Category");

        LoadingScreenUtility.showLoadingIndicator(mainMenuView.getLoadingIndicatorPanel());

        HashMap<String, Beverage> beverageMenu = mainMenuModel.getClientModel().getBeverageMenu();
        List<Beverage> beverageProducts = new ArrayList<>(beverageMenu.values());

        Task<Void> loadingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);

                Platform.runLater(() -> {
                    int columnIndex = 0;
                    int rowIndex = 1;

                    for (Beverage beverage : beverageProducts) {
                        Node productCard = createProductCard(beverage);

                        mainMenuView.getGridPaneMenu().getChildren().add(productCard);
                        GridPane.setConstraints(productCard, columnIndex, rowIndex);

                        if (columnIndex == 1) {
                            columnIndex = 0;
                            rowIndex++;
                        } else {
                            columnIndex++;
                        }
                    }

                    LoadingScreenUtility.hideLoadingIndicator(mainMenuView.getLoadingIndicatorPanel());
                });
                return null;
            }
        };

        Thread loadingThread = new Thread(loadingTask);
        loadingThread.start();
    } // end of loadBeverageMenu

    private Node createProductCard(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/menu_card.fxml"));
            Node productCard = loader.load();

            MenuCardModel menuCardModel = new MenuCardModel();
            MenuCardView menuCardView = loader.getController();
            MenuCardController menuCardController = new MenuCardController(menuCardModel, menuCardView);
            menuCardController.setProductData(product);

            //this code setups up add to cart button of each card
            menuCardView.getAddProductButton().setOnAction(actionEvent -> {
                addToCart(menuCardModel.getProduct());
            });

            return productCard;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    } // end of createProductCard

    private void debounceFilterMenuItems(String searchText) {
        debounceTimer.cancel();
        debounceTimer = new Timer();

        LoadingScreenUtility.showLoadingIndicator(mainMenuView.getLoadingIndicatorPanel());
        debounceTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(() -> {
                    filterMenuItems(searchText);
                    LoadingScreenUtility.hideLoadingIndicator(mainMenuView.getLoadingIndicatorPanel());
                });
            }
        }, DEBOUNCE_DELAY);

    } // end of debounceFilterMenuItems

    private void filterMenuItems(String searchText) {
        mainMenuView.getGridPaneMenu().getChildren().clear();

        List<Node> filteredProductCards = new ArrayList<>();
        int columnIndex = 0;
        int rowIndex = 1;
        if (currentLoadedMenu == 'f') {
            HashMap<String, Food> foodMenu = mainMenuModel.getClientModel().getFoodMenu();
            List<Food> filteredFoodProducts = foodMenu.values().stream()
                    .filter(food -> food.getName().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());

            for (Food food : filteredFoodProducts) {
                Node productCard = createProductCard(food);
                filteredProductCards.add(productCard);
            }
        } else {
            HashMap<String, Beverage> beverageMenu = mainMenuModel.getClientModel().getBeverageMenu();
            List<Beverage> filteredBeverageProducts = beverageMenu.values().stream()
                    .filter(beverage -> beverage.getName().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());

            for (Beverage beverage : filteredBeverageProducts) {
                Node productCard = createProductCard(beverage);
                filteredProductCards.add(productCard);
            }
        }

        for (Node productCard : filteredProductCards) {
            mainMenuView.getGridPaneMenu().getChildren().add(productCard);
            GridPane.setConstraints(productCard, columnIndex, rowIndex);
            if (columnIndex == 1) {
                columnIndex = 0;
                rowIndex++;
            } else {
                columnIndex++;
            }
        }
    }

    /**
     * This method clears up all the contents of the cart.
     * It implements the setUpActionClearCartButtonButton from the mainMenuClientPageView
     */
    private void setUpActionClearCartButton() {
        this.mainMenuView.setActionClearCartButton((ActionEvent event) -> {
            clearCart(true);
        });
    }

    private void clearCart(boolean isUpdateModel) {
        ObservableList<Node> cartItems = this.mainMenuView.getGridPaneCart().getChildren();

        if (cartItems.size() == 0) {
            return;
        }

        //for each card
        for (Node cartItem : cartItems) {
            String productName = null;
            int productQuantity = 0;
            char productType = ' ';
            String productSize = null;
            double productPrice = 0;

            AnchorPane cartItemPane = (AnchorPane) cartItem;
            for (Node label : cartItemPane.getChildren()) {
                if (label instanceof Label) {
                    switch (label.getId()) {
                        case "productNameLabel":
                            productName = ((Label) label).getText();
                            break;
                        case "quantityLabel":
                            String quantityLabel = ((Label) label).getText();
                            String cleanedQuantity = quantityLabel.replaceAll("[qty:\\s]", "");
                            productQuantity = Integer.parseInt(cleanedQuantity);
                            break;
                        case "sizeLabel":
                            String sizeLabel = ((Label) label).getText();
                            String cleanedSize = sizeLabel.replaceAll("[size:\\s]", "");
                            productSize = cleanedSize;

                            if (productSize.equals("S")) {
                                productSize = "small";
                                productType = 'b';
                            } else if (productSize.equals("M")) {
                                productSize = "medium";
                                productType = 'b';
                            } else if (productSize.equals("L")) {
                                productSize = "large";
                                productType = 'b';
                            } else {
                                productSize = "";
                                productType = 'f';
                            }
                            break;
                        case "priceLabel":
                            String priceLabel = ((Label) label).getText();
                            String cleanedPrice = priceLabel.replaceAll("[P\\s]", ""); //cleaning
                            productPrice = Double.parseDouble(cleanedPrice);
                    }
                }
            }

            try {
                if (isUpdateModel) {
                    //update the models
                    if (productType == 'f') {
                        this.mainMenuModel.getClientModel().getFoodMenu().get(productName).updateQuantity(-productQuantity); //negative because we want to add/revert back the subtracted from the menu
                    } else if (productType == 'b') {
                        this.mainMenuModel.getClientModel().getBeverageMenu().get(productName).updateQuantity(productSize, -productQuantity);
                    }

                    //clear the cart of the customer
                    this.mainMenuModel.getClientModel().getCart().clear();
                }

                //update the price label
                cartTotalPrice -= productPrice;

                //update the UI
                //set visible the labels
                mainMenuView.getCartLabel1().setVisible(true);
                mainMenuView.getCartLabel2().setVisible(true);
                mainMenuView.getCartImage().setVisible(true);

                //add to grid pane
                mainMenuView.getGridPaneCart().setVisible(false);
                mainMenuView.getPriceLabel().setText("P " + cartTotalPrice + "0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //after clearing, remove all the contents of the pane
        this.mainMenuView.getGridPaneCart().getChildren().clear();
    }

    /**
     * this method sets up the action for checking out button
     */
    private void setUpActionCheckoutButton() {
        this.mainMenuView.setUpActionCheckoutButton((ActionEvent event) -> {
            if (this.mainMenuModel.getClientModel().getCart().isEmpty()) {
                PushNotification.toastSuccess("Cart Empty", "No items to be checked out. Add items to cart.");
            } else {
                try {
                    CheckoutPageModel checkoutPageModel = new CheckoutPageModel(mainMenuModel.getClientModel().getCustomer(), mainMenuModel.getClientModel().getCart(), cartTotalPrice, mainMenuModel.getClientModel().placeOrder());
                    CheckoutPageView checkoutPageView = CheckoutPageView.loadCheckoutPage();
                    CheckoutPageController checkoutPageController = new CheckoutPageController(checkoutPageModel, checkoutPageView);

                    checkoutPageView.getPlaceOrderButton().setOnAction(actionEvent -> {
                        if (!checkoutPageView.getOnlinePayment().isSelected() && !checkoutPageView.getCashOnDelivery().isSelected()) {
                            checkoutPageView.setNoticeLabel("choose payment option");
                            checkoutPageView.getNoticeLabel().setVisible(true);
                        } else if (checkoutPageView.getOnlinePayment().isSelected() || checkoutPageView.getCashOnDelivery().isSelected()) {
                            String clientId = String.valueOf(checkoutPageModel.getCustomer().getName().hashCode());
                            Order order = checkoutPageModel.getOrderFromClient();
                            sendData(clientId, "PROCESS_ORDER",order);


                            checkoutPageView.closeCheckoutView();

                            clearCart(false);


                            PushNotification.toastSuccess("Order", "Order uploaded to the system");
                        } else {
                            checkoutPageView.closeCheckoutView();
                            PushNotification.toastSuccess("Order", "Order uploaded to the system");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Adds a product into a cart. It also loads the selection panels
     */
    private void addToCart(Product product) {
        int quantity = 0;

        SelectFoodController selectFoodController = null;
        SelectBeverageVariationController selectBeverageVariationController = null;

        //load the UIs for selection
        try {
            //set up first the select variation
            if (product.getType() == 'f') {
                FXMLLoader selectFoodLoader = new FXMLLoader(getClass().getResource("/fxml/client/select_food.fxml"));
                Parent root = selectFoodLoader.load();

                selectFoodController = new SelectFoodController(new SelectFoodModel(product), selectFoodLoader.getController());
                Scene scene = new Scene(root);
                Stage popupStage = new Stage();
                popupStage.setScene(scene);
                popupStage.showAndWait(); //wait until the popup stops
            } else {
                FXMLLoader selectBeverageVariationLoader = new FXMLLoader(getClass().getResource("/fxml/client/select_beverage_variation.fxml"));
                Parent root = selectBeverageVariationLoader.load();

                selectBeverageVariationController = new SelectBeverageVariationController(new SelectBeverageVariationModel(product), selectBeverageVariationLoader.getController());
                Scene scene = new Scene(root);
                Stage popupStage = new Stage();
                popupStage.setScene(scene);
                popupStage.showAndWait(); //wait until the popup stops
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //check whether what order type was produced
        if (product instanceof Food) { //means the product is food being added to cart is food
            if (selectFoodController.getFinalOrderedQuantity() != 0) {
                //update first the models
                updateModelsData(product, selectFoodController);

                //add to cart grid pane
                addToCartGridPane(product, selectFoodController.getFinalOrderedQuantity(), selectFoodController.getFinalOrderedPrice(), null);

                //update the cart totals and the view
                updateCartTotalPrice(selectFoodController.getFinalOrderedPrice());
            }
        } else { //means the product being added is food
            //process the small
            if (selectBeverageVariationController.getFinalSmallOrderedQuantity() != 0) {
                //update the models first
                updateModelsData(product, selectBeverageVariationController.getFinalSmallOrderedQuantity(), "small", selectBeverageVariationController.getFinalSmallOrderedPrice());

                //update the cart grid pane
                addToCartGridPane(product, selectBeverageVariationController.getFinalSmallOrderedQuantity(), selectBeverageVariationController.getFinalSmallOrderedPrice(), "S");

                //update the cart totals and the view
                updateCartTotalPrice(selectBeverageVariationController.getFinalSmallOrderedPrice());

            }

            //process the medium
            if (selectBeverageVariationController.getFinalMediumOrderedQuantity() != 0) {
                //update the models first
                updateModelsData(product, selectBeverageVariationController.getFinalMediumOrderedQuantity(), "medium", selectBeverageVariationController.getFinalMediumOrderedPrice());

                //update the cart grid pane
                addToCartGridPane(product, selectBeverageVariationController.getFinalMediumOrderedQuantity(), selectBeverageVariationController.getFinalMediumOrderedPrice(), "M");

                //update the cart totals and the view
                updateCartTotalPrice(selectBeverageVariationController.getFinalMediumOrderedPrice());
            }

            //process the medium
            if (selectBeverageVariationController.getFinalLargeOrderedQuantity() != 0) {
                //update the models first
                updateModelsData(product, selectBeverageVariationController.getFinalLargeOrderedQuantity(), "large", selectBeverageVariationController.getFinalLargeOrderedPrice());

                //update the cart grid pane
                addToCartGridPane(product, selectBeverageVariationController.getFinalLargeOrderedQuantity(), selectBeverageVariationController.getFinalLargeOrderedPrice(), "L");

                //update the cart totals and the view
                updateCartTotalPrice(selectBeverageVariationController.getFinalLargeOrderedPrice());
            }
        }
    }

    /**
     * This method updates the total price being shown in the cart display.
     */
    private void updateCartTotalPrice(double productPrice) {
        cartTotalPrice += productPrice;
        this.mainMenuView.getPriceLabel().setText("P " + cartTotalPrice + "0");
    }

    /**
     * This method updates the models for beverage
     */
    private void updateModelsData(Product product, int count, String size, double totalPrice) {
        try {
            //update the quantity of the main menu food
            mainMenuModel.getClientModel().getBeverageMenu().get(product.getName()).updateQuantity(size, count);

            int sQuantity = 0;
            int mQuantity = 0;
            int lQuantity = 0;
            double sPrice = 0;
            double mPrice = 0;
            double lPrice = 0;
            if (size.equals("small")) {
                sQuantity = count;
                sPrice = totalPrice;
            } else if (size.equals("medium")) {
                mQuantity = count;
                mPrice = totalPrice;
            } else if (size.equals("large")) {
                lQuantity = count;
                lPrice = totalPrice;
            }

            Object[] imageData = {product.getImageName(), ImageUtility.getImageBytes(product.getImageName())};
            Beverage beverage = new Beverage(product.getName(), product.getType(), product.getReview(), product.getReviewCount(), imageData, product.getDescription(), sQuantity, mQuantity, lQuantity, sPrice, mPrice, lPrice);
            //update first the cart of the client model which resides in MainMenuModel.getClientModel()
            mainMenuModel.getClientModel().getCart().add(beverage);

            //set visible the labels
            mainMenuView.getCartLabel1().setVisible(false);
            mainMenuView.getCartLabel2().setVisible(false);
            mainMenuView.getCartImage().setVisible(false);

            //add to grid pane
            mainMenuView.getGridPaneCart().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method updates the models for food
     */
    private void updateModelsData(Product product, SelectFoodController selectFoodController) {
        try {
            //update the quantity of the main menu food
            mainMenuModel.getClientModel().getFoodMenu().get(product.getName()).updateQuantity(selectFoodController.getFinalOrderedQuantity());

            //cast to create a new Food object to be passed on the cart
            Object[] imageData = {product.getImageName(), ImageUtility.getImageBytes(product.getImageName())};
            Food food = new Food(product.getName(), product.getType(), product.getReview(), product.getReviewCount(), imageData, product.getDescription(), selectFoodController.getFinalOrderedQuantity(), selectFoodController.getFinalOrderedPrice());
            //update first the cart of the client model which resides in MainMenuModel.getClientModel()
            mainMenuModel.getClientModel().getCart().add(food);


            //set visible the labels
            mainMenuView.getCartLabel1().setVisible(false);
            mainMenuView.getCartLabel2().setVisible(false);
            mainMenuView.getCartImage().setVisible(false);

            //add to grid pane
            mainMenuView.getGridPaneCart().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A helper method that creates a new cards in the cart grid view
     */
    private void addToCartGridPane(Product product, int finalOrderedQuantity, double finalOrderedPrice, String size) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/client/cart_item_card.fxml"));

            //putting the card on the anchorPane
            AnchorPane card = loader.load();

            CartItemCardController cardCart = new CartItemCardController(new CartItemCardModel(product, finalOrderedQuantity, finalOrderedPrice, size), loader.getController());
            cardCart.setData();

            if (cartColumn == 1) {
                cartColumn = 0;
                cartRow++;
            }
            mainMenuView.getGridPaneCart().add(card, cartColumn++, cartRow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainMenuClientPageView getMainMenuView() {
        return mainMenuView;
    }

    public void setMainMenuView(MainMenuClientPageView mainMenuView) {
        this.mainMenuView = mainMenuView;
    }

    public MainMenuClientPageModel getMainMenuModel() {
        return mainMenuModel;
    }

    public void setMainMenuModel(MainMenuClientPageModel mainMenuModel) {
        this.mainMenuModel = mainMenuModel;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public Object[] getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(Object[] serverResponse) {
        this.serverResponse = serverResponse;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getCartColumn() {
        return cartColumn;
    }

    public void setCartColumn(int cartColumn) {
        this.cartColumn = cartColumn;
    }

    public int getCartRow() {
        return cartRow;
    }

    public void setCartRow(int cartRow) {
        this.cartRow = cartRow;
    }

    public double getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(double cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }
} // end of MainMenuClientPageController class