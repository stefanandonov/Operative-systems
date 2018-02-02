package lab1;
import java.io.*;
import java.util.*;

class TxtFilesInfo {
	List<Long> memory;
	
	public TxtFilesInfo () {
		memory = new ArrayList<>();
	}
	
	public int getMemory() {
		return memory.stream().mapToInt(x -> x.intValue()).sum();
	}
	
	public double getAverage() {
		return memory.stream().mapToInt(x -> x.intValue()).average().orElse(0);
	}
	
	public void addFileInfo (Long size){
		memory.add(size);
	}
}

public class Lab01_01 {
	
	public static TxtFilesInfo getAverageMemoForTxt (String fileName, TxtFilesInfo info) {
			
		File file = new File(fileName);
		if (!file.exists()){
			System.out.println("Greshka");
			return null;
		}
		
		File [] files = file.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				File f = new File(dir.getAbsolutePath()+"//"+name);
				return f.isDirectory() || f.getName().endsWith(".txt");
			}			
		});
		
		Arrays.stream(files).forEach(x -> {
			if (!x.isDirectory())
				info.addFileInfo(x.length());
			else 
				getAverageMemoForTxt(x.getAbsolutePath(),info);
		});
		
		return info;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		String file = sc.nextLine();
		
		System.out.println(getAverageMemoForTxt(file,new TxtFilesInfo()).getAverage());

	}

}
