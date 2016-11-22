package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login extends JPanel{
	JTextField text;
	JLabel enter; 
	public Login(MainFrame mainFrame) {
		setLayout(null);
		setSize(800, 800);
		setBackground(Color.black);
		
		enter = new JLabel("Please Enter Your Username");
		enter.setForeground(Color.RED);
		enter.setFont(new Font("", 0, 20));
		enter.setBounds(250,300,400,50);
		
		text = new JTextField();
		text.setBounds(200,400,300,40);
		text.setFont(new Font("", 0, 20));
	
		
		add(text);
		add(enter);
		text.addActionListener(mainFrame);
		text.setActionCommand("username");
		
		this.validate();
	}
}
