package serverandclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import view.MainFrame;
import view.SelectList;

public class Controller implements ActionListener
{
	Client client;
	MainFrame mainFrame;
	Client clientReciever;

	public Controller() throws UnknownHostException, IOException
	{
		client = new Client();
		boolean connectionSet = client.operate(this);

		mainFrame = new MainFrame(this);
		clientReciever = new Client();
		clientReciever.setActionListener(this);
		if (connectionSet)
		{
			new Thread(clientReciever).start();
			client.output.println("JOIN");
		}
	}

	public static void main(String[] args) throws UnknownHostException,
			IOException
	{
		new Controller();
	}
	// listens to the actions from the view and send them to the servers through client outputstream
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		if (command.equalsIgnoreCase("username"))
		{
			String username = mainFrame.login.text.getText();
			client.output.println(username);

		} else if (command.startsWith("button"))
		{
			System.out.println(command);
			mainFrame.displayChat(command.split("#")[1]);
		} else if (command.startsWith("Send"))
		{
			System.out.println(command);
			String rec = command.split("#")[1];
			String msg = mainFrame.addMessage(rec);
			client.output.println(rec + ":" + msg);
		} else if (command.startsWith("my list")
				|| command.startsWith("all list"))
		{
			System.out.println("list request");
			if (command.equals("my list"))
				client.output.println("I want my list");
			else
				client.output.println("I want all lists");

		}
	}
	
	//listens to responses of the server sent to the client and perform needed operations in gui
	public void getMsgFromServerToClient(String serverMessage)
	{
		if (serverMessage.equals("You are now online"))
		{
			mainFrame.login.setVisible(false);
			mainFrame.clients.setVisible(true);
		}
		else if(serverMessage.equals("Sorry. Enter another username"))
		{
			//TODO show msg to enter another username
		}
		else if (serverMessage.startsWith("##message"))
		{
			String[] arr = serverMessage.split(":");
			String username = arr[1];
			String msg = arr[2];
			mainFrame.msgReceived(username, msg);
		} 
		else if (serverMessage.startsWith("LIST:"))
		{
			StringTokenizer st = new StringTokenizer(serverMessage, ":");
			st.nextToken();
			String list = st.nextToken();
			st = new StringTokenizer(list, ",");
			ArrayList<String> arr = new ArrayList<String>();
			while (st.hasMoreTokens())
			{
				String nxt = st.nextToken();
				System.out.println(nxt);
				arr.add(nxt);
			}
			mainFrame.addChats(arr);
			mainFrame.clients.setClients(arr);
		}
		//TODO remaining: quit, group chat, nndaf el code wel gui 
	}
}
