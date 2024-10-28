package com.yaroslav.server;

import java.net.InetAddress;

public class ServerClient {

    public String m_Name;
    public InetAddress m_Address;
    public int m_Port;
    public int m_Attempt;

    private final long m_ID;

    public ServerClient(String name, InetAddress address, int port, final long ID) {
        m_Name = name;
        m_Address = address;
        m_Port = port;
        m_ID = ID;
    }

    public long getID() {
        return m_ID;
    }

}
