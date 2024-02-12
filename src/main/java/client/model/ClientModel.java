package client.model;

import shared.*;
import java.util.HashMap;
import java.util.List;

public class ClientModel {
    private HashMap<String, Food> foodMenu; // return from server
    private HashMap<String, Beverage> beverageMenu; //return from server
    private Customer customer; //return from server
    private List<Product> cart;
    private Order order;

    /**This initially sets all fields to null.*/
    public ClientModel() {
        foodMenu = null;
        beverageMenu = null;
        customer = null;
        cart = null;
        order = null;
    }

//    Uncomment if needed!
//    /**This constructor will be used solely for logging in.*/
//    public ClientModel(HashMap<String, Food> foodMenu, HashMap<String, Beverage> beverageMenu, Customer customer) {
//        this.foodMenu = foodMenu;
//        this.beverageMenu = beverageMenu;
//        this.customer = customer;
//
//        this.cart = new ArrayList<>();
//    }

    /**This method updates the foodMenu and beverageMenu from server*/
    public void updateMenu(HashMap<String, Food> foodMenu, HashMap<String, Beverage> beverageMenu){
        /*Update the menu*/
    }

    /**This method creates an order if the user wants to checkout an order*/
    public Order checkout(){
        /*Logic for getting time stamp: The format is 01/05/2024*/

//        Order order = new Order(customer, cart, timestamp)
        //remove products in cart;
        return null; //remove return order
    }

    /**This method clears cart.*/
    public void clearCart(){
        //implement
    }

















    //getter and setter
    public HashMap<String, Food> getFoodMenu() {
        return foodMenu;
    }

    public void setFoodMenu(HashMap<String, Food> foodMenu) {
        this.foodMenu = foodMenu;
    }

    public HashMap<String, Beverage> getBeverageMenu() {
        return beverageMenu;
    }

    public void setBeverageMenu(HashMap<String, Beverage> beverageMenu) {
        this.beverageMenu = beverageMenu;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
