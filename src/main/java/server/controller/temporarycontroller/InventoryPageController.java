package server.controller.temporarycontroller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import shared.Beverage;
import shared.Food;
import shared.Product;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class InventoryPageController implements Initializable {
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, String> typeColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableView<Product> inventoryTableView;
    @FXML
    private TextField searchInventoryTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getType())));
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());

        addPlusButtonToTable();
        addMinusButtonToTable();
        setInventoryTextFieldListener();
    }

    public void populateTableFromMap(HashMap<String, Food> foodMenu, HashMap<String, Beverage> beverageMenu) {
        ObservableList<Product> productList = FXCollections.observableArrayList();

        for (Map.Entry<String, Food> entry : foodMenu.entrySet()) {
            Food food = entry.getValue();
            productList.add(food);
        }

        for (Map.Entry<String, Beverage> entry : beverageMenu.entrySet()) {
            Beverage beverage = entry.getValue();
            productList.add(beverage);
        }

        inventoryTableView.setItems(productList);
    }

    private void setInventoryTextFieldListener() {
        // Create a FilteredList to filter the items in the table
        FilteredList<Product> filteredData = new FilteredList<>(inventoryTableView.getItems(), p -> true);

        // Bind the FilteredList to the search bar text property
        searchInventoryTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> {
                // If search bar is empty, display all items
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Convert search query to lowercase for case-insensitive search
                String lowerCaseFilter = newValue.toLowerCase();

                // Filter by product name
                if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Match found
                }

                return false; // No match found
            });
        });

        // Wrap the filtered list in a SortedList
        SortedList<Product> sortedData = new SortedList<>(filteredData);

        // Bind the sorted list to the table
        sortedData.comparatorProperty().bind(inventoryTableView.comparatorProperty());
        inventoryTableView.setItems(sortedData);
    }

    private void addPlusButtonToTable() {
        TableColumn<Product, Void> plusColumn = new TableColumn<>("");
        plusColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                return new TableCell<>() {
                    private final Button plusButton = new Button("+");

                    {
                        plusButton.setOnAction(event -> {
                            Product product = getTableRow().getItem();
                           //TODO: Implement incrementation of quantity
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(plusButton);
                        }
                    }
                };
            }
        });
        inventoryTableView.getColumns().add(plusColumn);
    }

    private void addMinusButtonToTable() {
        TableColumn<Product, Void> minusColumn = new TableColumn<>("");
        minusColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                return new TableCell<>() {
                    private final Button minusButton = new Button("-");

                    {
                        minusButton.setOnAction(event -> {
                            Product product = getTableRow().getItem();
                           //TODO: Implement decrementation of quantity
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(minusButton);
                        }
                    }
                };
            }
        });
        inventoryTableView.getColumns().add(minusColumn);
    }
}
