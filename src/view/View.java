package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class View extends JFrame {
	static final int WIDTH = 850;
	static final int HEIGHT = 600;
	ArrayList<JPanel> clients;
	ClientList clientList;
	JTextField text ;
	JTextArea area;
	public View() {
		this.setSize(WIDTH, HEIGHT);
		clients = new ArrayList<>();
		setTitle("SmarTong");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.lightGray);
		clientList = new ClientList(); 
		add(clientList, BorderLayout.WEST);
		clientList.setVisible(true);
		text=new JTextField();
		area = new JTextArea();
//		DefaultListModel d = new DefaultListModel<>();
//		JList list = new JList<>();
//		JScrollPane s = new JScrollPane(list);
//		s.getVerticalScrollBar();
//		s.setVisible(true);
//		s.setSize(300, 700);
//		this.add(s);
		setVisible(true);
	}

	public static void main(String[] args) {
		new View();
	}
}
