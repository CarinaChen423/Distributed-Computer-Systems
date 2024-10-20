package Lab3;
import java.io.*;
import java.net.*;

public class TCPServer {
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.out.println("Usage: java TCPServer <port>");
			return;
		}

		int port = Integer.parseInt(args[0]);
		ServerSocket serverSocket = new ServerSocket(port);
		InetAddress ipAddress = InetAddress.getLocalHost();

		System.out.println("Server started on IP: " + ipAddress.getHostAddress() + ", Port: " + port);

		try {
			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Connection accepted: " + clientSocket);

				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);

				while (true) {
					String message = in.readLine();
					if (message.equals("END")) break;
					System.out.println("Received: " + message);
					out.println(message);
				}

				System.out.println("Closing connection...");
				clientSocket.close();
			}
		} finally {
			serverSocket.close();
		}
	}
}
