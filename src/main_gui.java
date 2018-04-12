import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class main_gui implements ActionListener,WindowListener {
	
	public static ServerSocket sstv;
	//public static Socket stv;
	
	public static ServerSocket ssct;
	//public static Socket sct;
	
	public static ServerSocket ssft;
	
	public static ServerSocket ssrc;
	
	public static ArrayList<String> al = new ArrayList<String>();
	
	public static int tv=8000;
	public static int ct=tv+1;
	public static int ft=tv+2;
	public static int rc=tv+3;
	
	public static String nm = "Demo User";
	
	JTabbedPane jtp;
	static JFrame f;
	static JPanel p1;
	JPanel p2;
	JButton b1,b2;
	static int alen = al.size();
	static JLabel lb;
	static Thread th0,th1,th2,th3,th4,th5;
	
	main_gui(){//
		
		JButton b2 = new JButton("Left");
		
		//For the left side of gui
		p1= new JPanel();
		JLabel lb = new JLabel("Users:");
		//JLabel lb1 = new JLabel("UJ");
		System.out.println("Al size: "+al.size());
		p1.setLayout(new GridLayout(18,1));
		p1.add(lb);
		//p1.add(lb1);
		p1.setPreferredSize(new Dimension(70,50));
		p1.setBackground(Color.WHITE);
		p1.setBorder(BorderFactory.createLoweredBevelBorder());
		//p1.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		
		//for the Right side of gui
		JPanel p3= new JPanel();
		//p3.setPreferredSize(new Dimension(300,200));
		p3.setLayout(new BorderLayout());
		p3.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		JLabel lb2 = new JLabel("RemoteCon",SwingConstants.CENTER);
		lb2.setForeground(Color.WHITE);
		lb2.setOpaque(true);
		lb2.setBackground(Color.BLUE);
		lb2.setFont(lb2.getFont().deriveFont(40.0f));
		System.out.println(lb2.getAlignmentX());
		p3.add(lb2,"North");
		JTextArea jta = new JTextArea(
                "\n Instructions:".toUpperCase() +
                "\n\n 1. Click on Connect, To connect to another pc." +
                "\n 2. After Clicking Connect, Enter the details and Click OK."
        );
		jta.setEditable(false);
		p3.add(jta,"Center");
		//p3.add(b2,"Center");
		
		
		//for the bottom side of gui
		JPanel p4= new JPanel();
		//p4.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		JButton bp1 =new JButton("Settings");
		JButton bp2 =new JButton("Connect");
		JButton bp3 =new JButton("Details");
		p4.add(bp3);
		p4.add(bp1);
		p4.add(bp2);
		bp1.addActionListener(this);
		bp2.addActionListener(this);
		bp3.addActionListener(this);
		
		JPanel p2= new JPanel();
		p2.setLayout(new BorderLayout());
		p2.add(p3,"Center");
		p2.add(p4,"South");
		//p2.setPreferredSize(new Dimension(800,800));
		//p2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		p2.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		
		//for the frame
		f = new JFrame("RemoteCon");
		f.addWindowListener(this);
		f.setLayout(new BorderLayout());
		f.add(p1,"West");
		f.add(p2,"Center");
		f.setVisible(true);
		f.setSize(500, 400);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			ssct = new ServerSocket(ct);
			sstv = new ServerSocket(tv);
			ssft = new ServerSocket(ft);
			ssrc = new ServerSocket(rc);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}//constructor
	
	public static void main(String args[]){
		main_gui mg = new main_gui();
		
		
		try {
			
			//to check internet connection
			th1 = new Thread(
					new Runnable(){

						@Override
						public void run() {
							while(true){
							
								Process process;
								try {
									process = java.lang.Runtime.getRuntime().exec("ping www.google.com");
									int x = process.waitFor();
							        if (x == 0) {
							            //System.out.println("Connection Successful, "+ "Output was " + x);
							        }
							        else {
							            System.out.println("Internet Not Connected, "
							                               + "Output was " + x);
							            JOptionPane.showMessageDialog(f,
												"No Internet Connection!",
												"Error",
											    JOptionPane.ERROR_MESSAGE);
							            f.dispose();
							            System.exit(0);
							        }
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						        
							
							}//while close
							
						}});
			th1.start();
			
			//to check connected users
			th1 = new Thread(
					new Runnable(){

						@Override
						public void run() {
							while(true){
							
								if(alen!=al.size()){
								p1.removeAll();
								System.out.println("You changed the size!!");
								p1.setLayout(new GridLayout(18,1));//size of new list
								JLabel lb1 = new JLabel("Users:");
								p1.add(lb1);
								alen=al.size();
								for(int i=0;i<al.size();i++){
									JLabel jl = new JLabel(al.get(i));
									jl.setForeground(Color.GREEN);
									p1.add(jl);
									}
								p1.validate();
								p1.repaint();
								}
								//System.out.println("Im here");
								//System.out.println("Al size is : "+al.size());
								//System.out.println("OAl size is : "+alen);
							}
							
						}});
			th1.start();
			//thread for online stat closed
			
			
			//for multiple connections 
			while(true){
			
			Socket stv = sstv.accept();
			
			Socket sct = ssct.accept();
			
			Socket sft = ssft.accept();
			
			Socket serc = ssrc.accept();
			
			if(sstv.isClosed()){
				stv.close();
				sct.close();
				sft.close();
				serc.close();
			}
			
			
			//to start chatbox
			th2 = new Thread(
					new Runnable(){
						public void run() {
							 try {
							 
							 if(sct.isConnected()){
							 String usr = new DataInputStream(sct.getInputStream()).readUTF().toString(); 
							 System.out.println("Name is : "+usr);
							 //sct.close();
							 al.add(usr);
							 new se_demo();
							 se_demo.server_chat(sct);
							 }
							} catch (Exception e) {
								e.printStackTrace();
								//sct.close();
							}	
						}
					}
			);
			th2.start(); // thread chat
			
			
			//to start tvbox
			th3 = new Thread(
					new Runnable(){
						public void run() {
							 try {
																
								if(stv.isConnected()){
								//stv.close();
								server_tv.server_tv_work(stv);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}	
						}
					}
			);
			th3.start(); // thread tv
			
			//to start remote control
			th4 = new Thread(
					new Runnable(){
						public void run() {
							 try {
																
								if(serc.isConnected()){
								//stv.close();
									se_demo_rc.server_rc(serc);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}	
						}
					}
			);
			th4.start(); // thread rc
			
			//to start file transfer
			th5 = new Thread(
					new Runnable(){
						public void run() {
							 try {
																
								if(sft.isConnected()){
								//stv.close();
								se_demo_ft.server_ft(sft);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}	
						}
					}
			);
			th5.start(); // thread ft
			
			
			
			//sct.close();
			//stv.close();
			}//while 2 close
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
			}
		
		
		
	}//main close

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()){	
		
		case "Connect":
			new m_conn();
			m_conn.m_conn_gui();
			System.out.println("con Al size: "+al.size());
			break;
			
		case "Settings":
			new main_so();
			break;
			
		case "Details":
			InetAddress localhost;
			try {
				localhost = InetAddress.getLocalHost();
				System.out.println("System IP Address : " +
	                      (localhost.getHostAddress()).trim());
			JOptionPane.showMessageDialog(f,
						" Username: "+nm+
				    	"\n Port Address: "+tv+
				    	"\n IP Address: "+localhost.getHostAddress(),
						"Details",
					    JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		}
		
	}//events closed

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e2) {
				//System.out.println("At last closed");
	}

	@Override
	public void windowClosing(WindowEvent e3) {
		// TODO Auto-generated method stub
		System.out.println("Close main_gui");
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
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
