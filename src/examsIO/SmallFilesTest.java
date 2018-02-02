package examsIO;
import java.io.*;
import java.util.*;

public class SmallFilesTest {
	
	public static final long KB100 = 102400;
	public static List<String> logs = new ArrayList<String>();
	
	public static String getPermissions (File f){
		return String.format("%s%s%S", 
				f.canRead() ? "r" : "-",
						f.canWrite() ? "w" : "-",
								f.canExecute() ? "x" : "-");
	}
	
	public static String writeLine (File f) {
		return String.format("%s, %s, %s", f.getName(), f.getAbsolutePath(), getPermissions(f));
	}
	
	public static void writeLog (String s) {
		logs.add(s);
	}
	
	public static void getTheSmallFiles (File src) {
		File [] files = src.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String fileName) {
				File file = new File(dir.getAbsolutePath()+"\\"+fileName);
				return file.isDirectory() || (file.length() < KB100 && fileName.endsWith(".dat"));
			}			
		});
		
		Arrays.stream(files).forEach(file -> {
			if (!file.isDirectory()){
				writeLog(writeLine(file));
			}
			else {
				getTheSmallFiles(file);
				
			}
		});
	}
	
	public static void writeInfo (PrintWriter pw){
		logs.stream().forEach(x -> {
			pw.println(x);
			System.out.println(x);
		});
		
		pw.flush();
	}
	
	public static void smallFiles (String in, String out) throws IOException {
		File src = new File(in);
		File dest = new File(out+"//files.txt");
		if (!dest.exists())
			dest.createNewFile();
		
		PrintWriter pw = null;
		
		try {
			pw = new PrintWriter(dest);
				
			getTheSmallFiles(src);
			writeInfo(pw);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		finally {
			if (pw!=null)
				pw.close();
		}		
		
	}
	
	public static void main (String [] args) throws IOException{
		
		/*File f = new File("C:\\Users\\Stefan\\Desktop\\testOS1\\stefan1\\test.dat");
		f.createNewFile();
		
		File f2 = new File("C:\\Users\\Stefan\\Desktop\\testOS1\\stefan1\\test2.dat");
		f2.createNewFile();
		
		File f3 = new File("C:\\Users\\Stefan\\Desktop\\testOS1\\test3.dat");
		f3.createNewFile();
		
		File f4 = new File("C:\\Users\\Stefan\\Desktop\\testOS1\\stefan2\\test3.dat");
		f4.createNewFile();*/
		
		smallFiles("C:\\Users\\Stefan\\Desktop\\testOS1", "C:\\Users\\Stefan\\Desktop\\testOS1");
	}

}
