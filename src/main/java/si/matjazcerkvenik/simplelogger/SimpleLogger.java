package si.matjazcerkvenik.simplelogger;

import java.io.Serializable;
import java.util.Properties;

/**
 * Main class that offers logging functionality. The purpose of simple-logger is 
 * to be simple and without any configuration files. All parameters for logging 
 * can be set directly from the code.<br><br>
 * Here is an example of usage:<br>
 * <code>SimpleLogger logger = new SimpleLogger();</code><br>
 * <code>logger.setFilename("/path/to/my-log.log");</code><br>
 * <code>logger.setLogLevel(LEVEL.DEBUG);</code><br>
 * <code>logger.info("some text...");// this text goes to log file</code><br>
 * 
 * @author Matjaz Cerkvenik
 *
 */
public class SimpleLogger implements Serializable {
	
	private static final long serialVersionUID = -61394748703L;

	private Config config = new Config();
	private LogWriter writer;
	
	/**
	 * Create new instance of SimpleLogger.
	 */
	public SimpleLogger() {
		writer = new LogWriter(config);
	}
	
	/**
	 * Create new instance of SimpleLogger with given filename and path.
	 * @param filename
	 */
	public SimpleLogger(String filename) {
		config.setFilename(filename);
		writer = new LogWriter(config);
	}
	
	/**
	 * Create new instance of SimpleLogger according to given properties.
	 */
	public SimpleLogger(Properties props) {
		config.loadProperties(props);
		writer = new LogWriter(config);
	}
	
	/**
	 * Write to log file without checking log level.
	 * @param s
	 */
	public void write(String s) {
		writer.writeToFile(null, 99, s, null);
	}
	
	/**
	 * Write to log file with logging level set to TRACE.
	 * @param s
	 */
	public void trace(String s) {
		if (config.getLogLevel()-1 < LEVEL.TRACE) {
			writer.writeToFile(config.getDateFormat(), LEVEL.TRACE, s, null);
		}
	}
	
	/**
	 * Write to log file with logging level set to TRACE.
	 * @param s
	 */
	public void trace(String s, Throwable t) {
		if (config.getLogLevel()-1 < LEVEL.TRACE) {
			writer.writeToFile(config.getDateFormat(), LEVEL.TRACE, s, t);
		}
	}
	
	/**
	 * Write to log file with logging level set to DEBUG.
	 * @param s
	 */
	public void debug(String s) {
		if (config.getLogLevel()-1 < LEVEL.DEBUG) {
			writer.writeToFile(config.getDateFormat(), LEVEL.DEBUG, s, null);
		}
	}
	
	/**
	 * Write to log file with logging level set to DEBUG.<br>
	 * Write throwable (exception) stack trace.
	 * @param s
	 */
	public void debug(String s, Throwable t) {
		if (config.getLogLevel()-1 < LEVEL.DEBUG) {
			writer.writeToFile(config.getDateFormat(), LEVEL.DEBUG, s, t);
		}
	}
	
	/**
	 * Write to log file with logging level set to INFO.
	 * @param s
	 */
	public void info(String s) {
		if (config.getLogLevel()-1 < LEVEL.INFO) {
			writer.writeToFile(config.getDateFormat(), LEVEL.INFO, s, null);
		}
	}
	
	/**
	 * Write to log file with logging level set to INFO.<br>
	 * Write throwable (exception) stack trace.
	 * @param s
	 */
	public void info(String s, Throwable t) {
		if (config.getLogLevel()-1 < LEVEL.INFO) {
			writer.writeToFile(config.getDateFormat(), LEVEL.INFO, s, t);
		}
	}
	
	/**
	 * Write to log file with logging level set to WARN.
	 * @param s
	 */
	public void warn(String s) {
		if (config.getLogLevel()-1 < LEVEL.WARN) {
			writer.writeToFile(config.getDateFormat(), LEVEL.WARN, s, null);
		}
	}
	
	/**
	 * Write to log file with logging level set to WARN.<br>
	 * Write throwable (exception) stack trace.
	 * @param s
	 */
	public void warn(String s, Throwable t) {
		if (config.getLogLevel()-1 < LEVEL.WARN) {
			writer.writeToFile(config.getDateFormat(), LEVEL.WARN, s, t);
		}
	}
	
	/**
	 * Write to log file with logging level set to ERROR.
	 * @param s
	 */
	public void error(String s) {
		if (config.getLogLevel()-1 < LEVEL.ERROR) {
			writer.writeToFile(config.getDateFormat(), LEVEL.ERROR, s, null);
		}
	}
	
	/**
	 * Write to log file with logging level set to ERROR.<br>
	 * Write throwable (exception) stack trace.
	 * @param s
	 */
	public void error(String s, Throwable t) {
		if (config.getLogLevel()-1 < LEVEL.ERROR) {
			writer.writeToFile(config.getDateFormat(), LEVEL.ERROR, s, t);
		}
	}
	
	/**
	 * Write to log file with logging level set to FATAL.
	 * @param s
	 */
	public void fatal(String s) {
		if (config.getLogLevel()-1 < LEVEL.FATAL) {
			writer.writeToFile(config.getDateFormat(), LEVEL.FATAL, s, null);
		}
	}
	
	/**
	 * Write to log file with logging level set to FATAL.<br>
	 * Write throwable (exception) stack trace.
	 * @param s
	 */
	public void fatal(String s, Throwable t) {
		if (config.getLogLevel()-1 < LEVEL.FATAL) {
			writer.writeToFile(config.getDateFormat(), LEVEL.FATAL, s, t);
		}
	}

	/**
	 * Close file writer stream
	 */
	public void close() {
		writer.closeLogger();
	}






	/* ****** Getters and setters ****** */



	/**
	 * Set weather the log file is overwritten or text is appended
	 * to log file when new instance of <code>SimpleLogger</code>
	 * is created. Set true to append or false to overwrite.
	 * @param append
	 */
	public void setAppend(boolean append) {
		this.config.setAppend(append);
	}

	/**
	 * Set logging level. The text with logging level equal or above
	 * logLevel will be logged to log file.
	 * @param logLevel
	 */
	public void setLogLevel(int logLevel) {
		this.config.setLogLevel(logLevel);
	}

	/**
	 * Set format of date
	 * @param dateFormat
	 */
	public void setDateFormat(String dateFormat) {
		this.config.setDateFormat(dateFormat);
	}

	/**
	 * Set filename and path.
	 * @param filename
	 */
	public void setFilename(String filename) {
		this.config.setFilename(filename);
	}

	/**
	 * Return filename
	 * @return filename
	 */
	public String getFilename() { return config.getFilename(); }

	/**
	 * Set maximum size of log file before it is rolled.
	 * @param maxSizeMb
	 */
	public void setMaxSizeMb(int maxSizeMb) {
		this.config.setMaxSizeMb(maxSizeMb);
	}

	/**
	 * Set number of rolling files.
	 * @param backup
	 */
	public void setBackup(int backup) {
		this.config.setBackup(backup);
	}

	/**
	 * Set true to print lines in console window.
	 * @param verbose
	 */
	public void setVerbose(boolean verbose) {
		this.config.setVerbose(verbose);
	}

	/**
	 * Set POSIX permissions on log file. Format: rw-r--r--
	 * @param permissions
	 */
	public void setFilePermissions(String permissions) {
		this.config.setFilePermissions(permissions);
	}

}
