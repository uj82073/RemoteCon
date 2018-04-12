import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class cl_demo_ft extends JPanel implements ActionListener{

	static JTextField tb;
	JPanel p1,p2,p3;
	JButton b1,b2;
	static JTextArea ta;
	JScrollPane scl;
	static int gtl=0;
	static File[] fl;
	JLabel l1,l2,l3;
	JButton[] bta;
	ArrayList<String> fsl = new ArrayList<String>();
	ArrayList<String> fph = new ArrayList<String>();
	static String ipadr;
	static int pna;
	static Socket s ;
		
	cl_demo_ft(){
		
		 l1 = new JLabel("File Name");
		 l1.setBorder(BorderFactory.createLineBorder(Color.black));
		 l2 = new JLabel("File Path");
		 l2.setBorder(BorderFactory.createLineBorder(Color.black));
		 l3 = new JLabel("Actions");
		 l3.setBorder(BorderFactory.createLineBorder(Color.black));
		 b1 = new JButton("Choose Files");
		 b1.addActionListener(this);
		 b2 = new JButton("Send Files");
		 b2.addActionListener(this);
		 p1 =new JPanel();
		 p2 =new JPanel();
		 
		 p2.setLayout(new GridLayout(1,2));
		 p2.add(b1);
		 p2.add(b2);
		 
		 //p1.setPreferredSize(new Dimension(50,30));
		 p1.setBackground(new Color(255,255,255));
		 p1.setLayout(new GridLayout(1, 3));
		 p1.add(l1);p1.add(l2);
		 p1.add(l3);
		  // set textArea non-editable
	     scl = new JScrollPane(p1);
         scl.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		 
     	setLayout(new BorderLayout());
     	//setPreferredSize(new Dimension(500, 510));
     	setBackground(new Color(255,255,255));
		add(scl,"Center");
		add(p2,"South");
		
	}
	
	//main
	public static void client_ft(){
			
		try {
			 //new cl_demo_ft();
			ipadr =m_conn.ipa; 
			pna = Integer.parseInt(m_conn.pna)+2;
			s = new Socket(ipadr,pna);
			/*
			 ServerSocket ss = new ServerSocket(8000);
			 Socket s = ss.accept();
			 DataInputStream br = new DataInputStream(s.getInputStream());
			 DataOutputStream wr = new DataOutputStream(s.getOutputStream());
			 
			 boolean fg = true;
			 ta.setText("Server Side");
			 System.out.println("Server side");
			 String sl="";
			 
			 while(fg==true){
				 
				 switch(gtl){
				 
				case 1 :
					 try {  
							//BufferedReader br =new BufferedReader(new InputStreamReader(s.getInputStream()));
							String sd ="Server : "+tb.getText();
							System.out.println("I said:"+sd);
							ta.append("\nYou: "+tb.getText());
							tb.setText("");
							wr.writeUTF(sd);
							wr.flush();	
							gtl=0;
						} catch (Exception ex) {
						ex.printStackTrace();
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
			 	}
			*/
			} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}//main close


	@Override
	//to get actions by button
	public void actionPerformed(ActionEvent e) {
		//gtl=1;
		//System.out.println("Sent msg g: "+gtl);
		switch(e.getActionCommand()){
		
		case "Send Files":
			System.out.println("Inside send file");
			for(int h=0;h<fsl.size();h++){
				String fpt = fph.get(h).replace('\\', '/');
				//String fpt = fph.get(h);
				int l = fpt.lastIndexOf('/');
				String fn = fpt.substring(l, fpt.length());
				System.out.println("Filepath sent: "+fn);
				
				try {
					if(s.isClosed()){
					s = new Socket(ipadr,pna); }
					send_file(fpt,fn,s);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			break;
			
		case "Choose Files":
			System.out.println("Inside choose file");
			JFileChooser fc=new JFileChooser();
			fc.setDialogTitle("Select files to Send");
			fc.setMultiSelectionEnabled(true);
		    int i=fc.showDialog(null,"Choose");    
		    if(i==JFileChooser.APPROVE_OPTION){
		    	gtl=1;
		    	File[] files = fc.getSelectedFiles();
		    	fl = files; 
		    	
		    	System.out.println("Files Found\n");
				Arrays.asList(files).forEach(x -> {
					if (x.isFile()) {
						//System.out.println(x.getName());
						String fname= x.getName();
						String filepath=x.getPath();
						fsl.add(fname);
						fph.add(filepath);
				        System.out.println("File Path: "+filepath);
					}
				});
				lt_up();
		        }//if close
			break;
			
		case "Remove":
			System.out.println("Removing this item");
			for(int l = 0;l<bta.length;l++){
			if(e.getSource()==bta[l]){
				System.out.println("Button number: "+l);
				fsl.remove(l);
				fph.remove(l);
				lt_up();
				}
			}
			break;
			
		default:
			System.out.println("Nothing happens");
			break;
		}
	}//send close

	//to update list
	private void lt_up() {
		p1.removeAll();
		p1.setLayout(new GridLayout(fsl.size()+1, 3));
		JLabel lb1 = new JLabel("File Name");
		lb1.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel lb2 = new JLabel("File Path");
		lb2.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel lb3 = new JLabel("Actions");
		lb3.setBorder(BorderFactory.createLineBorder(Color.black));
		p1.add(lb1);p1.add(lb2);p1.add(lb3);
		p1.setBorder(BorderFactory.createLineBorder(Color.black));
		
		bta = new JButton[fsl.size()];
		
		for(int p=0;p<fsl.size();p++){
			JLabel lbf = new JLabel(fsl.get(p));
			lbf.setForeground(Color.GREEN);
			lbf.setBorder(BorderFactory.createLineBorder(Color.black));
			p1.add(lbf);
			JLabel lbfp = new JLabel(fph.get(p));
			lbfp.setBorder(BorderFactory.createLineBorder(Color.black));
			p1.add(lbfp);
			bta[p] = new JButton("Remove");
			bta[p].setBorder(BorderFactory.createLineBorder(Color.black));
			bta[p].addActionListener(this);
			p1.add(bta[p]);
		}
		p1.validate();
		p1.repaint();
	}
	
	//to send a file
	public void send_file(String fp,String fn,Socket s){
		FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    OutputStream os = null;
	    ServerSocket servsock = null;
	    Socket sock = null;
	    DataOutputStream wr = null;
	    try {
	    
	         
			 //servsock = new ServerSocket(9000);
	      
	         System.out.println("Waiting...");
	         try {
	        	 
	        	sock=s; 
	          //sock = servsock.accept();
	          
	          wr = new DataOutputStream(sock.getOutputStream());
	          String sd = fn;	
				
	          System.out.println("Accepted connection : " + sock);
	          // send file
	          File myFile = new File (fp);
	          byte [] mybytearray  = new byte [(int)myFile.length()];
	          
	          sd = sd+"@"+(int)myFile.length();
	          System.out.println("File sent is "+sd);
	          wr.writeUTF(sd);
			  wr.flush();
	          
	          fis = new FileInputStream(myFile);
	          bis = new BufferedInputStream(fis);
	          bis.read(mybytearray,0,mybytearray.length);
	          os = sock.getOutputStream();
	          System.out.println("Sending "+fp+"(" + mybytearray.length + " bytes)");
	          os.write(mybytearray,0,mybytearray.length);
	          os.flush();
	          System.out.println("Done.");
	        }
	        catch(Exception e){
	        	e.printStackTrace();
	        }
	        finally {
	        try{
	          if (bis != null) bis.close();
	          if (os != null) os.close();
	          if (sock!=null) sock.close();
	          }catch(Exception e){e.printStackTrace();}
	        }
	      
	    }
	    catch(Exception e){e.printStackTrace();}
	    finally {
	    	try{ if (servsock != null) servsock.close(); }
	    	catch(Exception e){e.printStackTrace();}
	    }
	}
	
}
