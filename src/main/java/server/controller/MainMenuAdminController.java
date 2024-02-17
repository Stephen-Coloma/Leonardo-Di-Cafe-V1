package server.controller;

import javafx.application.Platform;
import org.controlsfx.control.Notifications;
import server.controller.accounts.AccountListPageController;
import server.controller.addproducts.AddProductPageController;
import server.controller.inventory.InventoryPageController;
import server.controller.orders.OrderListPageController;
import server.model.MainMenuAdminModel;
import server.model.accounts.AccountListPageModel;
import server.model.addproducts.AddProductPageModel;
import server.model.inventory.InventoryPageModel;
import server.model.listeners.AddProductPageObserver;
import server.model.listeners.InventoryPageObserver;
import server.model.listeners.OrderListPageObserver;
import server.model.orders.OrderListPageModel;
import server.view.MainMenuAdminView;
import server.view.accounts.AccountListPageView;
import server.view.addproducts.AddProductPageView;
import server.view.inventory.InventoryPageView;
import server.view.orders.OrderListPageView;

public class MainMenuAdminController implements InventoryPageObserver, OrderListPageObserver, AddProductPageObserver {
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
            OrderListPageModel orderListPageModel = new OrderListPageModel();
            orderListPageModel.setOrderList(model.getOrderList());
            orderListPageModel.registerObserver(this);

            OrderListPageView orderListPageView = OrderListPageView.loadOrderListPage(view.getBorderPane());
            OrderListPageController orderListPageController = new OrderListPageController(orderListPageModel, orderListPageView);
            orderListPageController.start();
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
            AddProductPageModel addProductPageModel = new AddProductPageModel();
            addProductPageModel.setFoodMenu(model.getFoodMenu());
            addProductPageModel.setBeverageMenu(model.getBeverageMenu());
            addProductPageModel.registerObserver(this);

            AddProductPageView addProductPageView = AddProductPageView.loadAddProductPage(view.getBorderPane());
            AddProductPageController addProductPageController = new AddProductPageController(addProductPageModel, addProductPageView);
            addProductPageController.start();
        });

        view.getViewAnalyticsButton().setOnMouseClicked(actionEvent -> {

        });
    } // end of setMainMenuButtons

    @Override
    public void notifyInventoryChanges(boolean inventoryChanges) {
        if (inventoryChanges) {
            model.setMenuChanges(true);
            model.notifyObservers("INVENTORY_CHANGE");
            model.setMenuChanges(false);
        }
    } // end of notifyInventoryChanges

    @Override
    public void notifyOrderStatusChange(boolean statusChanges) {
        if (statusChanges) {
            model.setMenuChanges(true);
            model.notifyObservers("STATUS_CHANGE");
            model.setMenuChanges(false);
        }
    } // end of notifyOrderStatusChange

    @Override
    public void notifyNewProduct(boolean newProduct, char type) {
        String code = null;
        if (newProduct) {
            model.setMenuChanges(true);
            if ('f' == type) {
                code = "NEW_FOOD_PRODUCT";
                System.out.println(model.getFoodMenu());
            } else if ('b' == type) {
                code = "NEW_BEVERAGE_PRODUCT";
                System.out.println(model.getBeverageMenu());
            }
            model.notifyObservers(code);
            model.setMenuChanges(false);
        }
    } // end of notifyNewProduct
} // end of MainMenuAdminController class
