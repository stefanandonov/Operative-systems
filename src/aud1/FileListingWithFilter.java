package aud1;
import java.io.*;
import java.util.*;
import aud1.FileListing;

public class FileListingWithFilter {
	
	public static void listFilteredFiles (String path, String prefix) {
		
		File f = new File(path);
		File [] subfiles = f.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				//System.out.println(dir.getAbsolutePath() + " " + name);
				File f1 = new File(dir.getAbsolutePath()+"\\"+name);				
				return f1.isDirectory() || name.endsWith(".pdf");
			}			
		});
		
		
		Arrays.stream(subfiles).forEach(x -> {
			System.out.println(FileListing.getPermisions(x, prefix));
			if (x.isDirectory()){
				listFilteredFiles(x.getAbsolutePath(),prefix+"   ");
			}
		});
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		String path  = sc.nextLine();
		
		listFilteredFiles(path," ");
		
		sc.close();

	}

}
