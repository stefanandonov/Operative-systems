package aud1;
import java.io.*;



public class MemoryInput {

	public static void redirect() throws IOException {
		InputStream consoleIn = System.in;	BufferedInputStream in = null;
		PrintStream console = System.out; 	PrintStream out = null;

		try {
			in = new BufferedInputStream(new FileInputStream("src/MemoryInput.java"));
			out = new PrintStream(new BufferedOutputStream(new FileOutputStream("test.out")));
			System.setIn(in);
			System.setOut(out);
			System.setErr(out);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String s;
			while ((s = br.readLine()) != null)
				System.out.println(s);
		} finally {
			if (in != null) 		in.close();
			if (out != null) 		out.close();
			System.setIn(consoleIn);
			System.setOut(console);
		}

	}

	

	static void display() throws IOException {
		RandomAccessFile rf = null;
		try {
			rf = new RandomAccessFile(file, "r");
			for (int i = 0; i < 7; i++)
				System.out.println("Value " + i + ": " + rf.readDouble());
			System.out.println(rf.readUTF());
		} finally {
			rf.close();
		}
	}

	public static void main(String[] args) throws IOException {
		//display();
		randomAccess();
	}
	
	public static String file = "C:\\Users\\Stefan\\Desktop\\test1";

	public static void randomAccess() throws IOException {
		RandomAccessFile rf = null;
		try {
			rf = new RandomAccessFile(file, "rw");
			for (int i = 0; i < 7; i++)
				rf.writeDouble(i * 1.414);
			rf.writeUTF("The end of the file");
		} finally {
			if (rf != null) {
				rf.close();
			}
		}
		display();
		try {
			rf = new RandomAccessFile(file, "rw");
			// Sets the file-pointer offset, measured from the beginning of this
			// file, at which the next read or write occurs
			rf.seek(5 * 8);
			rf.readDouble();
			rf.writeDouble(47.0001);
		} finally {
			if (rf != null) {
				rf.close();
			}
		}
		display();
	}

	public static void dataReadWrite() throws IOException {
		DataOutputStream out = null;
		DataInputStream in = null;
		try {
			out = new DataOutputStream(new BufferedOutputStream(
					new FileOutputStream("Data.txt")));
			out.writeDouble(3.14159);
			out.writeUTF("That was pi");
			out.writeDouble(1.41413);
			out.writeUTF("Square root of 2");
			in = new DataInputStream(new BufferedInputStream(
					new FileInputStream("Data.txt")));
			System.out.println(in.readDouble());
			// Only readUTF() will recover the Java-UTF String properly:
			System.out.println(in.readUTF());
			System.out.println(in.readDouble());
			System.out.println(in.readUTF());
		} finally {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
		}
	}

	public static void fileOutputWithLineNumbers() throws IOException {
		String outFile = "BasicFileOutput.out";
		BufferedReader in = null;

		PrintWriter out = null;
		try {
			in = new BufferedReader(new FileReader("src/BasicFileOutput.java"));
			out = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
			// Here’s the shortcut for the previous line:
			// PrintWriter out = new PrintWriter(outFile);
			int lineCount = 1;
			String s;
			while ((s = in.readLine()) != null)
				out.println(lineCount++ + ": " + s);
		} finally {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
		}
		// Show the stored file (use the method from the previous example):
		//System.out.println(readTextFile(outFile));
	}

	public static void stdinRead() throws IOException {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		String s;
		System.out.print("Enter a line:");
		while ((s = stdin.readLine()) != null && s.length() != 0)
			System.out.println(s);
		// An empty line or Ctrl-Z terminates the program
	}
	
	
}
