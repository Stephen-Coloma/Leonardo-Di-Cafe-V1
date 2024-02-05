package shared;

import java.util.HashMap;

/**This class represents a beverage object where there is a variation for the product.
 * Different prices and quantity for a product.
 * @author  Stephen Coloma*/
public class Beverage extends Product{
    private HashMap<String, Integer> sizeQuantity;
    private HashMap<String, Double> sizePrice;

    //constructor
    public Beverage(String productName, char productType, double productReview, int productReviewCount, int sQuantity, int mQuantity, int lQuantity, double sPrice, double mPrice, double lPrice) {
        super(productName, productType, productReview, productReviewCount);

        HashMap<String, Integer> sizeQuantity = new HashMap<>(3);
        sizeQuantity.put("small", sQuantity);
        sizeQuantity.put("medium", mQuantity);
        sizeQuantity.put("large", lQuantity);

        HashMap<String, Double> sizePrice = new HashMap<>(3);
        sizePrice.put("small", sPrice);
        sizePrice.put("medium", mPrice);
        sizePrice.put("large", lPrice);

        this.sizePrice = sizePrice;
        this.sizeQuantity = sizeQuantity;
    }

    //getters setter
    public HashMap<String, Integer> getSizeQuantity() {
        return sizeQuantity;
    }

    public void setSizeQuantity(HashMap<String, Integer> sizeQuantity) {
        this.sizeQuantity = sizeQuantity;
    }

    public HashMap<String, Double> getSizePrice() {
        return sizePrice;
    }

    public void setSizePrice(HashMap<String, Double> sizePrice) {
        this.sizePrice = sizePrice;
    }

    //getters and setters for different variations
    public int getVariationQuantity(String size){
        return sizeQuantity.get(size);
    }

    public double getVariationPrice(String size){
        return sizePrice.get(size);
    }

    /**This method is used to update quantity of the variation of the beverage.
     It accommodates multiple threads but will only allow one thread to make an update to a specific size
     @throws Exception when out of stocks*/
    public void updateQuantity(String size) throws Exception {
        //separated per size to accommodate multiple threads
        if (size.equals("small")){
            synchronized (this){
                int quantity = sizeQuantity.get(size);
                quantity--;
                sizeQuantity.put(size, quantity); //updates

                if (quantity < 0){
                    quantity++;
                    sizeQuantity.put(size, quantity);
                    throw new Exception("Out of Stocks");
                }
            }
        }else if (size.equals("medium")){
            synchronized (this){
                int quantity = sizeQuantity.get(size);
                quantity--;
                sizeQuantity.put(size, quantity); //updates

                if (quantity < 0){
                    quantity++;
                    sizeQuantity.put(size, quantity);
                    throw new Exception("Out of Stocks");
                }
            }
        }else if (size.equals("large")){
            synchronized (this){
                int quantity = sizeQuantity.get(size);
                quantity--;
                sizeQuantity.put(size, quantity); //updates

                if (quantity < 0){
                    quantity++;
                    sizeQuantity.put(size, quantity);
                    throw new Exception("Out of Stocks");
                }
            }
        }
    }

    public void updateQuantity(String size, int count) throws Exception{
        if (size.equals("small")){
            synchronized (this){
                int temp = sizeQuantity.get(size);
                int left = temp - count;
                sizeQuantity.put(size, left);

                if (left < 0){
                    sizeQuantity.put(size, temp);
                    throw new Exception("Out of Stocks");
                }
            }
        }else if (size.equals("medium")){
            synchronized (this){
                int temp = sizeQuantity.get(size);
                int left = temp - count;
                sizeQuantity.put(size, left);

                if (left < 0){
                    sizeQuantity.put(size, temp);
                    throw new Exception("Out of Stocks");
                }
            }
        }else if (size.equals("large")){
            synchronized (this){
                int temp = sizeQuantity.get(size);
                int left = temp - count;
                sizeQuantity.put(size, left);

                if (left < 0){
                    sizeQuantity.put(size, temp);
                    throw new Exception("Out of Stocks");
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Beverage{" +
                "sizeQuantity=" + sizeQuantity +
                ", sizePrice=" + sizePrice +
                '}';
    }
}
