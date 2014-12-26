package si.matjazcerkvenik.simplelogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This class implements writing to log file and handling of 
 * log files.
 * 
 * @author matjaz
 *
 */
public class LogWriter {

	private SimpleLogger logger = null;

	private File f = null;
	private FileWriter fwStream = null;
	private BufferedWriter out = null;
	private PrintWriter pw = null;
	private long maxSize = 1048584; // in bytes
	
	public LogWriter(SimpleLogger logger) {
		this.logger = logger;
	}

	/**
	 * Create new output file if it does not exists yet.
	 */
	private void initLogger() {
		
		if (fwStream != null) {
			return;
		}

		f = new File(logger.getFilename());

		if (f.exists()) {
			if (logger.isAppend()) {
				System.out.println("[SimpleLogger] Append output to: " + logger.getFilename());
			} else {
//				System.out.println("Overriding output file: "
//						+ logger.getFilename());
			}
		} else {
			try {
				f.createNewFile();
//				System.out.println("Creating output file: "
//						+ logger.getFilename());
			} catch (IOException e) {
//				System.err.println("Cannot create new file: "
//						+ logger.getFilename());
			}
		}

		try {
			fwStream = new FileWriter(logger.getFilename(), logger.isAppend());
			pw = new PrintWriter(fwStream);
		} catch (IOException e) {
			System.err.println("Writing exception");
		}
		out = new BufferedWriter(fwStream);

	}

	/**
	 * Write to logger. Init logger if it is not inited yet. 
	 * This method also checks the size of log file. If the size of 
	 * log file exceeds the <code>maxSize</code>, start new log file. 
	 * This method is synchronized to avoid concurrent access to 
	 * the log file.
	 * 
	 * @param level
	 * @param s
	 * @param t
	 */
	public synchronized void writeToFile(int level, String s, Throwable t) {
		if (fwStream == null) {
			initLogger();
		}
		try {
			if (t != null) {
				t.printStackTrace(pw);
				if (logger.isVerbose()) {
					t.printStackTrace();
				}
			} else {
				String str = getDate() + " - " + getLevel(level) + " " + s;
				out.write(str + "\n");
				if (logger.isVerbose()) {
					System.out.println(str);
				}
			}
			
			out.flush();
			if (f.length() > maxSize) {
//				System.out.println("start new log");
				startNewLog();
			}
		} catch (IOException e) {
			System.out.println("IOException: write");
		}
	}
	
	/**
	 * Start new log file: close output stream, rename the file to *.1 
	 * and reinitialize new log file.
	 */
	private void startNewLog() {
		closeLogger();
		renameAll();
		initLogger();
	}
	
	/**
	 * This method does rolling of files. The oldest log file is 
	 * deleted and all log files are renamed from *.n to *.n+1. The 
	 * last actual log file is renamed from *.log to *.1.
	 */
	private void renameAll() {
		
		File lastFile = new File(logger.getFilename() 
				+ "." + logger.getBackup());
		if (lastFile.exists()) {
			lastFile.delete();
		}
		
		for (int i = logger.getBackup() - 1; i > 0; i--) {
			File file = new File(logger.getFilename() + "." + i);
			if (file.exists()) {
				File newFile = new File(logger.getFilename() + "." + (i+1));
				file.renameTo(newFile);
			}
		}
		
		File firstFile = new File(logger.getFilename());
		if (firstFile.exists()) {
			File newFile = new File(logger.getFilename() + "." + 1);
			firstFile.renameTo(newFile);
		}
		
		
	}

	/**
	 * Close logger output streams.
	 */
	public void closeLogger() {
		try {
			out.close();
			out = null;
			fwStream.close();
			fwStream = null;
		} catch (IOException e) {
			System.out.println("IOException: close");
		}
	}

	/**
	 * Translate logging level from integer to string.
	 */
	private String getLevel(int level) {
		if (level == LEVEL.TRACE) {
			return "TRACE";
		} else if (level == LEVEL.DEBUG) {
			return "DEBUG";
		} else if (level == LEVEL.INFO) {
			return "INFO ";
		} else if (level == LEVEL.WARN) {
			return "WARN ";
		} else if (level == LEVEL.ERROR) {
			return "ERROR";
		} else if (level == LEVEL.FATAL) {
			return "FATAL";
		} else {
			return "-";
		}
	}

	/**
	 * Get current formated date.
	 * 
	 * @return formated date
	 */
	private String getDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(logger.getDateFormat());
		return sdf.format(cal.getTime());
	}
	

	/**
	 * Set maximum log size.
	 * 
	 * @param maxSizeMb
	 */
	public void setMaxSize(int maxSizeMb) {
		this.maxSize = maxSize * maxSizeMb;
	}
	
	

}
