package lab1;
import java.io.*;
import java.util.*;

public class Lab01_02 {
	
	public static void reverseCopying (RandomAccessFile raf, OutputStream os) throws IOException{
		
		long length = raf.length()-1;
		byte b;
		
		while (length > -1){
			raf.seek(length);
			b = raf.readByte();
			os.write(b);
			--length;
		}
	}

	public static void main(String[] args) throws IOException {
		
		RandomAccessFile raf = null;
		OutputStream os = null;
		
		try {
			raf = new RandomAccessFile(new File("C:\\Users\\Stefan\\workspace\\OS2018_JavaIO\\src\\lab1\\izvor.txt"),"r");
			os = new FileOutputStream(new File("C:\\Users\\Stefan\\workspace\\OS2018_JavaIO\\src\\lab1\\destinacija.txt"));
			
			reverseCopying(raf,os);
		}
		finally{
			if (raf!=null)
				raf.close();
			if (os!=null)
				os.close();
		}

	}

}
