package serverandclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MultiThreadServer0 extends Thread
{
	static ServerSocket s = null;
	static Socket client = null;
	static int maxclient = 5;
	static ServerThread serverAccept;
	static int clientNo = 0;
	static String ip = "localhost";
	static int port = 6000;
	static ArrayList<clientThread> threads = new ArrayList<clientThread>();

	public MultiThreadServer0()
	{

	}

	public static void main(String args[])
	{
		try
		{
			s = new ServerSocket(6000);
		} catch (IOException e)
		{
			System.out.println(e);
		}

		// creating connection to other servers
		Socket server1 = null;
		PrintStream out1 = null;
		DataInputStream inp1 = null;
		// BufferedReader br = null;
		int port1 = 6500;
		String host1 = Constants.IP_SERVER_1;
		boolean connectedServer = false;
		while (!connectedServer)
		{
			try
			{
				server1 = new Socket(host1, port1);
				inp1 = new DataInputStream(server1.getInputStream());
				out1 = new PrintStream(server1.getOutputStream());
				out1.println("SERVER");
				connectedServer = true;
				System.out.println("CONNECTED 0 TO 1");
			} catch (IOException e)
			{

				// System.out.println("Cannot connect to server");

			}
		}

		// create a client socket for each connection and pass it to a new
		// thread.

		while (true)
		{
			try
			{
				client = s.accept();

				DataInputStream in = new DataInputStream(
						client.getInputStream());
				PrintStream p = new PrintStream(client.getOutputStream());

				String username = "";
				// if client wants to initiate chat session, server'd ask for a
				// unique username
				String line = in.readLine();
				if (line == null)
				{
					System.out.println("line enetered in null");
					continue;
				}
				if (line.startsWith("SERVER"))
				{
					(serverAccept = new ServerThread(client, threads)).start();
					serverAccept.nextServer = server1;
				} else if (line.startsWith("JOIN"))
				{
					// System.out.println("YOOOOO");
					//					p.println("Enter your username: ");
					while (true)
					{
						boolean sameName = false;
						String fromUser = in.readLine();
						if(fromUser.equals("quit"))
						{
							p.println("Bye, The connection will end!");
							
							client.close();
							p.close();
							in.close();
						}
						else
						{
							username = fromUser;
							System.out.println(username);
							for (clientThread c : threads)
							{
								if (c.name.equalsIgnoreCase(username))
								{
									p.println("Sorry. Enter another username");
									sameName = true;
									break;
								}
							}
							if(sameName) continue;

							if(server1 != null)
							{
								//	System.out.println("send check to server1");
								out1.println("CHECK THIS NAME:1:"+username);
								String responce = inp1.readLine();
								if(responce.equals("FOUND"))
								{
									p.println("Sorry. Enter another username");
									sameName = true;
								}
							}

							if (!sameName)
							{
								//	System.out.println("not same name");
								clientThread thread = new clientThread(client,
										threads, username, clientNo, inp1, out1);
								threads.add(thread);
								thread.start();
								clientNo++;

								break;
							}
							// keep asking the client for another username if the
							// one chosen wasn't unique
							else
							{
								continue;
							}
						}
					}
				}
			} catch (IOException e)
			{
				System.out.println(e);
			}
		}
	}

}
