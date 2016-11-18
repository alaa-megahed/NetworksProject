package serverandclient;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class clientThread extends Thread
{
	DataInputStream input = null;
	PrintStream output = null;
	Socket client = null;
	ArrayList<clientThread> threads;
	int maxclient;
	String name;
	int clientNo;

	public clientThread(Socket client, ArrayList<clientThread> threads, String name, int clientNo)
	{
		this.client = client;
		this.threads = threads;
		maxclient = threads.size();
		this.name = name;
		this.clientNo = clientNo;
	}

	public void run()
	{
		try
		{
			//if the connection was successful, server sends confirmation msg to the client
			input = new DataInputStream(client.getInputStream());
			output = new PrintStream(client.getOutputStream());
			output.println("You are now online");

			while (true)
			{
				String line = input.readLine();
				
				String message = "";
				String username = "";
				String gpchatname="";
								
				//close the thread when the client quit
				if (line.startsWith("quit"))
				{
					output.println("Bye, The connection will end!");
					
					for (clientThread i: threads)
					{
						if (i == this)
						{
							clientNo--;
							threads.remove(i);
							return;
						}
					}
					client.close();
					output.close();
					input.close();
				}
				//loop over all online clients when client requests list of them
				else if (line.startsWith("I want list"))
				{
					boolean noone = true;
					for (clientThread i : threads)
					{
						if (i != this)
						{
							output.println(i.name);
							noone = false;
						}
					}
					if(noone)
						output.println("Nobody is online now :(");
				}
				
				//starting group chat
				else if(line.startsWith("Group Chat"))
				{
					//skipping first 11 characters of"Group Chat<"
					int l = 11;
					for (; l < line.length(); l++)
					{
						//when reaching the end of the group chat name break
						if(line.charAt(l) == '>')
							break;
						//still taking the group chat name
						else
							gpchatname += line.charAt(l);
					}
					//skipping "> " after the group chat name
					line = line.substring(l+2);
					//dividing the rest of the line into message and the names of the group participants, separated by":" 
					StringTokenizer st = new StringTokenizer(line, ":");
					String names = st.nextToken();
					message = st.nextToken();
					//in case the original msg contains ":"
					while(st.hasMoreTokens())
						message += ":" + st.nextToken();
					//extracting names of group participants 
					String [] namesList = names.split("&");
					//sending the message to all group members
					for (int i = 0; i < namesList.length; i++)
					{
						for (clientThread j: threads)
						{
							if(j.name.equals(namesList[i]))
							{
								String groupNames = "You";
								for (int k = 0; k < namesList.length; k++)
								{
									if(!namesList[k].equals(j.name))
										groupNames += ", "+namesList[k];
								}
								j.output.println(name + " has created group chat(" + gpchatname+") with "+ groupNames);
								j.output.println(name + ": " + message);
								break;
							}
						}
					}
				}
				//ordinary individual message
				else
				{
					String[] b = line.split(":");
					username = b[0];
					message = b[1];
					//in case original msg contains ":"
					for (int i = 2; i < b.length; i++)
					{
						message +=":" +b[i];
					}
					//sending the msg to the chosen receiver
					for (clientThread i: threads)
					{
						if (i.name.equals(username))
						{
							i.output.println(name + ":" + message);
							break;
						}
					}
				}
			}
		}

		catch (IOException e)
		{
			System.out.println("exception");
			e.printStackTrace();
		}
	}
}













