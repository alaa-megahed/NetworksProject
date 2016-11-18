package view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class ClientList extends JPanel {
	static JList clientButtons;
	//static ArrayList <JButton> clientButtons; 
	static int buttonCount; 
	JScrollPane scroll;
	public ClientList () {
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setSize(View.WIDTH/3, View.HEIGHT);
		clientButtons = new JList();
		clientButtons.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		clientButtons.setVisible(true);
		scroll = new JScrollPane(clientButtons);
		scroll.setSize(50,View.HEIGHT);
		scroll.getVerticalScrollBar();
		scroll.setVisible(true);
		clientButtons.setSize(20,View.HEIGHT);
		//clientButtons = new ArrayList<>(); 
		setBackground(Color.RED);
		clientButtons.add(new JButton("ahmed"));
		clientButtons.add(new JButton("gina"));
		clientButtons.add(new JButton("ali"));
		clientButtons.add(new JButton("radwa"));
		clientButtons.add(new JButton("salma"));
		clientButtons.add(new JButton("alaa"));
		clientButtons.add(new JButton("mohamed"));
		
		
		//addButton("Ahmed");
		//addButton("Ali");
		setVisible(true);
		add(clientButtons);
		add(scroll);
	}
	
	void addButton(String name) {
		JButton newClient = new JButton(name); 
		newClient.setMinimumSize(new Dimension(10,10));
		newClient.setActionCommand("client" + buttonCount++);
		add(newClient); 
		
	}
}
