package serverandclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MultiThreadServer2 extends Thread {
	static ServerSocket s = null;
	static Socket client = null;
	static ServerThread serverAccept;
	static int maxclient = 5;
	static int clientNo = 0;
	static String ip = "localhost";
	static int port = 7000;
	static ArrayList<clientThread> threads = new ArrayList<clientThread>();

	public MultiThreadServer2() {
		try {
			s = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void main(String args[]) {
		try {
			s = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println(e);
		}

		// creating connection to server 3
		Socket server3 = null;
		PrintStream out3 = null;
		DataInputStream inp3 = null;
		// BufferedReader br = null;
		int port3 = 7500;
		String host3 = "localhost";
		while (true) {
			try {
				while (client == null)
					client = s.accept();

				DataInputStream in = new DataInputStream(client.getInputStream());
				PrintStream p = new PrintStream(client.getOutputStream());
				String username = "";
				// if client wants to initiate chat session, server'd ask for a
				// unique username
				String line = in.readLine();
				if (line.startsWith("SERVER")) {
					(serverAccept = new ServerThread(client)).start();
					boolean connectedServer = false;
					while (!connectedServer) {
						try {
							server3 = new Socket(host3, port3);
							inp3 = new DataInputStream(server3.getInputStream());
							out3 = new PrintStream(server3.getOutputStream());
							out3.println("SERVER");
							connectedServer = true;
							System.out.println("CONNECTED SERVER 2 TO 3");
						} catch (IOException e) {

							System.out.println("Cannot connect to server");

						}
					}
				}

				else if (line.startsWith("JOIN")) {
					p.println("Enter your username: ");
					while (true) {
						boolean sameName = false;
						username = in.readLine();

						int firstAvailable = -1; // first null value in threads
													// array

						// check weather the username is unique, and weather the
						// server can afford new online client
						for (clientThread i : threads) {

							if (i.name.equalsIgnoreCase(username)) {
								p.println("Sorry. Enter another username");
								sameName = true;
								break;

							}
						}

						if (!sameName) {
							clientThread thread = new clientThread(client, threads, username, clientNo);
							threads.add(thread);
							thread.start();
							clientNo++;

							break;
						}
						// keep asking the client for another username if the
						// one chosen wasn't unique

						else {
							continue;
						}
					}
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}
