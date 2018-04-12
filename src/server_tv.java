import java.util.*;
import java.net.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;


public class server_tv implements ActionListener{

	JPanel p1,p2;
	JFrame f;
	JButton b1;
	static int gtl=-1;
	
	server_tv(){
		
		 b1 = new JButton("Host");
		 b1.addActionListener(this);
		 p1 =new JPanel();
		 f = new JFrame();
		 
		 p1.add(b1);
		 
     	f.setLayout(new BorderLayout());
		f.add(p1,"North");
		f.pack();
		f.setVisible(true);
    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//main
	public static void server_tv_work(Socket s){
			
		try {
			 //new server_tv();
			 
			 //ServerSocket ss = main_gui.sstv;
			 //Socket s = main_gui.stv;
			 //DataInputStream br = new DataInputStream(s.getInputStream());
			 //DataOutputStream wr = new DataOutputStream(s.getOutputStream());
			 
			 Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			 BufferedImage cursor = ImageIO.read(server_tv.class.getResource("cursor.png")); 
			 Robot bot = new Robot();
			 
			 //ArrayList<byte[]> by_st = new ArrayList<byte[]>();
			 
			// BufferedImage screenCapture = bot.createScreenCapture(screen);
			 //ImageIO.write(screenCapture, "png", new File("e:/Java stuff"));
			 
			 
			 
			OutputStream os = s.getOutputStream();
			 
			System.out.println("Server is working");
			 
			 boolean fg = true;
			 
			 while(fg==true){
				 
				 switch(gtl){
				 
				case 1 :
					System.out.println("Nothing happens");
					 break; 
				
				default :
					
					try { 
						
						//BufferedReader br =new BufferedReader(new InputStreamReader(s.getInputStream()));
																
										 BufferedImage screenCapture = bot.createScreenCapture(screen);

									     int x = MouseInfo.getPointerInfo().getLocation().x;
										 int y = MouseInfo.getPointerInfo().getLocation().y;
											
										 Graphics2D graphics2D = screenCapture.createGraphics();
										 graphics2D.drawImage(cursor, x, y, 16, 16, null);	
										 
										 //ImageIO.write(screenCapture, "jpg", s.getOutputStream());
										 
										 //MemoryCacheImageOutputStream byteArray = new MemoryCacheImageOutputStream(s.getOutputStream());
										 //ImageIO.write(screenCapture, "png", byteArray);
										
										//to convert image to byte and send
										 
										 ByteArrayOutputStream baos = new ByteArrayOutputStream();
								
										 ImageIO.write(screenCapture, "png", baos);
										 
										 byte[] byteImage = baos.toByteArray(); 
										
										 
										 ObjectOutputStream oos = new ObjectOutputStream(os);
									     oos.writeObject(byteImage);
									     
										 System.out.println("Data is sending..");
										 //System.out.println(by_st.size());
										 //byteArray.flush();
										 //byteArray.close();
										 oos.flush();
										
						 
					} 
					    catch (Exception ex) {
						ex.printStackTrace();
						s.close();
						fg=false;
						}	
					break;
					
				 	}//switch close
			 	
				 
				 
			 }//while close
			
			 os.close();
			 //main_gui.sstv.close();
			} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		
	}//main close


	@Override
	public void actionPerformed(ActionEvent e) {
		gtl=gtl*-1;
		System.out.println("Sent msg g: "+gtl);
	}//send close
	
}

