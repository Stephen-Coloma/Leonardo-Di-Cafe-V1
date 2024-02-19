package client.view.fxmlview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class OrderHistoryPageView {

    @FXML
    private Button cancelButton;
    @FXML
    private GridPane gridPaneOrderHistory;
    @FXML
    private ScrollPane scrollPaneOrderHistory;

    @FXML
    private Button submitReviewButton;
    private static Stage popupStage;

    public static <T> T loadCheckoutPage() {
        try {
            FXMLLoader loader = new FXMLLoader(CheckoutPageView.class.getResource("/fxml/client/order_history_page.fxml"));
            popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Order History");
            popupStage.setScene(new Scene(loader.load()));
            popupStage.show();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of loadCheckoutPage

    public void closeCheckoutView() {
        this.getGridPaneOrderHistory().getChildren().clear(); //make sure to clear all the items in the pane before closing
        popupStage.close();
    }


    public GridPane getGridPaneOrderHistory() {
        return gridPaneOrderHistory;
    }

    public void setGridPaneOrderHistory(GridPane gridPaneOrderHistory) {
        this.gridPaneOrderHistory = gridPaneOrderHistory;
    }

    public ScrollPane getScrollPaneOrderHistory() {
        return scrollPaneOrderHistory;
    }

    public void setScrollPaneOrderHistory(ScrollPane scrollPaneOrderHistory) {
        this.scrollPaneOrderHistory = scrollPaneOrderHistory;
    }

    public Button getSubmitReviewButton() {
        return submitReviewButton;
    }

    public void setSubmitReviewButton(Button submitReviewButton) {
        this.submitReviewButton = submitReviewButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
    }

    public static Stage getPopupStage() {
        return popupStage;
    }

    public static void setPopupStage(Stage popupStage) {
        OrderHistoryPageView.popupStage = popupStage;
    }
}
