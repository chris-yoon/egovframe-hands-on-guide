# Log

## Project

- New > eGovFrame Web Projcet
- Project name "lab-ex-logging"
- Group Id: lab.ex
- Make sure to leave the box "Generate Example" unchecked and Click Finish

## Package

- New > Package Name will be "lab.ex"

## Interface

- New > Interface name will be "HelloWorldService"
- Let me add two methods
- One is sayHello, the other is sayError

```
	public String sayHello();
	public void sayError();

```

## Class

- New > Class name will be "HelloWorldServiceImpl"
- implements the interface HelloWorldService
- And then mouse over the class and click "add unimplemented methods"
- let me define the logger "private static final Logger LOGGER = LoggerFactory.getLogger("lab");"
- import "import logger (org.slf4j)"
- let me add one field "name"
- Generate setter
- return value will be "Hello " + name
- I'll add the log for debug as well as info like these right before returning
- Inside the sayError method, I'll make an error intiontionally like this. the result of 100 devided by zero will occur ArithmeticException

```
public class HelloWorldServiceImpl implements HelloWorldService {

	private static final Logger LOGGER = LoggerFactory.getLogger("lab");

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String sayHello() {
		LOGGER.debug("[debug] sayHello executed");
		LOGGER.info("[info] sayHello executed");
		return "Hello " + name + "!!!";
	}

	public void sayError() {
		double i = 100 / 0;
	}

```

## Spring Bean Configuration file

- New > Spring Bean Configuration file > named "context-helloworld.xml", click next , check beanks and context and hit the finish
- Let me ask applicationContext to create bean named "helloworld" class equal "lab.ex.HelloWorldServiceImpl"
- property name is name and value would be "eGovFrame". almost done

```
	<bean name="helloworld" class="lab.ex.HelloWorldServiceImpl">
		<property name="name">
			<value>eGovFrame</value>
		</property>
	</bean>

```

## log4j2.xml

- If we run the application, the result "hello eGovFrame" is shown.
- However, log messages aren't shown, so we need to configure loggers so that log4j can recognized them.
- Create Other > XML named log42j.xml
- I want the logging messages to be printed to the console
- Logger name is defined as lab so that all classes in the package starting with "lab" will be affected

```
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <Appenders>
        <Console name="console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d %5p [%c] %m%n" />
        </Console>
    </Appenders>
    <Loggers>
        <Logger name="java.sql" level="DEBUG" additivity="false">
            <AppenderRef ref="console" />
        </Logger>
        <Logger name="lab" level="INFO" additivity="false">
            <AppenderRef ref="console" />
        </Logger>
		  <!-- log SQL with timing information, post execution -->
	    <Logger name="jdbc.sqltiming" level="INFO" additivity="false">
	        <AppenderRef ref="console" />
	    </Logger>
	    <Logger name="org.springframework" level="DEBUG" additivity="false">
	        <AppenderRef ref="console" />
	    </Logger>
        <Root level="ERROR">
            <AppenderRef ref="console" />
        </Root>
    </Loggers>
</Configuration>
```
- LogLevel is INFO so when the example is executed, the debug message will not show up

```
2022-03-04 16:43:05,130  INFO [lab] [info] sayHello executed
RESULT=Hello eGovFrame!!!
```

- When you change the Log level to DEBUG, you can find that two log messages, debug and info

```
        <Logger name="lab" level="DEBUG" additivity="false">

# result
2022-03-04 16:44:38,832 DEBUG [lab] [debug] sayHello executed
2022-03-04 16:44:38,832  INFO [lab] [info] sayHello executed
RESULT=Hello eGovFrame!!!
```