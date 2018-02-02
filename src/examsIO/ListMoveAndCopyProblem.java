package examsIO;
import java.io.*;
import java.util.Arrays;

public class ListMoveAndCopyProblem {
	
	public static FilenameFilter dirFilter = new FilenameFilter () {
	@Override
	public boolean accept(File dir, String name) {
		File f = new File(dir.getAbsolutePath()+"\\"+name);
		return f.isDirectory();
	}};
	
	public static FilenameFilter notDirFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			File f = new File(dir.getAbsolutePath()+"\\"+name);
			return !f.isDirectory();
		}		
	};
	
	public static boolean checks (File src, File dest) {
		if (!src.exists()){
			System.out.println("GRESKA");
			return false;
		}
		
		if (!dest.exists()){
			dest.mkdir();
		}
		else {
			if (dest.listFiles().length!=0) {
				Arrays.stream(dest.listFiles())
				.forEach(file -> file.delete());
			}
		}
		
		return true;
	}
	
	public static void work (String in, String out){
		File src = new File(in);
		File dest = new File(out);
		
		if (!checks(src, dest))
			return;
		
		Arrays.stream(src.listFiles())
		.forEach(file -> {
			if (!file.isDirectory()){
				
			}
			else {
				if (file.listFiles(notDirFilter).length>=5){
					Arrays
					.stream(file.listFiles(notDirFilter))
					.forEach(f -> f.delete());
				}
				else {
					Arrays.stream(file.listFiles(notDirFilter))
					.forEach(f -> f.renameTo(new File(dest,f.getName())));
				}
				work(file.getAbsolutePath(),out);
			}
		});
		
		
		
		
	}

	public static void main(String[] args) {
		String in = "C:\\Users\\Stefan\\Desktop\\test";
		String out = "C:\\Users\\Stefan\\Desktop\\test2";
		work(in,out);

	}

}
