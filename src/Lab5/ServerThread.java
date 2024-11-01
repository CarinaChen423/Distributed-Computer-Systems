package Lab5;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 *
 * @author Peter
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerThread implements Runnable {
    private final Socket clientSocket;

    public ServerThread(Socket socket) {
        this.clientSocket = socket;
        System.out.println("Server Thread for client " + socket.getPort() + " has started!");
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            ExecutorService executorService = Executors.newFixedThreadPool(2);

            // Thread for sending messages to client
            executorService.submit(() -> {
                try (BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in))) {
                    String serverMessage;
                    while ((serverMessage = keyboardInput.readLine()) != null) {
                        out.println("Server: " + serverMessage);
                    }
                } catch (IOException e) {
                    System.err.println("Error reading from server input: " + e.getMessage());
                }
            });

            // Thread for receiving messages from client
            executorService.submit(() -> {
                String clientMessage;
                try {
                    while ((clientMessage = in.readLine()) != null) {
                        System.out.println("Client: " + clientMessage);
                    }
                } catch (IOException e) {
                    System.err.println("Error reading client message: " + e.getMessage());
                }
            });
        } catch (IOException e) {
            System.err.println("Server Thread I/O error: " + e.getMessage());
        }
    }
}
