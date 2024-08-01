package app.window.netapplication;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Client {
    public Client(String name, String address, String port) {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 800, 600);
        stage.setTitle("Chat Application");
        stage.setScene(scene);
        stage.show();
    }
}
