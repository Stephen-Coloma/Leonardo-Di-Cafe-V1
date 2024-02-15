package client.view.fxmlview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import shared.Customer;

import java.io.IOException;

public class SignUpPageView {
    @FXML
    private Button homeButton;
    @FXML
    private Button createAccountButton;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox termsAndServicesCheckBox;
    @FXML
    private Label noticeLabel;
    private FXMLLoader loader;
    private Parent root;
    private Stage stage;

    public void setActionSignUpButton(EventHandler<ActionEvent> event){
        createAccountButton.setOnAction(event);
    }

    public void setActionHomeButton(EventHandler<ActionEvent> event){
        homeButton.setOnAction(event);
    }


    public void createAccountButtonEntered(MouseEvent event){
        createAccountButton.setStyle("-fx-background-color: lightgray;");
        createAccountButton.setTextFill(Paint.valueOf("Black"));
    }
    public void createAccountButtonExited(MouseEvent event){
        createAccountButton.setStyle("-fx-background-color:  #A38157;");
        createAccountButton.setTextFill(Paint.valueOf("White"));
    }

    public Button getCreateAccountButton() {
        return createAccountButton;
    }

    public void setCreateAccountButton(Button createAccountButton) {
        this.createAccountButton = createAccountButton;
    }

    public TextField getFullNameTextField() {
        return fullNameTextField;
    }

    public void setFullNameTextField(TextField fullNameTextField) {
        this.fullNameTextField = fullNameTextField;
    }

    public TextField getUserNameTextField() {
        return userNameTextField;
    }

    public void setUserNameTextField(TextField userNameTextField) {
        this.userNameTextField = userNameTextField;
    }

    public TextField getAddressTextField() {
        return addressTextField;
    }

    public void setAddressTextField(TextField addressTextField) {
        this.addressTextField = addressTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public void setEmailTextField(TextField emailTextField) {
        this.emailTextField = emailTextField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public CheckBox getTermsAndServicesCheckBox() {
        return termsAndServicesCheckBox;
    }

    public void setTermsAndServicesCheckBox(CheckBox termsAndServicesCheckBox) {
        this.termsAndServicesCheckBox = termsAndServicesCheckBox;
    }
    public Button getBackButton() {
        return homeButton;
    }

    public void setBackButton(Button homeButton) {
        this.homeButton = SignUpPageView.this.homeButton;
    }
    public Label getNoticeLabel() {
        return noticeLabel;
    }

    public void setNoticeLabel(Label noticeLabel) {
        this.noticeLabel = noticeLabel;
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

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
