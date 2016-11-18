package view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Chat extends JPanel {
	public Chat (String s ){
		setLayout(null);
		JLabel name = new JLabel(s);
		JTextField f = new JTextField(); 
		JButton send = new JButton("Send");
		name.setBounds(400, 0, 50, 50);
		f.setBounds(400,0,400,400);
		send.setBounds(500,500,50,50);
		add(name);
		add(send);
		add(f);
	}

}
