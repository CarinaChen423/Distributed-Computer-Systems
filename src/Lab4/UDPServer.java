package Lab4;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class UDPServer {
    private static final int PORT = 9876;
    private static final String TERMINATION_MESSAGE = "END";
    private ArrayList<InetSocketAddress> clients = new ArrayList<>();

    public static void main(String[] args) {
        new UDPServer().runServer();
    }

    public void runServer() {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                InetSocketAddress clientAddress = new InetSocketAddress(packet.getAddress(), packet.getPort());

                // Register new clients
                if (!clients.contains(clientAddress)) {
                    clients.add(clientAddress);
                    System.out.println("Client connected: " + clientAddress);

                    // Notify clients once two are connected
                    if (clients.size() == 2) {
                        System.out.println("Two clients connected. Ready to chat.");
                        sendMessage(socket, clients.get(0), "Connected to chat. Waiting for messages.");
                        sendMessage(socket, clients.get(1), "Connected to chat. Waiting for messages.");
                    } else {
                        sendMessage(socket, clientAddress, "Waiting for another client to connect...");
                    }
                }

                // Relay messages if two clients are connected
                if (clients.size() == 2) {
                    InetSocketAddress otherClient = clientAddress.equals(clients.get(0)) ? clients.get(1) : clients.get(0);

                    // End communication if message is the termination message
                    if (message.equalsIgnoreCase(TERMINATION_MESSAGE)) {
                        sendMessage(socket, otherClient, "Chat ended by the other client.");
                        System.out.println("Ending chat session.");
                        clients.clear(); // Reset server for new connections
                    } else {
                        // Forward message to the other client
                        sendMessage(socket, otherClient, "Client says: " + message);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(DatagramSocket socket, InetSocketAddress clientAddress, String message) {
        try {
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, clientAddress.getAddress(), clientAddress.getPort());
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
