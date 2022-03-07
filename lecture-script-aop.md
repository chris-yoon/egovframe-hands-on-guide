# AOP

- I'm gonna show you how to set up a project to make sure AOP is working
- Let's go back to the IDE

## Project

- Let me create a project named "lab2-4-aop" (New > eGovFrame Web Project > )
- Group Id is "org.egovframe.lab.ex",
- Click the next, leave the box unchecked, hit the finish
- Maven > Add Dependency > type "test" in search box > click the spring-test and ok

## Package creation

- I'll create a package named "org.egovframe.lab.ex"or

## Interface HelloWorldService

- Let me create interface named "HelloService" having sayHello method in the package "org.egovframe.lab.ex"
- Let's say public String sayHello with an argument message.

```
package org.egovframe.lab.ex;

public interface HelloService {
	public String sayHello(String message);
}
```

## Class HelloServiceImpl with sayHello()

- Let me create a class named "HelloServiceImpl" implements the interface "HelloService"
- This will return "Hello eGovFrame" and message;
- Create a field name.
- I'm generating setter. Click the right button > Source > Generate Getters and Setters > check the 'setName' and click Generate

```
public class HelloServiceImpl implements HelloService{

	private String name;

	@Value("eGovFrame")
	public void setName(String name) {
		this.name = name;
	}

	public String sayHello(String message) {
		return "Hello " + name + " "+message ;
	}
}
```

## context-helloservice.xml

- What I'm gonna do is bean configuration.
- in the resources folder > New > Spring Bean Configuration File named "context-helloservice.xml" > check the beans as well as context
- I'll use component-scan so that the ioc container can search for the annotated classes.

```
	<context:component-scan base-package="org.egovframe.lab.ex"></context:component-scan>
```

- go back the impl class, annotate @Component fot the class to be scanned and managed by IoC container.

```
# HelloServiceImpl.java

@Component("helloService")
public class HelloServiceImpl implements HelloService{

```

## ApplicationContext

- To run it, let me create a HelloApp class
- I'll ask ApplicationContext to give me a bean named "helloService"

```
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("context-*.xml");
		HelloService helloService = context.getBean("helloService", HelloService.class);

		System.out.println("RESULT=" + helloService.sayHello("Nice to meet you!"));
	}
```

## Aspect for Logging

- What I want to do next is to print out log message.
- The easiest way to print out the log message is to use sysout printline method.

```
	public String sayHello(String message) {
		System.out.println("logging");
		return "Hello " + name + " " + message;
	}
```

- logging message's showed up

- But, it's not the best practice, so I remove it.
- Instead of this kinds of coding, I'll use AOP.

## aop:aspectj-autoproxy

- In order to use AOP, let me define aspect inside the bean configuration file
- New > Spring Bean Configuration File > name is "context-aspect.xml"
- check the box aop and beans and hit the finish

- In XML config file, I'll add aop:aspectj-autoproxy element to enable @AspectJ annotation support.

```
	<aop:aspectj-autoproxy></aop:aspectj-autoproxy>
```

## LoggingAspect class

- I'll create a class named "LoggingAspect" that has an aspect which is a combination of advice and pointcut
- The @Aspect annotation on a class marks it as an aspect
- Recall that pointcuts determine join points of interest, and thus enable us to control when advice executes.
- Pointcut("execution(\* --> all kinds of return type
- org.egovframe.lab.ex.HelloServiceImpl.say\*(..))") means all methods starting with "say" in this HelloServiceImpl class
- What I'm going to do is to add before advice with @Before annotation
- Let's just run it to see output.

```
@Aspect
@Component
public class LoggingAspect {

	@Pointcut("execution(* org.egovframe.lab.ex.HelloServiceImpl.say*(..))")
	private void targetMethod() {}

	@Before("targetMethod()")
	public void beforeAdvice() {
		System.out.println("logging before the target method being executed");
	}

	@After("targetMethod()")
	public void afterAdvice() {
		System.out.println("logging after the target method being executed");
	}
}
```

## System.out.println should be converted into the way of using log4j

- In the LoggingAspect class, System.out.println should be converted into the way of using log4j
- I'll copy lo4j2.xml from the previous project and paste it
- Let's use the LoggerFactory

