package client.model.fxmlmodel;


import client.Client;
import shared.Beverage;
import shared.Customer;
import shared.Food;
import util.XMLUtility;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

/*This model represents the model for login. The model will hold all the processes for the application*/
public class LoginPageModel {
    private String username;
    private String password;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Object[] serverResponse;

    /**This method tries to make connection to the server
     * @throws RuntimeException
     * @throws IOException
     * @throws ClassNotFoundException
     *
     * Exceptions will be handled by the LoginPage Controller*/
    public void authenticate(String username, String password) throws RuntimeException, IOException, ClassNotFoundException {
//        //todo: uncomment for testing purposes
        //for testing purposes only
        HashMap<String, Food> foodMenu = (HashMap<String, Food>) XMLUtility.loadXMLData(new File("src/main/java/server/model/food_menu.xml"));
        HashMap<String, Beverage> beverageMenu = (HashMap<String, Beverage>) XMLUtility.loadXMLData(new File("src/main/java/server/model/beverage_menu.xml"));
        List<Customer> customers = (List<Customer>) XMLUtility.loadXMLData(new File("src/main/java/server/model/customer_account_list.xml"));
        Customer customer = customers.get(0);

        serverResponse = new Object[]{"1111", "LOGIN_SUCCESSFUL", new Object[]{customer, foodMenu, beverageMenu}};

    //        //Guide Object[] {"client.id", "LOGIN", credentials}
    //        String clientID = String.valueOf(username.hashCode());
    //        String requestType = "LOGIN";
    //        String[] credentials = {username, password};
    //
    //
    //        socket = new Socket(Client.IP_ADDRESS, Client.PORT);
    //        out = new ObjectOutputStream(socket.getOutputStream());
    //        in  = new ObjectInputStream(socket.getInputStream());
    //
    //        sendData(clientID, requestType, credentials);
    //        Object[] response = (Object[]) in.readObject();
    //
    //
    //        //Close the connection when it is not login successful
    //        if (!response[1].equals("LOGIN_SUCCESSFUL")){ //todo: if login is successful, set the socket for the main menu client page
    //            out.close();
    //            in.close();
    //            socket.close();
    //        }
    //
    //        serverResponse = response;
    }

    /**Helper method that sends data to server*/
    private void sendData(String clientID, String requestType, Object data) throws IOException{
        Object[] request = new Object[]{clientID, requestType, data};
        out.writeObject(request);
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

    public Object[] getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(Object[] serverResponse) {
        this.serverResponse = serverResponse;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}