# AOP

## sayHello() method in User class

```
	public void sayHello() {
		System.out.println("Hello World!!!");
	}

	public void sleeping() {
		System.out.println("z z z");
	}

```

## Aspect

- advice is what you want to apply to the specific business logic method
- join point is where you want to apply aspect. we call join point as target method
- pointcut is a set of join point
- let me create 'LoggingAspect' class
- and then define pointcut

```
@Aspect
public class LoggingAspect {

	@Pointcut("execution(* org.egovframe.lab.ioc.User.abc*(..))")
	private void selectShow(){}

	@Before("selectShow()")
	public void beforeAdvice(JoinPoint thisJoinPoint) {

        String className = thisJoinPoint.getTarget().getClass().getSimpleName();
        String methodName = thisJoinPoint.getSignature().getName();

		System.out.println("@Before " + className + "." + methodName + "()");
	}

	@Around("selectShow()")
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

	@After("selectShow()")
	public void afterAdvice() {
		System.out.println("@After finally");
	}

	@AfterThrowing("selectShow()")
	public void afterThrowingAdvice() {
		System.out.println("@AfterThrowing");
	}

	@AfterReturning("selectShow()")
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