package server.controller.inventory;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import server.view.inventory.FoodEditDetailsPopupView;

import java.io.File;

public class FoodEditDetailsPopupController {
    FoodEditDetailsPopupView controller;

    public FoodEditDetailsPopupController(FXMLLoader loader) {
        controller = loader.getController();
        setComponentActions();
    }

    public void setComponentActions() {
        controller.getChooseImageButton().setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Image File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.svg")
            );

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                controller.getImageTextField().setText(selectedFile.getAbsolutePath());
            }

            if (controller.getImageTextField().getText() == null || controller.getImageTextField().getText().isEmpty()) {
                controller.setImageLabel("No image chosen");
                controller.getImageLabel().setTextFill(Paint.valueOf("RED"));
            } else {
                controller.setImageLabel("Image chosen");
                controller.getImageLabel().setTextFill(Paint.valueOf("GREEN"));
            }
        });
    } // end of setComponentActions
} // end of FoodEditDetailsPopupController class
