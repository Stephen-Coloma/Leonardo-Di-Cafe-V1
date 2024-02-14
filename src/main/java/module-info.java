module LeonardoDiCafe {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.logging;
    requires java.xml;

    opens client to javafx.fxml;
    opens server to javafx.fxml;
    opens client.controller to javafx.fxml;
    opens server.view to javafx.fxml;
    opens server.view.fxmlcontroller to javafx.fxml;
    opens client.view to javafx.fxml;

    exports server.view.fxmlcontroller to javafx.fxml;
    exports client;
    exports server;
    exports client.controller;
    exports client.view;
    exports client.view.fxmlcontroller;
    opens client.view.fxmlcontroller to javafx.fxml;
    exports server.view;
    exports server.view.fxmlcontroller.inventory to javafx.fxml;
    opens server.view.fxmlcontroller.inventory to javafx.fxml;
}