package si.matjazcerkvenik.simplelogger.tester;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import si.matjazcerkvenik.simplelogger.LEVEL;
import si.matjazcerkvenik.simplelogger.SimpleLogger;

public class LogTest {
	
	public static void main(String[] args) throws Exception {
		
		startLogger1();
//		startLogger2();
//		startLogger10();
//		startLogger30();
		
	}
	
	/**
	 * Setup individual parameters
	 */
	private static void startLogger1() {
		
		SimpleLogger logger = new SimpleLogger();
		logger.setLogLevel(LEVEL.INFO);
		logger.setFilename("/Users/matjaz/Desktop/simple-logger.log");
//		logger.setFilename("D:\\aaaaaaaa\\testlog.log");
		logger.setMaxSizeMb(2);
		logger.setBackup(3);
		logger.setDateFormat(null);
		logger.setVerbose(true);
		logger.setAppend(false);
		
		for (int i = 0; i < 10; i++) {
			LogThread t = new LogThread(logger);
			t.start();
		}
		
	}
	
	/**
	 * Setup via constructor
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void startLogger2() throws FileNotFoundException, IOException {
		
		SimpleLogger logger = new SimpleLogger("./test.log");
		logger.info("Hello simple-logger!");
		
		for (int i = 0; i < 1; i++) {
			LogThread t = new LogThread(logger);
			t.start();
		}
		
	}
	
	/**
	 * Load from properties
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void startLogger10() throws FileNotFoundException, IOException {
		
		Properties p = new Properties();
		p.load(new FileInputStream("./simplelogger.properties"));
		
		SimpleLogger logger = new SimpleLogger(p);
		
		for (int i = 0; i < 3; i++) {
			LogThread t = new LogThread(logger);
			t.start();
		}
		
	}
	
	/**
	 * Write into non-existing directory
	 */
	private static void startLogger30() {
		SimpleLogger logger = new SimpleLogger("./test0/test1/test2/test.log");
		logger.info("Hello simple-logger!");
	}
	
}
