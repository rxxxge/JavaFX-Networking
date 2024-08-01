module app.window.netapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens app.window.netapplication to javafx.fxml;
    exports app.window.netapplication;
}