//import java.io.DataInputStream;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.StringTokenizer;
//
//public class clientThread extends Thread
//{
//	DataInputStream input = null;
//	PrintStream output = null;
//	Socket client = null;
//	clientThread[] threads;
//	int maxclient;
//	String name;
//
//	public clientThread(Socket client, clientThread[] threads, String name)
//			throws IOException
//	{
//		this.client = client;
//		this.threads = threads;
//		maxclient = threads.length;
//		this.name = name;
//		// if the connection was successful, server sends confirmation msg to
//		// the client
//
//		input = new DataInputStream(client.getInputStream());
//		output = new PrintStream(client.getOutputStream());
//
//		output.println("You are now online");
//	}
//
//	public void run()
//	{
//		try
//		{
//			while (true)
//			{
//				String line = input.readLine();
//
//				// close the thread when the client quit
//				if (line.startsWith("quit"))
//				{
//					quit();
//				}
//				// loop over all online clients when client requests list of
//				// them
//				else if (line.startsWith("I want list"))
//				{
//					list();
//				}
//
//				// starting group chat
//				else if (line.startsWith("Group Chat"))
//				{
//					// skipping first 11 characters of"Group Chat<"
//					groupChat(line);
//				}
//				// ordinary individual message
//				else
//				{
//					String[] b = line.split(":");
//					String username = b[0];
//					String message = b[1];
//					// in case original msg contains ":"
//					for (int i = 2; i < b.length; i++)
//					{
//						message += ":" + b[i];
//					}
//					// sending the msg to the chosen receiver
//					sendMsg(username, message);
//				}
//			}
//		}
//
//		catch (IOException e)
//		{
//			System.out.println("exception");
//			e.printStackTrace();
//		}
//	}
//
//	public void list()
//	{
//		boolean noone = true;
//		for (int i = 0; i < maxclient; i++)
//		{
//			if (threads[i] != null && threads[i] != this)
//			{
//				output.println(threads[i].name);
//				noone = false;
//			}
//		}
//		if (noone) output.println("Nobody is online now :(");
//	}
//
//	public void quit() throws IOException
//	{
//		
//		output.println("Bye, The connection will end!");
//		for (int i = 0; i < threads.length; i++)
//		{
//			if (threads[i] != null && threads[i] == this)
//			{
//				threads[i] = null;
//				break;
//			}
//		}
//		
//		client.close();
//		output.close();
//		input.close();
//		
//	}
//	
//	public ArrayList<String> groupChat(String line)
//	{
//		String gpchatname = "";
//		String message = "";
//		ArrayList<String> failedSend = new ArrayList<String>();
//		int l = 11;
//		for (; l < line.length(); l++)
//		{
//			// when reaching the end of the group chat name break
//			if (line.charAt(l) == '>')
//				break;
//			// still taking the group chat name
//			else
//				gpchatname += line.charAt(l);
//		}
//		// skipping "> " after the group chat name
//		line = line.substring(l + 2);
//		// dividing the rest of the line into message and the names
//		// of the group participants, separated by":"
//		StringTokenizer st = new StringTokenizer(line, ":");
//		String names = st.nextToken();
//		message = st.nextToken();
//		// in case the original msg contains ":"
//		while (st.hasMoreTokens())
//			message += ":" + st.nextToken();
//		// extracting names of group participants
//		String[] namesList = names.split("&");
//		// sending the message to all group members
//		for (int i = 0; i < namesList.length; i++)
//		{
//			
//			for (int j = 0; j < threads.length; j++)
//			{
//				if (threads[j] != null
//						&& threads[j].name.equals(namesList[i]))
//				{
//					String groupNames = "You";
//					for (int k = 0; k < namesList.length; k++)
//					{
//						if (!namesList[k].equals(threads[j].name))
//							groupNames += ", " + namesList[k];
//					}
//					message = name + " has created group chat("
//							+ gpchatname + ") with " + groupNames +"/n" + name + ": " + message;
//					if (!sendMsg(threads[j].name, message))
//						failedSend.add(namesList[i]);
//				}
//			}
//		}
//		return failedSend;
//	}
//	
//	public boolean sendMsg(String receiver, String msg)
//	{
//		for (int i = 0; i < maxclient; i++)
//		{
//			if (threads[i] != null
//					&& threads[i].name.equals(receiver))
//			{
//				threads[i].output.println(name + ":" + msg);
//				return true;
//			}
//		}
//		return false;
//	}
//}
