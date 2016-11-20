package serverandclient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class Client implements Runnable {

	static Socket myClient = null;
	static PrintStream output = null;
	static DataInputStream input = null;
	static BufferedReader br = null;

	public static void main(String[] args) throws UnknownHostException, IOException {
//		int portnumber = 6000;
//		String hostname = "localhost";
		String[] arr = getCorrectServer();
		int portnumber = Integer.parseInt(arr[0]);
		
		String hostname = arr[1];
		try {
			myClient = new Socket(hostname, portnumber);
			
			br = new BufferedReader(new InputStreamReader(System.in)); // take input from the user
																		
			input = new DataInputStream(myClient.getInputStream()); // to get the response from the server
															
			output = new PrintStream(myClient.getOutputStream()); // to send msg to the server
			

															
		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (IOException e) {

			System.out.println("can't get connection from host" + hostname);
		}

		if (myClient != null && input != null && output != null) {
			try {
				new Thread(new Client()).start();

				while (true) {
					output.println(br.readLine());
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	public void run() {
		String s;
		try {
			while ((s= input.readLine()) != null) {
				
					System.out.println(s);

				if (s != null && s.equals("Bye, The connection will end!"))
				{
					output.close();
					input.close();
					myClient.close();
					System.exit(0);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		}
	
	public static String[] getCorrectServer() throws UnknownHostException, IOException
	{
		
			Socket s = new Socket("localhost", 8000);
			
			input = new DataInputStream(s.getInputStream()); // to get the response from the server
															
			output = new PrintStream(s.getOutputStream()); // to send msg to the server
			
			String port = input.readLine();
			String IP = input.readLine();
			String[] arr = {port, IP};
			input.close();
			output.close();
			s.close();
			return arr;
		
	}

}
