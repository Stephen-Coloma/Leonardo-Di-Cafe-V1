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
    @FXML
    private LoginPageController loginPageController;
    @FXML
    private SignUpController signUpController;

    private FXMLLoader loader;
    private Parent root;
    private void loadPage(String page, ActionEvent event) throws IOException {
        loader = new FXMLLoader((getClass().getResource("/fxml/client/"+page+".fxml")));

        root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
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

    public Button getLoginButtonFrontPage() {
        return loginButtonFrontPage;
    }

    public void setLoginButtonFrontPage(Button loginButtonFrontPage) {
        this.loginButtonFrontPage = loginButtonFrontPage;
    }

    public Button getRegisterButtonFrontPage() {
        return registerButtonFrontPage;
    }

    public void setRegisterButtonFrontPage(Button registerButtonFrontPage) {
        this.registerButtonFrontPage = registerButtonFrontPage;
    }

    public LoginPageController getLoginPageController() {
        return loginPageController;
    }

    public void setLoginPageController(LoginPageController loginPageController) {
        this.loginPageController = loginPageController;
    }

    public SignUpController getSignUpController() {
        return signUpController;
    }

    public void setSignUpController(SignUpController signUpController) {
        this.signUpController = signUpController;
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
}
