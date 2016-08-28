package basDbUtility;

//import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
/**
 * 
 * 
 */
 
public class TailLogs implements Runnable {
 
	private boolean debug = false;
 
	private int RunEveryNSeconds = 2000;
	private long lastKnownPosition = 0;
	private boolean shouldIRun = true;
	private File File = null;
	private static int Counter = 0;
 
	public TailLogs(String myFile, int myInterval) {
		File = new File(myFile);
		this.RunEveryNSeconds = myInterval;
	}
 
	private void printLine(String message) {
		System.out.println(message);
	}
 
	public void stopRunning() {
		shouldIRun = false;
	}
 
	public void run() {
		try {
			while (shouldIRun) {
				Thread.sleep(RunEveryNSeconds);
				long fileLength = File.length();
				if (fileLength > lastKnownPosition) {
 
					// Reading and writing file
					RandomAccessFile readWriteFileAccess = new RandomAccessFile(File, "rw");
					readWriteFileAccess.seek(lastKnownPosition);
					String Line = null;
					while ((Line = readWriteFileAccess.readLine()) != null) {
						this.printLine(Line);
						Counter++;
					}
					lastKnownPosition = readWriteFileAccess.getFilePointer();
					readWriteFileAccess.close();
				} else {
					if (debug)
						this.printLine("Hmm.. Couldn't found new line after line # " + Counter);
				}
			}
		} catch (Exception e) {
			stopRunning();
		}
		if (debug)
			this.printLine("Exit the program...");
	}
 
	public static void main(String argv[]) {
 
		ExecutorService Executor = Executors.newFixedThreadPool(4);
 
		// Replace username with your real value
		// For windows provide different path like: c:\\temp\\.log
		String filePath = "C:\\Dev\\repo\\BASDBUtility\\scripts\\hot_backup20160815104400.log";
		TailLogs _tailF = new TailLogs(filePath, 2000);
 
		// Start running log file tailer on .log file
		Executor.execute(_tailF);

	}
 
	
}
