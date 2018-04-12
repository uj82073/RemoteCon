import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class cl_demo extends JPanel implements ActionListener{

	static JTextArea tb;
	JPanel p1,p2;
	static JFrame f;
	JButton b1;
	static JTextArea ta;
	JScrollPane scl;
	static Socket s;
	static int gtl=0;
	
	cl_demo(){
		
		 tb = new JTextArea(5, 40);
		 b1 = new JButton("Send");
		 b1.addActionListener(this);
		 p1 =new JPanel();
		 p2 =new JPanel();
		 //f = new JFrame();
		 
		 p2.setLayout(new BorderLayout());
		 p2.add(tb,"Center");
		 p2.add(b1,"East");
		 
		 ta = new JTextArea(16, 40);
		 ta.setEditable(false); // set textArea non-editable
	     scl = new JScrollPane(ta);
         scl.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		    //Add Textarea in panel
     	 p1.add(scl);
     	 setLayout(new BorderLayout());
     	 add(p1,"North");
     	 add(p2,"Center");

     	 /*
     	 f.pack();
     	 f.setVisible(true);
     	 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			*/
	}
	

	//main
	public static void client_chat(){
		
		try {
			
			//new cl_demo();
			
			String ipadr =m_conn.ipa; 
			int pna = Integer.parseInt(m_conn.pna)+1;
			
			Socket s = new Socket(ipadr,pna);
			DataInputStream br = new DataInputStream(s.getInputStream());
			DataOutputStream wr = new DataOutputStream(s.getOutputStream());
			 
			boolean fg = true;
			int g=0;
			ta.setText("Client Side");
			System.out.println("Inside chat Client side");
			
			wr.writeUTF(main_gui.nm);
			wr.flush();
			
			String usr = br.readUTF().toString();
			System.out.println("otherName is : "+usr);
			main_gui.al.add(usr);
			System.out.println("Now Al size: "+main_gui.al.size()+" & "+main_gui.alen);
			
			while(fg==true){
				
				switch(gtl){
					
				case 1:
					try {
						String sd =main_gui.nm+": "+tb.getText().toString();
						System.out.println("I said:"+sd);
						ta.append("\nYou: "+tb.getText().toString());
						tb.setText("");
						wr.writeUTF(sd);
						wr.flush();	
						//wr.close();
						gtl=0;
					} catch (Exception ex) {
					ex.printStackTrace();
					}	
					break;
					
					default :
						//System.out.println("Nothing happens");
						if(br.available()!=0){
						String cl = br.readUTF().toString();
						ta.append("\n"+cl);
						System.out.println(cl);
						}
						break;
						
				}//switch
				
			}
			if(m_conn.in_form == 1){
				s.close();
			}
			
			} catch (Exception ex) {
			ex.printStackTrace();
			}
		}//main close

@Override
	public void actionPerformed(ActionEvent e) {
	gtl=1;
	System.out.println("Sent msg g: "+gtl);
	}//send close

	
}
