package si.matjazcerkvenik.simplelogger;

import java.util.Properties;

/**
 * Main class that offers logging functionality. The purpose of simple-logger is 
 * to be simple and without any configuration files. All parameters for logging 
 * can be set directly from the code.<br><br>
 * Here is an example of usage:<br>
 * <code>SimpleLogger logger = new SimpleLogger();</code><br>
 * <code>logger.setFilename("/path/to/logger/mylog.log");</code><br>
 * <code>logger.setLogLevel(LEVEL.DEBUG);</code><br>
 * <code>logger.info("some text...");// this text goes to log file</code><br>
 * 
 * @author Matjaz Cerkvenik
 *
 */
public class SimpleLogger {
	
	/** set to false to start new log */
	private boolean append = true;
	private int logLevel = LEVEL.INFO;
	private String dateFormat = "yyyy.MM.dd hh:mm:ss:SSS";
	private String filename = "./simple-logger.log";
	// 1024b = 1kb, 1048584 = 1Mb
	private int maxSizeMb = 10;
	private int backup = 5;
	private boolean verbose = false;
	
	private LogWriter writer = null;
	
	/**
	 * Create new instance of SimpleLogger.
	 */
	public SimpleLogger() {
		writer = new LogWriter(this);
	}
	
	/**
	 * Create new instance of SimpleLogger with given filename and path.
	 * @param filename
	 */
	public SimpleLogger(String filename) {
		this.filename = filename;
		writer = new LogWriter(this);
	}
	
	/**
	 * Create new instance of SimpleLogger according to given properties.
	 */
	public SimpleLogger(Properties props) {
		
		if (props.getProperty(PROPS.FILENAME) != null) {
			this.filename = props.getProperty(PROPS.FILENAME);
		}
		if (props.getProperty(PROPS.LEVEL) != null) {
			String level = props.getProperty(PROPS.LEVEL);
			if (level.equalsIgnoreCase("trace")) {
				this.logLevel = LEVEL.TRACE;
			} else if (level.equalsIgnoreCase("debug")) {
				this.logLevel = LEVEL.DEBUG;
			} else if (level.equalsIgnoreCase("info")) {
				this.logLevel = LEVEL.INFO;
			} else if (level.equalsIgnoreCase("warn")) {
				this.logLevel = LEVEL.WARN;
			} else if (level.equalsIgnoreCase("error")) {
				this.logLevel = LEVEL.ERROR;
			} else if (level.equalsIgnoreCase("fatal")) {
				this.logLevel = LEVEL.FATAL;
			} else {
				this.logLevel = LEVEL.INFO;
			}
		}
		if (props.getProperty(PROPS.APPEND) != null) {
			if (props.getProperty(PROPS.APPEND).equalsIgnoreCase("true")) {
				this.append = true;
			}
		}
		if (props.getProperty(PROPS.VERBOSE) != null) {
			if (props.getProperty(PROPS.VERBOSE).equalsIgnoreCase("true")) {
				this.verbose = true;
			}
		}
		if (props.getProperty(PROPS.MAX_FILE_SIZE) != null) {
			try {
				this.maxSizeMb = Integer.parseInt(props.getProperty(PROPS.MAX_FILE_SIZE));
			} catch (NumberFormatException e) {
			}
		}
		if (props.getProperty(PROPS.MAX_BACKUP_FILES) != null) {
			try {
				this.backup = Integer.parseInt(props.getProperty(PROPS.MAX_BACKUP_FILES));
			} catch (NumberFormatException e) {
			}
		}
		if (props.getProperty(PROPS.DATE_FORMAT) != null) {
			this.dateFormat = props.getProperty(PROPS.DATE_FORMAT);
		}
		// TODO
		writer = new LogWriter(this);
	}
	
	/**
	 * Write to log file with logging level set to TRACE.
	 * @param s
	 */
	public void trace(String s) {
		if (logLevel-1 < LEVEL.TRACE) {
			writer.writeToFile(LEVEL.TRACE, s, null);
		}
	}
	
	/**
	 * Write to log file with logging level set to TRACE.
	 * @param s
	 */
	public void trace(String s, Throwable t) {
		if (logLevel-1 < LEVEL.TRACE) {
			writer.writeToFile(LEVEL.TRACE, s, t);
		}
	}
	
	/**
	 * Write to log file with logging level set to DEBUG.
	 * @param s
	 */
	public void debug(String s) {
		if (logLevel-1 < LEVEL.DEBUG) {
			writer.writeToFile(LEVEL.DEBUG, s, null);
		}
	}
	
