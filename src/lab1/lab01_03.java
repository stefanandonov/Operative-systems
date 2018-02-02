package lab1;

import java.io.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public class lab01_03 {

public static void reverseCopying (BufferedInputStream is, BufferedOutputStream os) throws IOException{
		
		int c;
		StringBuilder sb = new StringBuilder();
		
		while ((c=is.read())!=-1){
			sb.append((char) c);
		}
		
		os.write(sb.reverse().toString().getBytes());
		os.flush();
	}

	public static void main(String[] args) throws IOException {
		
		String SOURCE = "C:\\Users\\Stefan\\workspace\\OS2018_JavaIO\\src\\lab1\\izvor.txt";
		String DESTINATION = "C:\\Users\\Stefan\\workspace\\OS2018_JavaIO\\src\\lab1\\destinacija.txt";
		
		BufferedInputStream is = null;
		BufferedOutputStream os = null;
		
		try {
			is = new BufferedInputStream(new FileInputStream(SOURCE));
			os = new BufferedOutputStream(new FileOutputStream(DESTINATION));
			
			reverseCopying(is,os);
		}
		finally{
			if (is!=null)
				is.close();
			if (os!=null)
				os.close();
		}

	}

}
