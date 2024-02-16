package client.controller.fxmlcontroller;

import client.model.fxmlmodel.MenuCardModel;
import client.view.fxmlview.MenuCardView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import shared.Beverage;
import shared.Food;
import shared.Product;

import java.util.HashMap;

public class MenuCardController {
    private MenuCardModel menuCardModel;
    private MenuCardView menuCardView;

    public MenuCardController(MenuCardModel menuCardModel, MenuCardView menuCardView){
        this.menuCardView = menuCardView;
        this.menuCardModel = menuCardModel;
    }


    public void setData(){
        Product product = menuCardModel.getProduct();
        if (product.getType() == 'f'){
            Food food = (Food) product;
            setFoodDataOnCard(food);
        }else if (product.getType() == 'b'){
            Beverage beverage = (Beverage) product;
            setBeverageDataOnCard(beverage);
        }
    }

    /**This sets up Beverage data of in the card*/
    private void setBeverageDataOnCard(Beverage beverage) {
        HashMap<String, Double> sizePrice = beverage.getSizePrice();

        menuCardView.getProductNameLabel().setText(menuCardModel.getProduct().getName());
        menuCardView.getProductDetailsLabel().setText(menuCardModel.getProduct().getDescription());
        menuCardView.getProductRatingLabel().setText("Rating: " + menuCardModel.getProduct().getReview());
        menuCardView.getSizeLabel().setVisible(true);
        menuCardView.getProductPriceLabel().setText("P " + sizePrice.get("small") + "0 - " + "P " + sizePrice.get("large") + "0");
        menuCardView.getProductImage().setImage(menuCardModel.getProduct().getImage());
    }

    /**This sets up Food data of in the card*/
    private void setFoodDataOnCard(Food food) {
        menuCardView.getSizeLabel().setVisible(false); //no sizes for food

        menuCardView.getProductNameLabel().setText(menuCardModel.getProduct().getName());
        menuCardView.getProductDetailsLabel().setText(menuCardModel.getProduct().getDescription());
        menuCardView.getProductRatingLabel().setText("Rating: " + menuCardModel.getProduct().getReview());
        menuCardView.getProductPriceLabel().setText("P " + food.getPrice() + "0");
        menuCardView.getProductImage().setImage(menuCardModel.getProduct().getImage());
    }

    /*This method accepts method implementation from the main_menu_client page and passes the action to the button in the view*/
    public void setActionAddProductButton(EventHandler<ActionEvent> event){
        menuCardView.setActionAddProductButton(event);
    }

    public MenuCardModel getMenuCardModel() {
        return menuCardModel;
    }

    public void setMenuCardModel(MenuCardModel menuCardModel) {
        this.menuCardModel = menuCardModel;
    }

    public MenuCardView getMenuCardView() {
        return menuCardView;
    }

    public void setMenuCardView(MenuCardView menuCardView) {
        this.menuCardView = menuCardView;
    }
}