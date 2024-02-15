package client.view.fxmlview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageView {
    @FXML
    private Button backButton;

    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label noticeLabel;
    private FXMLLoader loader;
    private Parent root;

    //TODO
    public void showMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        loader = new FXMLLoader(getClass().getResource("/fxml/client/main_menu_client_page.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showLandingPage(ActionEvent event) throws IOException{
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        loader = new FXMLLoader(getClass().getResource("/fxml/client/landing_page.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public String[] getCredentials () throws IOException {
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        if (username.equals("") || password.equals("")){
            return null;
        }else {
            return new String[]{username, password};
        }
    }

    public void incorrectDetails(){
        usernameTextField.clear();
        passwordField.clear();
        noticeLabel.setText("incorrect login credentials");
        if (!noticeLabel.isVisible()){
            noticeLabel.setVisible(true);
        }
    }

    public void emptyField(){
        noticeLabel.setText("fill out all details");
        if (!noticeLabel.isVisible()){
            noticeLabel.setVisible(true);
        }
    }

    public void serverError() throws IOException {
        noticeLabel.setVisible(false);
        usernameTextField.clear();
        passwordField.clear();

        Parent root  = FXMLLoader.load(getClass().getResource("/fxml/client/server_error.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
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

    public Button getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(Button loginButton) {
        this.loginButton = loginButton;
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public void setUsernameTextField(TextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public Label getNoticeLabel() {
        return noticeLabel;
    }

    public void setNoticeLabel(Label noticeLabel) {
        this.noticeLabel = noticeLabel;
    }
    public Button getBackButton() {
        return backButton;
    }

    public void setBackButton(Button backButton) {
        this.backButton = backButton;
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
