package shared;

import javafx.scene.image.Image;
import util.exception.OutOfStockException;

import java.util.HashMap;

/**This class represents a beverage object where there is a variation for the product.
 * Different prices and quantity for a product.
 * @author  Stephen Coloma*/
public class Beverage extends Product{
    private HashMap<String, Integer> sizeQuantity;
    private HashMap<String, Double> sizePrice;

    //constructor
    public Beverage(String name, char type, double review, int reviewCount, Image image, String description, int sQuantity, int mQuantity, int lQuantity, double sPrice, double mPrice, double lPrice) {
        super(name, type, review, reviewCount, image, description);

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
                    throw new OutOfStockException("Out of Stock");
                }

                //reaches here means no error updating the value
                int sold =  super.getAmountSold() + 1;
                super.setAmountSold(sold);
            }
        }else if (size.equals("medium")){
            synchronized (this){
                int quantity = sizeQuantity.get(size);
                quantity--;
                sizeQuantity.put(size, quantity); //updates

                if (quantity < 0){
                    quantity++;
                    sizeQuantity.put(size, quantity);
                    throw new OutOfStockException("Out of Stock");
                }

                //reaches here means no error updating the value
                int sold =  super.getAmountSold() + 1;
                super.setAmountSold(sold);
            }
        }else if (size.equals("large")){
            synchronized (this){
                int quantity = sizeQuantity.get(size);
                quantity--;
                sizeQuantity.put(size, quantity); //updates

                if (quantity < 0){
                    quantity++;
                    sizeQuantity.put(size, quantity);
                    throw new OutOfStockException("Out of Stock");
                }

                //reaches here means no error updating the value
                int sold =  super.getAmountSold() + 1;
                super.setAmountSold(sold);
            }
        }
    }

    /**This method is used to update quantity of the variation of the beverage for a given amount of order
     It accommodates multiple threads but will only allow one thread to make an update to a specific size
     @throws Exception when out of stocks*/
    public void updateQuantity(String size, int count) throws Exception{
        if (size.equals("small")){
            synchronized (this){
                int temp = sizeQuantity.get(size);
                int left = temp - count;
                sizeQuantity.put(size, left);

                if (left < 0){
                    sizeQuantity.put(size, temp);
                    throw new OutOfStockException("Out of Stock");
                }

                //reaches here means no error updating the value
                int sold = super.getAmountSold() + count;
                super.setAmountSold(sold);
            }
        }else if (size.equals("medium")){
            synchronized (this){
                int temp = sizeQuantity.get(size);
                int left = temp - count;
                sizeQuantity.put(size, left);

                if (left < 0){
                    sizeQuantity.put(size, temp);
                    throw new OutOfStockException("Out of Stock");
                }

                //reaches here means no error updating the value
                int sold = super.getAmountSold() + count;
                super.setAmountSold(sold);
            }
        }else if (size.equals("large")){
            synchronized (this){
                int temp = sizeQuantity.get(size);
                int left = temp - count;
                sizeQuantity.put(size, left);

                if (left < 0){
                    sizeQuantity.put(size, temp);
                    throw new OutOfStockException("Out of Stock");
                }

                //reaches here means no error updating the value
                int sold = super.getAmountSold() + count;
                super.setAmountSold(sold);
            }
        }
    }

    public int getQuantity() {
        return sizeQuantity.values().stream().mapToInt(Integer::intValue).sum();
    }

    @Override
    public String toString() {
        String concat = super.toString();
        return "Beverage{" + concat +
                "sizeQuantity=" + sizeQuantity +
                ", sizePrice=" + sizePrice +
                '}';
    }
}
