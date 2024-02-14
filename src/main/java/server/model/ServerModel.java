package server.model;

import javafx.collections.ObservableList;
import shared.*;
import util.XMLUtility;
import util.exception.AccountExistsException;
import util.exception.InvalidCredentialsException;
import util.exception.OutOfStockException;
import java.io.File;
import java.util.HashMap;
import java.util.List;

/**Server Model class holds the data that will eventually be accessed by all the clients.
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

        /*Todo
           1. When the server is closed by the admin, it must write all these data in the necessary xmk files to be used for another run*/
    }

    public void updateMenuFromInventory(ObservableList<Object> list) {
        HashMap<String, Food> newFoodMenu = new HashMap<>();
        HashMap<String, Beverage> newBeverageMenu = new HashMap<>();

        for (Object entry : list) {
            if (entry instanceof Food food) {
                newFoodMenu.put(food.getName(), food);
            } else if (entry instanceof Beverage beverage) {
                newBeverageMenu.put(beverage.getName(), beverage);
            }
        }

        System.out.println(foodMenu);
        System.out.println(newFoodMenu);
        System.out.println(beverageMenu);
        System.out.println(newBeverageMenu);
    }


    /**Process client orders and updates the food menu if necessary
     * Algorithm
     * 1. From the list of products from order, check if there are available for it.
     * 2. If there are non, throw an exception
     * 3. If there are available products, then decrement it from the product quantity
     * 4. Save the order to orders list
     * 5. Return true
     * 2. */
    public boolean processOrder(Order order) throws Exception{
        checkAvailability(order); //order that is not successful
        updateMenu(order);

        Order successfulOrder = new Order(order); //if it reaches here, means the order is successful

        // therefore add to orderList for the admin
        orderList.add(successfulOrder);

        //then add the order in the orderList of the customer in the customerList
        for (Customer customer: customerAccountList) {
            if (customer.getUsername().equals(order.getCustomer().getUsername())){
                customer.getOrderHistory().add(order); //add the successful order to the customerOrderHistory
            }
        }

        return true; //temporary. If it reaches here, order is successful
    }

    /**This method update the product in the menu there are available products. Synchronization is handled here already.
     * @throws Exception if the product in the menu is out of stock */
    private void updateMenu(Order order) throws Exception {
        for (Product product: order.getOrders()) {
            if (product instanceof Food){
                Food food = (Food) product; //cast it
                int orderQuantity = food.getQuantity();

                Food productListed = foodMenu.get(food.getName());

                //updates the menu
                productListed.updateQuantity(orderQuantity); // this throws an exception
            }else if (product instanceof Beverage){
                Beverage beverage = (Beverage) product;

                Beverage productListed = beverageMenu.get(beverage.getName());

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

    /**This method checks the availability of products in the menu based on the customer's order.
     * @param order, client of the order
     * @throws Exception if the product menu is out of stock. */
    private void checkAvailability(Order order) throws Exception{
        for (Product product: order.getOrders()) {
            if (product instanceof Food){ //check first what type of product
                Food food = (Food) product; //cast it

                if (food.getQuantity() > foodMenu.get(food.getName()).getQuantity()){
                    //checks if the order food quantity is greater than what is on the menu
                    throw new OutOfStockException("Out of stock");
                }
            }else if (product instanceof Beverage){
                Beverage beverage = (Beverage) product;

                //check if all variation
                //small = 10
                //medium = 2
                // large = 3
                for (String variation: beverage.getSizeQuantity().keySet()) {
                    int variationQuantity = beverage.getVariationQuantity(variation); //small = 10;
                    int variationAvailableOnMenu = beverageMenu.get(beverage.getName()).getVariationQuantity(variation);
                    if (variationQuantity > variationAvailableOnMenu){
                        throw new OutOfStockException("Out of stock");
                    }
                }
            }
        }
    }

    /**This method handles all the signup processes from the client.
     * @param customerSignup new account that tries to create an account.
     *
     * Algorithm:
     * 1. Check the list of customers if the customer that that tries to sign up already exists.
     * 2. If exists, throws an exception  "Account exists"
     * 3. If not, create a new Customer out of a customer and add it to the list*/
    public synchronized boolean processSignUp(Customer customerSignup) throws Exception{
        String signUpUsername = customerSignup.getUsername();

        for (Customer customerAccount: customerAccountList) {
            if (customerAccount.getUsername().equals(signUpUsername)){
                throw new AccountExistsException("Account exists");
            }
        }

        Customer customer =  new Customer(customerSignup); //this creates a customer that has orderHistory in it
        customerAccountList.add(customer);
        return true;
    }

    /**This method handles all the login processes from the client.
     * @param username username
     * @param password password
     * @return the objects to be loaded in client side
     * @throws Exception when login credentials is invalid
     *
     * Algorithm:
     * 1. Check all customer in customerAccountList and see if it matches username and password.
     * 2. If not, thrown an exception called "invalid credentials"
     * 3. if account exist, get the accounts from the account list, get the foodMenu and beverageMenu
     * return foodMenu, beverageMenu and customer
     */
    public Object[] processLogin(String username, String password) throws Exception{
        for (Customer customerAccount: customerAccountList) {
            //account
            if (customerAccount.getUsername().equals(username) && customerAccount.getPassword().equals(password)){
                HashMap<String, Food> clientFoodMenuToLoad = new HashMap<>(foodMenu);
                HashMap<String, Beverage> clientBeverageMenuToLoad = new HashMap<>(beverageMenu);
                Object[] sendToClient = new Object[]{customerAccount, clientFoodMenuToLoad, clientBeverageMenuToLoad};

                //return to controller
                return sendToClient;
            }
        }
        throw new InvalidCredentialsException("Invalid credentials");
    }

    public HashMap<String, Food> getFoodMenu() {
        return foodMenu;
    }

    public HashMap<String, Beverage> getBeverageMenu() {
        return beverageMenu;
    }

    public List<Customer> getCustomerAccountList() {
        return customerAccountList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }
}