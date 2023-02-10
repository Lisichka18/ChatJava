package Server;

import java.io.IOException;
import java.net.Socket;
import java.io.PrintWriter;


public class ClientHandler{
    private Server server;
    private static final String HOST = "localhost";
    private static final int PORT = 5555;
    private PrintWriter outMessage;
    private Socket clientSocket = null;
    public ClientHandler(Socket socket, Server server) {
        this.server = server;
    }
    public void run(){
        server.sendMessageClients("Client connected to server");
    }
    public void close(){
        server.removeClient(this);
    }

    public void sendMsg(String msg) {
        try {
            outMessage.println(msg);
            outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}