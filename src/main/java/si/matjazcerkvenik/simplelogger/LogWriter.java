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

	private Config config;
	private File f;
	private FileWriter fwStream;
	private BufferedWriter out;
	private PrintWriter pw;
	private int megabyte = 1048584; // 1024b = 1kb, 1048584 = 1Mb
	private long maxSize = 1048584; // in bytes

	public LogWriter(Config config) {
		this.config = config;
		this.maxSize = megabyte * config.getMaxSizeMb();
	}

	/**
	 * Create new output file if it does not exists yet.
	 */
	private void initLogger() {
		
		if (fwStream != null) {
			return;
		}

		f = new File(config.getFilename());
		f.getParentFile().mkdirs();

		if (f.exists()) {
			if (config.isAppend()) {
				System.out.println("[SimpleLogger] Append output to: " + config.getFilename());
			} else {
				System.out.println("[SimpleLogger] Overriding output file: " + config.getFilename());
			}
		} else {
			try {
				f.createNewFile();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}

		try {
			fwStream = new FileWriter(config.getFilename(), config.isAppend());
			pw = new PrintWriter(fwStream);
		} catch (IOException e) {
			System.err.println("[SimpleLogger] Writing exception");
			e.printStackTrace();
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
	 * @param dateFormat
	 * @param logLevel
	 * @param text
	 * @param throwable
	 */
	public synchronized void writeToFile(String dateFormat, int logLevel, String text, Throwable throwable) {
		if (fwStream == null) {
			initLogger();
		}
		try {
			if (throwable != null) {
				throwable.printStackTrace(pw);
				if (config.isVerbose()) {
					throwable.printStackTrace();
				}
			} else {
				String str = getDate(dateFormat) + getLevel(logLevel) + text;
				out.write(str + "\n");
				if (config.isVerbose()) {
					System.out.println(str);
				}
			}
			
			out.flush();
			if (f.length() > maxSize) {
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
		
		File lastFile = new File(config.getFilename()
				+ "." + config.getBackup());
		if (lastFile.exists()) {
			lastFile.delete();
		}
		
		for (int i = config.getBackup() - 1; i > 0; i--) {
			File file = new File(config.getFilename() + "." + i);
			if (file.exists()) {
				File newFile = new File(config.getFilename() + "." + (i+1));
				file.renameTo(newFile);
			}
		}
		
		File firstFile = new File(config.getFilename());
		if (firstFile.exists()) {
			File newFile = new File(config.getFilename() + "." + 1);
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
			return "TRACE ";
		} else if (level == LEVEL.DEBUG) {
			return "DEBUG ";
		} else if (level == LEVEL.INFO) {
			return "INFO ";
		} else if (level == LEVEL.WARN) {
			return "WARN ";
		} else if (level == LEVEL.ERROR) {
			return "ERROR ";
		} else if (level == LEVEL.FATAL) {
			return "FATAL ";
		} else {
			return "";
		}
	}

	/**
	 * Get current formated date.
	 * 
	 * @return formated date
	 */
	private String getDate(String dateFormat) {
		if (dateFormat == null) {
			return "";
		}
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(config.getDateFormat());
		return sdf.format(cal.getTime())  + " - ";
	}
	
	

}
