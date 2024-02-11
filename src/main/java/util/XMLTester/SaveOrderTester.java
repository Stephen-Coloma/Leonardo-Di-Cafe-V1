package util.XMLTester;
import shared.*;
import util.XMLUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveOrderTester {

    public static void main(String[] args) {
        // Create some test products (Food and Beverage)
        Food pizza = new Food("Pizza", 'f', 4.5, 100, null, "Delicious pizza", 10, 20.0);
        Beverage coffee = new Beverage("Coffee", 'b', 5.0, 150, null, "Rich coffee", 10, 10, 10, 2.5, 3.0, 3.5);

        // Create a test customer
        Customer customer = new Customer("John Doe", "johndoe123", "123 Main Street, Anytown, NY 12345, United States", "johndoe@example.com", "password123");

        // Create a test order containing the products
        Order order = new Order(customer, Arrays.asList(pizza, coffee), 1, "2023/02/10", 45.5, true);

        // Create a list of orders and add the test order to it
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        String projectPath = System.getProperty("user.dir");
        File filePath = new File(projectPath + "/src/main/java/util/XMLTester/savedorders_test.xml");

        filePath.getParentFile().mkdirs();

        XMLUtility.saveOrders(filePath, orders);

        System.out.println("Orders saved successfully to " + filePath.getAbsolutePath());
    }
}
