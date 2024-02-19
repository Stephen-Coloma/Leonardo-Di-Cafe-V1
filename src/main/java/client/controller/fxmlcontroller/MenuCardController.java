package client.controller.fxmlcontroller;

import client.model.fxmlmodel.MenuCardModel;
import client.view.fxmlview.MenuCardView;
import shared.Beverage;
import shared.Food;
import shared.Product;

public class MenuCardController {
    private MenuCardModel model;
    private MenuCardView view;

    public MenuCardController(MenuCardModel model, MenuCardView view){
        this.model = model;
        this.view = view;
    }

    public void setProductData(Product product) {
        if (product instanceof Food food) {
            view.setProductName(food.getName());
            view.setProductDescription(food.getDescription());
            view.setProductRating("Rating: " + food.getReview());
            view.setProductPrice("₱" + food.getPrice());
            view.setProductImage(food.getImage());
            view.setSizeLabel("");
            model.setProduct(food);
        } else if (product instanceof Beverage beverage) {
            view.setProductName(beverage.getName());
            view.setProductDescription(beverage.getDescription());
            view.setProductRating("Rating: " + beverage.getReview());
            view.setProductPrice("₱" + beverage.getSizePrice().get("small") + "0 - " + "₱" + beverage.getSizePrice().get("large") + "0");
            view.setProductImage(beverage.getImage());
            view.setSizeLabel("size: S,M.L");
            model.setProduct(beverage);
        }
    } // end of setProductData


    /*
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

     */

    /*

    private void setBeverageDataOnCard(Beverage beverage) {
        HashMap<String, Double> sizePrice = beverage.getSizePrice();

        menuCardView.getProductNameLabel().setText(menuCardModel.getProduct().getName());
        menuCardView.getProductDetailsLabel().setText(menuCardModel.getProduct().getDescription());
        menuCardView.getProductRatingLabel().setText("Rating: " + menuCardModel.getProduct().getReview());
        menuCardView.getSizeLabel().setVisible(true);
        menuCardView.getProductPriceLabel().setText("P " + sizePrice.get("small") + "0 - " + "P " + sizePrice.get("large") + "0");
        menuCardView.getProductImage().setImage(menuCardModel.getProduct().getImage());
    }

    private void setFoodDataOnCard(Food food) {
        menuCardView.getSizeLabel().setVisible(false); //no sizes for food

        menuCardView.getProductNameLabel().setText(menuCardModel.getProduct().getName());
        menuCardView.getProductDetailsLabel().setText(menuCardModel.getProduct().getDescription());
        menuCardView.getProductRatingLabel().setText("Rating: " + menuCardModel.getProduct().getReview());
        menuCardView.getProductPriceLabel().setText("P " + food.getPrice() + "0");
        menuCardView.getProductImage().setImage(menuCardModel.getProduct().getImage());
    }

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

     */
}