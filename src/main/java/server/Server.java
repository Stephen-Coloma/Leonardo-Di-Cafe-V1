package server;

import javafx.application.Application;
import javafx.stage.Stage;
import server.controller.ServerController;
import server.model.ServerModel;
import server.view.ServerView;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server extends Application {
    private static final int PORT = 2000;
    private static final int THREAD_POOL_SIZE = 20;
    private static final int BROADCAST_PORT = 12345;
    private static ServerController controller;

    public static void main(String[] args) {
        launch(args);
    } // end of main

    @Override
    public void start(Stage stage) {
        ServerModel model = new ServerModel();
        ServerView view = new ServerView(stage);
        view.runInterface();
        controller = new ServerController(model, view);

        // launch the server
        startServer();

        // broadcast the server to all machines connected in the local network
        startBroadcastingServer();
    } // end of start

    private void startServer() {
        new Thread(() -> {
            try (ServerSocket server = new ServerSocket(PORT);
                 ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE)) {
                System.out.println("Server listening on port " + PORT);

                while (true) {
                    Socket client = server.accept();
                    System.out.println("Client connected: " + client.getInetAddress().getHostAddress());
                    controller.setClientSocket(client);
                    executorService.submit(controller::run);
                }
            } catch (IOException e) {
                System.err.println("Error during server launch: " + e.getMessage());
            }
        }).start();
    } // end of startServer

    /**
     * Starts broadcasting the server's presence to all machines connected in the local network.
     */
    private void startBroadcastingServer() {
        new Thread(() -> {
            try (DatagramSocket broadcastSocket = new DatagramSocket(BROADCAST_PORT)) {
                System.out.println("Server broadcasting its presence on port " + BROADCAST_PORT);

                while (true) {
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    broadcastSocket.receive(receivePacket);
                    System.out.println("Connection request received from: " + receivePacket.getAddress());

                    String serverIP = InetAddress.getLocalHost().getHostAddress();
                    byte[] sendData = serverIP.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                    broadcastSocket.send(sendPacket);
                }
            } catch (IOException e) {
                System.err.println("Error during broadcast startup: " + e.getMessage());
            }
        }).start();
    } // end of startBroadcastingServer
} // end of Server class