## LoggingAspect.java - Annotation Configuration

```
package org.egovframe.lab.ex;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	@Pointcut("execution(* org.egovframe.lab.ex.HelloServiceImpl.say*(..))")
	private void targetMethod() {
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

	@Before("targetMethod()")
	public void beforeAdvice(JoinPoint thisJoinPoint) {

		String className = thisJoinPoint.getTarget().getClass().getSimpleName();
		String methodName = thisJoinPoint.getSignature().getName();

		StringBuffer buf = new StringBuffer();
		buf.append("LoggingAspect.beforeAdvice : [" + className + "." + methodName + "()]");
		Object[] arguments = thisJoinPoint.getArgs();
		int argCount = 0;
		for (Object obj : arguments) {
			buf.append("\n - arg ");
			buf.append(argCount++);
			buf.append(" : ");
			buf.append(ToStringBuilder.reflectionToString(obj));
		}
		LOGGER.debug(buf.toString());
	}

	@After("targetMethod()")
	public void afterAdvice(JoinPoint thisJoinPoint) {
		LOGGER.debug("LoggingAspect.afterAdvice executed.");
	}

	@AfterReturning(value = "targetMethod()", returning = "retVal")
	public void afterReturningAdvice(JoinPoint thisJoinPoint, Object retVal) {

		String className = thisJoinPoint.getTarget().getClass().getSimpleName();
		String methodName = thisJoinPoint.getSignature().getName();

		StringBuffer buf = new StringBuffer();
		buf.append("LoggingAspect.afterReturningAdvice : [" + className + "." + methodName + "()]");

		buf.append("\n");

		if (retVal instanceof List) {
			List<?> resultList = (List<?>) retVal;
			buf.append("resultList size : " + resultList.size() + "\n");
			for (Object oneRow : resultList) {
				buf.append(ToStringBuilder.reflectionToString(oneRow));
				buf.append("\n");
			}
		} else {
			buf.append(ToStringBuilder.reflectionToString(retVal));
		}
		LOGGER.debug(buf.toString());
	}

	@AfterThrowing(value = "targetMethod()", throwing = "exception")
	public void afterThrowingAdvice(JoinPoint thisJoinPoint, Exception exception) throws Exception {
		LOGGER.debug("LoggingAspect.afterThrowingAdvice executed.");
		LOGGER.error("An error occured. {}", exception);

		throw new Exception("An error occured.", exception);
	}

	@Around("targetMethod()")
	public Object aroundAdvice(ProceedingJoinPoint thisJoinPoint) throws Throwable {
		LOGGER.debug("LoggingAspect.aroundAdvice start.");

		Object retVal = thisJoinPoint.proceed();

		LOGGER.debug("LoggingAspect.aroundAdvice end.");
		return retVal;
	}
}
```

## Before Advice & After Advice

- Before Advice and After advice can take an argument JoinPoint so that you can get class name and method name from target object.

```
	@Before("targetMethod()")
	public void beforeAdvice(JoinPoint thisJoinPoint) {

		System.out.println("logging before the target method " + thisJoinPoint.getSignature().getName() + " being executed");
	}
```

## AfterReturning Advice

- When it comes to the AfterReturning advice or AfterThrowing advice, we should take 2 arguments
- Make sure to have one more argument, returning and throwing respectively.
- inside the @AfterReturning advice can use returned value from the target method.

```
	@AfterReturning(value="targetMethod()", returning = "retVal")
	public void afterReturningAdvice(JoinPoint thisJoinPoint,
            Object retVal) {

        String className = thisJoinPoint.getTarget().getClass().getSimpleName();
        String methodName = thisJoinPoint.getSignature().getName();

        StringBuffer buf = new StringBuffer();
        buf.append("LoggingAspect.afterReturningAdvice : ["
            + className + "." + methodName + "()]");

        buf.append("\n");

        if (retVal instanceof List) {
            List<?> resultList = (List<?>) retVal;
            buf.append("resultList size : " + resultList.size() + "\n");
            for (Object oneRow : resultList) {
                buf.append(ToStringBuilder.reflectionToString(oneRow));
                buf.append("\n");
            }
        } else {
            buf.append(ToStringBuilder.reflectionToString(retVal));
        }
        LOGGER.debug(buf.toString());
    }
```

