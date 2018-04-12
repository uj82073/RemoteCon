import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class se_demo_ft extends JPanel implements ActionListener{

	static JTextField tb;
	JButton b1;
	static BufferedReader br;
	static int gtl=0;
	
	se_demo_ft(){
		
	}
	
	//main
	public static void server_ft(Socket s){
		
		try {
			
			//new cl_demo_f();
			
			//String ipa =JOptionPane.showInputDialog(f,"Enter IP address:"); 
			//Socket s = new Socket(ipa,9000);
			
			//servesock = new ServerSocket(9000);
			DataInputStream br =null;
			Socket sock = null;
			sock = s;
			while(true){
			//

			//sock = servesock.accept();
			
			if(sock.isClosed()){
				System.out.println("conn closed");
			}
			else{
				//System.out.println("Conn works");
			}
			br = new DataInputStream(sock.getInputStream());
				
			int bytesRead;
		    int current = 0;
		    FileOutputStream fos = null;
		    BufferedOutputStream bos = null;
		    String fn="",fl="";
		    //br = new DataInputStream(sock.getInputStream());
		    
		    try {
		      
		      //System.out.println("Connecting...");
		      
		      String cl="hii";
		      
			  if(br.available()>0){
					cl = br.readUTF().toString();
					//br.close();
					//ta.append("\n"+cl); 
					 fn = cl.substring(0,cl.lastIndexOf('@'));
					 fl = cl.substring(cl.lastIndexOf('@')+1, cl.length());
					 
					System.out.println("File name: "+fn+ " File Length: "+fl);
					
		      // receive file
		      byte [] mybytearray  = new byte [Integer.parseInt(fl)+100];
		      System.out.println("by: "+mybytearray.length);
		      InputStream is = sock.getInputStream();
		      File fde =new File("C:/Recieve_file"); 
		      if(fde.exists() == false && fde.isDirectory() == false){
		    	  fde.mkdir();
		    	  System.out.println("Directory created");
		      }
		      fos = new FileOutputStream("C:/Recieve_file"+fn);
		      bos = new BufferedOutputStream(fos);
		      bytesRead = is.read(mybytearray,0,mybytearray.length);
		      current = bytesRead;

		      do {
		         bytesRead =
		            is.read(mybytearray, current, (mybytearray.length-current));
		         if(bytesRead >= 0) current += bytesRead;
		      } while(bytesRead > -1);

		      bos.write(mybytearray, 0 , current);
		      bos.flush();
		      System.out.println("File " + "C:/Recieve_file"+cl
		          + " downloaded (" + current + " bytes read)");
			  
			  }//if close
		      
		    }
		    catch(Exception e){
		    e.printStackTrace();
		    br.close();
		    }
		    finally {
		      if (fos != null) fos.close();
		      if (bos != null) bos.close();
		      //if (sock != null) sock.close();
		    }
			
		}//while close
		    
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
