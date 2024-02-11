package util.XMLUtilityTest;

import shared.Food;
import util.XMLUtility;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SaveFoodMenuTester {

    public static void main(String[] args) {
        // Create some test food items
        Food pizza = new Food("Pizza", 'f', 4.5, 100, null, "Delicious pizza", 10, 20.0);
        Food burger = new Food("Burger", 'f', 4.2, 80, null, "Classic beef burger", 15, 15.0);

        // Create a Map to represent the food menu
        Map<String, Food> foodMenu = new HashMap<>();
        foodMenu.put(pizza.getName(), pizza);
        foodMenu.put(burger.getName(), burger);

        // Specify the file path where the food menu will be saved
        String projectPath = System.getProperty("user.dir");
        File filePath = new File(projectPath + File.separator + "src" + File.separator + "main" +
                File.separator + "java" + File.separator + "server" + File.separator + "model" +
                File.separator + "food_menu.xml");

        // Call the saveFoodMenu method to save the food menu to the specified XML file
        XMLUtility.saveFoodMenu(foodMenu, filePath);
    }
}