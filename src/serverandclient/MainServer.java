package serverandclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class MainServer
{
	static int nextServer; 
	static String [] IP = {Constants.IP_SERVER_0, Constants.IP_SERVER_1, Constants.IP_SERVER_2, Constants.IP_SERVER_3};
	static String [] port = {"6000", "6500", "7000", "7500"}; 
	public static void main(String[] args) throws IOException
	{
		ServerSocket s = new ServerSocket(8000);
		
		while(true)
		{ 
			Socket client = s.accept();
			DataInputStream in = new DataInputStream(
					client.getInputStream());
			PrintStream p = new PrintStream(client.getOutputStream());
			
			
			String[] arr = nextServer();
			int portNumber = Integer.parseInt(arr[0]);
			String ip = arr[1];
			
			p.println(portNumber);
			p.println(ip);
			System.out.println("IP and port sent");
			client.close();
			nextServer = (nextServer + 1)%4; 
			
		}
		
		
	}
	
	public static String[] nextServer()
	{
		String res [] = new String[2]; 
		for (int i = 0; i < 4 ; i++) {
			if(nextServer == i) {
				res[0] = port[i]; 
				res[1] = IP[i]; 
				return res; 
			}
		}
		return null; 
	}
}
