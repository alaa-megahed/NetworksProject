package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;


public class SelectList  extends JPanel{
	ArrayList<JButton> clients ; 
	int count ; 
	 public SelectList(){
		 setLayout(null);
		 JButton chatGp =  new JButton("Gp chat");
		 chatGp.setBounds(600,0,100,50);
		 add(chatGp);
		 validate();
		 
	 }
	 public void setClients (ArrayList<String> c){
		 int i ;
		 for ( i = 0 ; i <c.size() ; i++){
			 JButton b = new JButton(c.get(i));
			 count=i;
			 b.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					clientChosen(c.get(count));
				}
			});
		 }
	 }
	 public void clientChosen(String s){
		 
	 }
	 public static void main(String[] args) {
		JFrame f = new JFrame(); 
		f.setSize(800,800);
		f.setTitle("SmarTongue");
		f.setLocationRelativeTo(null);
		SelectList  b = new SelectList ();
		f.add(b);
		f.validate();
		f.setVisible(true);
	}

}
