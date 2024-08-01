package app.network;

import java.io.*;
import java.net.*;

public class Client {

    public Client(String address, int port) throws IOException {
        try {
            m_Socket = new Socket(address, port);
            m_DOStream = new DataOutputStream(m_Socket.getOutputStream());
        } catch (IOException e) {
            // TODO: Add logging
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void writeMessage(String message) {
        try {
            m_DOStream.writeUTF(message);
            m_DOStream.flush();
        } catch (IOException e) {
            // TODO: Add logging
            throw new RuntimeException(e.getMessage());
        }
    }

    public void clean() throws IOException {
        try {
            m_DOStream.close();
            m_Socket.close();
        } catch (IOException e) {
            // TODO: Add logging
            throw new RuntimeException(e.getMessage());
        }
    }

    private Socket m_Socket;
    private static DataOutputStream m_DOStream;
}
