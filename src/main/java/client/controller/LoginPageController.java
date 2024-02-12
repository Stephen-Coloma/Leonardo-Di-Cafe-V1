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

public class LoginPageController {
    @FXML
    private Button loginButton;



    public void showFrontPage (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/client/secondFrontPage.fxml")));
        Parent root=null;
        Stage stage=null;
        Scene scene=null;

        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void loginPageButtonEntered(MouseEvent event){
        loginButton.setStyle("-fx-background-color: lightgray;");
        loginButton.setTextFill(Paint.valueOf("Black"));
    }
    public void loginPageButtonExited(MouseEvent event){
        loginButton.setStyle("-fx-background-color:  #A38157;");
        loginButton.setTextFill(Paint.valueOf("White"));
    }

}
