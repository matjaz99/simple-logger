package si.matjazcerkvenik.simplelogger;

/**
 * This class holds definitions of six logging levels: 
 * TRACE, DEBUG, INFO (default), WARN, ERROR and FATAL.
 * DISABLED is a special case when all text is written anyway, 
 * without log level.
 * 
 * @author Matjaz Cerkvenik
 *
 */
public class LEVEL {
	
	public static final int TRACE = 1;
	public static final int DEBUG = 2;
	public static final int INFO = 3;
	public static final int WARN = 4;
	public static final int ERROR = 5;
	public static final int FATAL = 6;
	
	/**
	 * Convert string level to int level. Default level is INFO.
	 * @param level
	 * @return
	 */
	public static int getLevel(String level) {
		if (level.equalsIgnoreCase("trace")) {
			return TRACE;
		} else if (level.equalsIgnoreCase("debug")) {
			return DEBUG;
		} else if (level.equalsIgnoreCase("info")) {
			return INFO;
		} else if (level.equalsIgnoreCase("warn")) {
			return WARN;
		} else if (level.equalsIgnoreCase("error")) {
			return ERROR;
		} else if (level.equalsIgnoreCase("fatal")) {
			return FATAL;
		} else {
			return INFO;
		}
	}

}
