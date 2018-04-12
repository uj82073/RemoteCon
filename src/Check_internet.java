import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Check_internet {

	public boolean getNetStat(){
		
		boolean res;
		try{
		Socket s  = new Socket();
		InetSocketAddress addr = new InetSocketAddress("www.google.com", 80);
		s.connect(addr,3000);
		res=true;
		}
		catch(Exception e){
			e.printStackTrace();
			res = false;
		}
		return res;
	}
	
}
