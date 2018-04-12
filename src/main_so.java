import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.regex.Pattern;

import javax.swing.*;

import layout.SpringUtilities;

public class main_so implements ActionListener {

	JFrame f;
	JLabel l1,l2,l3;
	JPanel p1;
	JTextField t1,t2;

	main_so(){
		
		 l1 = new JLabel("Set Default Port number:");
		 l2 = new JLabel("Set New Name:");
		
		 t1 = new JTextField(10);
		 t2 = new JTextField(10);
		
		 t1.setText("Enter Port Address");
		 t2.setText("Enter Username");
		 
		 
		JButton b1 = new JButton("Save");
		JButton b3 = new JButton("Clear");
		JButton b2 = new JButton("Cancel");
		JPanel p1 =new JPanel();
		JPanel p2 =new JPanel();
		//t1.addActionListener(this);
		
		p1.setLayout(new SpringLayout());
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		
		p1.add(l1);
		p1.add(t1);
		p1.add(l2);
		p1.add(t2);
		
		SpringUtilities.makeCompactGrid(p1, //parent
                2, 2,
                6, 6,  //initX, initY
                6, 6); //xPad, yPad
		
		p2.add(b1);
		p2.add(b3);
		p2.add(b2);
		
		f = new JFrame();
		f.setLayout(new GridLayout(2, 1));
		f.add(p1);
		f.add(p2);
		
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.pack();
		f.setResizable(false);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()){
		
		case "Save":
			
			if(!t2.getText().equals(null) && !t2.getText().trim().equals("")){
				main_gui.nm = t2.getText();
				System.out.println("Data Changed: Name");
				JOptionPane.showMessageDialog(f,
					    "Username changed",
					    "Updated",
					    JOptionPane.INFORMATION_MESSAGE);
				t2.setText("");
			}
		
			Pattern p_po = Pattern.compile(
			        "^[0-9]{1,5}$");
			
			if(!t1.getText().equals(null) && !t1.getText().equals("") ){
				if(m_conn.validate(t1.getText(), p_po)){
			int nt = Integer.parseInt(t1.getText().toString());
			try {
					main_gui.sstv = new ServerSocket(nt);
					main_gui.ssct = new ServerSocket(nt+1);
					main_gui.ssft = new ServerSocket(nt+2);
					main_gui.ssrc = new ServerSocket(nt+3);
					System.out.println("Data Changed: port no.");
					JOptionPane.showMessageDialog(f,
						    "Port number changed",
						    "Updated",
						    JOptionPane.INFORMATION_MESSAGE);
					t1.setText("");
					
					main_gui.tv = nt;
			}
			
			catch (BindException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(f,
					    "Port Already in use",
					    "Update Error",
					    JOptionPane.WARNING_MESSAGE);
				}
			catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
				
				}
			
				}
				else{
					JOptionPane.showMessageDialog(f,
						    "Port number invalid",
						    "Update Error",
						    JOptionPane.WARNING_MESSAGE);
				}
			}
			
			break;
			
		case "Cancel":
			f.dispose();
			break;
		
		case "Clear":
			t1.setText("");
			t2.setText("");
			t1.requestFocus();
			break;
		}
		
	}
	
}
