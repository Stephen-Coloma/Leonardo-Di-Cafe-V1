package client.controller;

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

public class SignUpController {
    @FXML
    private Button createAccountButton;

    public void showLoginUI (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/loginPage.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void createAccountButtonEntered(MouseEvent event){
        createAccountButton.setStyle("-fx-background-color: lightgray;");
        createAccountButton.setTextFill(Paint.valueOf("Black"));
    }
    public void createAccountButtonExited(MouseEvent event){
        createAccountButton.setStyle("-fx-background-color:  #A38157;");
        createAccountButton.setTextFill(Paint.valueOf("White"));
    }
}
