package areacalculateapp;

import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Amir Zamoura
 */
public class Client extends JFrame {

    private JTextField ClientTextField = new JTextField();
    private JTextArea ClientTextArea = new JTextArea();
    private DataInputStream AreaFromServer;
    private DataOutputStream RadiusToServer;

    public static void main(String[] args) {
        new Client();
    }

    public Client() {
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        jp.add(new JLabel("Please Enter Radius: "), BorderLayout.WEST);

        jp.add(ClientTextField, BorderLayout.CENTER);
        ClientTextField.setHorizontalAlignment(JTextField.RIGHT);
        setLayout(new BorderLayout());
        add(jp, BorderLayout.NORTH);
        add(new JScrollPane(ClientTextArea), BorderLayout.CENTER);
        ClientTextField.addActionListener(new JTextFieldListener());
        setTitle("Client Machine");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
                ClientTextArea.setEditable(false);

        try {

            Socket c_socket = new Socket("localhost", 1024);

            AreaFromServer = new DataInputStream(c_socket.getInputStream());
            RadiusToServer = new DataOutputStream(c_socket.getOutputStream());

        } catch (IOException e) {
            ClientTextArea.append(e.toString() + "\n");

        }

    }

    private class JTextFieldListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double radius = Double.parseDouble(ClientTextField.getText().trim());

                RadiusToServer.writeDouble(radius);
                RadiusToServer.flush();

                double area = AreaFromServer.readDouble();

                ClientTextArea.append("Radius is: " + radius + "\n");
                ClientTextArea.append("Area is recived from server is: " + area + "\n");

            } catch (IOException e1) {
                System.err.println(e1);
            }
        }

    }
}
