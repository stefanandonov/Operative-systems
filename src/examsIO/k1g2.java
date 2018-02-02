package examsIO;
import java.io.*;
import java.util.*;

public class k1g2 {
	
	public static void copyLargeTxtFiles(String from, String to, long size) throws IOException{
		File src = new File(from);
		if (!src.exists()){
			System.out.println("Ne postoi");
			return;
		}
		
		File dest = new File(to);
		if (!dest.exists()){
			dest.createNewFile();
			dest.mkdir();			
		}
		
		File [] files = src.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String file) {
				File f = new File(dir.getAbsolutePath()+file);
				return f.isDirectory() || (f.getName().endsWith(".txt") && f.length()>size);
			}			
		});
		
		Arrays.stream(files).forEach(x -> {
			
		});
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
