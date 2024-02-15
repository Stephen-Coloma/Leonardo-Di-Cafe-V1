package server.controller.inventory;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import server.view.inventory.InventoryPageView;

import java.io.IOException;

public class InventoryPageController {
    InventoryPageView controller;

    public InventoryPageController(FXMLLoader loader) {
        controller = loader.getController();
        setComponentActions();
    }

    private void setComponentActions() {

    }

    private void setEditDetailsButton() {
        controller.getEditQuantityButtons().forEach(button -> {
            TableColumn<Object, Void> object = (TableColumn<Object, Void>) controller.getInventoryTableView().getColumns().get(3);
            button.setOnAction(actionEvent -> {
                try {
                    //
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }
} // end of InventoryPageController class
