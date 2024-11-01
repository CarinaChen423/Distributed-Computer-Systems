package Lab5;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Lab5.TCPMultithreading;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *
 * @author Dell
 */

public class TCPClient {

        public static void main(String[] args) {
            try {
                InetAddress address = InetAddress.getByName("localhost");
                try (Socket socket = new Socket(address, TCPMultithreading.PORT)) {
                    System.out.println("Client connected on port " + socket.getLocalPort());

                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));

                    ExecutorService executorService = Executors.newFixedThreadPool(2);

                    // Thread for sending messages to server
                    executorService.submit(() -> {
                        String userInput;
                        try {
                            while ((userInput = keyboardInput.readLine()) != null) {
                                out.println(userInput);
                            }
                        } catch (IOException e) {
                            System.err.println("Error reading user input: " + e.getMessage());
                        }
                    });
                    // Thread for receiving messages from server
                    executorService.submit(() -> {
                        String serverMessage;
                        try {
                            while ((serverMessage = in.readLine()) != null) {
                                System.out.println(serverMessage);
                            }
                        } catch (IOException e) {
                            System.err.println("Error reading server message: " + e.getMessage());
                        }
                    });
                }
            } catch (IOException e) {
                System.err.println("Client error: " + e.getMessage());
            }
        }
    }
