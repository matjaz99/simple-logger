simple-logger
---

Logger utility for java that is very simple to configure and use.

---

Add simple-logger-x.y.z.jar to project path (dependency).

Create new SimpleLogger object, set filename and start logging:

```java
SimpleLogger logger = new SimpleLogger("./test.log");
logger.info("Hello simple-logger!");
```

### Configuration
---

Configure simple-logger with simplelogger.properties file:

```
simplelogger.filename=./simple-logger.log
simplelogger.level=INFO
simplelogger.append=true
simplelogger.verbose=false
simplelogger.maxFileSize=1
simplelogger.maxBackupFiles=2
simplelogger.dateFormat=yyyy.MM.dd hh:mm:ss:SSS
```

*filename* - Relative or absolute path to log file.

*level* - Log level. Supported log levels: TRACE, DEBUG, INFO, WARN, ERROR, FATAL

*append* - Set true to append text to file, or false to delete it first.

*verbose* - Also send text to standard output (console).

*maxFileSize* - Maximum size of log file in megabytes.

*maxBackupFiles* - Number of rolling files. Active log file has suffix .log while older log files get suffix .log.1. When new log file is created, the index of old log files are shifted and the last file is deleted.

*dateFormat* - Date format in Java compatible syntax, for example:
- yyyy.MM.dd hh:mm:ss:SSS
- EEE, MMM d, yy
- h:mm a
- yyyy-MM-dd'T'HH:mm:ss.SSS Z



Load the properties into SimpleLogger and start logging:

```java
Properties p = new Properties();
p.load(new FileInputStream("./simplelogger.properties"));

SimpleLogger logger = new SimpleLogger(p);

logger.info("Hello simple-logger!");
```


---

All configuration can be done in the code. There are setters for all parameters:

```java
SimpleLogger logger = new SimpleLogger();

logger.setFilename("./simplelogger.properties")
logger.setAppend(true);
logger.setLogLevel(LEVEL.INFO)
logger.setDateFormat("yyyy.MM.dd hh:mm:ss:SSS");
logger.setVerbose(true);
logger.setMaxSizeMb(10);
logger.setBackup(5);

logger.info("Hello simple-logger!");
```



---

### Version history

1.6.3
- SimpleLogger implements Serializable

=============

1.6.2
- create directories on path if they don't exist

=============

1.6.1
- close file writer

MC
