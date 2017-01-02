package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

public class SelectList extends JPanel {
	ArrayList<JButton> clientButtons;
	int count;
	ActionListener listener;
	ArrayList<String> clientNames;

	public SelectList(ActionListener listener, ArrayList<String> clientNames) {
		this.listener = listener;
		setLayout(null);
		setSize(250, 700);
//		setBackground(Color.BLACK);
		JButton myList = new JButton("My list");
		myList.setActionCommand("my list");
		myList.addActionListener(listener);

		add(myList);
		myList.setBounds(0, 70, 100, 50);

		JButton allList = new JButton("All list");
		allList.setActionCommand("all list");
		allList.addActionListener(listener);
		clientButtons = new ArrayList<JButton>();

		add(allList);
		allList.setBounds(120, 70, 100, 50);
		this.clientNames = clientNames;

		validate();

	}

	public void setClients(ArrayList<String> c) {
		System.out.println("YOOOO");

		int i;
//		for (JButton button : clientButtons) {
//			remove(button);
//			clientButtons.remove(button);
//		}
		Iterator<JButton> iter = clientButtons.iterator();

		while (iter.hasNext()) {
		    JButton button = iter.next();
		    button.setVisible(false);
		        iter.remove();
		   
		}
		revalidate();

		for (i = 0; i < c.size(); i++) {
			JButton newClient = new JButton(c.get(i));
			clientButtons.add(newClient);
			newClient.setActionCommand("button#" + c.get(i));
			System.out.println(c.get(i));
			newClient.addActionListener(listener);
			newClient.setVisible(true);
			add(newClient);
			newClient.setBounds(0, 200 + i * 50, 120, 30);
		}
	}

	public void clientChosen(String s) {

	}

	public static void main(String[] args) {
		// JFrame f = new JFrame();
		// f.setSize(800, 800);
		// f.setTitle("SmarTongue");
		// f.setLocationRelativeTo(null);
		// // SelectList b = new SelectList ();
		// f.add(b);
		// f.validate();
		// f.setVisible(true);
	}

}
