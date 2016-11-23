package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainFrame extends JFrame {
	static final int WIDTH = 800, HEIGHT = 800;
	static ArrayList<String> onlineClients = new ArrayList<String>();
	ArrayList<Chat> chatViews;
	public Login login;
	public SelectList clients;
	public ActionListener listener; 
	public MainFrame(ActionListener listener) {
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		setTitle("SmarTongue");
		setLocationRelativeTo(null);
		this.listener = listener; 
		login = new Login(listener);
		add(login);
		login.setLocation(0, 0);

		// adds all panels
//		addChats();

		clients = new SelectList(listener, onlineClients);
		add(clients);
		clients.setBounds(600, 0, clients.getWidth(), clients.getHeight());

		// visibility
		login.setVisible(true);
		clients.setVisible(false);

		chatViews = new ArrayList<Chat>();
		
		this.validate();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	public void addChats(ArrayList<String> clientList) {
		// adds all chat views at the beginning, but sets their visibility to
		// null
		for (String client : clientList) {
			if(onlineClients.contains(client))
				continue; 
			onlineClients.add(client); 
			Chat chatView = new Chat(client, listener);
			chatViews.add(chatView);
			chatView.setBounds(0, 0, 500, 800);
			add(chatView);
			chatView.setVisible(false);

		}
	}

	
	public void displayChat(String username) {
		for (Chat chatView : chatViews) {
			if (chatView.username.equals(username)) {
				chatView.setVisible(true);
			} else {
				chatView.setVisible(false);
			}
		}
	}

	public String addMessage(String username) { // adds message to the right chat window
		String msg = ""; 
		for (Chat chatView : chatViews) {
			if (chatView.username.equals(username)) {
				msg = chatView.chatmsg();
				System.out.println("add message");
			}
		}
		return msg; 
	}

	public static void main(String[] args) {
		onlineClients.add("Radwa");
		onlineClients.add("Alaa");
		onlineClients.add("Omar");
		onlineClients.add("Gina");
		onlineClients.add("Mona");
		
		new MainFrame(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		

	}
	
	public String msgReceived(String username, String msg)
	{
		JLabel msgLabel = new JLabel(msg); 
		Chat sender = null;
//		scrollPane.setViewportView(msg);
		for (Chat chat : chatViews) 
		{
			if(chat.username.equals(username))
				sender = chat;
		}
		sender.chatPanel.add(msgLabel);
		sender.chatPanel.revalidate();
		sender.chatPanel.repaint();
		sender.scrollPane.revalidate();
		sender.scrollPane.repaint();
		//msg.setBounds(80, msgCount*100, 100, 50);
		//msg.setVisible(true);
		System.out.println("message sent");
		//scrollPane.setLayout(null);
//		f.setText("");
		sender.msgCount++; 
		return msg;  

	}
}
