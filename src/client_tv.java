import java.util.*;
import java.net.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;


public class client_tv extends JPanel 
	implements ActionListener, ComponentListener{

	
	public static JPanel p1,p2;
	static JFrame f;
	JButton b1;
	static JLabel lb;
	static Socket s;
	static int gtl=1;
	static int pw,ph=0;
	static int g=0;
	
	client_tv(){
		
		
		 lb = new JLabel("",JLabel.CENTER); 
		 b1 = new JButton("STOP");
		 b1.addActionListener(this);
		 p1 =new JPanel();
		 p2 =new JPanel();
		 //f = new JFrame();
		 
		 p2.add(b1);
     	 p1.add(lb);
     	 p1.addComponentListener(this);
     	 
     	 setLayout(new BorderLayout());
     	 add(p1,"Center");
     	 add(p2,"South");
     	 p1.setFocusable(true);
     	 /*f.pack();
     	 f.setVisible(true);
     	 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); */
	}
	
	//main
	public static void client_tv_work(){
		
		try {
			
			//new client_tv();
			
			String ipadr = m_conn.ipa+"";
			int pna = Integer.parseInt(m_conn.pna);			
			
			Socket s = new Socket(ipadr,pna);
			//DataInputStream br = new DataInputStream(s.getInputStream());
			//DataOutputStream wr = new DataOutputStream(s.getOutputStream());
			
			
			int ws = 0,wh=0;
			boolean fg = true;
			
			System.out.println("Outside loop");
			while(fg==true){
				
				System.out.println("Inside loop");
				switch(gtl){
					
				case 1:
					try {
						//BufferedImage img =ImageIO.read(ImageIO.createImageInputStream(s.getInputStream()));
						//img.flush();
						//MemoryCacheImageInputStream ois = new MemoryCacheImageInputStream(s.getInputStream());
			            //BufferedImage img = ImageIO.read(ois);
						
						//wr.writeUTF("TV server online");
						//wr.flush();
						ObjectInputStream ois = new ObjectInputStream(s.getInputStream()); 
						byte[] byteImage = (byte[])ois.readObject();
						InputStream in = new ByteArrayInputStream(byteImage);
						BufferedImage img = ImageIO.read(in);
						
			            if(img!=null){
			            	//if(img.getWidth()==ws){
						//BufferedImage img = ImageIO.read(s.getInputStream());
			            //Image nm = img.getScaledInstance(pw, ph, Image.SCALE_DEFAULT);
			            Image nm = getScaledImage(img,pw,ph);	
			            System.out.println("Image type : "+img.getType());
						ImageIcon ic = new ImageIcon(nm);
						lb.setIcon(ic);}
			            //}
						//Thread.sleep(4000);
					} 
					catch (Exception ex) {
					ex.printStackTrace();
					s.close();
					}	
					break;
					
					default :
						System.out.println("Nothing happens");
						break;
						
				}//switch
				
				
			}//while close
			
			if(m_conn.in_form == 1){
				s.close(); }
			
			} catch (Exception ex) {
			ex.printStackTrace();
			//s.close();
			}
		
		}//main close

@Override
	public void actionPerformed(ActionEvent e) {
	gtl=gtl*-1;
	System.out.println("Sent msg g: "+gtl);
	if(gtl!=1){
		b1.setText("PLAY");
		}
	else{
		b1.setText("STOP");
		}
	}//connect

@Override
public void componentHidden(ComponentEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void componentMoved(ComponentEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void componentResized(ComponentEvent e1) {
	// TODO Auto-generated method stub
	pw=p1.getWidth();
	ph=p1.getHeight();
}

@Override
public void componentShown(ComponentEvent e2) {
	// TODO Auto-generated method stub
	pw=p1.getWidth();
	ph=p1.getHeight();
}

//to scale image
public static BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
    int imageWidth  = image.getWidth();
    int imageHeight = image.getHeight();

    double scaleX = (double)width/imageWidth;
    double scaleY = (double)height/imageHeight;
    AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
    AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

    return bilinearScaleOp.filter(
        image,
        new BufferedImage(width, height, image.getType()));
}
	
}//class close
