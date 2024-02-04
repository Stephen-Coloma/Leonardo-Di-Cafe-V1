module LeonardoDiCafe {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens client to javafx.fxml;
    opens server to javafx.fxml;

    exports client;
    exports server;
}