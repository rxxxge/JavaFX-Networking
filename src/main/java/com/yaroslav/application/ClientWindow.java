package com.yaroslav.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientWindow implements Runnable {

    private TextArea m_TextArea;
    private TextField m_UserInput;
    private Button m_Button;

    private Thread m_Run, m_Listen;
    private Client m_Client;

    private boolean m_Running;

    public ClientWindow(String name, String address, Integer port) {
        m_Client = new Client(name, address, port);

        boolean connect = m_Client.openConnection(m_Client.getAddress());
        if (!connect) {
            System.err.println("Connection failed!");
        }

        createWindow();
        console("Attempting to connect to " + m_Client.getAddress() + ": " + m_Client.getPort() + ", user: " + m_Client.getName());
        String connection = "/c/" + m_Client.getName();
        m_Client.send(connection.getBytes());
        m_Run = new Thread(this, "Running");
        m_Running = true;
        m_Run.start();
    }

    private void createWindow() {
        Stage clientStage = new Stage();

        // Chat area
        m_TextArea = new TextArea();
        m_TextArea.setEditable(false);
        m_TextArea.setWrapText(true);
        m_TextArea.setPrefWidth(800);
        m_TextArea.setPrefHeight(600);
        m_TextArea.addEventFilter(KeyEvent.ANY, event -> m_UserInput.requestFocus());

        // Send Button
        m_Button = new Button();
        m_Button.setText("Send");
        m_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!m_UserInput.getText().isEmpty())
                    System.out.println(m_Client.getName() + ": " + m_UserInput.getText());
                send(m_UserInput.getText(), true);
                m_UserInput.clear();
            }
        });
        m_Button.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> m_UserInput.requestFocus());
        m_Button.addEventFilter(KeyEvent.ANY, event -> m_UserInput.requestFocus());

        // Window close event handle
        clientStage.setOnHiding(windowEvent -> {
            console(m_Client.getName() + " disconnected!");
            String disconnectMessage = "/d/" + m_Client.getID() + "/e/";
            send(disconnectMessage, false);
            m_Running = false;
//            System.out.println("CLOSE SOCKET HERE!");
            m_Client.disconnect();
        });

        // Field for user input
        m_UserInput = new TextField();

        m_UserInput.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                m_Button.fire();
            }
        });

        HBox hBox = new HBox(10, m_UserInput, m_Button);
        HBox.setHgrow(m_UserInput, Priority.ALWAYS);
        hBox.setPadding(new Insets(10, 0, 0, 0));

        VBox vbox = new VBox(m_TextArea, hBox);
        VBox.setVgrow(m_TextArea, Priority.ALWAYS);
        vbox.setPadding(new Insets(10));

        Scene m_Scene = new Scene(vbox);

        clientStage.setTitle("Chat Application");
        clientStage.setScene(m_Scene);
        clientStage.show();

        m_UserInput.requestFocus();
    }

    public void run() {
        listen();
    }

    public void listen() {
        m_Listen = new Thread("Listen") {
            public void run() {
                while (m_Running) {
                    String message = m_Client.receive();
                    if (message.startsWith("/c/")) {
                        m_Client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
                        console("Successfully connected to server! ID: " + m_Client.getID());
                    } else if (message.startsWith("/m/")) {
                        String text = message.split("/m/|/e/")[1];
                        console(text);
                    }
                }
            }
        };

        m_Listen.start();
    }

    private void console(String message) {
        m_TextArea.appendText(message + "\n\r");
    }

    private void send(String message, boolean userMessage) {
        if (message.isEmpty())
            return;
        if (userMessage) {
            message = m_Client.getName() + ": " + message;
            message = "/m/" + message;
        }
        m_Client.send(message.getBytes());
    }

}
