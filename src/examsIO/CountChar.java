package examsIO;
import java.io.*;
import java.util.*;

public class CountChar {
	
	private static List<byte []> readTheChars (InputStream is) throws IOException	{
		byte [] bytes = new byte[0];
		int c;
		List<byte []> result = new ArrayList<>();
		boolean flag=true;
		int i=0;
		while ((c=is.read())!=-1){
			if (flag){
				result.add(bytes);
				bytes = new byte [c-'0'];
				i=0;
				flag=false;
			}
			else {
				bytes[i]=(byte) c;
				if (i==bytes.length-1)						
					flag=true;
				else 
					i++;
			}
		}
		result.add(bytes);
		result.remove(0);
		return result;
	}
	
	public static List<byte[]> read (String input) throws IOException{
		
		InputStream is = null;
		List<byte []> res = new ArrayList<>();
		
		try {
			is = new FileInputStream(new File(input));
			res = readTheChars(is);
			
		}
		finally {
			if (is!=null)
				is.close();			
		}
		
		return res;		
	}
	
	public static void testTheFunction (String input) {
		List<byte[]> list = new ArrayList<>();
		try {
			list = read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<List<Character>> toPrint = new ArrayList<>();
		list.stream().forEach(byteArray -> {
			List<Character> chars = new ArrayList<>();
			for (byte b : byteArray){
				chars.add((char) b);
			}
			toPrint.add(chars);
		});
		
		System.out.println(toPrint.toString());
	}

	public static void main(String[] args) {
		String input = "C:\\Users\\Stefan\\Desktop\\test2.txt";
		testTheFunction(input);
	}

}
