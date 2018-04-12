import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class se_demo_rc {
	
	static int w =0;
	static int h=0;
	
	public static void server_rc(Socket st){
		
		//new se_demo_rc();
		
		try {
			
			Socket s = st;
			
			Robot bot = new Robot();
			
			System.out.println("Linked success");
			
			DataInputStream br = new DataInputStream(s.getInputStream());
			
			DataOutputStream wr = new DataOutputStream(s.getOutputStream());
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int width = (int) screenSize.getWidth();
			int height = (int) screenSize.getHeight();
			
			
			while(true){
				
				if(w!=width && h!=height){
				w=width;
				h=height;
				wr.writeUTF(w+" "+h);
				wr.flush();
				}
				
				if(br.available()!=0){
					
					int se = Integer.parseInt(br.readUTF().toString());
					switch(se){
					case 1:
						String rd = br.readUTF().toString();
						//System.out.println(rd);
						String[] dr = rd.split(" ");
						int mx = Integer.parseInt(dr[0]);
						int my = Integer.parseInt(dr[1]);
						System.out.println(mx+" "+my);
						bot.mouseMove(mx, my);
						break;
						
					case 2:
						int mp = Integer.parseInt(br.readUTF().toString());
						bot.mousePress(mp);
						break;
					
					case 3:
						int mr = Integer.parseInt(br.readUTF().toString());
						bot.mouseRelease(mr);
						break;
						
					case 4:
						int kp = Integer.parseInt(br.readUTF().toString());
						bot.keyPress(kp);
						break;
						
					case 5:
						int kr = Integer.parseInt(br.readUTF().toString());
						bot.keyRelease(kr);
						break;
				
					}//switch
					
				}
				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//main
	
}
