package client.controller.fxmlcontroller;

import client.model.fxmlmodel.LoginPageModel;
import client.model.fxmlmodel.MainMenuClientPageModel;
import client.view.fxmlview.LoginPageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {
    private LoginPageView loginView;
    private LoginPageModel loginModel;
    private LandingPageController landingPageController;
    private MainMenuClientPageController mainMenu;
    private FXMLLoader loader;
    private Parent root;
    private Object[] serverResponse;

    public LoginPageController(LoginPageView loginView, LoginPageModel loginModel){
        this.loginView = loginView;
        this.loginModel = loginModel;

        //setting up action for login button
        this.loginView.setActionLoginButton((ActionEvent event)->{
            String username = loginView.getUsernameTextField().getText();
            String password = loginView.getPasswordField().getText();

            if (username.equals("") || password.equals("")){
                loginView.getNoticeLabel().setText("fill out all details");
                loginView.getNoticeLabel().setVisible(true);
            }else {
                loginView.getNoticeLabel().setVisible(false);
                try {
                    loginModel.authenticate(username, password); //call the model for authentication
                    serverResponse = loginModel.getServerResponse();

                    //parse the server response
                    parseServerResponse(serverResponse, event);

                } catch (IOException e) { //load a new popup when failed to connect to server
                    showServerErrorUI();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //setting up action for home button. the button will load the Landing Page
        this.loginView.setActionHomeButton((ActionEvent event) ->{
            //load the view before getting its controller
           try {
               loader = new FXMLLoader(getClass().getResource("/fxml/client/landing_page.fxml"));
               root = loader.load();

               landingPageController = new LandingPageController(loader.getController());

               Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
               Scene scene = new Scene(root);
               stage.setScene(scene);
               stage.show();
           }catch (IOException e){
               e.printStackTrace();
           }
        });
    }

    /**This method parses the response from the server. If Login in successful, laod the main menu client page*/
    private void parseServerResponse(Object[] serverResponse, ActionEvent event) {
        //server response Guide Object[]{clientID, message, Object[] clientModelData}
        //load login UI if successful
        try {
            if (serverResponse[1].equals("LOGIN_SUCCESSFUL")){
                loader = new FXMLLoader(getClass().getResource("/fxml/client/main_menu_client_page.fxml"));
                root = loader.load();

                //when loading the main menu, pass the clientModel received from the server
                mainMenu = new MainMenuClientPageController(new MainMenuClientPageModel((Object[]) serverResponse[2]), loader.getController());
                mainMenu.setSocket(this.loginModel.getSocket()); //todo: getting the socket from the login controller

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }else {
                this.loginView.getNoticeLabel().setText("wrong credentials");
                this.loginView.getNoticeLabel().setVisible(true);
                this.loginView.getUsernameTextField().clear();
                this.loginView.getPasswordField().clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**This shows the server error UI*/
    private void showServerErrorUI() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/client/server_error.fxml"));
            Scene serverErrorScene = new Scene(root);
            Stage popUpStage = new Stage();
            popUpStage.setScene(serverErrorScene);
            popUpStage.show();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
