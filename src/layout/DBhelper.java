package layout;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBhelper {

	static Connection cn;
	
	public static Connection getConn(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/remcon","root","uj1234");
			System.out.println("Success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cn;
	}
	
}
