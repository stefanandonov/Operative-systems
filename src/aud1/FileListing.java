package aud1;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class FileListing {
	
	public static String getPermisions (File f, String prefix){
		return String.format("%s%s%s%s\t%s", 
				prefix, f.canWrite() ? "w" : "-", 
						f.canRead()? "r" : "-",
								f.canExecute() ? "x" : "-",
										f.getName());
	}
	
	public static void listFiles (String path, String prefix){
		File f = new File(path);
		
		if (f.exists()){
			File[] subfiles = f.listFiles();
			
			Arrays.stream(subfiles).forEach(x -> {
				System.out.println(getPermisions(x,prefix));
				if (x.isDirectory())
					listFiles(x.getAbsolutePath(),prefix+"   ");
			});			
		}
		else {
			System.out.println("GRESKA");
		}
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		String path  = sc.nextLine();
		
		listFiles(path," ");
		
		sc.close();
		

	}

}
