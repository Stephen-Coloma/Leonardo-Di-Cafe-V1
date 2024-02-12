package server.controller.temporarycontroller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import shared.Customer;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        addViewButtonToTable();
    }

    public void populateTableFromList(List<Customer> list) {
        ObservableList<Customer> accountList = FXCollections.observableArrayList();

        for (Customer entry : list) {
            accountList.add(entry);
        }

        accountsTableView.setItems(accountList);
    } // end of populateTableFromList

    private void addViewButtonToTable() {
        TableColumn<Customer, Void> viewColumn = new TableColumn<>("");
        viewColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Customer, Void> call(final TableColumn<Customer, Void> param) {
                return new TableCell<>() {
                    private final Button viewButton = new Button("View Account");

                    {
                        viewButton.setOnAction(event -> {
                            Customer rowData = getTableView().getItems().get(getIndex());
                            //showCustomerDetailsPopup(rowData);
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

        accountsTableView.getColumns().add(viewColumn);
    }
}
