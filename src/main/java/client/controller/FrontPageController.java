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

public class FrontPageController {
    @FXML
    private Button loginButtonFrontPage;
    @FXML
    private Button registerButtonFrontPage;

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

    public void showLoginUI (ActionEvent event) throws IOException {
        loadPage("loginPage", event);
    }
    public void showRegistrationUI (ActionEvent event) throws IOException {
        loadPage("signup", event);
    }
    public void loginFrontPageButtonEntered(MouseEvent event){
        loginButtonFrontPage.setStyle("-fx-background-color: lightgray;");
        loginButtonFrontPage.setTextFill(Paint.valueOf("Black"));
    }
    public void loginFrontPageButtonExited(MouseEvent event){
        loginButtonFrontPage.setStyle("-fx-background-color:  #A38157;");
        loginButtonFrontPage.setTextFill(Paint.valueOf("White"));
    }
    public void registerFrontPageButtonEntered(MouseEvent event){
        registerButtonFrontPage.setStyle("-fx-background-color: lightgray;");
        registerButtonFrontPage.setTextFill(Paint.valueOf("Black"));
    }
    public void registerFrontPageButtonExited(MouseEvent event){
        registerButtonFrontPage.setStyle("-fx-background-color:  #A38157;");
        registerButtonFrontPage.setTextFill(Paint.valueOf("White"));
    }
}
