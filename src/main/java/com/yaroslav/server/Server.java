package com.yaroslav.server;

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
        }
        m_Run = new Thread(this, "Server");
    }

    public void run() {
        m_Running = true;
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
                    
                }
            }
        };
        m_Receive.start();
    }

}
