module LeonardoDiCafe {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.logging;
    requires java.xml;

    opens client to javafx.fxml;
    opens server to javafx.fxml;
    opens server.view to javafx.fxml;

    exports client;
    exports server;
    exports server.view;
    exports server.view.inventory to javafx.fxml;
    exports server.controller.temporarycontroller;
    opens server.view.inventory to javafx.fxml;
    opens server.controller.temporarycontroller to javafx.fxml;
    exports server.view.accounts;
    opens server.view.accounts to javafx.fxml;
    exports server.view.orders;
    opens server.view.orders to javafx.fxml;
}