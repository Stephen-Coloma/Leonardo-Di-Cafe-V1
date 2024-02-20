package client.view.fxmlview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CheckoutPageView {
    @FXML
    private RadioButton cashOnDelivery;

    @FXML
    private Label clientAddress;

    @FXML
    private Label clientName;

    @FXML
    private Label deliveryFeeLabel;

    @FXML
    private GridPane gridPaneCartOnCheckOut;

    @FXML
    private Label noticeLabel;

    @FXML
    private RadioButton onlinePayment;

    @FXML
    private Button placeOrderButton;

    @FXML
    private ScrollPane scrollPaneCartOnCheckOut;

    @FXML
    private Label subtotalPriceLabel;

    @FXML
    private Label totalAmountLabel;
    private static Stage popupStage;

    public static <T> T loadCheckoutPage() {
        try {
            FXMLLoader loader = new FXMLLoader(CheckoutPageView.class.getResource("/fxml/client/checkout_page.fxml"));
            popupStage = new Stage();
            popupStage.getIcons().add(new Image(CheckoutPageView.class.getResource("/images/client/client_app_logo.png").toExternalForm()));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Checkout");
            popupStage.setScene(new Scene(loader.load()));
            popupStage.show();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of loadCheckoutPage

    public void closeCheckoutView() {
        popupStage.close();
    }

    public void setUpActionPlaceOrderButton(EventHandler<ActionEvent> event){
        placeOrderButton.setOnAction(event);
    }

    public void placeOrderButtonEntered(MouseEvent event){
        placeOrderButton.setStyle("-fx-background-color: lightgray;");
        placeOrderButton.setTextFill(Paint.valueOf("Black"));
    }
    public void placeOrderButtonExited(MouseEvent event){
        placeOrderButton.setStyle("-fx-background-color:  #A38157;");
        placeOrderButton.setTextFill(Paint.valueOf("White"));
    }

    public RadioButton getCashOnDelivery() {
        return cashOnDelivery;
    }

    public void setCashOnDelivery(RadioButton cashOnDelivery) {
        this.cashOnDelivery = cashOnDelivery;
    }

    public Label getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(Label clientAddress) {
        this.clientAddress = clientAddress;
    }

    public Label getClientName() {
        return clientName;
    }

    public void setClientName(Label clientName) {
        this.clientName = clientName;
    }

    public Label getDeliveryFeeLabel() {
        return deliveryFeeLabel;
    }

    public void setDeliveryFeeLabel(Label deliveryFeeLabel) {
        this.deliveryFeeLabel = deliveryFeeLabel;
    }

    public GridPane getGridPaneCartOnCheckOut() {
        return gridPaneCartOnCheckOut;
    }

    public void setGridPaneCartOnCheckOut(GridPane gridPaneCartOnCheckOut) {
        this.gridPaneCartOnCheckOut = gridPaneCartOnCheckOut;
    }

    public RadioButton getOnlinePayment() {
        return onlinePayment;
    }

    public void setOnlinePayment(RadioButton onlinePayment) {
        this.onlinePayment = onlinePayment;
    }

    public Button getPlaceOrderButton() {
        return placeOrderButton;
    }

    public void setPlaceOrderButton(Button placeOrderButton) {
        this.placeOrderButton = placeOrderButton;
    }

    public ScrollPane getScrollPaneCartOnCheckOut() {
        return scrollPaneCartOnCheckOut;
    }

    public void setScrollPaneCartOnCheckOut(ScrollPane scrollPaneCartOnCheckOut) {
        this.scrollPaneCartOnCheckOut = scrollPaneCartOnCheckOut;
    }

    public Label getSubtotalPriceLabel() {
        return subtotalPriceLabel;
    }

    public void setSubtotalPriceLabel(Label subtotalPriceLabel) {
        this.subtotalPriceLabel = subtotalPriceLabel;
    }

    public Label getTotalAmountLabel() {
        return totalAmountLabel;
    }

    public void setTotalAmountLabel(Label totalAmountLabel) {
        this.totalAmountLabel = totalAmountLabel;
    }

    public Label getNoticeLabel() {
        return noticeLabel;
    }

    public void setNoticeLabel(String value) {
        noticeLabel.setText(value);
    }
}
