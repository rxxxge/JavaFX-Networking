package app.window.netapplication;

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

import java.io.IOException;
import java.net.*;


public class Client {
    private final String m_Name;
    private final String m_Address;
    private final Integer m_Port;

    private Scene m_Scene;

    private TextArea m_TextArea;
    private TextField m_UserInput;
    private Button m_Button;

    private DatagramSocket m_Socket;
    private InetAddress ip;

    public Client(String name, String address, Integer port) {
        m_Name = name;
        m_Address = address;
        m_Port = port;

        boolean connect = openConnection(address, port);
        if (!connect) {
            System.err.println("Connection failed!");
        }
        try {
            createWindow();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean openConnection(String address, int port) {
        try {
            m_Socket = new DatagramSocket(port);
            ip = InetAddress.getByName(address);
        } catch (UnknownHostException | SocketException e) {
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }

    private String receive() {
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);

        try {
            m_Socket.receive(packet);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return new String(packet.getData());
    }

    private void createWindow() throws IOException {
        Stage clientStage = new Stage();

        // Chat area
        m_TextArea = new TextArea();
        m_TextArea.setEditable(false);
        m_TextArea.setWrapText(true);
        m_TextArea.setPrefWidth(800);
        m_TextArea.setPrefHeight(600);
        m_TextArea.addEventFilter(KeyEvent.ANY, event -> m_UserInput.requestFocus());

        // Button
        m_Button = new Button();
        m_Button.setText("Send");
        m_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!m_UserInput.getText().isEmpty())
                    System.out.println(m_Name + ": " + m_UserInput.getText());
                send(m_UserInput.getText());
                m_UserInput.clear();
            }
        });
        m_Button.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> m_UserInput.requestFocus());
        m_Button.addEventFilter(KeyEvent.ANY, event -> m_UserInput.requestFocus());

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

        m_Scene = new Scene(vbox);

        clientStage.setTitle("Chat Application");
        clientStage.setScene(m_Scene);
        clientStage.show();

        m_UserInput.requestFocus();
    }

    private void send(String message) {
        if (message.isEmpty())
            return;
        m_TextArea.appendText(m_Name + ": " + message + "\n\r");
    }
}
