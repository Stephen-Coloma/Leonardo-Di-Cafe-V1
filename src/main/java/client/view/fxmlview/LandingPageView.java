package client.view.fxmlview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class LandingPageView {
    @FXML
    private Button loginButtonFrontPage;
    @FXML
    private Button signupButtonFrontPage;
    @FXML
    private LoginPageView loginPageView;
    @FXML
    private SignUpPageView signUpPageView;

    private FXMLLoader loader;
    private Parent root;

    public void setActionLoginButton(EventHandler<ActionEvent> event){
        loginButtonFrontPage.setOnAction(event);
    }

    public void setActionSignUpButton(EventHandler<ActionEvent> event){
        signupButtonFrontPage.setOnAction(event);
    }

//    private void loadPage(String page, ActionEvent event) throws IOException {
//        loader = new FXMLLoader(getClass().getResource("/fxml/client/" + page + ".fxml"));
//
//        root = loader.load();
//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public void showLoginUI (ActionEvent event) throws IOException {
//        loadPage("login_page", event);
//    }
//    public void showRegistrationUI (ActionEvent event) throws IOException {
//        loadPage("signup_page", event);
//    }

    public void loginFrontPageButtonEntered(MouseEvent event){
        loginButtonFrontPage.setStyle("-fx-background-color: #c7a97f;");
    }
    public void loginFrontPageButtonExited(MouseEvent event){
        loginButtonFrontPage.setStyle("-fx-background-color:  #A38157;");
    }
    public void signupFrontPageButtonEntered(MouseEvent event){
        signupButtonFrontPage.setStyle("-fx-background-color: #c7a97f;");
    }
    public void signupFrontPageButtonExited(MouseEvent event){
        signupButtonFrontPage.setStyle("-fx-background-color:  #A38157;");
    }

    public Button getLoginButtonFrontPage() {
        return loginButtonFrontPage;
    }

    public void setLoginButtonFrontPage(Button loginButtonFrontPage) {
        this.loginButtonFrontPage = loginButtonFrontPage;
    }

    public Button getSignupButtonFrontPage() {
        return signupButtonFrontPage;
    }

    public void setSignupButtonFrontPage(Button signupButtonFrontPage) {
        this.signupButtonFrontPage = signupButtonFrontPage;
    }

    public LoginPageView getLoginPageView() {
        return loginPageView;
    }

    public void setLoginPageView(LoginPageView loginPageView) {
        this.loginPageView = loginPageView;
    }

    public SignUpPageView getSignUpPageView() {
        return signUpPageView;
    }

    public void setSignUpPageView(SignUpPageView signUpPageView) {
        this.signUpPageView = signUpPageView;
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
