package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * server
 */
public class Server implements Runnable {

    private static ServerSocket serverSocket;
    private static Socket socket;
    private static ObjectOutputStream output; // отправить
    private static ObjectInputStream input; // принять

    public static void main(String[] args) {
        new Thread(new Server()).start();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(4567, 1000000);
            while (true) {
                socket = serverSocket.accept();
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
                output.writeObject("Вы прислали:" + (String)input.readObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
