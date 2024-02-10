package util;

import javafx.scene.image.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import shared.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

public class XMLUtility {
    //Builder and Transformers
    private static DocumentBuilderFactory dbf;
    private static DocumentBuilder db;
    private static Document document;
    private static TransformerFactory tf;
    private static Transformer transformer;

    public static Object loadXMLData(File filePath) {
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

    /**This method loads the food menu available in the system from an xml file.
     * @param filePath filepath where the food_menu.xml resides
     * @return Object object which will be casted to List<Customer>*/
    private static Object loadFoodMenu(File filePath){
        HashMap<String, Food> foodMenu = new HashMap<>();

        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            document = db.parse(filePath);

            Element root = document.getDocumentElement();
            NodeList foodList = root.getElementsByTagName("food");

            for (int i = 0; i < foodList.getLength(); i++) {
                Node food = foodList.item(i);

                String name = "";
                char type = ' ';
                double review = 0;
                int reviewCount = 0;
                Image image = null;
                String description = "";
                int quantity = 0;
                double price = 0;
                int amountSold = 0;

                NodeList foodDetails = food.getChildNodes();
                for (int j = 0; j <foodDetails.getLength(); j++) {
                    Node foodDetail = foodDetails.item(j);

                    switch (foodDetail.getNodeName()){
                        case "name":
                            name =  foodDetail.getTextContent();
                            break;
                        case "type":
                            type =  foodDetail.getTextContent().charAt(0);
                            break;
                        case "review":
                            review =  Double.parseDouble(foodDetail.getTextContent());
                            break;
                        case "reviewCount":
                            reviewCount =  Integer.parseInt(foodDetail.getTextContent());
                            break;
                        case "image":
                            String fileName = foodDetail.getTextContent();
                            String path = "src/main/resources/fxml/productimages/" + fileName;
                            try {
                                image = new Image(new FileInputStream(path));
                            }catch (Exception e){
                                System.out.println("image not loaded");
                                e.printStackTrace();
                            }
                            break;
                        case "description":
                            description =  foodDetail.getTextContent();
                            break;
                        case "amountSold":
                            amountSold = Integer.parseInt(foodDetail.getTextContent());
                            break;
                        case "quantity":
                            quantity = Integer.parseInt(foodDetail.getTextContent()) ;
                            break;
                        case "price":
                            price =  Double.parseDouble(foodDetail.getTextContent());
                            break;
                    }
                }

                Food foodToAdd = new Food(name, type, review, reviewCount, image, description, quantity, price);
                foodToAdd.setAmountSold(amountSold);
                foodMenu.put(foodToAdd.getName(), foodToAdd);
            }
            return foodMenu;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Loads the beverage menu available in the server from a xml file.
     * @param filePath the path where the xml file is located
     * @return the Object that represents the list of beverages available
     */
    private static Object loadBeverageMenu(File filePath){
        HashMap<String, Beverage> beverageMenu = new HashMap<>();

        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            document = db.parse(filePath);

            Element root = document.getDocumentElement();
            NodeList beverageList = root.getElementsByTagName("beverage");

            for (int i = 0; i < beverageList.getLength(); i++) {
                Node beverage = beverageList.item(i);

                String name = "";
                char type = ' ';
                double review = 0;
                int reviewCount = 0;
                Image image = null;
                String description = "";
                int amountSold = 0;
                int sQuantity = 0;
                int mQuantity = 0;
                int lQuantity = 0;
                double sPrice = 0;
                double mPrice = 0;
                double lPrice = 0;

                NodeList beverageDetails = beverage.getChildNodes();
                for (int j = 0; j < beverageDetails.getLength(); j++) {
                    Node beverageDetail = beverageDetails.item(j);

                    switch (beverageDetail.getNodeName()) {
                        case "name" -> name = beverageDetail.getTextContent();
                        case "type" -> type = beverageDetail.getTextContent().charAt(0);
                        case "review" -> review = Double.parseDouble(beverageDetail.getTextContent());
                        case "reviewCount" -> reviewCount = Integer.parseInt(beverageDetail.getTextContent());
                        case "image" -> {
                            String filename = beverageDetail.getTextContent();
                            String path = "src/main/resources/fxml/productimages/" + filename;
                            try {
                                image = new Image(new FileInputStream(path));
                            } catch (Exception e) {
                                System.out.println("image not loaded");
                                e.printStackTrace();
                            }
                        }
                        case "description" -> description = beverageDetail.getTextContent();
                        case "amountSold" -> amountSold = Integer.parseInt(beverageDetail.getTextContent());
                        case "quantities" -> {
                            NodeList quantityList = beverageDetail.getChildNodes();
                            for (int k = 0; k < quantityList.getLength(); k++) {
                                Node quantityNode = quantityList.item(k);

                                if (quantityNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element quantityElement = (Element) quantityNode;
                                    String size = quantityElement.getAttribute("size");
                                    int quantity = Integer.parseInt(quantityElement.getTextContent());
                                    switch (size) {
                                        case "small" -> sQuantity = quantity;
                                        case "medium" -> mQuantity = quantity;
                                        case "large" -> lQuantity = quantity;
                                    }
                                }
                            }
                        }
                        case "prices" -> {
                            NodeList pricesList = beverageDetail.getChildNodes();
                            for (int l = 0; l < pricesList.getLength(); l++) {
                                Node priceNode = pricesList.item(l);

                                if (priceNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element priceElement = (Element) priceNode;
                                    String size = priceElement.getAttribute("size");
                                    Double price = Double.parseDouble(priceElement.getTextContent());
                                    switch (size) {
                                        case "small" -> sPrice = price;
                                        case "medium" -> mPrice = price;
                                        case "large" -> lPrice = price;
                                    }
                                }
                            }
                        }
                    }
                }

                Beverage beverageToAdd = new Beverage(name, type, review, reviewCount, image, description, sQuantity, mQuantity, lQuantity, sPrice, mPrice, lPrice);
                beverageToAdd.setAmountSold(amountSold);
                beverageMenu.put(beverageToAdd.getName(), beverageToAdd);
            }
            return beverageMenu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**This method loads the orders from a xml file. These orders are order summary for the server admin to access.*/
    private static Object loadOrders(File filePath) {
        //return
        List<Order> orderList = new ArrayList<>();

        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            document = db.parse(filePath);

            // Get the root element <orders>
            Element root = document.getDocumentElement();
            // Get all <order> elements
            NodeList orderElements = root.getElementsByTagName("order");

            // Iterate through each <order> element
            for (int i = 0; i < orderElements.getLength(); i++) {
                Node orderNode = orderElements.item(i);
                if (orderNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element orderElement = (Element) orderNode;

                    // Parse customer details
                    Element customerElement = (Element) orderElement.getElementsByTagName("customer").item(0);
                    String name = customerElement.getElementsByTagName("name").item(0).getTextContent();
                    String username = customerElement.getElementsByTagName("username").item(0).getTextContent();
                    String address = customerElement.getElementsByTagName("address").item(0).getTextContent();
                    Customer customer = new Customer(name, username, address, null, null);

                    // Parse product details
                    List<Product> productList = new ArrayList<>();
                    NodeList productNodes = orderElement.getElementsByTagName("product");
                    for (int j = 0; j < productNodes.getLength(); j++) {
                        Node productNode = productNodes.item(j);
                        if (productNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element productElement = (Element) productNode;
                            String prodName = productElement.getElementsByTagName("name").item(0).getTextContent();
                            char prodType = productElement.getElementsByTagName("type").item(0).getTextContent().charAt(0);
                            double prodReview = Double.parseDouble(productElement.getElementsByTagName("review").item(0).getTextContent());
                            // Parse image
                            String imageName = productElement.getElementsByTagName("image").item(0).getTextContent();
                            String imagePath = "src/main/resources/fxml/productimages/" + imageName;
                            Image prodImage = new Image(new FileInputStream(imagePath));
                            String prodSize = productElement.getElementsByTagName("size").item(0).getTextContent();
                            int prodQuantity = Integer.parseInt(productElement.getElementsByTagName("quantity").item(0).getTextContent());

                            Product product = null;
                            if (prodType == 'f') {
                                product = new Food(prodName, prodType, prodReview, 0, prodImage, "", prodQuantity, 0);
                            }else if (prodType == 'b'){
                                    int sQuantity = 0;
                                    int mQuantity = 0;
                                    int lQuantity = 0;

                                    if (prodSize.equals("small")){
                                        sQuantity = prodQuantity;
                                    }else if (prodSize.equals("medium")){
                                        mQuantity = prodQuantity;
                                    }else if (prodSize.equals("large")){
                                        lQuantity = prodQuantity;
                                    }
                                product = new Beverage(prodName, prodType, prodReview, 0, prodImage, null, sQuantity, mQuantity, lQuantity, 0,0,0);
                            }
                                    
                            productList.add(product);
                        }
                    }

                    // Parse order details
                    int id = Integer.parseInt(orderElement.getElementsByTagName("orderID").item(0).getTextContent());
                    String timeStamp = orderElement.getElementsByTagName("timeStamp").item(0).getTextContent();
                    double totalPrice = Double.parseDouble(orderElement.getElementsByTagName("totalPrice").item(0).getTextContent());
                    boolean status = Boolean.parseBoolean(orderElement.getElementsByTagName("status").item(0).getTextContent());

                    // Create Order object and add to the list
                    Order order = new Order(customer, productList, id, timeStamp, totalPrice, status);
                    orderList.add(order);
                }
            }
            return orderList;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**This method loads the customer accounts from a xml file where it was saved.
     * @param filePath the filepath of the xml file
     * @return Object object which will be casted to List<Customer>
     */
    private static List<Customer> loadCustomerAccounts(File filePath) {
        List<Customer> customerAccountList = new ArrayList<>();
        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            document = db.parse(filePath);

            Element root = document.getDocumentElement();
            NodeList customerList = root.getElementsByTagName("customer");
            for (int i = 0; i < customerList.getLength(); i++) {
                Node customerNode = customerList.item(i);
                customerAccountList.add(processCustomerElement(customerNode));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerAccountList;
    }

    /**Helper method*/
    private static Customer processCustomerElement(Node customer) {
        String name = ((Element) customer).getAttribute("name");
        String username = "";
        String address = "";
        String email = "";
        String password = "";
        List<Order> orderHistory = null;

        NodeList customerDetails = customer.getChildNodes();
        for (int i = 0; i < customerDetails.getLength(); i++) {
            Node customerDetail = customerDetails.item(i);

            switch (customerDetail.getNodeName()) {
                case "username":
                    username = customerDetail.getTextContent();
                    break;
                case "address":
                    address = customerDetail.getTextContent();
                    break;
                case "email":
                    email = customerDetail.getTextContent();
                    break;
                case "password":
                    password = customerDetail.getTextContent();
                    break;
                case "orderHistory":
                    orderHistory = processOrderHistoryElement(customerDetail);
                    break;
            }
        }

        return new Customer(new Customer(name, username, address, email, password), orderHistory);
    }

    /**Helper method*/
    private static List<Order> processOrderHistoryElement(Node orderHistoryDetail) {
        List<Order> parsedOrderHistory = new ArrayList<>();
        NodeList orderList = orderHistoryDetail.getChildNodes();
        for (int i = 0; i < orderList.getLength(); i++) {
            Node orderNode = orderList.item(i);
            if (orderNode.getNodeName().equals("order")) {
                parsedOrderHistory.add(processOrderElement(orderNode));
            }
        }
        return parsedOrderHistory;
    }

    /**Helper method*/
    private static Order processOrderElement(Node order) {
        String timeStamp = "";
        double totalPrice = 0;
        boolean status = true;
        int id = 0;
        List<Product> productList = new ArrayList<>();

        NodeList orderDetails = order.getChildNodes();
        for (int i = 0; i < orderDetails.getLength(); i++) {
            Node orderDetailElement = orderDetails.item(i);

            switch (orderDetailElement.getNodeName()) {
                case "product":
                    productList.add(processProductElement(orderDetailElement));
                    break;
                case "timeStamp":
                    timeStamp = orderDetailElement.getTextContent();
                    break;
                case "totalPrice":
                    totalPrice = Double.parseDouble(orderDetailElement.getTextContent());
                    break;
                case "status":
                    status = Boolean.parseBoolean(orderDetailElement.getTextContent());
                    break;
                case "id":
                    id = Integer.parseInt(orderDetailElement.getTextContent());
                    break;
            }
        }

        return new Order(productList, timeStamp, totalPrice, status, id);
    }

    /**Helper method*/
    private static Product processProductElement(Node productElement)  {
        String prodName = "";
        char prodType = ' ';
        int prodReview = 0;
        String prodSize = "";
        int prodQuantity = 0;
        Image prodImage = null;

        NodeList productDetails = productElement.getChildNodes();
        for (int i = 0; i < productDetails.getLength(); i++) {
            Node productDetail = productDetails.item(i);

            switch (productDetail.getNodeName()) {
                case "name":
                    prodName = productDetail.getTextContent();
                    break;
                case "type":
                    prodType = productDetail.getTextContent().charAt(0);
                    break;
                case "review":
                    prodReview = Integer.parseInt(productDetail.getTextContent());
                    break;
                case "image":
                    String fileName = productDetail.getTextContent();
                    String path = "src/main/resources/fxml/productimages/" + fileName;
                    try {
                        prodImage = new Image(new FileInputStream(path));
                    }catch (Exception e){
                        System.out.println("image not loaded");
                        e.printStackTrace();
                    }
                    break;
                case "size":
                    prodSize = productDetail.getTextContent();
                    break;
                case "quantity":
                    prodQuantity = Integer.parseInt(productDetail.getTextContent());
                    break;
            }
        }

        if (prodType == 'f') {
            return new Food(prodName, prodType, prodReview, 0, prodImage, null, prodQuantity, 0);
        } else if (prodType == 'b') {
            int sQuantity = 0;
            int mQuantity = 0;
            int lQuantity = 0;

            if (prodSize.equals("small")){
                sQuantity = prodQuantity;
            }else if (prodSize.equals("medium")){
                mQuantity = prodQuantity;
            }else if (prodSize.equals("large")){
                lQuantity = prodQuantity;
            }

            return new Beverage(prodName, prodType, prodReview, 0, prodImage, null, sQuantity, mQuantity, lQuantity, 0,0,0);
        }
        return null;
    }
}
