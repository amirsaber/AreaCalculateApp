package areacalculateapp;

import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Amir Zamoura
 */
public class Server extends JFrame {
    
    private JTextArea ServerTextArea = new JTextArea();
    
    public static void main(String[] args) {
        new Server();
    }
    
    public Server() {
        
        setLayout(new BorderLayout());
        add(new JScrollPane(ServerTextArea), BorderLayout.CENTER);
        setTitle("Server Machine");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        ServerTextArea.setEditable(false);
        try {
            
            ServerSocket server_socket = new ServerSocket(1024);
            ServerTextArea.append("Server started at " + new Date() + "\n");
            Socket s_socket = server_socket.accept();
            
            DataInputStream ClientInput = new DataInputStream(s_socket.getInputStream());
            DataOutputStream ServerOut = new DataOutputStream(s_socket.getOutputStream());
            
            while (true) {
                double radius = ClientInput.readDouble();
                double area = radius * radius * Math.PI;
                
                ServerOut.writeDouble(area);
                
                ServerTextArea.append("Radius which recived from client is: " + radius + "\n");
                ServerTextArea.append("The area is: " + area + "\n");
                
            }
            
        } catch (IOException e) {
            
            System.err.println(e);
        }
        
    }
}
