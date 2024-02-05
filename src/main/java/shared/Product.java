package shared;

/**This abstract class represent a product. A product is classified into f - food, and b - beverage.
 * Food does not have any variations, meaning same price and quantity.
 * Whereas for beverage, it may be small, medium, large and has different price and quantity for each
 * @author  Stephen Coloma*/
public abstract class Product {
    private String productName;
    private char productType;
    private double productReview; //average of 1-5
    private int productReviewCount;

    //constructor
    public Product(String productName, char productType, double productReview, int productReviewCount) {
        this.productName = productName;
        this.productType = productType;
        this.productReview = productReview;
        this.productReviewCount = productReviewCount;
    }

    //getters setters
    public String getProductName(){
        return productName;
    };

    public char getProductType(){
        return productType;
    };

    public double getProductReview(){
        return productReview;
    };
    public int getProductReviewCount() {
        return productReviewCount;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductType(char productType) {
        this.productType = productType;
    }

    public void setProductReview(double productReview) {
        this.productReview = productReview;
    }

    public void setProductReviewCount(int productReviewCount) {
        this.productReviewCount = productReviewCount;
    }

    /**This method updates the productReview and productReviewCount in a synchronized manner*/
    public synchronized void updateProductReview(double productReview){
        productReviewCount++;
        double updatedReview = (this.productReview + productReview)/this.productReviewCount;

        this.productReview = updatedReview;
    }
}