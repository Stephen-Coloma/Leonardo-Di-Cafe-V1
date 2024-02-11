package util;

import javafx.scene.image.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import shared.*;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static java.util.stream.Collectors.toMap;

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
                            image = getImage(fileName);
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

            NodeList beverageList = document.getElementsByTagName("beverage");

            IntStream.range(0, beverageList.getLength())
                    .mapToObj(beverageList::item)
                    .forEach(beverageNode -> {
                        if (beverageNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element beverage = (Element) beverageNode;

                            String name = getElementValue(beverage, "name");
                            char type = getElementValue(beverage, "type").charAt(0);
                            double review = Double.parseDouble(getElementValue(beverage, "review"));
                            int reviewCount = Integer.parseInt(getElementValue(beverage, "reviewCount"));
                            Image image = getImage(getElementValue(beverage, "image"));
                            String description = getElementValue(beverage, "description");
                            int amountSold = Integer.parseInt(getElementValue(beverage, "amountSold"));

                            Map<String, Integer> quantityList = IntStream.range(0, beverage.getElementsByTagName("quantity").getLength())
                                    .mapToObj(i -> (Element) beverage.getElementsByTagName("quantity").item(i))
                                    .collect(toMap(
                                            e -> e.getAttribute("size"),
                                            e -> Integer.parseInt(e.getTextContent())
                                    ));

                            Map<String, Double> priceList = IntStream.range(0, beverage.getElementsByTagName("price").getLength())
                                    .mapToObj(i -> (Element) beverage.getElementsByTagName("price").item(i))
                                    .collect(toMap(
                                            e -> e.getAttribute("size"),
                                            e -> Double.parseDouble(e.getTextContent())
                                    ));

                            Beverage beverageToAdd = new Beverage(
                                    name, type, review, reviewCount, image, description,
                                    quantityList.getOrDefault("small", 0),
                                    quantityList.getOrDefault("medium", 0),
                                    quantityList.getOrDefault("large", 0),
                                    priceList.getOrDefault("small", 0.0),
                                    priceList.getOrDefault("medium", 0.0),
                                    priceList.getOrDefault("large", 0.0)
                            );
                            beverageToAdd.setAmountSold(amountSold);
                            beverageMenu.put(beverageToAdd.getName(), beverageToAdd);

                        }
                    });
            return beverageMenu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    } // end of loadBeverageMenu

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
                            Image prodImage = getImage(imageName);
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
                    prodImage = getImage(fileName);
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

    // HELPER METHOD
    /**
     * Retrieves the text content of the first occurrence of the specified tag name within the parent element.
     * @param parentElement the parent element to retrieve the text content
     * @param tagName the name of the tag whose content is to be retrieved
     * @return the content of the first occurrence of the specified tag name
     */
    private static String getElementValue(Element parentElement, String tagName) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    } // end of getElementValue
    public static void saveCustomerAccounts(List<Customer> customerList) {
        String filePath =  "src/main/java/server/model/temp_customer_account_list.xml";
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Create the root element <accounts>
            Element rootElement = doc.createElement("accounts");
            doc.appendChild(rootElement);

            // Iterate over customer accounts
            for (Customer customer : customerList) {
                // Create customer element <customer>
                Element customerElement = doc.createElement("customer");
                customerElement.setAttribute("name", customer.getName());
                rootElement.appendChild(customerElement);

                // Create customer details elements
                Element nameElement = doc.createElement("name");
                nameElement.appendChild(doc.createTextNode(customer.getName()));
                customerElement.appendChild(nameElement);

                Element usernameElement = doc.createElement("username");
                usernameElement.appendChild(doc.createTextNode(customer.getUsername()));
                customerElement.appendChild(usernameElement);

                Element addressElement = doc.createElement("address");
                addressElement.appendChild(doc.createTextNode(customer.getAddress()));
                customerElement.appendChild(addressElement);

                Element emailElement = doc.createElement("email");
                emailElement.appendChild(doc.createTextNode(customer.getEmail()));
                customerElement.appendChild(emailElement);

                Element passwordElement = doc.createElement("password");
                passwordElement.appendChild(doc.createTextNode(customer.getPassword()));
                customerElement.appendChild(passwordElement);

                // Create order history element <orderHistory>
                Element orderHistoryElement = doc.createElement("orderHistory");
                orderHistoryElement.setAttribute("list", "order history");
                customerElement.appendChild(orderHistoryElement);

                // Iterate over orders in order history
                for (Order order : customer.getOrderHistory()) {
                    // Create order element <order>
                    Element orderElement = doc.createElement("order");
                    orderHistoryElement.appendChild(orderElement);

                    // Iterate over products in the order
                    for (Product product : order.getOrders()) {
                        // Create product element <product>
                        Element productElement = doc.createElement("product");
                        orderElement.appendChild(productElement);

                        // Add product details
                        Element productNameElement = doc.createElement("name");
                        productNameElement.appendChild(doc.createTextNode(product.getName()));
                        productElement.appendChild(productNameElement);

                        Element productTypeElement = doc.createElement("type");
                        productTypeElement.appendChild(doc.createTextNode(String.valueOf(product.getType())));
                        productElement.appendChild(productTypeElement);

                        Element productReviewElement = doc.createElement("review");
                        productReviewElement.appendChild(doc.createTextNode(String.valueOf(product.getReview())));
                        productElement.appendChild(productReviewElement);


                        Element imageElement = doc.createElement("image");
                        String imageURL= product.getImage().getUrl();
                        File imageFile = new File(imageURL);
                        imageElement.appendChild(doc.createTextNode(imageFile.getName()));
                        productElement.appendChild(imageElement);

                        if (product instanceof Food) {
                            // For Food products
                            // Add quantity element
                            Element productQuantityElement = doc.createElement("quantity");
                            productQuantityElement.appendChild(doc.createTextNode(String.valueOf(((Food) product).getQuantity())));
                            productElement.appendChild(productQuantityElement);
                        } else if (product instanceof Beverage) {
                            // For Beverage products
                            Beverage beverage = (Beverage) product;

                            // Iterate over sizeQuantity map entries
                            for (Map.Entry<String, Integer> entry : beverage.getSizeQuantity().entrySet()) {
                                String size = entry.getKey();
                                Integer quantity = entry.getValue();

                                // Check if quantity is greater than 0
                                if (quantity > 0) {
                                    // Create size element
                                    Element sizeElement = doc.createElement("size");
                                    sizeElement.appendChild(doc.createTextNode(size));
                                    productElement.appendChild(sizeElement);

                                    // Create quantity element
                                    Element quantityElement = doc.createElement("quantity");
                                    quantityElement.appendChild(doc.createTextNode(String.valueOf(quantity)));
                                    productElement.appendChild(quantityElement);

                                }
                            }
                        }

                    }


                    // Add order details
                    Element timeStampElement = doc.createElement("timeStamp");
                    timeStampElement.appendChild(doc.createTextNode(order.getTimeStamp()));
                    orderElement.appendChild(timeStampElement);

                    Element totalPriceElement = doc.createElement("totalPrice");
                    totalPriceElement.appendChild(doc.createTextNode(String.valueOf(order.getTotalPrice())));
                    orderElement.appendChild(totalPriceElement);

                    Element statusElement = doc.createElement("status");
                    statusElement.appendChild(doc.createTextNode(String.valueOf(order.isStatus())));
                    orderElement.appendChild(statusElement);

                    Element idElement = doc.createElement("id");
                    idElement.appendChild(doc.createTextNode(String.valueOf(order.getID())));
                    orderElement.appendChild(idElement);
                }//end of for each Order in the list


            }//end of for each Customer in the List

            // Write the content into XML file
            tf = TransformerFactory.newInstance();
            transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("XML file saved successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // HELPER METHOD
    /**
     * Creates a new JavaFX Image object from the given filename.
     * @param filename the path to the image file
     * @return the Image object created from the specified file
     */
    private static Image getImage(String filename) {
        try {
            return new Image("file:src/main/resources/productimages/" + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    } // end of getImage
}
