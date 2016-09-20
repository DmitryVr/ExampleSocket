package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
/**
 * client
 */
public class Client extends JFrame implements Runnable {

    private static Socket clientSocket;
    private static ObjectOutputStream output; // отправить
    private static ObjectInputStream input; // принять

    public Client(String title) {
        super(title);
        setLayout(new FlowLayout());
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        final JTextField tf1 = new JTextField(10);
        final JButton b1 = new JButton("Отпрвить");

        setVisible(true);


        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == b1)
                    sendData(tf1.getText());
            }
        });

        add(tf1);
        add(b1);
    }

    public static void main(String[] args) {
        new Thread(new Client("Клиент")).start();
    }

    private static void sendData(Object object) {
        try {
            output.flush();
            output.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            while (true) {
                clientSocket = new Socket(InetAddress.getByName("127.0.0.1"), 4567);
                output = new ObjectOutputStream(clientSocket.getOutputStream());
                input = new ObjectInputStream(clientSocket.getInputStream());
                JOptionPane.showMessageDialog(null, (String)input.readObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
