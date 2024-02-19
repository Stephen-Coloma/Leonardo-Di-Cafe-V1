package client.view.fxmlview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class OrderHistoryCardView {

    @FXML
    private Label productDescriptionLabel;
    @FXML
    private Label typeLabel;

    @FXML
    private Pane orderHistoryCard;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productNameLabel;

    @FXML
    private ToggleButton star1;

    @FXML
    private ToggleButton star2;

    @FXML
    private ToggleButton star3;

    @FXML
    private ToggleButton star4;

    @FXML
    private ToggleButton star5;

    @FXML
    private HBox starRating;

    public void setActionStar1(){
        star1.setOnAction((EventHandler<ActionEvent>) event ->{
            resetStars();
            star1.setSelected(true);
        });
    }
    public void setActionStar2(){
        star2.setOnAction((EventHandler<ActionEvent>) event ->{
            resetStars();
            star1.setSelected(true);
            star2.setSelected(true);
        });
    }
    public void setActionStar3(){
        star3.setOnAction((EventHandler<ActionEvent>) event ->{
            resetStars();
            star1.setSelected(true);
            star2.setSelected(true);
            star3.setSelected(true);
        });
    }
    public void setActionStar4(){
        star4.setOnAction((EventHandler<ActionEvent>) event ->{
            resetStars();
            star1.setSelected(true);
            star2.setSelected(true);
            star3.setSelected(true);
            star4.setSelected(true);
        });
    }
    public void setActionStar5(){
        star5.setOnAction((EventHandler<ActionEvent>) event ->{
            resetStars();
            star1.setSelected(true);
            star2.setSelected(true);
            star3.setSelected(true);
            star4.setSelected(true);
            star5.setSelected(true);
        });
    }

    /**Resets the stars*/
    private void resetStars() {
        star1.setSelected(false);
        star2.setSelected(false);
        star3.setSelected(false);
        star4.setSelected(false);
        star5.setSelected(false);
    }

    public Pane getOrderHistoryCard() {
        return orderHistoryCard;
    }

    public void setOrderHistoryCard(Pane orderHistoryCard) {
        this.orderHistoryCard = orderHistoryCard;
    }

    public ImageView getProductImage() {
        return productImage;
    }

    public void setProductImage(ImageView productImage) {
        this.productImage = productImage;
    }

    public Label getProductNameLabel() {
        return productNameLabel;
    }

    public void setProductNameLabel(Label productNameLabel) {
        this.productNameLabel = productNameLabel;
    }

    public ToggleButton getStar1() {
        return star1;
    }

    public void setStar1(ToggleButton star1) {
        this.star1 = star1;
    }

    public ToggleButton getStar2() {
        return star2;
    }

    public void setStar2(ToggleButton star2) {
        this.star2 = star2;
    }

    public ToggleButton getStar3() {
        return star3;
    }

    public void setStar3(ToggleButton star3) {
        this.star3 = star3;
    }

    public ToggleButton getStar4() {
        return star4;
    }

    public void setStar4(ToggleButton star4) {
        this.star4 = star4;
    }

    public ToggleButton getStar5() {
        return star5;
    }

    public void setStar5(ToggleButton star5) {
        this.star5 = star5;
    }

    public HBox getStarRating() {
        return starRating;
    }

    public void setStarRating(HBox starRating) {
        this.starRating = starRating;
    }

    public Label getProductDescriptionLabel() {
        return productDescriptionLabel;
    }

    public void setProductDescriptionLabel(Label productDescriptionLabel) {
        this.productDescriptionLabel = productDescriptionLabel;
    }

    public Label getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(Label typeLabel) {
        this.typeLabel = typeLabel;
    }
}