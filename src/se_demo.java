import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class se_demo implements ActionListener{

	static JTextArea tb;
	JPanel p1,p2;
	JFrame f;
	JButton b1;
	static JTextArea ta;
	JScrollPane scl;
	static int gtl=0;
	
	se_demo(){
		
		try{
		 
		}catch(Exception e){e.printStackTrace();}
		
		 tb = new JTextArea(5,40);
		 tb.setEditable(true);
		 b1 = new JButton("Send");
		 b1.addActionListener(this);
		 p1 =new JPanel();
		 p2 =new JPanel();
		 f = new JFrame();
		 
		 p2.setLayout(new BorderLayout());
		 p2.add(tb,"West");
		 p2.add(b1,"East");
		 
		 ta = new JTextArea(16, 40);
		 ta.setEditable(false); // set textArea non-editable
	     scl = new JScrollPane(ta);
         scl.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		    //Add Textarea in panel
     	 p1.add(scl);
     	 
     	f.setLayout(new BorderLayout());
		f.add(p1,"North");
		f.add(p2,"Center");
		f.pack();
		f.setVisible(true);
    	//f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//main
	public static void server_chat( Socket s){
			
		try {
			 //new se_demo();
			
			 
			 //ServerSocket ss = main_gui.ssct;
			 //Socket s = main_gui.sct;
			 
			 DataInputStream br = new DataInputStream(s.getInputStream());
			 DataOutputStream wr = new DataOutputStream(s.getOutputStream());
			 
			 boolean fg = true;
			 ta.setText("Server Side");
			 System.out.println("Server side");
			 String sl="";
			 
			 wr.writeUTF(main_gui.nm);
			 wr.flush();
			 
			 while(fg==true){
				 
				 switch(gtl){
				 
				case 1 :
					 try {  
							String sd =main_gui.nm+": "+tb.getText().toString();
							System.out.println("I said:"+sd);
							ta.append("\nYou: "+tb.getText().toString());
							tb.setText("");
							wr.writeUTF(sd);
							wr.flush();	
							gtl=0;
						} catch (Exception ex) {
						ex.printStackTrace();
						s.close();
						}	
					 break; 
				
				default :
					//System.out.println("Nothing happens");
					if(br.available()!=0){
					sl = br.readUTF().toString();
					ta.append("\n"+sl);
					System.out.println(sl);
					}
					break;
				 	}//switch close
				 
				 
			 	}//while close
			 //s.close();
			 //main_gui.ssct.close();
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
