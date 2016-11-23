package serverandclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MultiThreadServer3 extends Thread {
	static ServerSocket s = null;
	static ServerThread serverAccept;
	static Socket client = null;
	static int maxclient = 5;
	static int clientNo = 0;
	static String ip = "localhost";
	static int port = 7500;
	static ArrayList<clientThread> threads = new ArrayList<clientThread>();

	public MultiThreadServer3() {
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

		// creating connection to server 1
		Socket server0 = null;
		PrintStream out0 = null;
		DataInputStream inp0 = null;
		// BufferedReader br = null;
		int port0 = 6000;
		String host0 = Constants.IP_SERVER_0;
		while (true) {
			try {
					client = s.accept();

				DataInputStream in = new DataInputStream(client.getInputStream());

				PrintStream p = new PrintStream(client.getOutputStream());
				String username = "";
				// if client wants to initiate chat session, server'd ask for a×
				// unique username
				String line = in.readLine();
				if(line == null) {
					System.out.println("line enetered in null");
					continue; 
				}
				if (line != null && line.startsWith("SERVER")) {
					(serverAccept = new ServerThread(client, threads)).start();
					
					boolean connectedServer = false;
					while (!connectedServer) {
						try {
							server0 = new Socket(host0, port0);
							
							inp0 = new DataInputStream(server0.getInputStream());
							out0 = new PrintStream(server0.getOutputStream());
							out0.println("SERVER");
							connectedServer = true;
							serverAccept.nextServer = server0;
							System.out.println("CONNECTED SERVER 3 TO 0");
						} catch (IOException e) {
							e.printStackTrace();
							System.out.println("Cannot connect to server");

						}
					}
				} else if (line.startsWith("JOIN")) {
//					p.println("Enter your username: ");
					while (true) {
						boolean sameName = false;

						username = in.readLine();

						// check weather the username is unique, and weather the
						// server can afford new online client
						for (clientThread i : threads) {

							if (i.name.equalsIgnoreCase(username)) {
								p.println("Sorry. Enter another username");
								sameName = true;
								break;

							}
						}
						if(sameName) continue;
						
						if(server0 != null)
						{
							out0.println("CHECK THIS NAME:1:"+username);
							String responce = inp0.readLine();
							if(responce.equals("FOUND"))
							{
								p.println("Sorry. Enter another username");
								sameName = true;
							}
						}
						if (!sameName) {

							clientThread thread = new clientThread(client, threads, username, clientNo, inp0, out0);

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
