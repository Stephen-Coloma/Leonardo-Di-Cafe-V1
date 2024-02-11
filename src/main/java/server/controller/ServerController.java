package server.controller;

import server.model.ServerModel;
import server.view.ServerView;

import java.net.Socket;

public class ServerController {
    private ServerModel model;
    private ServerView view;
    private Socket socket;

    public ServerController(Socket socket, ServerModel model, ServerView view) {
        this.socket = socket;
        this.model = model;
        this. view = view;
    } // end of constructor

    // TODO
    public void run() {

    } // end of run
} // end of ServerController
