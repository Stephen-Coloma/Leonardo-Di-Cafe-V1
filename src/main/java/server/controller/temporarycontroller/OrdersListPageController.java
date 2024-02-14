package server.controller.temporarycontroller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.util.Duration;
import shared.Order;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrdersListPageController implements Initializable {
    @FXML
    private TableView<Order> orderTableView;
    @FXML
    private TableColumn<Order, Integer> orderIDColumn;
    @FXML
    private TableColumn<Order, String> dateAndTimeColumn;
    @FXML
    private TableColumn<Order, String> customerColumn;
    @FXML
    private TableColumn<Order, String> addressColumn;
    @FXML
    private TableColumn<Order, Double> totalColumn;
    @FXML
    private TableColumn<Order, String> statusColumn;
    @FXML
    private TextField searchOrderTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderIDColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getID()).asObject());
        dateAndTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTimeStamp()));
        customerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getName()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getAddress()));
        totalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalPrice()).asObject());
        statusColumn.setCellValueFactory(cellData -> {
            boolean status = cellData.getValue().isStatus();
            return new SimpleStringProperty(status ? "Received" : "Not Received");
        });


        //adds the view button to the table
        addViewButtonToTable();
        //adds the confirm button to the table
        addConfirmButtonToTable();
        //set search functionality for search text field
        setSearchOrderTextFieldListener();
    }

    public void populateTableFromList(List<Order> list) {
        ObservableList<Order> orderList = FXCollections.observableArrayList();

        orderList.addAll(list);

        orderTableView.setItems(orderList);
    } // end of populateTableFromList

    private void setSearchOrderTextFieldListener() {
        // Create a FilteredList to filter the items in the table
        FilteredList<Order> filteredData = new FilteredList<>(orderTableView.getItems(), p -> true);

        // Bind the FilteredList to the search bar text property
        searchOrderTextField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(order -> {
            // If search bar is empty, display all items
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            // Convert search query to lowercase for case-insensitive search
            String lowerCaseFilterString = newValue.toLowerCase();

            // Filter by order ID and customer name
            return order.getCustomer().getName().toLowerCase().contains(lowerCaseFilterString) ||
                    String.valueOf(order.getID()).contains(lowerCaseFilterString); // Match found
        }));

        // Wrap the filtered list in a SortedList
        SortedList<Order> sortedData = new SortedList<>(filteredData);

        // Bind the sorted list to the table
        sortedData.comparatorProperty().bind(orderTableView.comparatorProperty());
        orderTableView.setItems(sortedData);
    }

    private void addViewButtonToTable() {
        TableColumn<Order, Void> viewColumn = new TableColumn<>("");
        viewColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
                return new TableCell<>() {
                    private final Button viewButton = new Button("View");

                    {
                        viewButton.setOnAction(event -> {
                            Order rowData = getTableView().getItems().get(getIndex());
                            //showOrderDetailsPopup(rowData);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(viewButton);
                        }
                    }
                };
            }
        });

        orderTableView.getColumns().add(viewColumn);
    }
    private void addConfirmButtonToTable() {
        TableColumn<Order, Void> confirmColumn = new TableColumn<>("");
        confirmColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
                return new TableCell<>() {
                    private final Button confirmButton = new Button("Confirm");

                    {
                        confirmButton.setOnAction(event -> {
                            Order rowData = getTableView().getItems().get(getIndex());
                            startCountdown(rowData);
                            confirmButton.setDisable(true);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Order rowData = getTableView().getItems().get(getIndex());
                            confirmButton.setDisable(rowData.isStatus());
                            setGraphic(confirmButton);
                        }
                    }

                    private void startCountdown(Order order) {
                        if (!order.isStatus()) {
                            Timeline countdownTimer = new Timeline();
                            countdownTimer.setCycleCount(0);
                            countdownTimer.getKeyFrames().add(
                                    new KeyFrame(Duration.seconds(2), e -> {
                                        order.setStatus(true);
                                        getTableView().refresh();
                                        countdownTimer.stop();
                                    })
                            );
                            countdownTimer.play();
                        }
                    }
                };
            }
        });

        orderTableView.getColumns().add(confirmColumn);
    }

}
