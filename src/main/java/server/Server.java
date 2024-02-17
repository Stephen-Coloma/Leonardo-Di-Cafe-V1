package server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import server.controller.ServerController;
import server.model.ServerModel;
import server.view.ServerView;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;


public class Server extends Application {
    private static final int PORT = 2000;
    private static final int THREAD_POOL_SIZE = 20;
    private static ServerModel model;
    private static ServerView view;
    private static ServerController controller;

    public static void main(String[] args) {
        launch(args);
    } // end of main

    @Override
    public void start(Stage stage) {
        model = new ServerModel();
        view = new ServerView(stage);
        view.runInterface();
        controller = new ServerController(model, view);

        new Thread(() -> {
            try {
                startServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    } // end of start

    private void startServer() throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server listening on port " + PORT);

            ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

            while (true) {
                Socket client = server.accept();
                System.out.println("Client connected");
                controller.setClientSocket(client);
                executorService.submit(controller::run);
            }
        }
    }
} // end of Server class
