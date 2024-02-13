package client.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class CheckoutPageController {

    @FXML
    private Button placeOrderButton;
    private void loadPage(String page, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/client/"+page+".fxml")));
        Parent root=null;
        Stage stage=null;
        Scene scene=null;

        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);


        stage.show();
    }
    public void placeOrder (ActionEvent event) throws IOException {
        loadPage("clientMenu", event);
    }
    public void placeOrderButtonEntered(MouseEvent event){
        placeOrderButton.setStyle("-fx-background-color: lightgray;");
        placeOrderButton.setTextFill(Paint.valueOf("Black"));
    }
    public void placeOrderButtonExited(MouseEvent event){
        placeOrderButton.setStyle("-fx-background-color:  #A38157;");
        placeOrderButton.setTextFill(Paint.valueOf("White"));
    }
}
