package client.model.fxmlmodel;


import shared.Food;
import shared.Product;

public class CartItemCardModel {
    private String productName;
    private int productQuantity;
    private String size;
    private double totalPrice;

    public CartItemCardModel(Product product, int orderQuantity, double orderTotalPrice, String orderSize){
        productName = product.getName();
        productQuantity = orderQuantity;
        totalPrice = orderTotalPrice;
        size = orderSize;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
