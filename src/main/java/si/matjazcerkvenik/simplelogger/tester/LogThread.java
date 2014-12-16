package si.matjazcerkvenik.simplelogger.tester;

import si.matjazcerkvenik.simplelogger.SimpleLogger;

public class LogThread extends Thread {
	
	private SimpleLogger logger = null;
	
	private String longText = "\t\t\tasdfmalkflaknaldkfnasdnfkajsdnkjadsf" +
			"aljksdnfkajsdnflkajsdnflkajdsfirebfaksjdbfkjasbdf" +
			"lakjsdfkjadsflkjasdlfkjalskdfjh";

	public LogThread(SimpleLogger log) {
		this.logger = log;
	}
	
	@Override
	public void run() {
		
		
		
		int count = 0 ;
		logger.info("starting" + this);
		
		while (true) {
			
			if (count % 2 == 0) {
				logger.trace("writing to log, count=" + count + longText);
			} else if (count % 3 == 0) {
				logger.debug("writing to log, count=" + count + longText);
			} else if (count % 5 == 0) {
				logger.info("writing to log, count=" + count + longText);
			} else if (count % 7 == 0) {
				logger.warn("writing to log, count=" + count + longText);
			} else if (count % 11 == 0) {
				logger.error("writing to log, count=" + count + longText);
			} else if (count % 17 == 0) {
				logger.fatal("writing to log, count=" + count + longText);
			} else if (count % 19 == 0) {
				try {
					throwException();
				} catch (Exception e) {
					logger.error("Exception is thrown", e);
				}
			}
			
			count++;
			
			
			try {
				sleep((int) (1 * 1000 * Math.random()));
			} catch (InterruptedException e) {
			}
			
		}
	}
	
	private int throwException() {
		
		int[] a = new int[1];
		
		return a[2];
		
	}
	
}
