package server.controller;

import javafx.application.Platform;
import server.controller.accounts.AccountListPageController;
import server.controller.inventory.InventoryPageController;
import server.model.MainMenuAdminModel;
import server.model.accounts.AccountInformationPopupModel;
import server.model.accounts.AccountListPageModel;
import server.model.inventory.InventoryPageModel;
import server.model.listeners.InventoryPageObserver;
import server.view.MainMenuAdminView;
import server.view.accounts.AccountListPageView;
import server.view.inventory.InventoryPageView;

public class MainMenuAdminController implements InventoryPageObserver {
    private final MainMenuAdminModel model;
    private final MainMenuAdminView view;

    public MainMenuAdminController(MainMenuAdminModel model, MainMenuAdminView view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        setMainMenuButtons();
    } // end of start

    public void setMainMenuButtons() {

        view.getHomeButton().setOnMouseClicked(actionEvent -> view.returnToHomePage());

        view.getViewOrderButton().setOnMouseClicked(actionEvent -> {

        });

        view.getViewAccountsButton().setOnMouseClicked(actionEvent -> {
            AccountListPageModel accountListPageModel = new AccountListPageModel();
            accountListPageModel.setCustomerAccountList(model.getCustomerAccountList());

            AccountListPageView accountListPageView = AccountListPageView.loadAccountListPage(view.getBorderPane());
            AccountListPageController accountListPageController = new AccountListPageController(accountListPageModel, accountListPageView);
            accountListPageController.start();
        });

        view.getViewInventoryButton().setOnMouseClicked(actionEvent -> Platform.runLater(() -> {
            InventoryPageModel inventoryPageModel = new InventoryPageModel();
            inventoryPageModel.setFoodList(model.getFoodMenu());
            inventoryPageModel.setBeverageList(model.getBeverageMenu());
            inventoryPageModel.registerObserver(this);

            InventoryPageView inventoryPageView = InventoryPageView.loadInventoryPage(view.getBorderPane());
            InventoryPageController inventoryPageController = new InventoryPageController(inventoryPageModel, inventoryPageView);
            inventoryPageController.start();
        }));

        view.getAddProductsPageButton().setOnMouseClicked(actionEvent -> {

        });

        view.getViewAnalyticsButton().setOnMouseClicked(actionEvent -> {

        });
    } // end of setMainMenuButtons

    @Override
    public void notifyInventoryChanges(boolean inventoryChanges) {
        if (inventoryChanges) {
            model.setMenuChanges(true);
            model.notifyObservers();
            model.setMenuChanges(false);
        }
    } // end of notifyInventoryChanges
} // end of MainMenuAdminController class
