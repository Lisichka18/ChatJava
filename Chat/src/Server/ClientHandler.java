package Server;

import java.io.IOException; //Библиотека для обработки исключений в коде
import java.io.PrintWriter; //Библиотека для обработки сообщений
import java.net.Socket; //Библиотека для обработки сокета
import java.util.ArrayList; //Библиотека обработки массивов
import java.util.Scanner; //Бтблиотека для обработки данных с консоли

/*
Класс, который отвечаает за обработку подключения клиента к серверу
 */

public class ClientHandler implements Runnable {  //создаем класс, и реализуем интерфес через ключевое слово и класса Runnable
    private Server server;  //Экземпляр класса
    private PrintWriter outMessage;   //Исходящие сообщение
    private Scanner inMessage;  //Входящие сообщение
    private static final String HOST = "localhost"; //Константа хоста
    private static final int PORT = 3443; //Константа порта
    private Socket clientSocket = null; //Сокет клиента

    public ClientHandler(Socket socket, Server server) { //конструктор, который принимает клиентский сокет и сервер
        try {
            this.server = server; //Присваем переменной
            this.clientSocket = socket; //Присваем переменной
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {   //Запускаем бесконечный цикл, чтобы было запущено постоянно
                if (inMessage.hasNext()) {   //Проверяем, если пришло сообщение
                    String clientMessage = inMessage.nextLine();
                    if (clientMessage.equalsIgnoreCase("##session##end##")) {  //Если пришло данное сообщение,
                                                                                          //то цикл прерывается,
                                                                                          //и клиент выходит из чата
                        break;
                    }
                    System.out.println(clientMessage); //Выводим сообщение в консоль
                    server.sendMessageToAllClients(clientMessage); //Выводим сообщение в чат
                }
                Thread.sleep(100);
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        finally {
            this.close();
        }
    }

    public void sendMsg(String msg) { // отправляем сообщение
        try {
            outMessage.println(msg);
            outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void close() { // клиент выходит из чата
        server.removeClient(this);
    }
}
