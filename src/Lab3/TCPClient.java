package Lab3;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCPClient {
	public static void main(String[] args) throws IOException {

		String configFile = "";
		String ip = "";
		int port = 0;

		try (Scanner scanner = new Scanner(new File(configFile))) {
			ip = scanner.nextLine().trim(); // Read IP address
			port = Integer.parseInt(scanner.nextLine().trim()); // Read port number
		} catch (FileNotFoundException e) {
			System.out.println("Config file not found: " + configFile);
			return;
		}

		InetAddress addr = InetAddress.getByName(ip);
		System.out.println("Connecting to server at IP: " + ip + ", Port: " + port);

		Socket socket = new Socket(addr, port);
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

			for (int i = 0; i < 10; i++) {
				out.println("Hi: " + i);
				String response = in.readLine();
				System.out.println("Server response: " + response);
			}
			out.println("END");
		} finally {
			System.out.println("Closing connection...");
			socket.close();
		}
	}
}
