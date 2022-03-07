# Log

- I'm gonna show you how to use logger
- Let's go back to the IDE

## Project

- New > eGovFrame Web Projcet
- Project name "lab2-3-logging"
- Group Id: org.egovframe.lab.ex
- Make sure to leave the box "Generate Example" unchecked and Click Finish
- If you open up the Maven Dependencies, necessary dependencies on log4j related libraries are added automatically so you don't need to configure for logging related dependencies
- Just create a project through eGovFrame menu

## Package

- New > Package Name will be "org.egovframe.lab.ex"

## Interface

- New > Interface name will be "HelloService"
- Let me add two methods
- One is sayHello, the other is sayError

```
	public String sayHello();
	public void sayError();

```

## Class

- New > Class name will be "HelloServiceImpl"
- implements the interface HelloService
- And then mouse over the class and click "add unimplemented methods"
- let me define the logger "private static final Logger LOGGER = LoggerFactory.getLogger("");"
- import "import logger (org.slf4j)"
- return value will be "Hello " + name
- let me add one field "name"
- Generate setter
- I'll add the log for debug as well as info like these right before returning
- Inside the sayError() method, I'll make an error intiontionally like this. the result of 100 devided by zero will occur ArithmeticException

```
public class HelloServiceImpl implements HelloService {

	private static final Logger LOGGER = LoggerFactory.getLogger("");

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

- What I'm gonna do now is bean configuration.
- in the resources folder > New > Spring Bean Configuration File named "context-helloservice.xml" > check the beans as well as context
- Let me ask applicationContext to create bean named "helloService", class equals "org.egovframe.lab.ex.HelloServiceImpl"
- property name is name and value would be "eGovFrame". almost done

```
	<bean id="helloService" class="org.egovframe.lab.ex.HelloServiceImpl">
		<property name="name">
			<value>eGovFrame</value>
		</property>
	</bean>
```

## ApplicationContext

- To run it, let me create a HelloApp class
- I'll ask ApplicationContext to give me a bean named "helloService"

```
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("context-*.xml");
		HelloService helloService = context.getBean("helloService", HelloService.class);

		System.out.println("RESULT="+helloService.sayHello());
	}

# result
RESULT=Hello eGovFrame!!!
```

- If we run the application, the result "hello eGovFrame" is shown.

## log4j2.xml

- However, these log messages aren't shown, so we need to configure loggers so that log4j can recognize them.
- Create Other > XML named log4j2.xml
- I want the logging messages to be printed to the console
- Package of a logger is defined by the attribute "name" so Logger logs messages in all the child packages and their classes.
- Logger also has level and may have one or many appenders (logging destinations) attached to it.

- Root logger that logs messages with level ERROR or above in all packages to the console.
- "org.egovframe.lab.ex" logger that logs messages with level INFO or above in package "org.egovframe.lab.ex" and its child packages to the console.

```
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <Appenders>
        <Console name="console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d %5p [%c] %m%n" />
        </Console>
    </Appenders>
    <Loggers>
        <Logger name="org.egovframe.lab.ex" level="DEBUG" additivity="false">
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

- Only Log level INFO has showed up.
- When you change the Log level to DEBUG, you will see messages with level DEBUG or above.

```
        <Logger name="lab" level="DEBUG" additivity="false">

# result
2022-03-04 16:44:38,832 DEBUG [lab] [debug] sayHello executed
2022-03-04 16:44:38,832  INFO [lab] [info] sayHello executed
RESULT=Hello eGovFrame!!!
```

- you can find that two log messages, debug and info

## Ending

- We’ve learned what are the options when it comes to logging, how to add logging to your application, how to configure it.
- In fact, logging is invaluable in troubleshooting in general
- I hope this gave you an idea on how to prepare your future application to be ready with a nice logging management
- Thank you for watching and see you next sesion

