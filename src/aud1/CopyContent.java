package aud1;
import java.io.*;
//import java.util.*;

public class CopyContent {
	
	public static void copyStream(InputStream in, OutputStream out) throws IOException{
		int c;
		try {
			while ((c = in.read())!=-1){
				out.write(c);
			}
		}
		finally {
			if (in!=null)
				in.close();
			if (out!=null)
				out.close();
		}		
	}

	public static void main(String[] args) {
		
		try {
			copyStream(new FileInputStream("C:\\Users\\Stefan\\Desktop\\test1.txt"), 
					new FileOutputStream("C:\\Users\\Stefan\\Desktop\\test2.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
