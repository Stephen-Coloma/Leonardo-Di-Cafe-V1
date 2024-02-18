package client.model.fxmlmodel;

import shared.Customer;
import shared.Order;
import shared.Product;

import java.util.List;

public class CheckoutPageModel{
    private Customer customer;
    private List<Product> cart;
    private Order orderFromClient;
    private double subTotal;

    public CheckoutPageModel(Customer customer, List<Product> cart, double subTotal, Order orderFromClient){
        this.customer = customer;
        this.cart = cart;
        this.subTotal = subTotal;
        this.orderFromClient = orderFromClient;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getCart() {
        return cart;
    }

    public void setCart(List<Product> cart) {
        this.cart = cart;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public Order getOrderFromClient() {
        return orderFromClient;
    }

    public void setOrderFromClient(Order orderFromClient) {
        this.orderFromClient = orderFromClient;
    }
}