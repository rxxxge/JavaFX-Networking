package com.yaroslav.application;

import java.io.IOException;
import java.net.*;

public class Client {

    private final String m_Name;
    private final String m_Address;
    private final Integer m_Port;

    private DatagramSocket m_Socket;
    private InetAddress m_Ip;
    private Thread m_Send;

    public Client(String name, String address, Integer port) {
        m_Name = name;
        m_Address = address;
        m_Port = port;

        boolean connect = openConnection(m_Address);
        if (!connect) {
            System.err.println("Connection failed!");
        }

        String connection = "/c/" + m_Name;
        send(connection.getBytes());
    }

    public String getName() {
        return m_Name;
    }

    public String getAddress() {
        return m_Name;
    }

    public String getPort() {
        return m_Name;
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

}
