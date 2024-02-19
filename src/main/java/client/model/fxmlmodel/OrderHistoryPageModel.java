package client.model.fxmlmodel;

import shared.Product;

import java.util.List;

public class OrderHistoryPageModel {
    private List<Product> productList;
    public OrderHistoryPageModel(List<Product> productList){
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}