package server.controller.temporarycontroller.inventory;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import server.controller.temporarycontroller.YesNoPopupController;
import shared.Beverage;
import shared.Food;
import shared.SerializableImage;
import util.ImageCopier;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class InventoryPageController implements Initializable {
    @FXML
    private TableColumn<Object, String> productNameColumn;
    @FXML
    private TableColumn<Object, String> typeColumn;
    @FXML
    private TableColumn<Object, Integer> quantityColumn;
    @FXML
    private TableColumn<Object, Void> editQuantityColumn;
    @FXML
    private TableColumn<Object, Void> editDetailsColumn;
    @FXML
    private TableColumn<Object, Void> deleteProductColumn;
    @FXML
    private TableView<Object> inventoryTableView;
    @FXML
    private TextField searchInventoryTextField;
    @FXML
    private Button saveChangesButton;
    private FilteredList<Object> filteredList;
    private ObservableList<Object> productList;

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

        addEditQuantityButton();
        addEditDetailsButton();
        addDeleteProductButton();
        searchInventoryTextField.textProperty().addListener((observable, oldValue, newValue) -> filterTable(newValue));
    } // end of initialize

    private Button createButton(String buttonName, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(buttonName);
        button.setOnAction(eventHandler);
        return button;
    } // end of createButton

    private void addEditQuantityButton() {
        editQuantityColumn.setCellValueFactory(new PropertyValueFactory<>(""));

        Callback<TableColumn<Object, Void>, TableCell<Object, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Object, Void> call(final TableColumn<Object, Void> param) {
                return new TableCell<>() {
                    private final Button editButton = createButton("quantity", event -> {
                        Object product = getTableRow().getItem();
                        if (product instanceof Food food) {
                            Platform.runLater(() -> {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/server/inventory/food_inventory_size_popup.fxml"));
                                    Stage popupStage = new Stage();
                                    popupStage.initModality(Modality.APPLICATION_MODAL);
                                    popupStage.setTitle("Edit Food Quantity");
                                    popupStage.setScene(new Scene(loader.load()));
                                    FoodInventoryPopupController controller = loader.getController();

                                    int filteredIndex = getTableRow().getIndex();
                                    int originalIndex = filteredList.getSourceIndex(filteredIndex);

                                    AtomicInteger total = new AtomicInteger(food.getQuantity());

                                    controller.setTotalQuantity("total: " + total);

                                    controller.getIncrementButton().setOnAction(actionEvent -> {
                                        int value = Integer.parseInt(controller.getQuantity().getText()) + 1;
                                        total.getAndIncrement();
                                        controller.setQuantity(String.valueOf(value));
                                        controller.setTotalQuantity("total: " + total);
                                    });

                                    controller.getDecrementButton().setOnAction(actionEvent -> {
                                        int value = Integer.parseInt(controller.getQuantity().getText()) - 1;
                                        total.getAndDecrement();
                                        controller.setQuantity(String.valueOf(value));
                                        controller.setTotalQuantity("total: " + total);
                                    });

                                    controller.getAcceptButton().setOnAction(actionEvent -> {

                                        int counter = Integer.parseInt(controller.getQuantity().getText());

                                        do {
                                            if (counter > 0) {
                                                food.incrementQuantity();
                                                counter--;
                                            } else {
                                                food.decrementQuantity();
                                                counter++;
                                            }
                                        } while (counter != 0);

                                        if (originalIndex != -1) {
                                            if (originalIndex >= 0 && originalIndex < productList.size()) {
                                                productList.set(originalIndex, food);
                                            }
                                        }

                                        popupStage.close();
                                    });
                                    popupStage.show();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        } else if (product instanceof Beverage beverage) {
                            Platform.runLater(() -> {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/server/inventory/beverage_inventory_size_popup.fxml"));
                                    Stage popupStage = new Stage();
                                    popupStage.initModality(Modality.APPLICATION_MODAL);
                                    popupStage.setTitle("Edit Beverage Quantity");
                                    popupStage.setScene(new Scene(loader.load()));
                                    BeverageInventorySizesPopupController controller = loader.getController();

                                    int filteredIndex = getTableRow().getIndex();
                                    int originalIndex = filteredList.getSourceIndex(filteredIndex);

                                    AtomicInteger smallTotal = new AtomicInteger(beverage.getSizeQuantity().get("small"));
                                    AtomicInteger mediumTotal = new AtomicInteger(beverage.getSizeQuantity().get("medium"));
                                    AtomicInteger largeTotal = new AtomicInteger(beverage.getSizeQuantity().get("large"));

                                    controller.setTotalSmallQuantityLabel("total: " + smallTotal.get());
                                    controller.setTotalMediumQuantityLabel("total: " + mediumTotal.get());
                                    controller.setTotalLargeQuantityLabel("total: " + largeTotal.get());

                                    controller.getSmallIncrementButton().setOnAction(actionEvent -> {
                                        int value = Integer.parseInt(controller.getSmallQuantity().getText()) + 1;
                                        smallTotal.getAndIncrement();
                                        controller.setSmallQuantity(String.valueOf(value));
                                        controller.setTotalSmallQuantityLabel("total: " + smallTotal.get());
                                    });

                                    controller.getMediumIncrementButton().setOnAction(actionEvent -> {
                                        int value = Integer.parseInt(controller.getMediumQuantity().getText()) + 1;
                                        mediumTotal.getAndIncrement();
                                        controller.setMediumQuantity(String.valueOf(value));
                                        controller.setTotalMediumQuantityLabel("total: " + mediumTotal.get());
                                    });

                                    controller.getLargeIncrementButton().setOnAction(actionEvent -> {
                                        int value = Integer.parseInt(controller.getLargeQuantity().getText()) + 1;
                                        largeTotal.getAndIncrement();
                                        controller.setLargeQuantity(String.valueOf(value));
                                        controller.setTotalLargeQuantityLabel("total: " + largeTotal.get());
                                    });

                                    controller.getSmallDecrementButton().setOnAction(actionEvent -> {
                                        int value = Integer.parseInt(controller.getSmallQuantity().getText()) - 1;
                                        smallTotal.getAndDecrement();
                                        controller.setSmallQuantity(String.valueOf(value));
                                        controller.setTotalSmallQuantityLabel("total: " + smallTotal.get());
                                    });

                                    controller.getMediumDecrementButton().setOnAction(actionEvent -> {
                                        int value = Integer.parseInt(controller.getMediumQuantity().getText()) - 1;
                                        mediumTotal.getAndDecrement();
                                        controller.setMediumQuantity(String.valueOf(value));
                                        controller.setTotalMediumQuantityLabel("total: " + mediumTotal.get());
                                    });

                                    controller.getLargeDecrementButton().setOnAction(actionEvent -> {
                                        int value = Integer.parseInt(controller.getLargeQuantity().getText()) - 1;
                                        largeTotal.getAndDecrement();
                                        controller.setLargeQuantity(String.valueOf(value));
                                        controller.setTotalLargeQuantityLabel("total: " + largeTotal.get());
                                    });

                                    controller.getAcceptButton().setOnAction(actionEvent -> {
                                        int smallCounter = Integer.parseInt(controller.getSmallQuantity().getText());
                                        int mediumCounter = Integer.parseInt(controller.getMediumQuantity().getText());
                                        int largeCounter = Integer.parseInt(controller.getLargeQuantity().getText());

                                        do {
                                            if (smallCounter > 0) {
                                                beverage.incrementQuantity("small");
                                                smallCounter--;
                                            } else {
                                                beverage.decrementQuantity("small");
                                                smallCounter++;
                                            }
                                        } while (smallCounter != 0);

                                        do {
                                            if (mediumCounter > 0) {
                                                beverage.incrementQuantity("medium");
                                                mediumCounter--;
                                            } else {
                                                beverage.decrementQuantity("medium");
                                                mediumCounter++;
                                            }
                                        } while (mediumCounter != 0);

                                        do {
                                            if (largeCounter > 0) {
                                                beverage.incrementQuantity("large");
                                                largeCounter--;
                                            } else {
                                                beverage.decrementQuantity("large");
                                                largeCounter++;
                                            }
                                        } while (largeCounter != 0);

                                        if (originalIndex != -1) {
                                            if (originalIndex >= 0 && originalIndex < productList.size()) {
                                                productList.set(originalIndex, beverage);
                                            }
                                        }

                                        popupStage.close();
                                    });
                                    popupStage.show();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }
                    });

                    {
                        editButton.setStyle("-fx-background-color: #634921; -fx-text-fill: white;");

                        editButton.setOnMouseEntered(e -> editButton.setStyle("-fx-background-color: #9a7133; -fx-text-fill: white;"));

                        editButton.setOnMouseExited(e -> editButton.setStyle("-fx-background-color: #634921; -fx-text-fill: white;"));
                    }
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(editButton);
                            setAlignment(Pos.CENTER);
                        }
                    }
                };
            }
        };
        editQuantityColumn.setCellFactory(cellFactory);
    } // end of addEditQuantityButton

    private void addEditDetailsButton() {
        editDetailsColumn.setCellValueFactory(new PropertyValueFactory<>(""));

        Callback<TableColumn<Object, Void>, TableCell<Object, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Object, Void> call(final TableColumn<Object, Void> param) {
                return new TableCell<>() {
                    private final Button editButton = createButton("details", event -> {
                        Object product = getTableRow().getItem();
                        if (product instanceof Food food) {
                            Platform.runLater(() -> {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/server/inventory/food_edit_details_popup.fxml"));
                                    Stage popupStage = new Stage();
                                    popupStage.initModality(Modality.APPLICATION_MODAL);
                                    popupStage.setTitle("Edit Food Details");
                                    popupStage.setScene(new Scene(loader.load()));
                                    FoodEditDetailsPopupController controller = loader.getController();

                                    int filteredIndex = getTableRow().getIndex();
                                    int originalIndex = filteredList.getSourceIndex(filteredIndex);

                                    controller.setProductNameTextField(food.getName());
                                    controller.setPriceTextField(String.valueOf(food.getPrice()));
                                    controller.setProductDescriptionTextArea(food.getDescription());

                                    controller.getAcceptButton().setOnAction(actionEvent -> {
                                        String productName = controller.getProductNameTextField().getText().trim();
                                        String description = controller.getProductDescriptionTextArea().getText().trim();
                                        double price = Double.parseDouble(controller.getPriceTextField().getText().trim());

                                        if (!controller.getImageTextField().getText().isEmpty()) {
                                            String absolutePath = new File(controller.getImageTextField().getText()).getAbsolutePath();
                                            String extension = absolutePath.substring(absolutePath.lastIndexOf('.'));
                                            String copiedImagePath = ImageCopier.copyImage(absolutePath, productName + extension);

                                            String url = food.getImage().getUrl();
                                            ImageCopier.deleteImage(url.substring(url.lastIndexOf('/') + 1));

                                            SerializableImage image = new SerializableImage("file:" + copiedImagePath);
                                            food.setImage(image);
                                        }

                                        food.setName(productName);
                                        food.setDescription(description);
                                        food.setPrice(price);

                                        if (originalIndex != -1) {
                                            if (originalIndex >= 0 && originalIndex < productList.size()) {
                                                productList.set(originalIndex, food);
                                            }
                                        }

                                        popupStage.close();
                                    });
                                    popupStage.show();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        } else if (product instanceof Beverage beverage) {
                            Platform.runLater(() -> {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/server/inventory/beverage_edit_details_popup.fxml"));
                                    Stage popupStage = new Stage();
                                    popupStage.initModality(Modality.APPLICATION_MODAL);
                                    popupStage.setTitle("Edit Beverage Details");
                                    popupStage.setScene(new Scene(loader.load()));
                                    BeverageEditDetailsPopupController controller = loader.getController();

                                    int filteredIndex = getTableRow().getIndex();
                                    int originalIndex = filteredList.getSourceIndex(filteredIndex);

                                    controller.setProductNameTextField(beverage.getName());
                                    controller.setSmallPriceTextField(String.valueOf(beverage.getSizePrice().get("small")));
                                    controller.setMediumPriceTextField(String.valueOf(beverage.getSizePrice().get("medium")));
                                    controller.setLargePriceTextField(String.valueOf(beverage.getSizePrice().get("large")));
                                    controller.setProductDescriptionTextArea(beverage.getDescription());

                                    controller.getAcceptButton().setOnAction(actionEvent -> {
                                        String productName = controller.getProductNameTextField().getText().trim();
                                        String description = controller.getProductDescriptionTextArea().getText().trim();
                                        double smallPrice = Double.parseDouble(controller.getSmallPriceTextField().getText().trim());
                                        double mediumPrice = Double.parseDouble(controller.getMediumPriceTextField().getText().trim());
                                        double largePrice = Double.parseDouble(controller.getLargePriceTextField().getText().trim());

                                        if (!controller.getImageTextField().getText().isEmpty()) {
                                            String absolutePath = new File(controller.getImageTextField().getText()).getAbsolutePath();
                                            String extension = absolutePath.substring(absolutePath.lastIndexOf('.'));
                                            String copiedImagePath = ImageCopier.copyImage(absolutePath, productName + extension);

                                            String url = beverage.getImage().getUrl();
                                            ImageCopier.deleteImage(url.substring(url.lastIndexOf('/') + 1));

                                            SerializableImage image = new SerializableImage("file:" + copiedImagePath);
                                            beverage.setImage(image);
                                        }

                                        HashMap<String, Double> priceSize = new HashMap<>();
                                        priceSize.put("small", smallPrice);
                                        priceSize.put("medium", mediumPrice);
                                        priceSize.put("large", largePrice);

                                        beverage.setName(productName);
                                        beverage.setDescription(description);
                                        beverage.setSizePrice(priceSize);

                                        if (originalIndex != -1) {
                                            if (originalIndex >= 0 && originalIndex < productList.size()) {
                                                productList.set(originalIndex, beverage);
                                            }
                                        }

                                        popupStage.close();
                                    });
                                    popupStage.show();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }
                    });

                    {
                        editButton.setStyle("-fx-background-color: #634921; -fx-text-fill: white;");

                        editButton.setOnMouseEntered(e -> editButton.setStyle("-fx-background-color: #9a7133; -fx-text-fill: white;"));

                        editButton.setOnMouseExited(e -> editButton.setStyle("-fx-background-color: #634921; -fx-text-fill: white;"));
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(editButton);
                            setAlignment(Pos.CENTER);
                        }
                    }
                };
            }
        };
        editDetailsColumn.setCellFactory(cellFactory);
    } // end of addEditDetailsButton

    private void addDeleteProductButton() {
        deleteProductColumn.setCellValueFactory(new PropertyValueFactory<>(""));

        Callback<TableColumn<Object, Void>, TableCell<Object, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Object, Void> call(final TableColumn<Object, Void> param) {
                return new TableCell<>() {
                    private final Button deleteButton = createButton("delete", event -> Platform.runLater(() -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/server/yes_no_popup.fxml"));
                            Stage popupStage = new Stage();
                            popupStage.initModality(Modality.APPLICATION_MODAL);
                            popupStage.setTitle("Delete Product");
                            popupStage.setScene(new Scene(loader.load()));
                            YesNoPopupController controller = loader.getController();

                            int filteredIndex = getTableRow().getIndex();
                            int originalIndex = filteredList.getSourceIndex(filteredIndex);

                            controller.setQuestionPromptLabel("Are you sure you want to delete this product?");

                            controller.getYesButton().setOnAction(actionEvent -> {
                                if (originalIndex >= 0 && originalIndex < productList.size()) {
                                    productList.remove(originalIndex);
                                }
                                popupStage.close();
                            });

                            controller.getNoButton().setOnAction(actionEvent -> popupStage.close());

                            popupStage.show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }));

                    {
                        deleteButton.setStyle("-fx-background-color: #634921; -fx-text-fill: white;");

                        deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-background-color: #9a7133; -fx-text-fill: white;"));

                        deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-background-color: #634921; -fx-text-fill: white;"));
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                            setAlignment(Pos.CENTER);
                        }
                    }
                };
            }
        };
        deleteProductColumn.setCellFactory(cellFactory);
    }

    public void populateTableFromMap(HashMap<String, Food> foodMenu, HashMap<String, Beverage> beverageMenu) {
        productList = FXCollections.observableArrayList();

        for (Map.Entry<String, Food> entry : foodMenu.entrySet()) {
            Food food = entry.getValue();
            productList.add(food);
        }

        for (Map.Entry<String, Beverage> entry : beverageMenu.entrySet()) {
            Beverage beverage = entry.getValue();
            productList.add(beverage);
        }

        filteredList = new FilteredList<>(productList, item -> true);
        inventoryTableView.setItems(filteredList);
    } // end of populateTableFromMap

    private void filterTable(String searchText) {
        filteredList.setPredicate(item -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }

            if (item instanceof Food food) {
                return food.getName().toLowerCase().contains(searchText.toLowerCase());
            } else if (item instanceof Beverage beverage) {
                return beverage.getName().toLowerCase().contains(searchText.toLowerCase());
            }
            return false;
        });
        inventoryTableView.setItems(filteredList);
    } // end of filterTable

    public Button getSaveChangesButton() {
        return saveChangesButton;
    } // end of getSaveChangesButton

    public ObservableList<Object> getProductList() {
        return productList;
    } // end of getProductList
} // end of InventoryPageController class
