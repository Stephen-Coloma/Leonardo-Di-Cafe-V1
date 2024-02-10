package client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.InputStream;

public class OrderHistory {

    @FXML
    private ToggleButton star1;
    @FXML
    private ToggleButton star2;
    @FXML
    private ToggleButton star3;
    @FXML
    private ToggleButton star4;
    @FXML
    private ToggleButton star5;

    // ... other star fields ...

    private Image starFilledImage;
    private Image starEmptyImage;

    public void initialize() {
        try {
            starFilledImage = new Image(getClass().getResourceAsStream("/resources/images/client/orderHistory/star_empty.png"));
            starEmptyImage = new Image(getClass().getResourceAsStream("/resources/images/client/orderHistory/star_empty.png"));

            setupRatingStars(star1, star2, star3, star4, star5);
            // ... setup other stars ...

            // Load XML file
            loadXMLFile();
        } catch (NullPointerException e) {
            e.printStackTrace(); // Print the stack trace to identify the cause of the NullPointerException
        }
    }

    private void setupRatingStars(ToggleButton... stars) {
        for (ToggleButton star : stars) {
            star.setGraphic(new ImageView(starEmptyImage));
            star.selectedProperty().addListener((observable, oldValue, isSelected) -> {
                star.setGraphic(new ImageView(isSelected ? starFilledImage : starEmptyImage));
            });
        }
    }

    private void loadXMLFile() {
        try {
            // Get the XML file from the resources folder
            InputStream inputStream = getClass().getResourceAsStream("/orders.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);

            // Normalize the XML structure
            doc.getDocumentElement().normalize();

            // Here we assume the root element is 'orders'
            NodeList orderList = doc.getElementsByTagName("order");

            for (int i = 0; i < orderList.getLength(); i++) {
                Element orderElement = (Element) orderList.item(i);
                // Get data from XML elements
                String id = orderElement.getAttribute("id");
                String productName = orderElement.getElementsByTagName("product").item(0).getTextContent();
                String size = orderElement.getElementsByTagName("size").item(0).getTextContent();
                String quantity = orderElement.getElementsByTagName("quantity").item(0).getTextContent();
                String price = orderElement.getElementsByTagName("price").item(0).getTextContent();
                String date = orderElement.getElementsByTagName("date").item(0).getTextContent();

                // For now, we just print the order details to the console
                System.out.printf("Order ID: %s, Product: %s, Size: %s, Quantity: %s, Price: %s, Date: %s%n",
                        id, productName, size, quantity, price, date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
