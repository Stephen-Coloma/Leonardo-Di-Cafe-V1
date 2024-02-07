package server.model;

import server.Server;
import shared.*;
import util.XMLUtility;

import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**Server Model class holds the data that will eventually accessed by all the clients.
 * The idea is, when a client places an order,it will update the menu of this server model (e.g. by decrementing it)
 * and that the updated menu will be visible to other clients.
 *
 * There is a predefined menu products for food, beverage and orders which will be loaded from xml files when the server model is initialized.
 */
public class ServerModel {
    private HashMap<String, Food> foodMenu; //Hashmap for faster searching
    private HashMap<String, Beverage> beverageMenu;
    private List<Customer> customerAccountList;
    private List<Order> orderList; //List for listing only orders

    /**The constructor of the server model should load the predefined menu products*/
    public ServerModel() {
        foodMenu = (HashMap<String, Food>) XMLUtility.loadXMLData(new File("src/main/java/server/model/food_menu.xml"));
        beverageMenu = (HashMap<String, Beverage>) XMLUtility.loadXMLData(new File("src/main/java/server/model/beverage_menu.xml"));
        customerAccountList = (List<Customer>) XMLUtility.loadXMLData(new File("src/main/java/server/model/customer_account_list.xml"));
        orderList = (List<Order>) XMLUtility.loadXMLData(new File("src/main/java/server/model/order_list.xml"));
    }


    /**Process client orders and updates the food menu if necessary
     * Algorithm
     * 1. From the list of products from order, check if there are available for it.
     * 2. If there are non, throw an exception
     * 3. If there are available products, then decrement it from the product quantity
     * 4. Save the order to orders list
     * 5. Return true
     * 2. */
    public void processOrder(Order order) throws Exception{
        checkAvailability(order);
        updateMenu(order);
        orderList.add(order); //if it reaches here, means the order is successful, therefore add to orderList
    }

    private void updateMenu(Order order) throws Exception {
        for (Product product: order.getOrders()) {
            if (product instanceof Food){
                Food food = (Food) product; //cast it
                int orderQuantity = food.getQuantity();

                Food productListed = foodMenu.get(food.getProductName());

                //updates the menu
                productListed.updateQuantity(orderQuantity); // this throws an exception
            }else if (product instanceof Beverage){
                Beverage beverage = (Beverage) product;

                Beverage productListed = beverageMenu.get(beverage.getProductName());

                //for each variation of the beverage order
                //small = 10
                //medium = 2
                // large = 3
                for (String variation: beverage.getSizeQuantity().keySet()) {
                    productListed.updateQuantity(variation, beverage.getSizeQuantity().get(variation));
                }
            }
        }
    }

    private void checkAvailability(Order order) throws Exception{
        for (Product product: order.getOrders()) {
            if (product instanceof Food){ //check first what type of product
                Food food = (Food) product; //cast it

                if (food.getQuantity() > foodMenu.get(food.getProductName()).getQuantity()){
                    //checks if the order food quantity is greater than what is on the menu
                    throw new Exception("Out of stocks");
                }
            }else if (product instanceof Beverage){
                Beverage beverage = (Beverage) product;

                //check if all variation
                //small = 10
                //medium = 2
                // large = 3
                for (String variation: beverage.getSizeQuantity().keySet()) {
                    int variationQuantity = beverage.getVariationQuantity(variation); //small = 10;
                    int variationAvailableOnMenu = beverageMenu.get(beverage.getProductName()).getVariationQuantity(variation);
                    if (variationQuantity > variationAvailableOnMenu){
                        throw new Exception("Out of stocks");
                    }
                }
            }
        }
    }
}
