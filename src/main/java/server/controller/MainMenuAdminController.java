package server.controller;

import javafx.application.Platform;
import server.controller.inventory.InventoryPageController;
import server.model.MainMenuAdminModel;
import server.model.ServerModel;
import server.model.inventory.InventoryPageModel;
import server.view.MainMenuAdminView;
import server.view.inventory.InventoryPageView;

public class MainMenuAdminController {
    private final ServerModel serverModel;
    private final MainMenuAdminModel model;
    private final MainMenuAdminView view;

    public MainMenuAdminController(ServerModel serverModel, MainMenuAdminModel model, MainMenuAdminView view) {
        this.serverModel = serverModel;
        this.model = model;
        this.view = view;
    }

    public void start() {
        setMainMenuButtons();
    }

    public void setMainMenuButtons() {

        view.getHomeButton().setOnMouseClicked(actionEvent -> view.returnToHomePage());

        view.getViewOrderButton().setOnMouseClicked(actionEvent -> {

        });

        view.getViewAccountsButton().setOnMouseClicked(actionEvent -> {

        });

        view.getViewInventoryButton().setOnMouseClicked(actionEvent -> Platform.runLater(() -> {
            InventoryPageModel inventoryPageModel = new InventoryPageModel();
            InventoryPageView inventoryPageView = InventoryPageView.loadInventoryPage(view.getBorderPane());
            InventoryPageController inventoryPageController = new InventoryPageController(model, inventoryPageModel, inventoryPageView);
            inventoryPageController.start();
        }));

        view.getAddProductsPageButton().setOnMouseClicked(actionEvent -> {

        });

        view.getViewAnalyticsButton().setOnMouseClicked(actionEvent -> {

        });
    } // end of setMainMenuButtons
} // end of MainMenuAdminController class
