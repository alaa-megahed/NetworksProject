package serverandclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {

	public static Socket myClient = null;
	public static PrintStream output = null;
	public static DataInputStream input = null;
	public static BufferedReader br = null;
	public static String s = "";
	public ActionListener listener; 
	public String response;

	public boolean operate(ActionListener listener) throws UnknownHostException, IOException {

		this.listener = listener; 
		String[] arr = getCorrectServer();
		int portnumber = Integer.parseInt(arr[0]);

		String hostname = arr[1];
		System.out.println(arr[0]);
		System.out.println("host: " + arr[1]);
		try {
			myClient = new Socket(hostname, portnumber);
			System.out.println("hey");
			br = new BufferedReader(new InputStreamReader(System.in)); 
			input = new DataInputStream(myClient.getInputStream()); 
			output = new PrintStream(myClient.getOutputStream()); 
		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (IOException e) {

			System.out.println("can't get connection from host" + hostname);
		}

		if (myClient != null && input != null && output != null) {
			return true;
		}
		else
			return false;

	}

	public void setActionListener(ActionListener listener)
	{
		this.listener = listener;
	}

	public void run() {
		
		try {	
				System.out.println(s);
			while ((s = input.readLine()) != null) {
				System.out.println(s);
				((Controller)listener).getMsgFromServerToClient(s);
				
				if (s != null && s.equals("Bye, The connection will end!")) {
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

	public static String[] getCorrectServer() throws UnknownHostException,
			IOException {

		Socket s = new Socket(Constants.IP_MAIN_SERVER, 8000);
		input = new DataInputStream(s.getInputStream()); // to get the response from the server														
		output = new PrintStream(s.getOutputStream()); // to send msg to the server
									
		String port = input.readLine();
		String IP = input.readLine();
		String[] arr = { port, IP };
		input.close();
		output.close();
		s.close();
		return arr;

	}

}
