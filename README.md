simple-logger
=============

Logger utility for java that works out of the box. Very simple to configure and use.

-------------

Create new SimpleLogger object, provide a filename and start logging:

SimpleLogger logger = new SimpleLogger("./test.log");
logger.info("Hello simple-logger!");

-------------

You can configure simple-logger also with properties file:

Properties p = new Properties();
p.load(new FileInputStream("./simplelogger.properties"));

SimpleLogger logger = new SimpleLogger(p);
logger.info("Hello simple-logger!");


Here is example of simplelogger.properties:

simplelogger.filename=./simple-logger.log
simplelogger.level=INFO
simplelogger.append=true
simplelogger.verbose=false
simplelogger.maxFileSize=1
simplelogger.maxBackupFiles=2
simplelogger.dateFormat=yyyy.MM.dd hh:mm:ss:SSS

-------------

=============

1.6.2
- create directories on path if they don't exist

=============

1.6.1
- close file writer

MC
