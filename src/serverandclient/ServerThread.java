package serverandclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;


public class ServerThread extends Thread
{
	DataInputStream input = null;
	PrintStream output = null;
	Socket server = null;
	public ServerThread(Socket server) throws IOException
	{
		this.server = server;
		input = new DataInputStream(server.getInputStream());
		output = new PrintStream(server.getOutputStream());
	}
	
	public void run()
	{
		
	}
}
