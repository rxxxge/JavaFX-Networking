package com.yaroslav.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;

public class LoginInterface {

    protected TextField userName, ipAddress, Port;
    protected Label userLabel, addressLabel, portLabel;
    protected Label errorLabel;
    protected Button submitButton;

    public LoginInterface() {
        Init();
    }

    public void Init() {
        userLabel = new Label("Name");
        userName = new TextField();

        addressLabel = new Label("IP Address");
        ipAddress = new TextField();
        ipAddress.setPromptText("192.168.0.1");

        // Accept only integers in port field
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?\\d*"))
                return change;
            return null;
        };

        portLabel = new Label("Port");
        Port = new TextField();
        Port.setPromptText("6969");
        TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter(), null, filter);
        Port.setTextFormatter(formatter);

        submitButton = new Button();
        submitButton.setText("Submit");

        errorLabel = new Label("");

        setFieldKeyEvents();
    }

    public void setupSubmitButton(Stage stage) {
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!validateCredentials())
                    return;
                System.out.println("New client joined");
                System.out.println("Name: " + userName.getText());
                System.out.println("Address: " + ipAddress.getText());
                System.out.println("Port: " + Port.getText() + "\n");

                String name = userName.getText();
                String address = ipAddress.getText();
                Integer port = Integer.valueOf(Port.getText());

                // Close login window
                stage.close();

                // Open new client window with given credentials
                loginClient(name, address, port);
            }
        });
    }

    public GridPane configureInterface() {
        VBox vBox = new VBox(10, submitButton, errorLabel);
        vBox.setAlignment(Pos.BOTTOM_CENTER);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.addColumn(0, userLabel, userName);
        grid.addColumn(0, addressLabel, ipAddress);
        grid.addColumn(0, portLabel, Port);
        grid.addColumn(0, vBox);

        return grid;
    }

    private void loginClient(String name, String address, Integer port) {
        new Client(name, address, port);
    }

    private void setFieldKeyEvents() {
        userName.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                submitButton.fire();
            }
        });

        ipAddress.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                submitButton.fire();
            }
        });

        Port.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                submitButton.fire();
            }
        });
    }


    private boolean validateCredentials() {
        if (userName.getText().isEmpty() ||
                ipAddress.getText().isEmpty() ||
                Port.getText().isEmpty())
        {
            errorLabel.setText("Fill empty fields!");
            return false;
        }

        return true;
    }

}
