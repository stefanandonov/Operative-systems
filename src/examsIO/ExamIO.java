package examsIO;
import java.io.*;
import java.util.Arrays;

public class ExamIO {
	
	public static String append_file = "resources\\writable_content.txt";
	
	public static void append (File src, String dest) throws IOException{
		File out = new File(dest);
		InputStream is = null;
		OutputStream os = null;
		int c;
		try {
			is = new FileInputStream(src);
			os = new FileOutputStream(out,true);
			
			while ((c=is.read())!=-1){
				os.write(c);
			}			
		}
		finally {
			if (is!=null)
				is.close();
			if (os!=null)
				os.close();
		}
	}
	
	public static void manage (String in, String out) {
		File src = new File(in);
		File dest = new File(out);
		
		if (!src.exists()){
			System.out.println("Ne postoi");
			return;
		}
		
		if (dest.exists()){
			File [] files = dest.listFiles();
			
			Arrays.stream(files).forEach(f -> {
				f.delete();
			});		
		}
		else {
			dest.mkdir();
		}
			
		
		File [] files = src.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String fileName) {
				File file = new File(dir.getAbsolutePath()+"\\"+fileName);
				return file.isDirectory() || file.getName().endsWith(".dat");
			}			
		});
		
		Arrays.stream(files).forEach(x -> {
			
			if (!x.isDirectory()) {
				if (x.canRead()){
					x.renameTo(new File(out,x.getName()));
					System.out.println("pomestuvam "+x.getName());
				}
				else {					
					try {
						System.out.println("dopisuvam "+x.getName());
						append(x,append_file);						
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}
				
				if (x.isHidden()){
					System.out.println("zbunet sum"+ x.getName());
					x.delete();
				}
			}
			else {
				manage(x.getAbsolutePath(),out);
			}
		});
	}
	
	public static void main(String [] args) {
		
		
	}

}
