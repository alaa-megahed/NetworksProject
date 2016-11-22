package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class MainFrame extends JFrame implements ActionListener {
	static final int WIDTH = 800, HEIGHT = 800;
	static ArrayList<String> onlineClients = new ArrayList<String>();
	ArrayList<Chat> chatViews;
	Login login;
	SelectList clients;

	public MainFrame() {
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		setTitle("SmarTongue");
		setLocationRelativeTo(null);

		login = new Login(this);
		add(login);
		login.setLocation(0, 0);

		// adds all panels
		addChats();

		clients = new SelectList(this, onlineClients);
		add(clients);
		clients.setBounds(600, 0, clients.getWidth(), clients.getHeight());

		// visibility
		login.setVisible(true);
		clients.setVisible(false);

		this.validate();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	void addChats() {
		// adds all chat views at the beginning, but sets their visibility to
		// null
		chatViews = new ArrayList<Chat>();
		for (String client : onlineClients) {
			Chat chatView = new Chat(client, this);
			chatViews.add(chatView);
			chatView.setBounds(0, 0, 500, 800);
			add(chatView);
			chatView.setVisible(false);

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equalsIgnoreCase("username")) {
			String username = login.text.getText();
			login.setVisible(false);
			clients.setVisible(true);
		} else if (command.startsWith("button")) {
			System.out.println(command);
			displayChat(command.split(" ")[1]);
		} else if (command.startsWith("Send")) {
			System.out.println(command);
			addMessage(command.split(" ")[1]);
		}
	}

	void displayChat(String username) {
		for (Chat chatView : chatViews) {
			if (chatView.username.equals(username)) {
				chatView.setVisible(true);
			} else {
				chatView.setVisible(false);
			}
		}
	}

	void addMessage(String username) { // adds message to the right chat window
		for (Chat chatView : chatViews) {
			if (chatView.username.equals(username)) {
				chatView.chatmsg();
				System.out.println("add message");
			}
		}
	}

	public static void main(String[] args) {
		onlineClients.add("Radwa");
		onlineClients.add("Alaa");
		onlineClients.add("Omar");
		onlineClients.add("Gina");
		onlineClients.add("Mona");
		new MainFrame();

	}
}
