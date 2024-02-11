package util.XMLTester;
import shared.*;
import util.XMLUtility;

import java.io.File;
import java.util.*;

public class SaveOrderTester {

    public static void main(String[] args) {
        // Create some test products (Food and Beverage)
        Food pizza = new Food("Pizza", 'f', 4.5, 100, null, "Delicious pizza", 10, 20.0);
        Beverage coffee = new Beverage("Coffee", 'b', 5.0, 150, null, "Rich coffee", 10, 10, 10, 2.5, 3.0, 3.5);

        // Create a test customer
        Customer customer = new Customer("Sanchie Guzman", "sanchie123", "123 Main Street, Baracca, NY 12345, United States", "user123@example.com", "pass345");

        // Create a test order containing the products
        Order order = new Order(customer, Arrays.asList(pizza, coffee), 1, "2023/02/10", 45.5, true);

        // Create a map to hold orders
        Map<String, Order> orderMap = new HashMap<>();
        orderMap.put(order.getCustomer().getName(), order);

        // Save the orders
        XMLUtility.saveOrders((List<Order>) orderMap);

        System.out.println("Orders saved successfully.");
    }
}
