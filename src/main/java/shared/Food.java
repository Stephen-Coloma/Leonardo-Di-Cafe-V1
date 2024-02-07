package shared;

/**This class represents a food object where there is no variation for it.
 * @author  Stephen Coloma*/
public class Food extends Product {
    private int quantity;
    private double price;

    //constructors
    public Food(String productName, char productType, double productReview, int productReviewCount, int quantity, double price) {
        super(productName, productType, productReview, productReviewCount);
        this.quantity = quantity;
        this.price = price;
    }

    //getters setters

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    /**This method updates the quantity of the product for every successful order
     * @throws Exception when the quantity cannot accommodate the order*/
    public synchronized void updateQuantity(int count) throws Exception{
        int temp = quantity;

        quantity = quantity - count;

        if (quantity<0){
            quantity = temp;
            throw new Exception("Out of stocks");
        }
    }

    /**This method updates the quantity of the product by the quantity ordered by a single client
     * @throws Exception when the quantity cannot accommodate the order*/
    public synchronized void updateQuantity() throws Exception{
        quantity--;

        if (quantity < 0){
            quantity++; //reverting back the changes
            throw new Exception("Out of stocks");
        }
    }
}
