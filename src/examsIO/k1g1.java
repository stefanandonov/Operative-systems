package examsIO;
import java.io.*;
import java.util.Arrays;
import java.util.*;

public class k1g1 {
	
	public static void moveWritableTxtFiles(String from, String to) throws IOException{
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
				return f.isDirectory() || f.getName().endsWith(".txt");
			}			
		});
		
		Arrays.stream(files).forEach(x -> {
			if (!x.isDirectory()){
				x.renameTo(new File(dest.getAbsolutePath()+"\\"+x.getName()));
			}
			else{
				try {
					moveWritableTxtFiles(x.getAbsolutePath(),to);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}				
		});		
	}
	
	public static void deserializeData(String source, List<byte[]> data, long elementLength) throws IOException{
		
		byte [] b = new byte [(int) elementLength];
		
		InputStream is = null;
		
		try {
			is = new FileInputStream(new File(source));
			
			while ((is.read(b))!=-1){
				data.add(b);
			}
		}
		finally{
			if (is!=null)
				is.close();
		}
	}



	public static void main(String[] args) {
		
		String source = "C:\\Users\\Stefan\\workspace\\OS2018_JavaIO\\src\\examsIO\\izvor.txt";
		List<byte[]> data = new ArrayList<>();
		try {
			deserializeData(source,data,6);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//StringBuilder sb = new StringBuilder();
		System.out.println(data.toString());
		if (data.size()!=0){
			data.stream().forEach(x -> {
				StringBuilder sb = new StringBuilder();
				for (int i=0;i<6;i++)
					sb.append((char) x[i]);
				System.out.println(sb.toString());
			});
		}

	}

}
