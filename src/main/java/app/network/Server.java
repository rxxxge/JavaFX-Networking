package app.network;

import java.io.*;
import java.net.*;

public class Server {
    // Class interface
    public Server(int port) throws IOException {
        try {
            m_Client = new Client("localhost", port);
            m_ServerSocket = new ServerSocket(port);
            m_Socket = m_ServerSocket.accept();
            m_DIStream = new DataInputStream(m_Socket.getInputStream());
        } catch (IOException e) {
            // TODO: Add logging
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String readMessage() throws IOException {
        try {
            m_Client.writeMessage("hallo! :3 AMERICA YAAA");
            String message = m_DIStream.readUTF();
            return message;
        } catch (IOException e) {
            // TODO: Add logging
            throw new RuntimeException(e.getMessage());
        }
    }

    public void clean() throws IOException {
        try {
            m_ServerSocket.close();
            m_Client.clean();
        } catch (IOException e) {
            // TODO: Add logging
            throw new RuntimeException(e.getMessage());
        }
    }

    // Private, for class implementation purposes
    private ServerSocket m_ServerSocket;
    private Socket m_Socket;
    private static DataInputStream m_DIStream;

    private static Client m_Client;
}