package com.yaroslav.server;

public class ServerMain {

    private int m_Port;
    private Server m_Server;

    public ServerMain(int port) {
        m_Port = port;
        m_Server = new Server(port);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar NetApp.jar [port]");
            return;
        }

        int port = Integer.parseInt(args[0]);
        new ServerMain(port);
    }

}
