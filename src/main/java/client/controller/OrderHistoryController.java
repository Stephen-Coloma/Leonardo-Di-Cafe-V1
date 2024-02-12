package client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class OrderHistoryController {

    @FXML
    private Button orderHistoryExit;


    // Defining all sets of stars
    @FXML private ToggleButton star1, star2, star3, star4, star5;
    @FXML private ToggleButton star11, star21, star31, star41, star51;
    @FXML private ToggleButton star111, star211, star311, star411, star511;
    @FXML private ToggleButton star12, star22, star32, star42, star52;
    // Add more sets as necessary...

    @FXML
    public void initialize() {
        setupStarRatingListeners(new ToggleButton[]{star1, star2, star3, star4, star5});
        setupStarRatingListeners(new ToggleButton[]{star11, star21, star31, star41, star51});
        setupStarRatingListeners(new ToggleButton[]{star111, star211, star311, star411, star511});
        setupStarRatingListeners(new ToggleButton[]{star12, star22, star32, star42, star52});
        // Initialize more sets as required...
    }

    private void setupStarRatingListeners(ToggleButton[] stars) {
        for (int i = 0; i < stars.length; i++) {
            int rating = i + 1; // Rating value for each star
            ToggleButton star = stars[i];
            star.setOnAction(event -> {
                updateStarSelection(stars, rating);
            });
        }
    }

    private void updateStarSelection(ToggleButton[] stars, int rating) {
        for (int i = 0; i < stars.length; i++) {
            stars[i].setSelected(i < rating);
        }
    }

    public void orderHistoryExit(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    public void orderHistoryExitButtonEntered(MouseEvent event){
        orderHistoryExit.setStyle("-fx-background-color: white;");
        orderHistoryExit.setTextFill(Paint.valueOf("red"));
    }
    public void orderHistoryExitButtonExited(MouseEvent event){
        orderHistoryExit.setStyle("-fx-background-color:  #FFFFFF;");
        orderHistoryExit.setTextFill(Paint.valueOf("Black"));
    }
}
