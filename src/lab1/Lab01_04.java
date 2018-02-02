package lab1;
import java.io.*;
import java.util.*;

public class Lab01_04 {
	
	public static int countWords (String location, String word) throws IOException{
		File f = new File(location);
		int count = 0;
		InputStream is = null;
		Scanner sc = null;

		
		try {
			is = new FileInputStream(f);
			sc = new Scanner (is);
			
			while (sc.hasNext()){
				String s = sc.next();
				if (s.equalsIgnoreCase(word) || s.toLowerCase().contains(word.toLowerCase()))
					count++;
			}
			
		}
		finally{
			if (is!=null)
				is.close();
			if (sc!=null)
				sc.close();			
		}
		
		return count;
		
	}

	public static void main(String[] args) {
		
		String source = "C:\\Users\\Stefan\\workspace\\OS2018_JavaIO\\src\\lab1\\izvor.txt";
		
		try {
			System.out.println(countWords(source,"Stefan"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
