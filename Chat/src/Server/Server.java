package Server;

import java.io.IOException; //Библиотека для обработки исключений в коде
import java.net.ServerSocket; //Библиотека для обработки сокета сервера
import java.net.Socket; //Библиотека для обработки сокета
import java.util.ArrayList; //Библиотека обработки массивов

/*
Класс, который отвечаает за логику сервера
 */

public class Server {
    static final int PORT = 3443; //Константа - Порт, который будеь прослушивать наш сервер
    private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>(); //Список подключенных клиентов
    public Server() {
        Socket clientSocket = null; //Сокет клиента
        ServerSocket serverSocket = null; //Сокет сервера
        try {                                       //Обработка исключений
            serverSocket = new ServerSocket(PORT);  //Серверный сокет с определенным портом
            System.out.println("Сервер запущен!");  //Надпись, что сервер запущен
            while (true) {                          //Цикл, который запускай бесконечный цикл, чтобы был запущен сервер
                clientSocket = serverSocket.accept();  //метод accept, ждем подключений клиентов
                ClientHandler client = new ClientHandler(clientSocket, this); //Объект класса - обработчика клиента,
                                                                              //подключенного к серверу
                clients.add(client); //Добавляем в список нового клиента
                new Thread(client).start(); //Запускаем клиент в новом окне
            }
        }
        catch (IOException ex) {   //Обработка исключения
            ex.printStackTrace();
        }
        finally {     //Обработка неопределенного исключения
            try {
                clientSocket.close();  //Завершение работы сокета клиента
                System.out.println("Сервер остановлен");  //Надпись, что сервер закрыт
                serverSocket.close();  //Завершение работы сокета сервера
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendMessageToAllClients(String msg) {   // отправляем сообщение всем клиентам
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }

    public void removeClient(ClientHandler client) {     // удаляем клиента из коллекции при выходе из чата
        clients.remove(client);
    }
}

