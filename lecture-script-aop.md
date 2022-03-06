# AOP

- I'm gonna show you how to set up your project to make sure AOP is working
- Let's go back to the IDE

## Project

- Let me create a project named "lab2-3-aop" (New > eGovFrame Web Project > )
- Group Id is "org.egovframe.lab.ex",
- Click the next, leave the box unchecked, hit the finish
- Maven > Add Dependency > type "test" in search box > click the spring-test and ok

## Interface HelloWorldService

- I'll create a package named "org.egovframe.lab.ex"or
- Let me create interface named "HelloService" having sayHello method in the package "org.egovframe.lab.ex"
- public String sayHello with a message parameter

```
package org.egovframe.lab.ex;

public interface HelloService {
	public String sayHello(String message);
}
```

## Class HelloServiceImpl with sayHello()

- Let me create a class named "HelloServiceImpl" implements the interface "HelloService"
- This class has one private field name;
- I'm generating setter. Click the right button > Source > Generate Getters and Setters > check the 'setName' and click Generate

```
public class HelloServiceImpl implements HelloService{

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String sayHello(String message) {
		return "Hello " + name + " "+message ;
	}
}
```

## Client

- Shall I test it? Let me create a HelloWorldApp class

## context-helloworld.xml

- in the resources folder > New > Spring Bean Configuration File named "context-helloservice.xml" > check the beans as well as context
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

## ApplicationContext

- I'll ask ApplicationContext to give me a bean named "helloService"

```
	public static void main(String[] args) throws Exception {
		String configLocation = "classpath*:META-INF/spring/context-*.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
		HelloWorldService helloworld = (HelloWorldService)context.getBean("helloworld");

		System.out.println("RESULT="+helloworld.sayHello("Nice to meet you!"));
		helloworld.sayError();
	}
```

## Aspect for Loggin

- The easiest way to print out the log message is to use sysout printline method.

```
	public String sayHello(String message) {
		System.out.println("logging");
		return "Hello " + name + " " + message;
	}
```

- logging message showed up

- But, it's not the best practice, so I remove it.
- Instead of this kinds of coding, I'll use AOP.
- Let me create a class named "LogAspect" that has an aspect which is a combination of advice and pointcut

```
public class LogAspect {
	public void beforeTargetMethod() {
		System.out.println("logging before method being executed");
	}
}
```

- In order to use this advice, let me define aspect inside the bean configuration file
- New > Spring Bean Configuration File > name is "context-aop.xml"
- check the box aop and beans and hit the finish

```
	<bean id="logAspect" class="org.egovframe.lab.ex.LogAspect" />

	<aop:config>
		<aop:pointcut id="targetMethod" expression="execution(* org.egovframe.lab..*Impl.*(..))" />
		<aop:aspect ref="logAspect">
			<aop:before pointcut-ref="targetMethod" method="beforeTargetMethod" />
		</aop:aspect>
	</aop:config>

```

- before advice is what you want to apply to the specific business logic method.
- join point is where you want to apply advice. we call join point as target method
- pointcut is a set of join point



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

	@After("targetMethod()")
	public void afterAdvice() {
		System.out.println("@After finally");
	}

	@AfterThrowing("targetMethod()")
	public void afterThrowingAdvice() {
		System.out.println("@AfterThrowing");
	}

	@AfterReturning("targetMethod()")
	public void afterReturningAdvice() {
		System.out.println("@AfterReturning");
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

## AfterReturning

- return value 를 advice 에서 받을 수 있습니다.
- returning="retVal" 포인트컷과 나란히 적어준 후 advice에 파라미터를 추가해 줍니다.

```
	public String sayHello() {
		// <- you want to add log with time
		System.out.println("Hello World!!!");
		return "Hello";
	}
```

```
	@AfterReturning(value="selectShow()", returning="retVal")
	public void afterReturningAdvice(JoinPoint thisJoinPoint, Object retVal) {
		System.out.println("@AfterReturning " + ", return value = " + retVal);
	}
```

## AfterThrowing

- AfterThrowing는 exception을 인자로 받아 사용할 수 있습니다.

```
	@AfterThrowing(value="selectShow()", throwing="exception")
	public void afterThrowingAdvice(JoinPoint thisJoinPoint, Exception exception) {
		System.out.println("@AfterThrowing");
	}
```
