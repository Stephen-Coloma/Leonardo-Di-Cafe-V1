package server.controller.temporarycontroller;

import javafx.application.Platform;
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
import shared.Customer;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AccountsListPageController implements Initializable {
    @FXML
    private TableView<Customer> accountsTableView;
    @FXML
    private TableColumn<Customer, String> usernameColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> emailColumn;
    @FXML
    private TableColumn<Object, Void> viewInformationColumn;
    @FXML
    private TextField searchAccountTextField;
    private FilteredList<Customer> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        addViewInformationButton();
        searchAccountTextField.textProperty().addListener((observable, oldValue, newValue) -> filterTable(newValue));
    } // end of initialize

    private Button createButton(EventHandler<ActionEvent> eventHandler) {
        Button button = new Button("View");
        button.setOnAction(eventHandler);
        return button;
    } // end of createButton

    private void addViewInformationButton() {
        viewInformationColumn.setCellValueFactory(new PropertyValueFactory<>(""));

        Callback<TableColumn<Object, Void>, TableCell<Object, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Object, Void> call(final TableColumn<Object, Void> param) {
                return new TableCell<>() {
                    private final Button viewButton = createButton(event -> {
                        Customer customer = (Customer) getTableRow().getItem();
                        Platform.runLater(() -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/server/account_information_popup.fxml"));
                                Stage popupStage = new Stage();
                                popupStage.initModality(Modality.APPLICATION_MODAL);
                                popupStage.setTitle("Customer Information");
                                popupStage.setScene(new Scene(loader.load()));
                                AccountInformationPopupController controller = loader.getController();

                                controller.populateTableFromList(customer.getOrderHistory());
                                controller.setName(customer.getName());
                                controller.setUsername(customer.getUsername());
                                controller.setAddress(customer.getAddress());
                                controller.setEmail(customer.getEmail());
                                controller.setTotal(String.valueOf(customer.getOrderHistory().size()));

                                controller.getCloseTabButton().setOnAction(actionEvent -> {
                                    popupStage.close();
                                });

                                popupStage.show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    });

                    {
                        viewButton.setStyle("-fx-background-color: #634921; -fx-text-fill: white;");

                        viewButton.setOnMouseEntered(e -> viewButton.setStyle("-fx-background-color: #9a7133; -fx-text-fill: white;"));

                        viewButton.setOnMouseExited(e -> viewButton.setStyle("-fx-background-color: #634921; -fx-text-fill: white;"));
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(viewButton);
                            setAlignment(Pos.CENTER);
                        }
                    }
                };
            }
        };
        viewInformationColumn.setCellFactory(cellFactory);
    } // end of addViewInformationButton

    public void populateTableFromList(List<Customer> list) {
        ObservableList<Customer> accountList = FXCollections.observableArrayList();

        accountList.addAll(list);

        filteredList = new FilteredList<>(accountList, item -> true);
        accountsTableView.setItems(filteredList);
    } // end of populateTableFromList

    private void filterTable(String searchText) {
        filteredList.setPredicate(item -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }

            return item.getName().toLowerCase().contains(searchText.toLowerCase());
        });
        accountsTableView.setItems(filteredList);
    } // end of filterTable
} // end of AccountsListPageController class
