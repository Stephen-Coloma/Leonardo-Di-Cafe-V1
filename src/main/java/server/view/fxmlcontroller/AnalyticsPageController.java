package server.view.fxmlcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AnalyticsPageController {
    @FXML
    private TableView beverageAnalyticsTableView;
    @FXML
    private TableColumn productBeverageColumn;
    @FXML
    private TableColumn orderCountBeverageColumn;
    @FXML
    private TableView foodAnalyticsTableView;
    @FXML
    private TableColumn productFoodColumn;
    @FXML
    private TableColumn orderCountFoodColumn;
    @FXML
    private Label totalOrdersTextLabel;
    @FXML
    private Label salesTextLabel;
}
