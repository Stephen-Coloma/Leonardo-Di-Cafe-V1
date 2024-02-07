package util;

import java.io.File;

public class XMLUtility {
    public static Object loadXMLData(File filePath){
        String filename = filePath.getName();
        switch (filename) {
            case "food_menu.xml":
                return loadFoodMenu(filePath);
            case "beverage_menu.xml":
                return loadBeverageMenu(filePath);
            case "customer_account_list.xml":
                return loadCustomerAccounts(filePath);
            case "order_list.xml":
                return loadOrders(filePath);
            default:
                // Handle unknown file paths or types
                return null;
        }
    }

    /*Todo: Implement the methods.*/
    private static Object loadFoodMenu(File filePath){
        System.out.println("1");
        return null;
    }

    private static Object loadBeverageMenu(File filePath){
        System.out.println("2");
        return null;
    }

    private static Object loadOrders(File filePath) {
        System.out.println("3");
        return null;
    }

    private static Object loadCustomerAccounts(File filePath) {
        System.out.println("4");
        return null;
    }
}
