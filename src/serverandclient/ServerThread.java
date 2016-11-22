package serverandclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ServerThread extends Thread
{
	DataInputStream input = null;
	PrintStream output = null;
	Socket inServer = null;
	Socket nextServer = null;
	DataInputStream nxtInput = null;
	PrintStream nxtOutput = null;
	ArrayList<clientThread> threads;

	public ServerThread(Socket inServer, ArrayList<clientThread> threads)
			throws IOException
	{
		this.inServer = inServer;
		input = new DataInputStream(inServer.getInputStream());
		output = new PrintStream(inServer.getOutputStream());
		this.threads = threads;
	}

	public void run()
	{
		while (true)
		{
			if (nextServer != null)
			{
				try
				{
					nxtInput = new DataInputStream(nextServer.getInputStream());
					nxtOutput = new PrintStream(nextServer.getOutputStream());
					break;
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		while (true)
		{
//			synchronized (this)
//			{

				try
				{
					String line = input.readLine();
					System.out.println("read " + line + "in serverThread");
					if (line.startsWith("I want all lists"))
					{
						StringTokenizer st = new StringTokenizer(line, ":");
						st.nextToken();
						int count = Integer.parseInt(st.nextToken()) + 1;
						int statusSoFar = Integer.parseInt(st.nextToken());

						String myList = "";
						for (clientThread i : threads)
						{
							myList += i.name + ",";
							statusSoFar = 1;
						}
						if (count == 4)
						{
							if (statusSoFar == 0)
								output.println("Nobody is online now :(");
							else
								output.println(myList);

						} else if (nextServer != null)
						{
							nxtOutput.println("I want all lists:" + (count)
									+ ":" + statusSoFar);
							output.println(myList + nxtInput.readLine());
						}
					} else if (line.startsWith("CHECK THIS NAME"))
					{
						StringTokenizer st = new StringTokenizer(line, ":");
						st.nextToken();
						int count = Integer.parseInt(st.nextToken());
						String username = st.nextToken();
						if (count == 4)
							output.println("NOT FOUND");
						else
						{
							boolean found = false;
							for (clientThread i : threads)
								if (i.name.equalsIgnoreCase(username))
								{
									output.println("FOUND");
									found = true;
									break;
								}
							if (!found)
							{
								nxtOutput.println("CHECK THIS NAME:"
										+ (count + 1) + ":" + username);
								output.println(nxtInput.readLine());
							}
						}
					} else
					{
						String[] b = line.split(":");
						int ttl = Integer.parseInt(b[0]);
						String reciever = b[1];
						String message = b[2];
						// in case original msg contains ":"
						for (int i = 3; i < b.length; i++)
						{
							message += ":" + b[i];
						}
						if (ttl == 0)
						{
							output.println("ERROR: MESSAGE CAN NOT BE SENT TO "
									+ reciever);
						} else
						{
							boolean found = false;
							// sending the msg to the chosen receiver
							for (clientThread i : threads)
							{
								if (i.name.equals(reciever))
								{
									i.output.println(message);
									found = true;
									break;
								}
							}
							if (!found)
							{
								nxtOutput.println((ttl - 1) + ":" + reciever
										+ ":" + message);
								output.println(nxtInput.readLine());
							} else
							{
								output.println("OK MESSAGE SENT");
							}
						}
					}
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
	//	}
	}
}
