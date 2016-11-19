package serverandclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MultiThreadServer1 extends Thread {
	static ServerSocket s = null;
	static Socket client = null;
	static ServerThread serverAccept;
	static int maxclient = 5;
	static int clientNo = 0;
	static String ip = "localhost";
	static int port = 6500;
	static ArrayList<clientThread> threads = new ArrayList<clientThread>();

	public MultiThreadServer1() {

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

		// connecting to server 2
		Socket server2 = null;
		PrintStream out2 = null;
		DataInputStream inp2 = null;
		// BufferedReader br = null;
		int port2 = 7000;
		String host2 = "localhost";
		// create a client socket for each connection and pass it to a new
		// thread.

		while (true) {
			try {
				
					client = s.accept();

				DataInputStream in = new DataInputStream(client.getInputStream());
				PrintStream p = new PrintStream(client.getOutputStream());
				String username = "";
				// if client wants to initiate chat session, server'd ask for a
				// unique username

				String line = in.readLine();
				if(line == null) {
					System.out.println("line enetered in null");
					continue; 
				}
				if ( line.startsWith("SERVER")) {
					(serverAccept = new ServerThread(client)).start();

					boolean connectedServer = false;
					while (!connectedServer) {
						try {
							server2 = new Socket(host2, port2);
							inp2 = new DataInputStream(server2.getInputStream());
							out2 = new PrintStream(server2.getOutputStream());
							out2.println("SERVER");
							connectedServer = true;
							System.out.println("CONNECTED SERVER 1 TO 2");
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
