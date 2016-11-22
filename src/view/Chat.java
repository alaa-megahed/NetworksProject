package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Chat extends JPanel {
	ArrayList<JButton> clients;
	String username;
	int count;
	JLabel label;
	JScrollPane scrollPane;
	JPanel chatPanel; 
	JTextField messageBox;
	JTextArea chatbox;
	JTextField f;
	int msgCount; 
	public Chat(String name, ActionListener mainFrame) {
		msgCount = 0; 
		this.username = name;
		setLayout(null);
		setSize(500, 800);
		label = new JLabel(name);
		f = new JTextField();
		f.setFont(new Font("", 0, 15));
		JButton send = new JButton("Send");
		send.addActionListener(mainFrame);
		send.setActionCommand("Send " + name);
		label.setBounds(0, 0, 120, 30);
		f.setBounds(0, 600, 300, 100);
		send.setBounds(310, 600, 100, 20);
		add(label);
		add(send);
		add(f);
		JButton chatGp = new JButton("Gp chat");
		chatGp.setBounds(600, 0, 100, 50);
		
//		JPanel panel = new JPanel();
		JPanel panel = new JPanel(new BorderLayout()); 
		panel.setBounds(50, 50, 300, 500);
		
		chatPanel = new JPanel(); 
		chatPanel.setLayout(new GridLayout(1000, 1));
//		chatPanel.getVerticalScrollBar(); 
		chatPanel.setBackground(Color.PINK);
		//chatPanel.setVisible(true);
		chatPanel.setSize(300, 500); 
		
		scrollPane = new JScrollPane(chatPanel); 
		
		panel.add(scrollPane, BorderLayout.CENTER); 
		add(chatGp);
		add(panel);

		this.validate();
		setVisible(true);

	}

	public void chatmsg() {
		JLabel msg = new JLabel(f.getText()); 
//		scrollPane.setViewportView(msg);
		chatPanel.add(msg);
		chatPanel.revalidate();
		chatPanel.repaint();
		scrollPane.revalidate();
		scrollPane.repaint();
		//msg.setBounds(80, msgCount*100, 100, 50);
		//msg.setVisible(true);
		System.out.println("message sent");
		//scrollPane.setLayout(null);
		f.setText("");
		msgCount++; 

	}
}