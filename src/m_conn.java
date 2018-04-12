import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.*;

public class m_conn implements WindowListener{

	public static String ipa;
	public static String pna;
	
	public static int in_form=0;
	static boolean ogui = false; 
	
	JFrame f;
	JTextField ip_t,u_t,po_t;
	JTextField pn_t;
	static Thread th1,th2,th3,th4;
	
	private static Pattern p_ip,p_po;
	
	m_conn(){
		
		JTabbedPane jtp = new JTabbedPane();
		f =new JFrame();
		JPanel p1 = new client_tv();
		JPanel p2 = new cl_demo();
		JPanel p3 = new cl_demo_ft();
		
		
		jtp.addTab("Remote TV", null, p1,
                "Display Remote PC Screen");
		jtp.addTab("Chat", null, p2,
                "Chat to Remote PC");
		jtp.addTab("File Transfer", null, p3,
                "Send Files");
		
		p_ip = Pattern.compile(
		        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

		p_po = Pattern.compile(
		        "^[0-9]{1,5}$");

		//custom joption
		
		  ip_t = new JTextField(5);
	      pn_t = new JTextField(5);

	      
	      JPanel myPanel_m = new JPanel();
	      myPanel_m.setLayout(new GridLayout(2,3,15,5));
	      myPanel_m.add(new JLabel("Enter IP Address"));
	      myPanel_m.add(new JLabel(":"));
	      myPanel_m.add(ip_t);
	      //myPanel.add(new JLabel(" ")); // a spacer
	      myPanel_m.add(new JLabel("Enter Port Address"));
	      myPanel_m.add(new JLabel(":"));
	      myPanel_m.add(pn_t);
	  
	    	  
	    
	    		  System.out.println("Bye");
	    		  int result = JOptionPane.showConfirmDialog(null, myPanel_m, 
	   	               "Enter Details:", JOptionPane.OK_CANCEL_OPTION);
	   	      
	   	      if (result == JOptionPane.OK_OPTION) {
	   	    	  System.out.println("ip "+ip_t.getText()+" pn "+pn_t.getText());
	   	    	  if(validate(ip_t.getText(),p_ip) && validate(pn_t.getText(),p_po)){
	   	    		  ipa = ip_t.getText();
	   		    	  pna = pn_t.getText();
	   		    	  ogui = true;
	   	    	  }
	   	    	  else{
	   	    		  JOptionPane.showMessageDialog(f,
	   						    "Enter Proper I.P. Address or Proper Port Number",
	   						    "Connecting Error",
	   						    JOptionPane.WARNING_MESSAGE);
	   	    		  ogui=false;}
	   	      	}
	   	      
	   	      if (result == JOptionPane.CANCEL_OPTION) {
	   	    	  ipa = null;
	   	    	  pna = null;
	   	    	  ogui = false;
	   	    	  //f.dispose();
	   	      	}
	    	  
	     
	      
		f.add(jtp);
		f.addWindowListener(this);
		f.setPreferredSize(new Dimension(590,510));
		if(ogui==true){
		f.setVisible(true);}
		f.pack();
		//f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	



	public static boolean validate(final String d, Pattern p) {
	    return p.matcher(d).matches();
	}
	
	public static void m_conn_gui(){
		
		System.out.println("OGUI : "+ogui);
		if(ogui == true){
			
		th1 = new Thread(
				new Runnable(){
					public void run() {
						 try {
							 client_tv.client_tv_work();
							 System.out.println("Starting tv");
						} catch (Exception e) {
							e.printStackTrace();
						}	
					}
				}
		);
		th1.start();
		
		
		th2 = new Thread(
				new Runnable(){
					public void run() {
						 try {
						 cl_demo.client_chat();
						 System.out.println("Starting chat");
						} catch (Exception e) {
							e.printStackTrace();
						}	
					}
				}
		);
		th2.start();
		
		
		th3 = new Thread(
				new Runnable(){
					public void run() {
						 try {
						 cl_demo_rc.client_rc();
						 System.out.println("Starting Remote control");
						} catch (Exception e) {
							e.printStackTrace();
						}	
					}
				}
		);
		th3.start();
		
		
		th4 = new Thread(
				new Runnable(){
					public void run() {
						 try {
						 cl_demo_ft.client_ft();
						 System.out.println("Starting file transfer");
						} catch (Exception e) {
							e.printStackTrace();
						}	
					}
				}
		);
		th4.start();
		
		
		}//close if
		
	}

	@Override
	public void windowActivated(WindowEvent e1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e2) {
			
	}

	@Override
	public void windowClosing(WindowEvent e3) {
		System.out.println("close mconn");
		
		in_form=1;
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e1) {
		// TODO Auto-generated method stub
		ip_t.requestFocus();
	}

}
