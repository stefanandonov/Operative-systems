package aud1;
import java.io.*;
import java.util.Arrays;
import java.util.stream.IntStream;

public class BufferedReadingFromInputStream {
	
	public static void doSomethingWithData (byte [] buffer, int offset){
		StringBuilder sb = new StringBuilder();
		IntStream.range(0, offset).forEach(i -> sb.append(" "));
		for (int i=0;i<buffer.length;i++)
			sb.append((char) buffer[i]);
		
		System.out.println(sb.toString());
	}
	
	public static void correctRead(InputStream in) throws IOException{
		
		try {
			byte [] buffer = new byte[100];
			int readLen=0;
			int leftToRead = 100;
			int offset = 0;
			
			while ((readLen=in.read(buffer, offset, readLen))!=1){
				
				offset+=readLen;
				leftToRead -= readLen;
				System.out.println(readLen+ " " + leftToRead+ " " + offset);
			}
			doSomethingWithData(buffer,offset);
		}
		finally{
			if (in!=null)
				in.close();
		}
		
		
		
	}

	public static void main(String[] args) {
		
		try {
			File f = new File("C:\\Users\\Stefan\\Desktop\\test2.txt");
			System.out.println(f.exists());
			correctRead(new FileInputStream(f));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
