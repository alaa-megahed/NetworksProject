package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;


public class MainFrame  extends JFrame{
	
	public MainFrame (){
		this.setSize(800,800);
		this.setTitle("SmarTongue");
		this.setLocationRelativeTo(null);
		JPanel MainPanel = new JPanel(); 
		MainPanel.setBackground(Color.BLACK);
		MainPanel.setLayout(null);
		this.add(MainPanel);
		JLabel l = new JLabel("Please Enter Your BIsso");
		l.setForeground(Color.RED);
		l.setFont(new Font("", 0, 20));
		l.setBounds(250,300,400,50);
		JTextField text = new JTextField();
		text.setBounds(200,400,300,40);
		text.setFont(new Font("", 0, 20));
		text.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		MainPanel.add(text);
		MainPanel.add(l);
		this.validate();
		this.setVisible(true);

	}
	public static void main(String[] args) {
		MainFrame b = new MainFrame();
	}

}
