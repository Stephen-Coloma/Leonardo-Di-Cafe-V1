package client.controller.fxmlcontroller;

import client.model.fxmlmodel.CheckoutItemCardModel;
import client.model.fxmlmodel.CheckoutPageModel;
import client.view.fxmlview.CheckoutPageView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import shared.Beverage;
import shared.Food;
import shared.Order;
import shared.Product;
import util.PushNotification;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class CheckoutPageController {
    private CheckoutPageModel model;
    private CheckoutPageView view;
    boolean orderSuccessful = false; // to be accessed after pop up
    private Order orderProcessedByServer;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private double deliveryPayment = 60;

    public CheckoutPageController(CheckoutPageModel model, CheckoutPageView view){
        this.model = model;
        this.view = view;

        //set up the grid pane from the client's cart
        populateGridPane();

        //setting up the view
        setUpView();

        /*
        //setting up the action for placeOrderButton
        this.view.setUpActionPlaceOrderButton((ActionEvent event) ->{
            if (!this.view.getOnlinePayment().isSelected() && !this.view.getCashOnDelivery().isSelected()){
                this.view.getNoticeLabel().setText("choose payment option");
                this.view.getNoticeLabel().setVisible(true);
                return;
            }else{
                orderProcessedByServer = submitOrderToServer(this.model.getOrderFromClient());
            }

            //after server transaction
            if (orderProcessedByServer != null){
                orderSuccessful = true;
            }

            //closes the whole checkout page
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        });

         */
    }

    /**This method of the client sends an order request to the server*/
    private Order submitOrderToServer(Order orderFromClient) {
        try {
            String clientID = String.valueOf(this.model.getCustomer().getName().hashCode());
            sendData(clientID, "PROCESS_ORDER", orderFromClient);

            Object[] serverResponse;
            if ((serverResponse = (Object[]) in.readObject()) != null){
                return parseServerResponse(serverResponse);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }



    /**This method populates the grid pane in the checkout page*/
    private void populateGridPane() {
        List<Product> cart = model.getCart();
        int column = 0;
        int row = 1;
        try {
            for (Product product: cart) {
                //load first
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/client/checkout_item_card.fxml"));

                //putting the card on the anchorPane
                Pane card = loader.load();

                if (product instanceof Food){
                    Food food = (Food) product;
                    CheckoutItemCardController cardOnCheckout = new CheckoutItemCardController(new CheckoutItemCardModel(food), loader.getController());
                    cardOnCheckout.setData();
                }else if (product instanceof Beverage){
                    Beverage beverage = (Beverage) product;
                    CheckoutItemCardController cardOnCheckout = new CheckoutItemCardController(new CheckoutItemCardModel(beverage), loader.getController());
                    cardOnCheckout.setData();
                }

                if (column == 1){
                    column = 0;
                    row++;
                }
                view.getGridPaneCartOnCheckOut().add(card, column++, row);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setUpView() {
        this.view.getClientName().setText(model.getCustomer().getName());
        this.view.getClientAddress().setText(model.getCustomer().getAddress());

        //randomize the delivery payment
        // Initialize a Random object
        Random random = new Random();

        // Generate a random integer between 0 and 14 (15 intervals)
        int interval = random.nextInt(4);

        // Calculate the deliveryPayment within the specified range and intervals
        deliveryPayment = 8 + interval * 10;

        this.view.getSubtotalPriceLabel().setText("P " + model.getSubTotal() + "0");
        this.view.getDeliveryFeeLabel().setText("P " + deliveryPayment + "0");
        double totalAmount = model.getSubTotal() + deliveryPayment;
        this.view.getTotalAmountLabel().setText("P " + totalAmount + "0");
    }

    public CheckoutPageModel getModel() {
        return model;
    }

    public void setModel(CheckoutPageModel model) {
        this.model = model;
    }

    public CheckoutPageView getView() {
        return view;
    }

    public void setView(CheckoutPageView view) {
        this.view = view;
    }

    public boolean isOrderSuccessful() {
        return orderSuccessful;
    }

    public void setOrderSuccessful(boolean orderSuccessful) {
        this.orderSuccessful = orderSuccessful;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Order getOrderProcessedByServer() {
        return orderProcessedByServer;
    }

    public void setOrderProcessedByServer(Order orderProcessedByServer) {
        this.orderProcessedByServer = orderProcessedByServer;
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

    public double getDeliveryPayment() {
        return deliveryPayment;
    }

    public void setDeliveryPayment(double deliveryPayment) {
        this.deliveryPayment = deliveryPayment;
    }

    /**Helper method that sends data to server*/
    private void sendData(String clientID, String requestType, Object data) throws IOException{
        Object[] request = new Object[]{clientID, requestType, data};
        out.writeObject(request);
    }

    /**Parses the server response*/
    private Order parseServerResponse(Object[] serverResponse) {
        //Guide Object[] {clientID, key, data}
        if (serverResponse[1].equals("PROCESS_ORDER_SUCCESSFUL")){
            PushNotification.toastSuccess("Order Successful", "Your order has been placed.");
            return (Order) serverResponse[2];
        }else {
            PushNotification.toastError("Order Failed", "Your order is not successful");
            return null;
        }
    }
}
