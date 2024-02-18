package client.controller.fxmlcontroller;


import client.model.fxmlmodel.*;
import client.view.fxmlview.CheckoutPageView;
import client.view.fxmlview.MainMenuClientPageView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import shared.*;
import util.ImageUtility;
import util.PushNotification;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainMenuClientPageController {
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

    public MainMenuClientPageController(MainMenuClientPageModel mainMenuModel, MainMenuClientPageView mainMenuView) {
        this.mainMenuView = mainMenuView;
        this.mainMenuModel = mainMenuModel;

        // setting up the time and date labels
        setupClock();
        setupDate();

        //initialize the account name
        String accountName = this.mainMenuModel.getClientModel().getCustomer().getName();
        this.mainMenuView.getAccountNameLabel().setText(accountName);

        initializeFoodMenu();

        setComponentActions();
    }

    private void setComponentActions() {
        //setting up action listener for food button
        setActionMenuFoodButton();
        setActionMenuBeverageButton();

        //setting up the action for setUpActionClearCartButtonButton
        setUpActionClearCartButton();

        //set up the action for checking out
        seUpActionCheckoutButton();
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

    /**
     * After login is successful, load the food menu
     */
    private void initializeFoodMenu() {
        mainMenuView.getProductTypeLabel().setText("Food Category");

        List<Food> foodMenu = mainMenuModel.getClientModel().getFoodMenu().values().stream().toList();
        int column = 0;
        int row = 1;

        //initializing the food menu cards from the hashmap
        try {
            for (Food product : foodMenu) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/client/menu_card.fxml"));

                //putting the card on the anchorPane
                AnchorPane card = loader.load();

                MenuCardController menuCard = new MenuCardController(new MenuCardModel(product), loader.getController());
                menuCard.setData();

                //this code setups up add to cart button of each card
                menuCard.setActionAddProductButton((ActionEvent event1) -> {
                    addToCart(product);
                });

                if (column == 2) {
                    column = 0;
                    row++;
                }
                mainMenuView.getGridPaneMenu().add(card, column++, row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method sets up the action for menuBeverage button
     */
    private void setActionMenuBeverageButton() {
        mainMenuView.setActionMainMenuBeverageButton((ActionEvent event) -> {
            mainMenuView.getProductTypeLabel().setText("Beverage Category");

            mainMenuView.getGridPaneMenu().getChildren().clear();//clears up the content

            List<Beverage> beverageMenu = mainMenuModel.getClientModel().getBeverageMenu().values().stream().toList();
            int column = 0;
            int row = 1;

            //initializing the food menu cards from the hashmap
            try {
                for (Beverage product : beverageMenu) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/fxml/client/menu_card.fxml"));

                    //putting the card on the anchorPane
                    AnchorPane card = loader.load();

                    MenuCardController menuCard = new MenuCardController(new MenuCardModel(product), loader.getController());
                    menuCard.setData();

                    //this code setups up add to cart button of each card
                    menuCard.setActionAddProductButton((ActionEvent event1) -> {
                        addToCart((Product) product);
                    });

                    if (column == 2) {
                        column = 0;
                        row++;
                    }
                    mainMenuView.getGridPaneMenu().add(card, column++, row);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * This method sets up action listener for menuFoodButton
     */
    private void setActionMenuFoodButton() {
        mainMenuView.setActionMainMenuFoodButton((ActionEvent event) -> {
            mainMenuView.getGridPaneMenu().getChildren().clear();
            initializeFoodMenu();
        });
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
    private void seUpActionCheckoutButton() {
        this.mainMenuView.setUpActionCheckoutButton((ActionEvent event) -> {
            if (this.mainMenuModel.getClientModel().getCart().isEmpty()) {
                PushNotification.toastSuccess("Cart Empty", "No items to be checked out. Add items to cart.");
                return;
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


                    /*
                    FXMLLoader checkoutLoader = new FXMLLoader(getClass().getResource("/fxml/client/checkout_page.fxml"));
                    Parent root = checkoutLoader.load();

                    //passing now the data needed for checkout
                    Order orderFromClient = this.mainMenuModel.getClientModel().placeOrder();
                    Customer customer = this.mainMenuModel.getClientModel().getCustomer();
                    List<Product> clientCart = this.mainMenuModel.getClientModel().getCart();

                    double subtotal = cartTotalPrice; //get what is on the cartTotalLabelPrice

                    checkoutPageController.setSocket(this.getSocket());
                    checkoutPageController.setIn(in); //in and out of the main menu client page
                    checkoutPageController.setOut(out);

                    Scene scene = new Scene(root);
                    Stage popupStage = new Stage();
                    popupStage.setScene(scene);
                    popupStage.showAndWait(); //wait until the popup stops

                     */

                    //after the pop up closed, access now the orderProcessedByServer
                    /*
                    if (checkoutPageController.isOrderSuccessful()) {
                        //clear the cart without updating the model
                        clearCart(false);

                        //update the client model
                        this.mainMenuModel.getClientModel().orderProcessSuccessful(checkoutPageController.getOrderProcessedByServer());
                    }
                     */
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