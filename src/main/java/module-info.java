module app.window.netapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.yaroslav.application to javafx.fxml;
    exports com.yaroslav.application;
}