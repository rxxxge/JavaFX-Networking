package com.yaroslav.server;

import com.yaroslav.utils.UniqueIdentifier;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

    private final List<ServerClient> clients = new ArrayList<ServerClient>();

    private DatagramSocket m_Socket;
    private final int m_Port;
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
                    process(packet);
                    System.out.println(clients.get(0).m_Address.toString() + ":" + clients.get(0).m_Port);
                }
            }
        };
        m_Receive.start();
    }

    private void process(DatagramPacket packet) {
        String string = new String(packet.getData(), packet.getOffset(), packet.getLength());

        if (string.startsWith("/c/")) {
            // UUID id = UUID.randomUUID();
            int id = UniqueIdentifier.getIdentifier();
            clients.add(new ServerClient(string.substring(3, string.length()), packet.getAddress(), packet.getPort(), id));
            System.out.println(string.substring(3, string.length()));
        } else if (string.startsWith("/m/")) {
            sendToAll(string);
        } else {
            System.out.println(string);
        }
    }

    private void sendToAll(String message) {
        for (ServerClient client : clients) {
            send(message.getBytes(), client.m_Address, client.m_Port);
        }
    }

    private void send(byte[] data, InetAddress address, int port) {
        m_Send = new Thread("Send") {
            public void run() {
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                try {
                    m_Socket.send(packet);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        };
        m_Send.start();
    }

}
