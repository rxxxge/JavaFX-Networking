package app.window.netapplication;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Client {
    private final String m_Name;
    private final String m_Address;
    private final String m_Port;

    private Scene m_Scene;

    private TextArea m_TextArea;
    private TextField m_UserInput;
    private Button m_Button;

    public Client(String name, String address, String port) {
        m_Name = name;
        m_Address = address;
        m_Port = port;

        createWindow();
    }

    private void createWindow() {
        Stage stage = new Stage();

        m_TextArea = new TextArea();
        m_TextArea.setEditable(false);
        m_TextArea.setPrefWidth(800);

        m_UserInput = new TextField();

        m_Button = new Button();
        m_Button.setText("Send");
        m_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(m_Name + ": " + m_UserInput.getText());
                console(m_UserInput.getText());
                m_UserInput.clear();
            }
        });

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addColumn(0, m_TextArea, m_UserInput);
        grid.addRow(1, m_Button);

        m_Scene = new Scene(grid, 800, 600);

        stage.setTitle("Chat Application");
        stage.setScene(m_Scene);
        stage.show();
    }

    private void console(String message) {
        if (message.equals(""))
            return;
        m_TextArea.appendText(m_Name + ": " + message + "\n\r");
        m_UserInput.clear();
    }
}
