package app.window.netapplication;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;


public class Login extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Label userId = new Label("Name");
        final TextField userName = new TextField();

        Label address = new Label("IP Address");
        final TextField ipAddress = new TextField();
        ipAddress.setPromptText("192.168.0.1");

        Label port = new Label("Port");
        final TextField Port = new TextField();
        Port.setPromptText("6969");

        Button btn = new Button();

        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().add(btn);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);

        btn.setText("Submit");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Name: " + userName.getText());
                System.out.println("Address: " + ipAddress.getText());
                System.out.println("Port: " + Port.getText());

                String name = userName.getText();
                String address = ipAddress.getText();
                String port = Port.getText();

                // Close login window
                stage.close();

                // Open new client window with given credentials
                login(name, address, port);
            }
        });

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.addColumn(0, userId, userName);
        grid.addColumn(0, address, ipAddress);
        grid.addColumn(0, port, Port);
        grid.addColumn(0, hbBtn);

        Scene scene = new Scene(grid);
        stage.setTitle("Chat Application");
        stage.setScene(scene);
        stage.show();
    }

    private void login(String name, String address, String port) {
        new Client(name, address, port);
    }

    public static void main(String[] args) {
        launch(args);
    }


}