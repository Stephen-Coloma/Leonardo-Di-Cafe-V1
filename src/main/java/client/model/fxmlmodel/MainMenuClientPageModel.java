package client.model.fxmlmodel;

import client.controller.ClientControllerNew;
import client.controller.fxmlcontroller.MainMenuClientPageController;
import client.model.ClientModel;
import shared.Beverage;
import shared.Customer;
import shared.Food;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.HashMap;

public class MainMenuClientPageModel {
    //data to be accessed after login by the user
    private ClientModel clientModel;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public MainMenuClientPageModel(Object[] clientModelData) {
        //the serverResponse[2] responds Object[] {customer, foodMenu, beverageMenu}
        clientModel = new ClientModel();
        clientModel.setCustomer((Customer) clientModelData[0]);
        clientModel.setFoodMenu((HashMap<String, Food>) clientModelData[1]);
        clientModel.setBeverageMenu((HashMap<String, Beverage>) clientModelData[2]);
    }

    public ClientModel getClientModel() {
        return clientModel;
    }

    public void setClientModel(ClientModel clientModel) {
        this.clientModel = clientModel;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }
}