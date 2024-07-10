package si.matjazcerkvenik.simplelogger;

import java.util.Map;
import java.util.Properties;

/**
 * Here are all configuration parameters. This class intentionally doesn't implement
 * static variables, so you can have many instances of SimpleLogger with different
 * configurations.
 *
 * @author matjaz
 */
public class Config {

    private boolean append = true;
    private int logLevel = LEVEL.INFO;
    private String dateFormat = "yyyy.MM.dd hh:mm:ss:SSS";
    private String filename = "./simple-logger.log";
    private int maxSizeMb = 10;
    private int backup = 5;
	private String filePermissions = "rw-r--r--";
    private boolean verbose = true;

    public static final String PROP_FILENAME = "simplelogger.filename";
    public static final String PROP_LEVEL = "simplelogger.level";
    public static final String PROP_APPEND = "simplelogger.append";
    public static final String PROP_VERBOSE = "simplelogger.verbose";
    public static final String PROP_MAX_FILE_SIZE = "simplelogger.maxFileSize";
    public static final String PROP_MAX_BACKUP_FILES = "simplelogger.maxBackupFiles";
	public static final String PROP_FILE_PERMISSIONS = "simplelogger.filepermissions";
    public static final String PROP_DATE_FORMAT = "simplelogger.dateFormat";

	public static final String ENV_FILENAME = "SIMPLELOGGER_FILENAME";
	public static final String ENV_LEVEL = "SIMPLELOGGER_LEVEL";
	public static final String ENV_APPEND = "SIMPLELOGGER_APPEND";
	public static final String ENV_VERBOSE = "SIMPLELOGGER_VERBOSE";
	public static final String ENV_MAX_FILE_SIZE = "SIMPLELOGGER_MAXFILESIZE";
	public static final String ENV_MAX_BACKUP_FILES = "SIMPLELOGGER_MAXBACKUPFILES";
	public static final String ENV_FILE_PERMISSIONS = "SIMPLELOGGER_FILEPERMISSIONS";
	public static final String ENV_DATE_FORMAT = "SIMPLELOGGER_DATEFORMAT";

	public Config() {

		// first read config parameters from environment variables
		Map<String, String> map = System.getenv();

		filename = map.getOrDefault(ENV_FILENAME, "./simple-logger.log");

		String level = map.getOrDefault(ENV_LEVEL, "info");
		setLogLevel(level);

		if (map.getOrDefault(ENV_APPEND, "true").equalsIgnoreCase("true")) {
			append = true;
		} else {
			append = false;
		}

		if (map.getOrDefault(ENV_VERBOSE, "true").equalsIgnoreCase("true")) {
			verbose = true;
		} else {
			verbose = false;
		}

		try {
			maxSizeMb = Integer.parseInt(map.getOrDefault(ENV_MAX_FILE_SIZE, "10"));
		} catch (NumberFormatException e) {
		}

		try {
			backup = Integer.parseInt(map.getOrDefault(ENV_MAX_BACKUP_FILES, "5"));
		} catch (NumberFormatException e) {
		}

		filePermissions = map.getOrDefault(ENV_FILE_PERMISSIONS, "rw-r--r--");

		dateFormat = map.getOrDefault(ENV_DATE_FORMAT, "yyyy.MM.dd hh:mm:ss:SSS");

	}

	public void loadProperties(Properties props) {

        filename = props.getProperty(PROP_FILENAME, "./simple-logger.log");

        String level = props.getProperty(PROP_LEVEL, "info");
        setLogLevel(level);

        if (props.getProperty(PROP_APPEND, "true").equalsIgnoreCase("true")) {
            append = true;
        } else {
            append = false;
        }

        if (props.getProperty(PROP_VERBOSE, "true").equalsIgnoreCase("true")) {
            verbose = true;
        } else {
            verbose = false;
        }

        try {
            maxSizeMb = Integer.parseInt(props.getProperty(PROP_MAX_FILE_SIZE, "10"));
        } catch (NumberFormatException e) {
        }

        try {
            backup = Integer.parseInt(props.getProperty(PROP_MAX_BACKUP_FILES, "5"));
        } catch (NumberFormatException e) {
        }

		filePermissions = props.getProperty(PROP_FILE_PERMISSIONS, "rw-r--r--");

        dateFormat = props.getProperty(PROP_DATE_FORMAT, "yyyy.MM.dd hh:mm:ss:SSS");

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
	 * Get current log file permissions.
	 * @return permissions
	 */
	public String getFilePermissions() {
		return filePermissions;
	}

	/**
	 * Set log file permissions
	 * @param filePermissions
	 */
	public void setFilePermissions(String filePermissions) {
		this.filePermissions = filePermissions;
	}

	private void setLogLevel(String level) {
		if (level.equalsIgnoreCase("trace")) {
			logLevel = LEVEL.TRACE;
		} else if (level.equalsIgnoreCase("debug")) {
			logLevel = LEVEL.DEBUG;
		} else if (level.equalsIgnoreCase("info")) {
			logLevel = LEVEL.INFO;
		} else if (level.equalsIgnoreCase("warn")) {
			logLevel = LEVEL.WARN;
		} else if (level.equalsIgnoreCase("error")) {
			logLevel = LEVEL.ERROR;
		} else if (level.equalsIgnoreCase("fatal")) {
			logLevel = LEVEL.FATAL;
		} else {
			logLevel = LEVEL.INFO;
		}
	}

	@Override
	public String toString() {
		return "[" + "append=" + append + ", logLevel=" + logLevel +
				", dateFormat='" + dateFormat + ", filename='" + filename +
				", maxSizeMb=" + maxSizeMb + ", backup=" + backup +
				", permissions=" + filePermissions +
				", verbose=" + verbose + "]";
	}
}
