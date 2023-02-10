package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    static final int PORT = 5555;
    public Server() throws IOException {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started");
            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, this);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
                clientSocket.close();
                System.out.println("Server close");
                serverSocket.close();
        }
    }

    public void sendMessageClients(String clientConnectedToServer) {
        client.sendMsg(msg);
    }

    public void removeClient(ClientHandler client) {
        client.remove(client);
    }
}
