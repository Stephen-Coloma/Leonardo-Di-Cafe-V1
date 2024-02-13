package client.view;

import javafx.event.ActionEvent;
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

public class SignUpPageController {
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
    private FXMLLoader loader;
    private Parent root;
    private Stage stage;

    public Customer getCredentials(ActionEvent event) throws IOException {

        String fullname = fullNameTextField.getText();
        String username = userNameTextField.getText();
        String address = addressTextField.getText();
        String email = emailTextField.getText();
        String password = passwordField.getText();

        //TODO if checkbox is not yet marked throw exception
        Customer customer = new Customer(fullname, username, address, email, password);
        return customer;
    }

    //TODO
    public void showLoginPage(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        loader = new FXMLLoader((getClass().getResource("/fxml/client/login_page.fxml")));

        root = loader.load();
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
