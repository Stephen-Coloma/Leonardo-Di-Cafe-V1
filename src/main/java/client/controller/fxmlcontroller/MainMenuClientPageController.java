package client.controller.fxmlcontroller;


import client.model.fxmlmodel.MainMenuClientPageModel;
import client.model.fxmlmodel.MenuCardModel;
import client.view.fxmlview.MainMenuClientPageView;
import client.view.fxmlview.MenuCardView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import shared.Beverage;
import shared.Food;
import shared.Product;

import java.util.HashMap;
import java.util.List;

public class MainMenuClientPageController {
    private MainMenuClientPageView mainMenuView;
    private MainMenuClientPageModel mainMenuModel;
    private FXMLLoader loader;
    private Parent root;
    private Object[] serverResponse;
    public MainMenuClientPageController(MainMenuClientPageModel mainMenuModel, MainMenuClientPageView mainMenuView){
        this.mainMenuView = mainMenuView;
        this.mainMenuModel = mainMenuModel;

        initializeFoodMenu();

        //setting up action listener for food button
        setActionMenuFoodButton();
        setActionMenuBeverageButton();
    }

    private void addToCart(){

    }
    /**After login is successful, load the food menu*/
    private void initializeFoodMenu() {
        List<Food> foodMenu = mainMenuModel.getClientModel().getFoodMenu().values().stream().toList();
        int column = 0;
        int row = 1;

        //initializing the food menu cards from the hashmap
        try {
            for (Food product : foodMenu) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/client/menu_card.fxml"));

                //putting the card on the anchorPane
                AnchorPane card = loader.load();

                MenuCardController menuCard = new MenuCardController(new MenuCardModel(product) , loader.getController());
                menuCard.setData();

                //this code setups up add to cart button of each card
                menuCard.setActionAddProductButton((ActionEvent event1) ->{
                    addToCart();
                });

                if (column == 2){
                    column = 0;
                    row++;
                }
                mainMenuView.getGridPaneMenu().add(card, column++, row);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**This method sets up the action for menufoodMenu button*/
    private void setActionMenuBeverageButton() {
        mainMenuView.getMainMenuBeveragesButton().setOnAction((ActionEvent event)->{

            mainMenuView.getGridPaneMenu().getChildren().clear();//clears up the content

            List<Beverage> beverageMenu = mainMenuModel.getClientModel().getBeverageMenu().values().stream().toList();
            int column = 0;
            int row = 1;

            //initializing the food menu cards from the hashmap
            try {
                for (Beverage product : beverageMenu) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/fxml/client/menu_card.fxml"));

                    //putting the card on the anchorPane
                    AnchorPane card = loader.load();

                    MenuCardController menuCard = new MenuCardController(new MenuCardModel(product) , loader.getController());
                    menuCard.setData();

                    //this code setups up add to cart button of each card
                    menuCard.setActionAddProductButton((ActionEvent event1) ->{
                        addToCart();
                    });



                    if (column == 2){
                        column = 0;
                        row++;
                    }
                    mainMenuView.getGridPaneMenu().add(card, column++, row);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });


    }

    /**This method sets up action listener for menuFoodButton*/
    private void setActionMenuFoodButton() {
        mainMenuView.getMainMenuFoodButton().setOnAction((ActionEvent event) ->{
            mainMenuView.getGridPaneMenu().getChildren().clear();
            initializeFoodMenu();
        });
    }
}