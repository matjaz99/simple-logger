# simple-logger

Logger utility for Java that is very simple to configure and use.

Last stable release: 1.6.4.

### Maven project

Build with maven:

```
mvn clean install
```

Simple-logger is not available on Maven central repo.

In your pipeline, you can download it and install it into local maven repository:

```
whet http://matjazcerkvenik.si/download/simple-logger-1.6.4.jar
mvn install:install-file -Dfile=simple-logger-1.6.4.jar -DgroupId=si.matjazcerkvenik.simplelogger -DartifactId=simple-logger -Dversion=1.6.4 -Dpackaging=jar
```

Add maven dependency in `pom.xml`:

```xml
<dependency>
    <groupId>si.matjazcerkvenik.simplelogger</groupId>
    <artifactId>simple-logger</artifactId>
    <version>1.6.4</version>
</dependency>
```

### Usage

In Java create new `SimpleLogger` object, set filename and start logging:

```java
SimpleLogger logger = new SimpleLogger("./test.log");
logger.info("Hello simple-logger!");
```

### Configuration

Simple-logger can be configured:
- directly in the code
- with properties file
- with environment variables

> All parameters are optional and have default values. 

Parameters:

*filename* - Relative or absolute path to log file.

*level* - Log level. Supported log levels: TRACE, DEBUG, INFO, WARN, ERROR, FATAL

*append* - Set true to append text to file, or false to delete file first.

*verbose* - Also send text to standard output (console).

*maxFileSize* - Maximum size of log file in megabytes.

*maxBackupFiles* - Number of rolling files. Active log file has suffix .log while older log files get suffix .log.1. When new log file is created, the index of old log files are shifted and the last file is deleted.

*dateFormat* - Date format in Java compatible syntax, for example:
- yyyy.MM.dd hh:mm:ss:SSS
- EEE, MMM d, yy
- h:mm a
- yyyy-MM-dd'T'HH:mm:ss.SSS Z



#### Configuring inline

Create new object and set parameters.

```java
SimpleLogger logger = new SimpleLogger();
logger.setFilename("./test.log")
logger.setAppend(true);
logger.setLogLevel(LEVEL.INFO)
logger.setDateFormat("yyyy.MM.dd hh:mm:ss:SSS");
logger.setVerbose(true);
logger.setMaxSizeMb(10);
logger.setBackup(5);

logger.info("Hello simple-logger!");
```

#### Properties file

Prepare a `simplelogger.properties` file with parameters:

```
simplelogger.filename=./simple-logger.log
simplelogger.level=INFO
simplelogger.append=true
simplelogger.verbose=true
simplelogger.maxFileSize=1
simplelogger.maxBackupFiles=2
simplelogger.dateFormat=yyyy.MM.dd hh:mm:ss:SSS
```

Pass the properties to SimpleLogger when creating new object and start logging:

```java
Properties props = new Properties();
props.load(new FileInputStream("./simplelogger.properties"));

SimpleLogger logger = new SimpleLogger(props);
logger.info("Hello simple-logger!");
```

#### Environment variables

Set environment variables:

SIMPLELOGGER_FILENAME="./simple-logger.log"
SIMPLELOGGER_LEVEL="INFO"
SIMPLELOGGER_APPEND="true"
SIMPLELOGGER_VERBOSE="true"
SIMPLELOGGER_MAXFILESIZE="1"
SIMPLELOGGER_MAXBACKUPFILES="2"
SIMPLELOGGER_DATEFORMAT="yyyy.MM.dd hh:mm:ss:SSS"

In this case use empty constructor when creating new SimpleLogger object.

> Environment variables are always loaded first (if they exist, otherwise default values will apply). They can be overwritten by properties file or inline whenever is necessary.

```java
SimpleLogger logger = new SimpleLogger();
logger.info("Hello simple-logger!");
```


### Writing plain text

Simple-logger can also be used to write some text quickly in the file. Those days 
when you are thinking about InputStreams and Readers and Buffers and are gone. 

Call `write` method to write just plain text, whatever you like:

```java
logger.write("Just some text");
```

### Logging exceptions

Simple-logger can also log exception stacks:

```java
try {...}
catch (Exception e) {
    logger.error("Exception is thrown: ", e);
}
```

### Closing streams

It is recommended to close the simple-logger output streams before stopping application:

```java
logger.close();
```


### Version history

1.7.0
- Refactored and a bit redesigned, but mostly refactored
- Support configuration with environment variables

---

1.6.4
- Added write(text) method where the text is written to log file without checking log level. Date and log level is also not printed. Just text.

---

1.6.3
- SimpleLogger implements Serializable

---

1.6.2
- create directories on path if they don't exist

---

1.6.1
- close file writer

...
