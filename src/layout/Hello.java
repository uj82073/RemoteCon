package layout;
import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.util.*;

//not in use
//Program to read and reverse the text of file .txt

public class Hello {
	//private static Formatter x;

	public static void main(String[]args){
		try{
		
		//Another method to read a file
		//Scanner sc = new Scanner(new File("D:\\uj.txt"));
			
			
		//Scanner sc = new Scanner(System.in); 
		
		File f = new File("D:\\uj.txt");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		System.out.println("Text in file");
		String s;
		s = br.readLine();
		System.out.println(s);
		  
		//x = new Formatter("D:\\uj.txt");
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
		System.out.println("Text will be reversed");
		StringBuffer sd= new StringBuffer(s);
		String sw = sd.reverse().toString();
		//x.format("%s",sw);
		bw.write(sw);
		System.out.println(sw);
		//x.close();
		bw.close();
		}
		
		catch(Exception e){
			e.printStackTrace();
		}

			
		}
}
