package serverandclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket welcomeSocket = new ServerSocket(6000);
		String line;
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToCient = new DataOutputStream(connectionSocket.getOutputStream());
			line = inFromClient.readLine();
			outToCient.writeBytes(line.toUpperCase() + "\n");
			if(line.equalsIgnoreCase("bye")) 
				break; 
		}
		welcomeSocket.close(); 
	}
}
