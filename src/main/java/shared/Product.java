package shared;

import javafx.scene.image.Image;

/**This abstract class represent a product. A product is classified into f - food, and b - beverage.
 * Food does not have any variations, meaning same price and quantity.
 * Whereas for beverage, it may be small, medium, large and has different price and quantity for each
 * @author  Stephen Coloma*/
public abstract class Product {
    private String name;
    private char type;
    private double review; //average of 1-5
    private int reviewCount;
    private Image image;
    private String description;
    private int amountSold;

    /**A constructor initializes the details of the classes whose direct descendant of this class.*/
    public Product(String name, char type, double review, int reviewCount, Image image, String description) {
        this.name = name;
        this.type = type;
        this.review = review;
        this.image = image;
        this.reviewCount = reviewCount;
        this.description = description;
    }

    //getters setters
    public String getName(){
        return name;
    };
    public char getType(){
        return type;
    };

    public double getReview(){
        return review;
    };

    public int getReviewCount() {
        return reviewCount;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setType(char type) {
        this.type = type;
    }

    public void setReview(double review) {
        this.review = review;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmountSold() {
        return amountSold;
    }

    public void setAmountSold(int amountSold) {
        this.amountSold = amountSold;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    /**This method updates the review and reviewCount in a synchronized manner*/
    public synchronized void updateReview(double review){
        reviewCount++;
        double updatedReview = (this.review + review)/this.reviewCount;

        this.review = updatedReview;
    }
}