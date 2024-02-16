package server.model;

import server.model.listeners.MainMenuAdminObserver;
import shared.Beverage;
import shared.Food;
import util.XMLUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainMenuAdminModel {
    private final AtomicBoolean menuChanges = new AtomicBoolean(false);
    private final List<MainMenuAdminObserver> observers = new ArrayList<>();
    private HashMap<String, Food> foodMenu = (HashMap<String, Food>) XMLUtility.loadXMLData(new File("src/main/java/server/model/food_menu.xml"));
    private HashMap<String, Beverage> beverageMenu = (HashMap<String, Beverage>) XMLUtility.loadXMLData(new File("src/main/java/server/model/beverage_menu.xml"));

    public void setFoodMenu(HashMap<String, Food> foodMenu) {
        this.foodMenu = foodMenu;
    }

    public void setBeverageMenu(HashMap<String, Beverage> beverageMenu) {
        this.beverageMenu = beverageMenu;
    }

    public void setMenuChanges(boolean value) {
        menuChanges.set(value);
    }

    public void registerObserver(MainMenuAdminObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (MainMenuAdminObserver observer : observers) {
            observer.notifyMenuChanges(menuChanges.get());
        }
    }

    public HashMap<String, Food> getFoodMenu() {
        return foodMenu;
    }

    public HashMap<String, Beverage> getBeverageMenu() {
        return beverageMenu;
    }
} // end of MainMenuAdminModel class
