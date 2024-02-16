package client.model.fxmlmodel;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import shared.Beverage;
import shared.Food;
import shared.Product;

import java.util.HashMap;

public class MenuCardModel {
    private Product product;

    public MenuCardModel(Food food){
        this.product = food;
    }
    public MenuCardModel(Beverage beverage){
        this.product = beverage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}