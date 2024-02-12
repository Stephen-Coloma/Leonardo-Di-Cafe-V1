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

    public static void main(String[] args) {
       launch(args);
    } // end of main

    @Override
    public void start(Stage stage) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server listening on port " + PORT);

            ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

            ServerModel model = new ServerModel();
            ServerView view = new ServerView(stage);
            view.runInterface();
            ServerController controller = new ServerController(model, view);


            while (view.UIExit()) {
                Socket client = server.accept();
                controller.setClientSocket(client);
                executorService.submit(controller::run);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    } // end of start
} // end of Server class
