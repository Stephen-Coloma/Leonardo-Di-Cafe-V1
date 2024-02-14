package server.controller.temporarycontroller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import shared.Order;
import shared.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AccountInformationPopupController implements Initializable {
    @FXML
    private TableView<Order> orderTableView;
    @FXML
    private TableColumn<Order, String> ordersColumn;
    @FXML
    private TableColumn<Order, String> timestampColumn;
    @FXML
    private Button closeTabButton;
    @FXML
    private Label name;
    @FXML
    private Label username;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label total;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ordersColumn.setCellValueFactory(cellData -> toStringOrder(cellData.getValue()));
        timestampColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTimeStamp()));
    }

    public void setName(String value) {
        name.setText(value);
    }

    public void setUsername(String value) {
        username.setText(value);
    }

    public void setAddress(String value) {
        address.setText(value);
    }

    public void setEmail(String value) {
        email.setText(value);
    }

    public void setTotal(String value) {
        total.setText(value);
    }

    public Button getCloseTabButton() {
        return closeTabButton;
    }

    public void populateTableFromList(List<Order> orderHistory) {
        ObservableList<Order> orderList = FXCollections.observableArrayList();

        orderList.addAll(orderHistory);

        orderTableView.setItems(orderList);
    } // end of populateTableFromList

    public StringProperty toStringOrder(Order order) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Product product : order.getOrders()) {
            stringBuilder.append(product.getName() + " (" + product.getQuantity() + ")").append("\n");
        }
        return new SimpleStringProperty(stringBuilder.toString());
    }

    public void setCloseTabButtonMouseEntered() {
        closeTabButton.setStyle("-fx-background-color: #9a7133");
    }

    public void setCloseTabButtonMouseExited() {
        closeTabButton.setStyle("-fx-background-color: #634921");
    }
} // end of ViewAccountInformationPopupController
