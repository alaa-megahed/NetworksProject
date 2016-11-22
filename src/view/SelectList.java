package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class SelectList extends JPanel {
	ArrayList<JButton> clients;
	int count;
	ActionListener mainFrame;
	ArrayList<String> clientNames; 
	public SelectList(ActionListener mainFrame, ArrayList<String> clientNames) {
		setLayout(null);
		setSize(200, 800);
		JButton chatGp = new JButton("Gp chat");
		
		add(chatGp);
		chatGp.setBounds(0, 0, 100, 50);
		this.mainFrame = mainFrame;
		this.clientNames = clientNames; 
		
		setClients(clientNames);
	
		validate();

	}

	public void setClients(ArrayList<String> c) {
		int i;
		for (i = 0; i < c.size(); i++) {
			JButton newClient = new JButton(c.get(i));
			newClient.setActionCommand("button " + c.get(i));
			newClient.addActionListener(mainFrame);
			add(newClient);
			newClient.setBounds(0, 100 + i * 50, 120, 30);
		}
	}

	public void clientChosen(String s) {

	}

	public static void main(String[] args) {
//		JFrame f = new JFrame();
//		f.setSize(800, 800);
//		f.setTitle("SmarTongue");
//		f.setLocationRelativeTo(null);
//		// SelectList b = new SelectList ();
//		f.add(b);
//		f.validate();
//		f.setVisible(true);
	}

}
