package client.view.fxmlcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import shared.Beverage;
import shared.Food;
import shared.Product;

import java.util.HashMap;

public class MenuCardController {
    @FXML
    private Button addProductButton;

    @FXML
    private Label productDetailsLabel;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label productPriceLabel;

    @FXML
    private Label productRatingLabel;
    @FXML
    private Label sizeLabel;

    //the data to be passed
    private Product product;
    private Food food;
    private Beverage beverage;
    public void setData(Product product){
        if (product.getType() == 'f'){
            food = (Food) product;
            setFoodDataOnCard(food);
        }else if (product.getType() == 'b'){
            beverage = (Beverage) product;
            setBeverageDataOnCard(beverage);
        }
    }

    /**This sets up Beverage data of in the card*/
    private void setBeverageDataOnCard(Beverage beverage) {
        HashMap<String, Double> sizePrice = beverage.getSizePrice();

        productNameLabel.setText(beverage.getName());
        productDetailsLabel.setText(beverage.getDescription());
        productRatingLabel.setText("Rating: " + beverage.getReview());
        sizeLabel.setVisible(true);
        productPriceLabel.setText("P " + sizePrice.get("small") + " - " + "P " + sizePrice.get("large"));
        productImage.setImage(beverage.getImage());
    }

    /**This sets up Food data of in the card*/
    private void setFoodDataOnCard(Food food) {
        sizeLabel.setVisible(false); //no sizes for food

        productNameLabel.setText(food.getName());
        productDetailsLabel.setText(food.getDescription());
        productRatingLabel.setText("Rating: " + food.getReview());
        productPriceLabel.setText("P " + food.getPrice());
        productImage.setImage(food.getImage());
    }
}
