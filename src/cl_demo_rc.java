import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class cl_demo_rc
	implements MouseMotionListener,KeyListener,MouseListener{

	JPanel p;
	static Socket s;
	static int ml=0; 
	static int mx=0,my=0;
	static int pw=0,ph=0;
	static int wd=0,ht=0;
	static DataOutputStream wr;
	
	cl_demo_rc(){
		client_tv.p1.addMouseMotionListener(this);
		client_tv.p1.addMouseListener(this);
		client_tv.p1.addKeyListener(this);
		//client_tv.p1.setFocusable(true);
	}

	
	public static void client_rc(){
		new cl_demo_rc();
		
		//String ipa =JOptionPane.showInputDialog(f,"Enter IP address:"); 
		//Socket s;
		try {
			
			String ipadr = m_conn.ipa;
			int pna = Integer.parseInt(m_conn.pna)+3;
			
			s = new Socket(ipadr,pna) ;
			//s = new Socket(ipa,8000);
			//DataOutputStream wr = new DataOutputStream(s.getOutputStream());
			DataInputStream br = new DataInputStream(s.getInputStream());
			//wr.writeUTF(mx+" "+my);
			//wr.writeUTF(pw+" "+ph);
			wr = new DataOutputStream(s.getOutputStream());

			while(true){
				if(br.available()!=0){
					String rd = br.readUTF().toString();
					String[] dr = rd.split(" ");
					wd= Integer.parseInt(dr[0]);
					ht= Integer.parseInt(dr[1]);
				}
			}
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		mx= e.getX();
		my= e.getY();
		
		try {
			pw = client_tv.pw;
			ph = client_tv.ph;
			if(client_tv.gtl==1){
			wr.writeUTF("1");
			int rx = (int) (((double)mx/(double)pw)*wd);
			int ry = (int) (((double)my/(double)ph)*ht);
			wr.writeUTF(rx+" "+ry);
			wr.flush();
			}
			//wr.writeUTF("Width "+width+" Height "+height);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Mouse moved");
		ml=1;
	}//mv closed


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		try {
		if(client_tv.gtl==1){
		wr.writeUTF("2");
		int mp = e.getButton();
		System.out.println("Mouse pressed"); 
		int xButton = 16;
	        if (mp == 3) {
	            xButton = 4;
	        }
	        wr.writeUTF(xButton+"");
	        wr.flush();}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		try {
			if(client_tv.gtl==1){
			wr.writeUTF("3");
			int mp = e.getButton();
			System.out.println("Mouse pressed");
			 int xButton = 16;
		        if (mp == 3) {
		            xButton = 4;
		        }
		        wr.writeUTF(xButton+"");
		        wr.flush();}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Key pressed");
		try {			
			if(client_tv.gtl==1){
			wr.writeUTF("4");
			int kp = e.getKeyCode();
		        wr.writeUTF(kp+"");
		        wr.flush();}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("Key pressed");
		try {
			if(client_tv.gtl==1){
			wr.writeUTF("5");
			int kp = e.getKeyCode();
		        wr.writeUTF(kp+"");
		        wr.flush();}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


}
