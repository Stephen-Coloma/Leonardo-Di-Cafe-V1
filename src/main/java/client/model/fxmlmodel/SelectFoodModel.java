package client.model.fxmlmodel;

import javafx.scene.image.Image;
import shared.Food;
import shared.Product;

import java.text.DecimalFormat;

public class SelectFoodModel {
    private String productName;
    private String productDetails;
    private int productAvailability;
    private double productPrice;
    private Image productImage;
    private Food food;
    private double total;
    private int orderedQuantity;

    public SelectFoodModel(Product product){
        food = (Food) product;

        this.productName = food.getName();
        this.productDetails = food.getDescription();
        this.productAvailability = food.getQuantity(); //need to subtract 1 for display
        this.productPrice = food.getPrice();
        this.productImage = food.getImage();

        //total
        total = 0;
        orderedQuantity = 0;
    }

    public String incrementQuantity(){
        if (productAvailability > 0){

            ++orderedQuantity;
            --productAvailability; //decrement the available
            total+=productPrice;

            return "";
        }else {
            return null;
        }

    }

    public String decrementQuantity(){
        if (orderedQuantity > 0){
            --orderedQuantity;
            ++productAvailability;  //increment the available stocks
            total-=productPrice;
            return "";
        }else {
            return null;
        }
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public int getProductAvailability() {
        return productAvailability;
    }

    public void setProductAvailability(int productAvailability) {
        this.productAvailability = productAvailability;
    }

    public String getProductPrice() {
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedPrice = df.format(productPrice);
        return formattedPrice;
    }

    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getTotal() {
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedTotal = df.format(total);
        return formattedTotal;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Image getProductImage() {
        return productImage;
    }

    public void setProductImage(Image productImage) {
        this.productImage = productImage;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