## AfterThrowing Advice

- inside the @AfterThrowing advice can use throwed exception from the target method.

```
	@AfterThrowing(value = "targetMethod()", throwing = "exception")
	public void afterThrowingAdvice(JoinPoint thisJoinPoint, Exception exception) throws Exception {
		LOGGER.debug("LoggingAspect.afterThrowingAdvice executed.");
		LOGGER.error("An error occured. {}", exception);

		throw new Exception("An error occured.", exception);
	}
```

## Around Advice

- Around advice runs before and after the target method execution.
- Around advice are always required to have ProceedingJoinPoint as an argument and we should use it's proceed() method to invoke the target method.

```
	@Around("targetMethod()")
	public Object aroundAdvice(ProceedingJoinPoint thisJoinPoint) throws Throwable {
		LOGGER.debug("LoggingAspect.aroundAdvice start.");

		Object retVal = thisJoinPoint.proceed();

		LOGGER.debug("LoggingAspect.aroundAdvice end.");
		return retVal;
	}
```

- First LOGGER here is before the target method execution
- Second LOGGER is after the target method execution

## sayError()

- Shall we raise exception by calling sayError() method?
- go to the HelloServiceImpl class, let's say public void sayError()

```
	public void sayError() {
        double i = 100/0;
	}
```

- go back to the App class, just call it. helloService.sayError();
- you can see the log message from adviced target object

## XML config

- If you want to use XML configuration instead of Annotation, please refer to the training materials
- for your information, first remove all annotations
- define bean tag with name and class
- as for aop configuration, it should be like this

```
	<bean name="helloService" class="org.egovframe.lab.ex.HelloServiceImpl">
		<property name="name">
			<value>eGovFrame</value>
		</property>
	</bean>
```

- before advice is what you want to apply to the specific business logic method.
- join point is where you want to apply advice. we call join point as target method
- pointcut is a set of join point

```
	<bean id="loggingAspect" class="org.egovframe.lab.ex.LoggingAspect" />

	<aop:config>
		<aop:pointcut id="targetMethod" expression="execution(* org.egovframe.lab..*Impl.*(..))" />
		<aop:aspect ref="loggingAspect">
			<aop:before pointcut-ref="targetMethod" method="beforeAdvice" />
			<aop:after-returning pointcut-ref="targetMethod" method="afterReturningAdvice" returning="retVal" />
			<aop:after-throwing pointcut-ref="targetMethod" method="afterThrowingAdvice" throwing="exception" />
			<aop:after pointcut-ref="targetMethod" method="afterAdvice" />
			<aop:around pointcut-ref="targetMethod" method="aroundAdvice" />
		</aop:aspect>
	</aop:config>
```

## Ending

- Thank you so much for your interest and attention
- I hope this session would be helpful for your understanding on AOP.

---

# Additional Information

- 처음부터 Annotation 방식이 아니라, XML 방식으로 시작했을 때 사용하는 강의 스트립트이다.

- bean tag should be defined. name is "helloService"
- when it comes to class, it should be the full qualified name. so go to the class HelloServiceImpl, right click, Copy qualified Name
- go back to the configuration and paste it.
- property name equals "name", value would be "eGovFrame"

```
	<bean name="helloService" class="org.egovframe.lab.ex.HelloServiceImpl">
		<property name="name">
			<value>eGovFrame</value>
		</property>
	</bean>
```

- before advice is what you want to apply to the specific business logic method.
- join point is where you want to apply advice. we call join point as target method
- pointcut is a set of join point

```
	<bean id="loggingAspect" class="org.egovframe.lab.ex.LoggingAspect" />

	<aop:config>
		<aop:pointcut id="targetMethod" expression="execution(* org.egovframe.lab..*Impl.*(..))" />
		<aop:aspect ref="loggingAspect">
			<aop:before pointcut-ref="targetMethod" method="beforeAdvice" />
			<aop:after-returning pointcut-ref="targetMethod" method="afterReturningAdvice" returning="retVal" />
			<aop:after-throwing pointcut-ref="targetMethod" method="afterThrowingAdvice" throwing="exception" />
			<aop:after pointcut-ref="targetMethod" method="afterAdvice" />
			<aop:around pointcut-ref="targetMethod" method="aroundAdvice" />
		</aop:aspect>
	</aop:config>
```

