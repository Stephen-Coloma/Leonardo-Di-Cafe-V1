package server.controller.temporarycontroller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import shared.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountsListPageController implements Initializable {
    @FXML
    private TableView accountsTableView;
    @FXML
    private TableColumn usernameColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn addressColumn;
    @FXML
    private TableColumn emailColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addViewButtonToTable();
    }


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
