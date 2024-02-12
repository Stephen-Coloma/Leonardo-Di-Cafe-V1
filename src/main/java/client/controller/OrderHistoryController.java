package client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class OrderHistoryController {

    @FXML
    private Button orderHistoryExit;

    public void orderHistoryExit(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    public void orderHistoryExitButtonEntered(MouseEvent event){
        orderHistoryExit.setStyle("-fx-background-color: white;");
        orderHistoryExit.setTextFill(Paint.valueOf("red"));
    }
    public void orderHistoryExitButtonExited(MouseEvent event){
        orderHistoryExit.setStyle("-fx-background-color:  #FFFFFF;");
        orderHistoryExit.setTextFill(Paint.valueOf("Black"));
    }
}
