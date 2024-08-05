package com.yaroslav.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server implements Runnable {

    private DatagramSocket m_Socket;
    private int m_Port;
    private boolean m_Running = true;
    private Thread m_Run, m_Manage, m_Send, m_Receive;

    public Server(int port) {
        m_Port = port;
        try {
            m_Socket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.err.println(e.getMessage());
            return;
        }
        m_Run = new Thread(this, "Server");
        m_Run.start();
    }

    public void run() {
        m_Running = true;

        System.out.println("Server started on port " + m_Port);

        manageClients();
        receive();
    }

    private void manageClients() {
        m_Manage = new Thread("Manage") {
            public void run() {
                while (m_Running) {
                    // Managing
                }
            }
        };
        m_Manage.start();
    }

    private void receive() {
        m_Receive = new Thread("Receive") {
            public void run() {
                while (m_Running) {
                    byte[] data = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    try {
                        m_Socket.receive(packet);
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                    String string = new String(packet.getData());
                    System.out.println(string);
                }
            }
        };
        m_Receive.start();
    }

}