## Java Config

- let me create 'LoggingAspect' class
- and then define pointcut

```
@Aspect
public class LoggingAspect {

	@Pointcut("execution(* org.egovframe.lab..*Impl.*(..))")
	private void targetMethod(){}

	@Before("targetMethod()")
	public void beforeAdvice(JoinPoint thisJoinPoint) {

        String className = thisJoinPoint.getTarget().getClass().getSimpleName();
        String methodName = thisJoinPoint.getSignature().getName();

		System.out.println("@Before " + className + "." + methodName + "()");
	}

	@Around("targetMethod()")
	public void aroundAdvice(ProceedingJoinPoint thisJoinPoint) {
        String className = thisJoinPoint.getTarget().getClass().getSimpleName();
        String methodName = thisJoinPoint.getSignature().getName();

		System.out.println("@Around before " + className + "." + methodName + "()");
		try {
			thisJoinPoint.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("@Around after " + className + "." + methodName + "()");
	}

}
```

## bean creation in UserConfig.java

- aspect 도 bean 으로 생성되어 있어야 합니다.

```
	@Bean
	public LoggingAspect  loggingAspect() {
		LoggingAspect loggingAspect = new LoggingAspect();
		return loggingAspect;
	}
```

## App Test

```
public class App {

	public static void main(String[] args) {

		ApplicationContext context = new AnnotationConfigApplicationContext(UserConfig.class);
		User user = context.getBean("user", User.class);

		user.sayHello();
		user.sleeping();
	}
}
```

## LoggingAspect.java - XML Configuration

```
package org.egovframe.lab.ex;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingAspect {

	private void targetMethod() {
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

	public void beforeAdvice(JoinPoint thisJoinPoint) {

		String className = thisJoinPoint.getTarget().getClass().getSimpleName();
		String methodName = thisJoinPoint.getSignature().getName();

		StringBuffer buf = new StringBuffer();
		buf.append("LoggingAspect.beforeAdvice : [" + className + "." + methodName + "()]");
		Object[] arguments = thisJoinPoint.getArgs();
		int argCount = 0;
		for (Object obj : arguments) {
			buf.append("\n - arg ");
			buf.append(argCount++);
			buf.append(" : ");
			buf.append(ToStringBuilder.reflectionToString(obj));
		}
		LOGGER.debug(buf.toString());
	}

	public void afterAdvice(JoinPoint thisJoinPoint) {
		LOGGER.debug("LoggingAspect.afterAdvice executed.");
	}

	public void afterReturningAdvice(JoinPoint thisJoinPoint, Object retVal) {

		String className = thisJoinPoint.getTarget().getClass().getSimpleName();
		String methodName = thisJoinPoint.getSignature().getName();

		StringBuffer buf = new StringBuffer();
		buf.append("LoggingAspect.afterReturningAdvice : [" + className + "." + methodName + "()]");

		buf.append("\n");

		if (retVal instanceof List) {
			List<?> resultList = (List<?>) retVal;
			buf.append("resultList size : " + resultList.size() + "\n");
			for (Object oneRow : resultList) {
				buf.append(ToStringBuilder.reflectionToString(oneRow));
				buf.append("\n");
			}
		} else {
			buf.append(ToStringBuilder.reflectionToString(retVal));
		}
		LOGGER.debug(buf.toString());
	}

	public void afterThrowingAdvice(JoinPoint thisJoinPoint, Exception exception) throws Exception {
		LOGGER.debug("LoggingAspect.afterThrowingAdvice executed.");
		LOGGER.error("An error occured. {}", exception);

		throw new Exception("An error occured.", exception);
	}

	public Object aroundAdvice(ProceedingJoinPoint thisJoinPoint) throws Throwable {
		LOGGER.debug("LoggingAspect.aroundAdvice start.");

		Object retVal = thisJoinPoint.proceed();

		LOGGER.debug("LoggingAspect.aroundAdvice end.");
		return retVal;
	}
}
```
