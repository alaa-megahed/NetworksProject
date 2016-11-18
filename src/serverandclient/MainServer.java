package serverandclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class MainServer
{
	static MultiThreadServer0 server0;
	static MultiThreadServer1 server1;
	static MultiThreadServer2 server2;
	static MultiThreadServer3 server3;
	
	public static void main(String[] args) throws IOException
	{
		ServerSocket s = new ServerSocket(8000);
		
		server0 = new MultiThreadServer0();
		server1 = new MultiThreadServer1();
		server2 = new MultiThreadServer2();
		server3 = new MultiThreadServer3();
		
		server0.start();
		server1.start();
		server2.start();
		server3.start();
		
		while(true)
		{ 
			Socket client = s.accept();
			DataInputStream in = new DataInputStream(
					client.getInputStream());
			PrintStream p = new PrintStream(client.getOutputStream());
			
			
			String[] arr = min();
			int portNumber = Integer.parseInt(arr[0]);
			String ip = arr[1];
			
			p.println(portNumber);
			p.println(ip);
			
		}
		
		
	}
	
	public static String[] min()
	{
		int minimum = Math.min((Math.min(server0.clientNo, server1.clientNo)), (Math.min(server2.clientNo, server3.clientNo)));
		String[] res = new String[2];
		
		if(minimum == server0.clientNo)
		{
			res[0] = "" + server0.port;
			res[1] = server0.ip;
		}
		
		if(minimum == server1.clientNo)
		{
			res[0] = "" + server1.port;
			res[1] = server1.ip;
		}
		
		if(minimum == server2.clientNo)
		{
			res[0] = "" + server2.port;
			res[1] = server2.ip;
		}
		if(minimum == server3.clientNo)
		{
			res[0] = "" + server3.port;
			res[1] = server3.ip;
		}
			return res;
	}
}
