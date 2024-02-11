package server.controller.temporarycontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class AddProductsPageController implements Initializable {
    int quantity;
    double price;
    String productName;
    String productDescription;

    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField productDescriptionTextField;


    //MENU AND MENU BUTTONS
    @FXML
    private MenuButton typeOfProductMenuButton;
    @FXML
    private MenuItem foodMenuItem;
    @FXML
    private MenuItem beverageMenuItem;


    //SIZES TEXT FIELDS
    @FXML
    private TextField mainPriceTextField;
    @FXML
    private TextField mediumPriceTextField;
    @FXML
    private TextField largePriceTextField;

    //QUANTITY TEXT FIELDS

    @FXML
    private TextField mainQuantityTextField;
    @FXML
    private TextField mediumQuantityTextField;
    @FXML
    private TextField largeQuantityTextField;


    //LABELS
    @FXML
    private Label mainPriceLabel;
    @FXML
    private Label mediumPriceLabel;
    @FXML
    private Label largePriceLabel;
    @FXML
    private Label mainQuantityLabel;
    @FXML
    private Label mediumQuantityLabel;
    @FXML
    private Label largeQuantityLabel;


    @FXML
    private Label imageLabel;
    @FXML
    private TextField imageTextField;

    @FXML
    private Button addProductButton;

    @FXML
    private ImageView sampleImageView;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set text fields input to only accept integer and double inputs
        setFormatterForTextFields();
    }
    private void setFormatterForTextFields(){
        // Restrict price text fields to accept integers and doubles
        mainPriceTextField.setTextFormatter(createNumberTextFormatter());
        mediumPriceTextField.setTextFormatter(createNumberTextFormatter());
        largePriceTextField.setTextFormatter(createNumberTextFormatter());

        // Restrict quantity text fields to accept only integers
        mainQuantityTextField.setTextFormatter(createIntegerTextFormatter());
        mediumQuantityTextField.setTextFormatter(createIntegerTextFormatter());
        largeQuantityTextField.setTextFormatter(createIntegerTextFormatter());

    }
    private TextFormatter<Double> createNumberTextFormatter() {
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false); // Disables thousand separator
        DoubleStringConverter converter = new DoubleStringConverter();
        return new TextFormatter<>(converter, null, c ->
                c.getControlNewText().matches("-?\\d*\\.?\\d*") ? c : null);
    }

    private TextFormatter<Integer> createIntegerTextFormatter() {
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false); // Disables thousand separator
        IntegerStringConverter converter = new IntegerStringConverter();
        return new TextFormatter<>(converter, null, c ->
                c.getControlNewText().matches("\\d*") ? c : null);
    }

    public void chooseFoodMenuItem(ActionEvent actionEvent) {
        typeOfProductMenuButton.setText("Food");
        //Price TextFields
        mainPriceTextField.setEditable(true);
        mediumPriceTextField.setEditable(false);
        mediumPriceTextField.setText(null);
        largePriceTextField.setEditable(false);
        largePriceTextField.setText(null);
        //Quantity Text Fields
        mainQuantityTextField.setEditable(true);
        mediumQuantityTextField.setEditable(false);
        mediumQuantityTextField.setText(null);
        largeQuantityTextField.setEditable(false);
        largeQuantityTextField.setText(null);

        //Labels
        mainPriceLabel.setText("Price for Food");
        mediumPriceLabel.setText("...");
        largePriceLabel.setText("...");
        mainQuantityLabel.setText("Quantity for Food");
        mediumQuantityLabel.setText("...");
        largeQuantityLabel.setText("...");



    }

    public void chooseBeverageMenuItem(ActionEvent actionEvent) {
        typeOfProductMenuButton.setText("Beverage");

        //TextFields
        mainPriceTextField.setEditable(true);
        mediumPriceTextField.setEditable(true);
        largePriceTextField.setEditable(true);
        mainQuantityTextField.setEditable(true);
        mediumQuantityTextField.setEditable(true);
        largeQuantityTextField.setEditable(true);

        //Labels
        mainPriceLabel.setText("Price for Small");
        mediumPriceLabel.setText("Price for Medium");
        largePriceLabel.setText("Price for Large");
        mainQuantityLabel.setText("Quantity for Small");
        mediumQuantityLabel.setText("Quantity for Medium");
        largeQuantityLabel.setText("Quantity for Large");
    }

    public void addProductButtonEntered(MouseEvent mouseEvent) {
        addProductButton.setStyle("-fx-background-color: #634950;");
    }

    public void addProductButtonExited(MouseEvent mouseEvent) {
        addProductButton.setStyle("-fx-background-color: #634921;");
    }
    //----TEST METHOD ONLY----
    public void addProductClicked(MouseEvent mouseEvent) {
        productName = productNameTextField.getText();
        productDescription = productDescriptionTextField.getText();
        if(typeOfProductMenuButton.getText().equals("Food")){
            quantity = Integer.parseInt(mainQuantityTextField.getText());
            price = Double.parseDouble(mainPriceTextField.getText());
            price *= quantity;
        }
        System.out.println("Product Name: "+productName);
        System.out.println("Product Description: "+ productDescription);
        System.out.println("Quantity: " +quantity);
        System.out.println("Price: P" +price);

    }
    //-----TEST METHOD-----
    public void chooseImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.svg")
        );

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Handle the selected file (you can add further processing here)
        if (selectedFile != null) {
            // Do something with the selected file
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            imageTextField.setText(selectedFile.getAbsolutePath());
            // Set the chosen image to the sampleImageView for preview
            Image image = new Image(selectedFile.toURI().toString());
            sampleImageView.setImage(image);
        }

        if(imageTextField.getText() == null || imageTextField.getText().isEmpty()){
            imageLabel.setText("No image chosen");
            imageLabel.setTextFill(Paint.valueOf("RED"));
        }else{
            imageLabel.setText("Image chosen");
            imageLabel.setTextFill(Paint.valueOf("GREEN"));
        }
    }
}

