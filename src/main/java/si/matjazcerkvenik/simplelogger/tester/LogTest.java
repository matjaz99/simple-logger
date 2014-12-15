package si.matjazcerkvenik.simplelogger.tester;

import si.matjazcerkvenik.simplelogger.LEVEL;
import si.matjazcerkvenik.simplelogger.SimpleLogger;

public class LogTest {
	
	public static void main(String[] args) {
		
		SimpleLogger logger = new SimpleLogger();
		logger.setLogLevel(LEVEL.DEBUG);
		logger.setFilename("/Users/matjaz/Desktop/simple-logger.log");
//		logger.setFilename("D:\\aaaaaaaa\\testlog.log");
		logger.setMaxSizeMb(2);
		logger.setBackup(3);
		
		for (int i = 0; i < 100; i++) {
			LogThread t = new LogThread(logger);
			t.start();
		}
		
	}
	
}
