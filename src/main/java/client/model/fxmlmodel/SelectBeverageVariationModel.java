package client.model.fxmlmodel;

import javafx.scene.image.Image;
import shared.Beverage;
import shared.Food;
import shared.Product;

import java.text.DecimalFormat;

public class SelectBeverageVariationModel {
    private String productName;
    private String productDetails;
    private Image productImage;
    private Beverage beverage;
    private int smallAvailability;
    private int mediumAvailability;
    private int largeAvailability;

    private double smallTotal;
    private double mediumTotal;
    private double largeTotal;

    private double smallPrice;
    private double mediumPrice;
    private double largePrice;

    private int smallOrderedQuantity;
    private int mediumOrderedQuantity;
    private int largeOrderedQuantity;

    public SelectBeverageVariationModel(Product product){
        beverage = (Beverage) product;

        this.productName = beverage.getName();
        this.productDetails = beverage.getDescription();

        this.smallAvailability = beverage.getSizeQuantity().get("small"); //need to subtract 1 for display
        this.mediumAvailability = beverage.getSizeQuantity().get("medium"); //need to subtract 1 for display
        this.largeAvailability = beverage.getSizeQuantity().get("large"); //need to subtract 1 for display

        this.smallPrice = beverage.getSizePrice().get("small");
        this.mediumPrice = beverage.getSizePrice().get("medium");
        this.largePrice = beverage.getSizePrice().get("large");

        this.productImage = beverage.getImage();

        //totals
        smallTotal = 0;
        mediumTotal = 0;
        largeTotal = 0;

        //order quantity
        smallOrderedQuantity = 0;
        mediumOrderedQuantity = 0;
        largeOrderedQuantity = 0;
    }

    public String incrementSmallQuantity(){
        if (smallAvailability > 0){

            ++smallOrderedQuantity;
            --smallAvailability; //decrement the available
            smallTotal+=smallPrice;
            return "";
        }else {
            return null;
        }

    }

    public String decrementSmallQuantity(){
        if (smallOrderedQuantity > 0){
            --smallOrderedQuantity;
            ++smallAvailability;  //increment the available stocks
            smallTotal-=smallPrice;
            return "";
        }else {
            return null;
        }
    }

    public String incrementMediumQuantity(){
        if (mediumAvailability > 0){

            ++mediumOrderedQuantity;
            --mediumAvailability; //decrement the available
            mediumTotal+=mediumPrice;
            return "";
        }else {
            return null;
        }

    }

    public String decrementMediumQuantity(){
        if (mediumOrderedQuantity > 0){
            --mediumOrderedQuantity;
            ++mediumAvailability;  //increment the available stocks
            mediumTotal-=mediumPrice;
            return "";
        }else {
            return null;
        }
    }

    public String incrementLargeQuantity(){
        if (largeAvailability > 0){

            ++largeOrderedQuantity;
            --largeAvailability; //decrement the available
            largeTotal+=largePrice;
            return "";
        }else {
            return null;
        }

    }

    public String decrementLargeQuantity(){
        if (largeOrderedQuantity > 0){
            --largeOrderedQuantity;
            ++largeAvailability;  //increment the available stocks
            largeTotal-=largePrice;
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

    public Image getProductImage() {
        return productImage;
    }

    public void setProductImage(Image productImage) {
        this.productImage = productImage;
    }

    public Beverage getBeverage() {
        return beverage;
    }

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    public int getSmallAvailability() {
        return smallAvailability;
    }

    public void setSmallAvailability(int smallAvailability) {
        this.smallAvailability = smallAvailability;
    }

    public int getMediumAvailability() {
        return mediumAvailability;
    }

    public void setMediumAvailability(int mediumAvailability) {
        this.mediumAvailability = mediumAvailability;
    }

    public int getLargeAvailability() {
        return largeAvailability;
    }

    public void setLargeAvailability(int largeAvailability) {
        this.largeAvailability = largeAvailability;
    }

    public String getSmallTotal() {
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedTotal = df.format(smallTotal);
        return formattedTotal;
    }

    public void setSmallTotal(double smallTotal) {
        this.smallTotal = smallTotal;
    }

    public String getMediumTotal() {
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedTotal = df.format(mediumTotal);
        return formattedTotal;
    }

    public void setMediumTotal(double mediumTotal) {
        this.mediumTotal = mediumTotal;
    }

    public String  getLargeTotal() {
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedTotal = df.format(largeTotal);
        return formattedTotal;
    }

    public void setLargeTotal(double largeTotal) {
        this.largeTotal = largeTotal;
    }

    public String getSmallPrice() {
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedPrice = df.format(smallPrice);
        return formattedPrice;
    }

    public void setSmallPrice(double smallPrice) {
        this.smallPrice = smallPrice;
    }

    public String getMediumPrice() {
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedPrice = df.format(mediumPrice);
        return formattedPrice;
    }

    public void setMediumPrice(double mediumPrice) {
        this.mediumPrice = mediumPrice;
    }

    public String getLargePrice() {
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedPrice = df.format(largePrice);
        return formattedPrice;
    }

    public void setLargePrice(double largePrice) {
        this.largePrice = largePrice;
    }

    public int getSmallOrderedQuantity() {
        return smallOrderedQuantity;
    }

    public void setSmallOrderedQuantity(int smallOrderedQuantity) {
        this.smallOrderedQuantity = smallOrderedQuantity;
    }

    public int getMediumOrderedQuantity() {
        return mediumOrderedQuantity;
    }

    public void setMediumOrderedQuantity(int mediumOrderedQuantity) {
        this.mediumOrderedQuantity = mediumOrderedQuantity;
    }

    public int getLargeOrderedQuantity() {
        return largeOrderedQuantity;
    }

    public void setLargeOrderedQuantity(int largeOrderedQuantity) {
        this.largeOrderedQuantity = largeOrderedQuantity;
    }
}
