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
			
//			String fromServer0 = input.readLine();
//			System.out.println(fromServer0);
//			if(!fromServer0.equals("Connected"))
//			{
//				StringTokenizer st = new StringTokenizer(fromServer0);
//				int newPort = Integer.parseInt(st.nextToken());
//				String newHost = st.nextToken();
//				myClient.close();
//				input.close();
//				output.close();
//				myClient = new Socket(newHost, newPort);
//				input = new DataInputStream(myClient.getInputStream());
//				output = new PrintStream(myClient.getOutputStream());
//			}
															
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

		/*
		 * String hostname = "localhost"; int portnumber = 6000;
		 * 
		 * DataInputStream input = null; DataOutputStream output = null;
		 * 
		 * BufferedReader br = new BufferedReader(new
		 * InputStreamReader(System.in)); // take // input // from // the //
		 * user
		 * 
		 * try { String response; while (true) { try { myClient = new
		 * Socket(hostname, portnumber); } catch (UnknownHostException e) {
		 * 
		 * e.printStackTrace(); } catch (IOException e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * try { input = new DataInputStream(myClient.getInputStream()); // to
		 * // get // the // response // from // the // server } catch
		 * (IOException e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * try { output = new DataOutputStream(myClient.getOutputStream()); //
		 * to // send // messages // to // the // server } catch (IOException e)
		 * {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * String req = br.readLine(); output.writeBytes(req + "\n"); response =
		 * input.readLine(); System.out.println("Server: " + response); if
		 * (response.equals("Bye, The connection will end!")) { // to // handle
		 * // the // case // when // the // client // sends // bye // or // quit
		 * break; } } } catch (Exception e) { }
		 * 
		 * try { output.close(); input.close(); myClient.close(); // to close
		 * the socket } catch (IOException e) { }
		 */}
	
	public static String[] getCorrectServer() throws UnknownHostException, IOException
	{
		
			Socket s = new Socket("localhost", 8000);
			
			input = new DataInputStream(s.getInputStream()); // to get the response from the server
															
			output = new PrintStream(s.getOutputStream()); // to send msg to the server
			
			String port = input.readLine();
			String IP = input.readLine();
			String[] arr = {port, IP};
			s.close();
			input.close();
			output.close();
			return arr;
		
	}

}
