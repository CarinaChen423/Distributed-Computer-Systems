package Lab4;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UDPClient {
    private static final String SERVER_ADDRESS = "localhost"; // Replace with server IP if needed
    private static final int SERVER_PORT = 9876;
    private static final String TERMINATION_MESSAGE = "END";

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverIP = InetAddress.getByName(SERVER_ADDRESS);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Receive initial message from the server
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String serverMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Server: " + serverMessage);

                if (serverMessage.equals("Waiting for another client to connect...")) {
                    continue;
                }

                // Start chat
                while (true) {
                    System.out.print("You: ");
                    String message = scanner.nextLine();

                    // Send message to the server
                    sendMessage(socket, serverIP, SERVER_PORT, message);

                    if (message.equalsIgnoreCase(TERMINATION_MESSAGE)) {
                        System.out.println("Chat ended.");
                        break;
                    }

                    // Receive response from the other client through the server
                    packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    serverMessage = new String(packet.getData(), 0, packet.getLength());
                    System.out.println(serverMessage);

                    if (serverMessage.contains("Chat ended")) {
                        System.out.println("Chat ended by the other client.");
                        break;
                    }
                }

                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(DatagramSocket socket, InetAddress address, int port, String message) {
        try {
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