	/**
	 * Write to log file with logging level set to DEBUG.<br>
	 * Write throwable (exception) stack trace.
	 * @param s
	 */
	public void debug(String s, Throwable t) {
		if (logLevel-1 < LEVEL.DEBUG) {
			writer.writeToFile(LEVEL.DEBUG, s, t);
		}
	}
	
	/**
	 * Write to log file with logging level set to INFO.
	 * @param s
	 */
	public void info(String s) {
		if (logLevel-1 < LEVEL.INFO) {
			writer.writeToFile(LEVEL.INFO, s, null);
		}
	}
	
	/**
	 * Write to log file with logging level set to INFO.<br>
	 * Write throwable (exception) stack trace.
	 * @param s
	 */
	public void info(String s, Throwable t) {
		if (logLevel-1 < LEVEL.INFO) {
			writer.writeToFile(LEVEL.INFO, s, t);
		}
	}
	
	/**
	 * Write to log file with logging level set to WARN.
	 * @param s
	 */
	public void warn(String s) {
		if (logLevel-1 < LEVEL.WARN) {
			writer.writeToFile(LEVEL.WARN, s, null);
		}
	}
	
	/**
	 * Write to log file with logging level set to WARN.<br>
	 * Write throwable (exception) stack trace.
	 * @param s
	 */
	public void warn(String s, Throwable t) {
		if (logLevel-1 < LEVEL.WARN) {
			writer.writeToFile(LEVEL.WARN, s, t);
		}
	}
	
	/**
	 * Write to log file with logging level set to ERROR.
	 * @param s
	 */
	public void error(String s) {
		if (logLevel-1 < LEVEL.ERROR) {
			writer.writeToFile(LEVEL.ERROR, s, null);
		}
	}
	
	/**
	 * Write to log file with logging level set to ERROR.<br>
	 * Write throwable (exception) stack trace.
	 * @param s
	 */
	public void error(String s, Throwable t) {
		if (logLevel-1 < LEVEL.ERROR) {
			writer.writeToFile(LEVEL.ERROR, s, t);
		}
	}
	
	/**
	 * Write to log file with logging level set to FATAL.
	 * @param s
	 */
	public void fatal(String s) {
		if (logLevel-1 < LEVEL.FATAL) {
			writer.writeToFile(LEVEL.FATAL, s, null);
		}
	}
	
	/**
	 * Write to log file with logging level set to FATAL.<br>
	 * Write throwable (exception) stack trace.
	 * @param s
	 */
	public void fatal(String s, Throwable t) {
		if (logLevel-1 < LEVEL.FATAL) {
			writer.writeToFile(LEVEL.FATAL, s, t);
		}
	}

	/**
	 * Return true if log file will be appended to existing one, 
	 * or false if new log file will be created.
	 * @return append
	 */
	public boolean isAppend() {
		return append;
	}

	/**
	 * Set weather the log file is overwritten or text is appended 
	 * to log file when new instance of <code>SimpleLogger</code> 
	 * is created. Set true to append or false to overwrite.
	 * @param append
	 */
	public void setAppend(boolean append) {
		this.append = append;
	}

	/**
	 * Get current logging level.
	 * @return logLevel
	 */
	public int getLogLevel() {
		return logLevel;
	}

	/**
	 * Set logging level. The text with logging level equal or above 
	 * logLevel will be logged to log file.
	 * @param logLevel
	 */
	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	/**
	 * Get current date format
	 * @return dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * Set format of date
	 * @param dateFormat
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * Get current filename
	 * @return filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Set filename and path.
	 * @param filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Get maximum size of log file before it is rolled over (in MB).
	 * @return maxSizeMb
	 */
	public int getMaxSizeMb() {
		return maxSizeMb;
	}

	/**
	 * Set maximum size of log file before it is rolled.
	 * @param maxSizeMb
	 */
	public void setMaxSizeMb(int maxSizeMb) {
		this.maxSizeMb = maxSizeMb;
		writer.setMaxSize(maxSizeMb);
	}

	/**
	 * Get number of backup rolling files
	 * @return backup
	 */
	public int getBackup() {
		return backup;
	}

	/**
	 * Set number of rolling files.
	 * @param backup
	 */
	public void setBackup(int backup) {
//		writer.setBackupCopies(backup);
		this.backup = backup;
	}

	/**
	 * If set to true, text will be also printed in console
	 * @return verbose
	 */
	public boolean isVerbose() {
		return verbose;
	}

	/**
	 * Set true to print lines in console window.
	 * @param verbose
	 */
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
	/**
	 * Close file writer stream
	 */
	public void close() {
		writer.closeLogger();
	}
	
	
	
	
}
