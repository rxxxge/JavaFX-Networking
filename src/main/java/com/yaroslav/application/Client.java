package com.yaroslav.application;

import java.io.IOException;
import java.net.*;

public class Client extends ClientInterface {

    private DatagramSocket m_Socket;
    private InetAddress m_Ip;

    private Thread m_Send;

    public Client(String name, String address, Integer port) {
        super(name, address, port);

        boolean connect = openConnection(m_Address);
        if (!connect) {
            System.err.println("Connection failed!");
        }

        createWindow();
        console("Attempting to connect to " + m_Address + ":" + m_Port + ", user: " + m_Name);
        String connection = m_Name + " connected from " + m_Address + ":" + m_Port;
        send(connection.getBytes());
    }

    private boolean openConnection(String address) {
        try {
            m_Socket = new DatagramSocket();
            m_Ip = InetAddress.getByName(address);
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

    private void send(final byte[] data) {
        m_Send = new Thread("Send") {
            public void run() {
                DatagramPacket packet = new DatagramPacket(data, data.length, m_Ip, m_Port);
                try {
                    m_Socket.send(packet);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        };
        m_Send.start();
    }

    @Override
    protected void send(String message) {
        if (message.isEmpty())
            return;
        message = m_Name + ": " + message;
        console(message);
        send(message.getBytes());
    }

}
