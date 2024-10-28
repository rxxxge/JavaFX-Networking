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
//                    System.out.println("# of clients at the server: " + clients.size());
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
//                    if (clients.get(0) != null) {
//                        System.out.println("Received and processed packet from client: ");
//                        System.out.println("\t" + clients.get(0).m_Address.toString() + ":" + clients.get(0).m_Port);
//                        System.out.println("\tIdentifier: " + clients.get(0).getID() + "\n");
//                    }
                }
            }
        };
        m_Receive.start();
    }


    private void send(String message, InetAddress address, int port) {
        message += "/e/";
        send(message.getBytes(), address, port);
    }

    private void process(DatagramPacket packet) {
        String string = new String(packet.getData(), packet.getOffset(), packet.getLength());

        if (string.startsWith("/c/")) {
            long id = UniqueIdentifier.getIdentifier();
            clients.add(new ServerClient(string.substring(3, string.length()), packet.getAddress(), packet.getPort(), id));
            System.out.println(string.substring(3, string.length()));
            String ID = "/c/" + id;
            send(ID, packet.getAddress(), packet.getPort());
        } else if (string.startsWith("/m/")) {
            sendToAll(string);
        } else if (string.startsWith("/d/")) {
            int ID = Integer.parseInt(string.split("/d/|/e/")[1]);
            disconnect(ID, true);
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

    private void disconnect(int id, boolean status) {
        ServerClient c = null;

        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getID() == id) {
                c = clients.get(i);
                clients.remove(i);
                break;
            }
        }

        if (c != null) {
            String message = "";
            if (status) {
                message = "Client " + c.m_Name + "[" + c.getID() + "] at " + c.m_Address.toString() + ":" + c.m_Port + " disconnected.";
            } else {
                message = "Client " + c.m_Name + "[" + c.getID() + "] at " + c.m_Address.toString() + ":" + c.m_Port + " timed out.";
            }
            System.out.println(message);
        }
    }

}
