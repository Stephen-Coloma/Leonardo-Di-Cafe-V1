package server.controller.temporarycontroller;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import shared.Beverage;
import shared.Food;
import shared.Order;
import shared.Product;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class InventoryPageController implements Initializable {
    @FXML
    private TableColumn<Object, String> productNameColumn;
    @FXML
    private TableColumn<Object, String> typeColumn;
    @FXML
    private TableColumn<Object, Integer> quantityColumn;
    @FXML
    private TableView<Object> inventoryTableView;
    @FXML
    private TextField searchInventoryTextField;
    @FXML
    private Button saveChangesButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productNameColumn.setCellValueFactory(cellData -> {
            Object object = cellData.getValue();

            String name = "";
            if (object instanceof Food) {
                name = ((Food) object).getName();

            } else if (object instanceof Beverage) {
                name = ((Beverage) object).getName();
            }
            return new SimpleStringProperty(name);
        });

        typeColumn.setCellValueFactory(cellData -> {
            Object object = cellData.getValue();

            char type = ' ';
            if (object instanceof Food) {
                type = ((Food) object).getType();
            } else if (object instanceof Beverage) {
                type = ((Beverage) object).getType();
            }
            return new SimpleStringProperty(type == 'f' ? "food" : "beverage");
        });

        quantityColumn.setCellValueFactory(cellData -> {
            Object object = cellData.getValue();

            int quantity = 0;
            if (object instanceof Food) {
                quantity = ((Food) object).getQuantity();
            } else if (object instanceof Beverage) {
                quantity = ((Beverage) object).getQuantity();
            }
            return new SimpleIntegerProperty(quantity).asObject();
        });

        /*
        productNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getType())));
        typeColumn.setCellValueFactory(cellData -> {
            char type = cellData.getValue().getType();
            return new SimpleStringProperty(type == 'f' ? "food" : "beverage");
        });

        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());

         */

        addPlusButtonToTable();
        addMinusButtonToTable();
        setInventoryTextFieldListener();
    }

    public void populateTableFromMap(HashMap<String, Food> foodMenu, HashMap<String, Beverage> beverageMenu) {
        ObservableList<Object> productList = FXCollections.observableArrayList();

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
        FilteredList<Object> filteredData = new FilteredList<>(inventoryTableView.getItems(), p -> true);

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
                if (product instanceof Food food) {
                    return food.getName().toLowerCase().contains(lowerCaseFilter);
                } else if (product instanceof Beverage beverage) {
                    return beverage.getName().toLowerCase().contains(lowerCaseFilter);
                }

                return false; // No match found
            });
        });

        // Wrap the filtered list in a SortedList
        SortedList<Object> sortedData = new SortedList<>(filteredData);

        // Bind the sorted list to the table
        sortedData.comparatorProperty().bind(inventoryTableView.comparatorProperty());
        inventoryTableView.setItems(sortedData);
    }

    private void addPlusButtonToTable() {
        TableColumn<Object, Void> plusColumn = new TableColumn<>("");
        plusColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Object, Void> call(final TableColumn<Object, Void> param) {
                return new TableCell<>() {
                    private final Button plusButton = new Button("+");

                    {
                        plusButton.setOnAction(event -> {
                            Object product = getTableRow().getItem();
                            //TODO: Implement incrementation of quantity
                            if (product instanceof Food food) {
                                try {
                                    food.incrementQuantity();
                                    int rowIndex = getTableRow().getIndex();
                                    inventoryTableView.getItems().set(rowIndex, food);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            } else if (product instanceof Beverage beverage) {
                                try {
                                    Platform.runLater(() -> {
                                        try {
                                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/server/BeverageInventorySizePopup.fxml"));
                                            Stage popupStage = new Stage();
                                            popupStage.initModality(Modality.APPLICATION_MODAL);
                                            popupStage.setScene(new Scene(loader.load()));
                                            BeverageInventorySizesPopupController controller = loader.getController();

                                            controller.setSmallQuantity(String.valueOf(beverage.getSizeQuantity().get("small")));
                                            controller.setMediumQuantity(String.valueOf(beverage.getSizeQuantity().get("medium")));
                                            controller.setLargeQuantity(String.valueOf(beverage.getSizeQuantity().get("large")));

                                            int rowIndex = getTableRow().getIndex();

                                            controller.getSmallIncrementButton().setOnAction(actionEvent -> {
                                                beverage.incrementQuantity("small");
                                                controller.setSmallQuantity(String.valueOf(beverage.getSizeQuantity().get("small")));
                                                inventoryTableView.getItems().set(rowIndex, beverage);
                                            });

                                            controller.getMediumIncrementButton().setOnAction(actionEvent -> {
                                                beverage.incrementQuantity("medium");
                                                controller.setMediumQuantity(String.valueOf(beverage.getSizeQuantity().get("medium")));
                                                inventoryTableView.getItems().set(rowIndex, beverage);
                                            });

                                            controller.getLargeIncrementButton().setOnAction(actionEvent -> {
                                                beverage.incrementQuantity("large");
                                                inventoryTableView.getItems().set(rowIndex, beverage);
                                                controller.setLargeQuantity(String.valueOf(beverage.getSizeQuantity().get("large")));
                                            });

                                            controller.getAcceptButton().setOnAction(actionEvent -> {
                                                popupStage.close();
                                            });

                                            popupStage.show();
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
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
        TableColumn<Object, Void> minusColumn = new TableColumn<>("");
        minusColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Object, Void> call(final TableColumn<Object, Void> param) {
                return new TableCell<>() {
                    private final Button minusButton = new Button("-");

                    {
                        minusButton.setOnAction(event -> {
                            Object product = getTableRow().getItem();
                            //TODO: Implement decrementation of quantity
                            if (product instanceof Food food) {
                                try {
                                    food.updateQuantity(1);
                                    int rowIndex = getTableRow().getIndex();
                                    inventoryTableView.getItems().set(rowIndex, food);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            } else if (product instanceof Beverage beverage) {
                                try {
                                    beverage.updateQuantity("small", 1);
                                    int rowIndex = getTableRow().getIndex();
                                    inventoryTableView.getItems().set(rowIndex, beverage);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
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

    public Button getSaveChangesButton() {
        return saveChangesButton;
    }

    public ObservableList<Object> getItems() {
        return inventoryTableView.getItems();
    }
}
