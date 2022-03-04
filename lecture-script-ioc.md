# IoC Container & Dependency Injection

## create project

- 개발환경의 Package Explorer에서 마우스 우클릭 > New 를 클릭합니다. 프로젝트명을 'lab201-ioc-concept' 이라 입력하고, Group Id에는 'org.egovframe.lab' 이라 입력하고 Next 버튼을 클릭하겠습니다
- Generate Example 체크 안 된 상태로 그대로 두고 Finish 버튼을 클릭하겠습니다.
Let me hit finish.

## create package

- main java source 에 패키지를 생성하겠습니다. 마우스 우클릭 > New > Package, Name 에는 'org.egovframe.lab.ioc' 라 하겠습니다.

## Create class

- 요즘 스마트폰 없는 사람이 없죠? 스마트폰 하나를 사서, 전화도 걸고, 문자도 보내보는 매우 간단한 프로젝트를 만들어 보겠습니다.
- 이 프로젝트의 목적은 IoC를 이해하는데 있으니, 그것에만 초점을 맞추어 진행해 볼께요.
- 먼저, Phone class 를 생성합니다. package 위에서 마우스 우클릭 > New > Class 클래스명은 그냥 Phone 이라 할께요.
- Phone에는 어떤 메소드가 필요할까요? 전화를 걸고, 문자를 보냅니다. 이렇게요. 매우 간단하죠?

```
package org.egovframe.lab.ioc;

public class Phone {

	public void call() {
		System.out.println("calling via phone");
	}

	public void text() {
		System.out.println("texting via phone");
	}

}
```

- 이번엔 User가 스마트폰을 소유하도록 하겠습니다. User class 를 생성하고, Phone 을 속성으로 추가헸습니다.
- package 위에서 마우스 우클릭 > New > Class 클래스명은 그냥 User 라 하고, Phone class를 private field로 갖습니다.

```
package org.egovframe.lab.ioc;

public class User {

	Phone phone;
}
```

- 그럼, User 객체를 생성하여 Phone의 전화거는 기능, 문자보내는 기능을 사용해 보도록 하겠습니다.
- UserPhoneApp 이라는 클래스를 만들어 보겠습니다.
- main method를 만들고 User 객체를 생성하여 전화를 걸어보겠습니다.

```
package org.egovframe.lab.ioc;

public class UserPhoneApp {

	public static void main(String[] args) {
		User user = new User();
		user.phone.call();
	}
}
```

- 전화가 걸릴까요? 실행해 볼까요?
- NullPointerException 이 발생합니다. 왜죠? Phone의 객체가 없죠? 그럼, User class 에서 객체를 생성 후 실행해 보겠습니다.

```
public class User {

	Phone phone = new Phone();

}
```

- 예, 전화가 걸렸지만, 여러분이 훌륭한 개발자라고 한다면 이렇게 개발하지 않을 것입니다. 외부에서 설정해 주도록 하는 것이 좋습니다.
- Phone을 private 하게 감추고 외부에서 설정되게 하려면 이렇게 합니다. private Phone phone; 로 바꾸고
- 마우스 우클릭 > Source > Generate Getters and Setters > 체크박스 체크하고 Generate 클릭
- UserPhoneApp Class 의 main method 에서 Phone phone = new Phone();

```
public class UserPhoneApp {

	public static void main(String[] args) {
		User user = new User();
		Phone phone = new Phone();
		user.setPhone(phone);
		user.getPhone().call();
	}
}
```

## Rename a class name and its corresponding file in eclipse

- Phone 이라는 것은 일반 명사입니다. 특정 회사에서 만든 Phone으로 바꾸고 싶습니다.
- I want to change Phone class into another name like SamsungPhone. How can I rename a class name and its corresponding files.
- Just right click on the class and select Refactor > Rename. That's it.
- ApplePhone 클래스를 하나 생성해 볼까요?
- Package Explorer 에서 SamsungPhone.java 를 선택 후 마우스 우클릭 Copy & Paste 합니다.
- I want to change my samsung phone to ApplePhone.
- User class에서 SamsungPhone을 ApplePhone으로 바꾸어 주어야합니다. 이렇게요.

```
public class User {

	private ApplePhone phone;

	public void setPhone(ApplePhone phone) {
		this.phone = phone;
	}

	public ApplePhone getPhone() {
		return phone;
	}

}
```

- UserPhoneApp 도 수정해 주어야 합니다.

```
public class UserPhoneApp {

	public static void main(String[] args) {
		User user = new User();
		ApplePhone phone = new ApplePhone();
		user.setPhone(phone);
		user.getPhone().call();
	}
}
```

## Use Interface

- 수정해야할 소스코드가 너무 많습니다. 훌륭한 개발자는 이렇게 변경사항이 생겼을 때를 대비해야 합니다.
- Phone 이라는 인터페이스를 생성하여 각 ApplePhone과 SamsungPhone이 상속받도록 하겠습니다.

```
public interface Phone {
	public void call();
	public void text();
}
```

- 각 class 들에는 implements Phone 를 붙여줍니다.
- User 에는 Phone 인터페이스로 바꾸어 주고요,
- UserPhoneApp 에서 다음과 같이 바꾸어줍니다.

```
public class UserPhoneApp {

	public static void main(String[] args) {
		User user = new User();
		Phone phone = new ApplePhone();
		user.setPhone(phone);
		user.getPhone().call();
	}
}
```

- 이제는 폰을 바꾸고 싶으면 이부분만 바꾸어주면 됩니다. 고객이 무엇을 바꾸어 달라고 해도 걱정이 없습니다.
- 이렇게 객체를 생성한 후 사용하기 위해서 new operator를 사용하였습니다. 최대한 Client side에서 Phone 이라고 하는 Dependency를 꽂아 주었습니다.
- 하지만, 이것 조차도 수정하지 않게 할 수 있을까요?
- 그래서 나온 것이 IoC Container 입니다.

## IoC Container

- 환경설정을 외부화 하겠습니다. 즉, xml 파일에 metadata를 만들고 IoC Container 로 하여금 읽고 객체를 생성하게 하겠습니다.
- main resource 에 spring bean configuration xml file 을 하나 생성하여 beans.xml 이라 하겠습니다. hit the next button and select beans and context
- bean element를 작성하여 id와 class 를 지정합니다. class 는 full qualified name 을 적어 주어야 해서, User class 에서 마우스 우클릭 Copy Qualified Name을 클릭하여, xml 파일에서 붙여넣기 합니다.

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="user" class="org.egovframe.lab.ioc.User">
		<property name="phone">
			<bean class="org.egovframe.lab.ioc.ApplePhone"></bean>
		</property>
	</bean>

</beans>
```

- phone bean이 다양한 클래스에서 참조할 수 있다면, phone bean 을 밖으로 빼고 참조하는 것이 좋습니다.

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="user" class="org.egovframe.lab.ioc.User">
		<property name="phone" ref="phone" />
	</bean>

	<bean id="phone" class="org.egovframe.lab.ioc.ApplePhone"></bean>

</beans>
```

- main 함수에서는 ApplicationContext 인터페이스를 사용합니다. 여기서 ApplicationContext가 IoC Container 입니다.
- 이 IoC Container에 특정 xml 파일을 가지고, 빈을 생성하라고 요청합니다.
ApplicationContext container will look for this particular file called beans.xml.
- 여기서 생성된 객체를 바로 bean 이라고 부릅니다. new operator 로 생성된 객체를 빈이라고 부르지 않고, IoC Container에서 생성된 객체를 bean 이라고 부릅니다.

```
public class UserPhoneApp {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    System.out.println("beans loaded successfully");
	}
}
```

- 실행해보면 에러가 없으면 객체들이 만들어져서 IoC Container에 존재합니다. 그럼, IoC Container에서 빈을 꺼내서 전화를 걸어보겠습니다.
- say that hey spring, could you give me the object for this application please 

```
public class UserPhoneApp {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		System.out.println("beans loaded successfully");
		User user = (User)context.getBean("user");
		user.getPhone().call();
	}
}
```

- 실행해보면, 정상적으로 ApplePhone으로 전화를 걸고 있습니다.
- 삼성폰으로 전화를 걸도록 바꾸려면 어떻게 하면 됩니까? 예, 외부화된 metadata 만 바꾸어 주면 됩니다.

```
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="user" class="org.egovframe.lab.ioc.User">
		<property name="phone" ref="phone" />
	</bean>

	<bean id="phone" class="org.egovframe.lab.ioc.SamsungPhone"></bean>

</beans>
```

- xml에 있는 metadata를 통해 bean을 생성할 때 setter method를 통해 dependency가 injected 된다고 해서 setter Injection이라고 합니다.
- setter을 통해서 주입되는지 확인해 보겠습니다.

```
public class User {

	private Phone phone;

	public void setPhone(Phone phone) {
		this.phone = phone;
		System.out.println("dependency injected via setPhone setter");
	}

	public Phone getPhone() {
		return phone;
	}

}

# 실행결과
dependency injected via setPhone setter
beans loaded successfully
calling via Samsung phone
```

## straight values like primitives and String and so on

- let me add fields name and age in User class

```
public class User {

	private String name;
	private int age;

```

- setter와 getter를 생성하고, toString() 을 Override 하겠습니다.
- user bean metadata 에 property element를 추가하겠습니다.

```
	<bean id="user" class="org.egovframe.lab.ioc.User" autowire="byName">
		<property name="name" value="Chris"></property>
		<property name="age" value="30"></property>
	</bean>

	<bean id="phone" class="org.egovframe.lab.ioc.SamsungPhone"></bean>
```

- UserPhoneApp Main method에서 확인해 볼까요?

```
public class UserPhoneApp {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		System.out.println("beans loaded successfully");
		User user = (User)context.getBean("user");
		user.getPhone().call();
		System.out.println(user.toString());
	}
}

# result
dependency injected via setPhone setter
beans loaded successfully
calling via Samsung phone
User [name=Chris, age=30]
```

- 보시는 바와 같이 이름과 나이가 출력되는 것을 통해 straight values like primitives and String 가 주입되는 것을 확인할 수 있습니다.

## beans tag

- 이렇게 IoC Container 를 통해서 DI 될 수 있는 것은 beans tag가 해석해 주기 때문입니다.
- beans tag 가 좀 복잡하게 적혀 있는데 이는 bean tag를 IoC Container가 bean을 생성할 수 있도록 하는 해석기라고 생각하시면 되겠습니다.

## Constructor Injection

- Constructor 를 통한 Injection 을 보여드리겠습니다.
- User class에서 마우스 우클릭 > Source > Generate Constructor using fields... > 모두 선택 후 generate 합니다.

```
	public User(String name, int age, Phone phone) {
		this.name = name;
		this.age = age;
		this.phone = phone;
		System.out.println("dependency injected via constructor");
	}

```

```
	<bean id="user" class="org.egovframe.lab.ioc.User">
		<constructor-arg type="String" value="Chris"></constructor-arg>
		<constructor-arg type="int" value="52"></constructor-arg>
		<constructor-arg>
			<bean class="org.egovframe.lab.ioc.ApplePhone"></bean>
		</constructor-arg>
	</bean>

# results
dependency injected via constructor
beans loaded successfully
calling via Applephone
User [name=Chris, age=52]
```

- constructor 를 통해 dependecy injected 됨을 알 수 있습니다.
- 이렇게 순서를 바꾸어도 IoC Container가 알아서 주입해 줍니다.

```
	<bean id="user" class="org.egovframe.lab.ioc.User">
		<constructor-arg type="int" value="52"></constructor-arg>
		<constructor-arg>
			<bean class="org.egovframe.lab.ioc.ApplePhone"></bean>
		</constructor-arg>
		<constructor-arg type="String" value="Chris"></constructor-arg>
	</bean>
```

- 대신 type을 없애면, 순서가 안맞으면 오류가 발생합니다.
- 그럴 때는 순서대로 적어주던지, 아니면, index attribute를 주면 됩니다.

```
	<bean id="user" class="org.egovframe.lab.ioc.User">
		<constructor-arg index="1" value="52"></constructor-arg>
		<constructor-arg>
			<bean class="org.egovframe.lab.ioc.ApplePhone"></bean>
		</constructor-arg>
		<constructor-arg index="0" value="Chris"></constructor-arg>
	</bean>
```

## autowire="byName"

- metadata에서 property 로 일일이 지정하지 않아도 User Bean에 phone bean 을 자동으로 엮어주는 기능이 있습니다.
- property element를 없애고, autowire="byName" 으로 하면 자동으로 엮어줍니다.

```
	<bean id="user" class="org.egovframe.lab.ioc.User" autowire="byName">
		<property name="name" value="Chris"></property>
		<property name="age" value="30"></property>
	</bean>
	<bean id="phone" class="org.egovframe.lab.ioc.SamsungPhone"></bean>
```

- 자동엮기로 실행 시 No default constructor found 오류가 발생할 수 있습니다. constructor 가 있다면 반드시 default constructor가 있는 지 확인해 주셔야 합니다.
- User class 에서 마우스 우클릭 > Source > Generate Constructor from superclass
- 실행해보면 정상작동하는 것을 알 수 있습니다. byName 으로 엮기 때문에 bean id와 User class의 phone 이라는 field가 일치해야 합니다.
- 만약 bean id="samsungPhone" 으로 고치면 오류가 발생합니다.

## autowire="byType"

- autowire="byType" 으로 고치면 Class type 으로 주입해 줍니다.
-

```
	<bean id="user" class="org.egovframe.lab.ioc.User" autowire="byType">
		<property name="name" value="Chris"></property>
		<property name="age" value="30"></property>
	</bean>

	<bean id="samsungPhone" class="org.egovframe.lab.ioc.ApplePhone"></bean>

# results
dependency injected via setPhone setter
beans loaded successfully
calling via Applephone
User [name=Chris, age=30]
```

- id와는 상관없이 type 으로 dependency injected 됨을 알 수 있습니다.

## @Autowired @Qualifier annotation

- xml file에 autowire attribute을 제거해도 User class에 @Autowired annotation을 붙여주면 IoC Container가 자동으로 엮어줍니다.
- Source > Organize Imports 를 한다.

```
# User class
	@Autowired
	public void setPhone(Phone phone) {
		this.phone = phone;
		System.out.println("dependency injected via setPhone setter");
	}
```

- 그리고, bean 설정 파일에서 autowire="byName" attribute를 삭제하고 <context:annotation-config/> element를 추가합니다.
- 즉, autowired attribute와 @Autowired는 동일하게 작동합니다.
- <context:annotation-config/> element 는 beans tag에 context 관련 namespace와 schema를 추가해 주어야 IoC Container가 인식합니다.

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
				http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">

	<context:annotation-config/>

	<bean id="user" class="org.egovframe.lab.ioc.User">
		<property name="name" value="Chris"></property>
		<property name="age" value="30"></property>
	</bean>
	<bean id="phone" class="org.egovframe.lab.ioc.SamsungPhone"></bean>
</beans>

# results
dependency injected via setPhone setter
beans loaded successfully
calling via Samsung phone
User [name=Chris, age=30]
```

- bean id를 "samsungPhone"으로 수정해도 작동합니다. 왜냐하면, name 으로 엮다가 매칭되는 name이 없으면, type 으로 찾아 엮어주기 때문입니다.

```
	<bean id="user" class="org.egovframe.lab.ioc.User">
		<property name="name" value="Chris"></property>
		<property name="age" value="30"></property>
	</bean>
	<bean id="samsungPhone" class="org.egovframe.lab.ioc.SamsungPhone"></bean>
```

- 그럼, SamsungPhone과 ApplePhone을 동시에 작성해 놓으면 IoC가 알아서 엮어줄까요?

```
	<bean id="user" class="org.egovframe.lab.ioc.User">
		<property name="name" value="Chris"></property>
		<property name="age" value="30"></property>
	</bean>
	<bean id="samsungPhone" class="org.egovframe.lab.ioc.SamsungPhone"></bean>
	<bean id="applePhone" class="org.egovframe.lab.ioc.ApplePhone"></bean>

# error
expected single matching bean but found 2: samsungPhone,applePhone
```

- User class에는 하나만 정의 되어 있는데, 2개의 bean 이 존재하니 어떤 것을 엮어 주어야 할지 판단하지 못하여 에러를 발생합니다.
- 이럴 때 사용하는 것이 Qualifier 입니다. User class 에서 @Qualifier annotation을 추가하고 이름을 작성해서 특정 bean 을 엮도록 IoC Container에 알려줍니다.

```
	@Autowired
	@Qualifier("samsungPhone")
	public void setPhone(Phone phone) {
		this.phone = phone;
		System.out.println("dependency injected via setPhone setter");
	}

# result
dependency injected via setPhone setter
beans loaded successfully
calling via Samsung phone
User [name=Chris, age=30]

	@Autowired
	@Qualifier("applePhone")
	public void setPhone(Phone phone) {
		this.phone = phone;
		System.out.println("dependency injected via setPhone setter");
	}

# result
dependency injected via setPhone setter
beans loaded successfully
calling via Applephone
User [name=Chris, age=30]
```

## Field Injection

- Setter 가 아닌 field에 @Autowired 를 적어주면 어떻게 될까요?

```
	@Autowired
	@Qualifier("samsungPhone")
	private Phone phone;

# result
beans loaded successfully
calling via Samsung phone
User [name=Chris, age=30]
```

- 엇 그런데, dependency injected via setPhone setter 메시지가 안보이네요.
- 그럼, Setter method를 삭제해도 엮어준다고 예상할 수 있습니다. 없애볼까요? 예, 예상대로 자동으로 엮어졌습니다.
- 이것이 Field Injection입니다.

## @Inject instead of @Autowired

- @Autowired 대신 @Inject annotation을 사용해 볼까요?

```
	@Inject
	@Qualifier("samsungPhone")
	private Phone phone;
```

- 정상적으로 작동합니다.

## @Resource instead of @Autowired

- @Autowired 대신 @Resource annotation을 사용해 볼까요?

```
	@Resource
	@Qualifier("samsungPhone")
	private Phone phone;
```

- 예, 동일하게 작동합니다. @Resource annotation은 바로 name을 지정할 수 있습니다. @Resource allows you to specify a name of the injected bean
- @Resource means get me a known resource by name. The name is extracted from the name of the annotated setter or field, or it is taken from the name-Parameter.

```
	@Resource(name="samsungPhone")
	public void setPhone(Phone phone) {
		this.phone = phone;
		System.out.println("dependency injected via setPhone setter");
	}

```

- @Autowired and @Inject are spring annotation, they search bean through Matches by Type and then Restricts by Qualifiers and then Matches by Name
- Whereas @Resource is a part of Java, it matches by Name, if there is no matches, it starts to seek bean through Matches by Type

## @PostConstruct

- 객체가 생성된 후 별도의 초기화 작업을 위해 실행하는 메소드를 선언할 수 있습니다. 예를 들어, 데이터베이스 connection 객체를 생성한다 든지,

```
# User class
	@PostConstruct
	public void init() {
		System.out.println("init method");
	}

# result
init method
beans loaded successfully
calling via Samsung phone
User [name=null, age=0]
```

- 어디에서도 init method를 호출하지 않았는데도, IoC가 bean을 생성한 후 자동으로 호출하였습니다.
- Spring calls methods annotated with @PostConstruct only once, just after the initialization of bean properties.

## @PreDestroy

```
	@PreDestroy
	public void destroy() {
		System.out.println("destroy method");
	}

# result
init method
beans loaded successfully
calling via Samsung phone
User [name=null, age=0]
```

- IoC Container 가 종료되기 직전에 호출되기 때문에 IoC Container를 close 해주어야 합니다.

```
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		System.out.println("beans loaded successfully");
		User user = (User)context.getBean("user");
		user.getPhone().call();
		System.out.println(user.toString());

		((ClassPathXmlApplicationContext)context).close();
	}

# result
init method
beans loaded successfully
calling via Samsung phone
User [name=null, age=0]
destroy method
```

## context:component-scan & @Component

- xml configuration file 에서 bean 을 정의하지 않고, class 에 annotation으로 정의할 수 있습니다.
- xml의 <bean id="user" class="org.egovframe.lab.ioc.User"> 에서 정의한 것은 User class 로 가서 @Component("user") 를 붙인 것과 동일합니다.
- 따라서, bean tag를 지우겠습니다.
- 마찬가지로 SamsungPhone class 위에 @Component("samsungPhone") 를 붙이고, bean tag samsungPhone를 지우고
- applePhone 도 마찬가지로 ApplePhone class 위에 @Component("applePhone") 를 붙이고, bean tag applePhone를 지웁니다.

```
	<bean id="user" class="org.egovframe.lab.ioc.User">
		<property name="name" value="Chris"></property>
		<property name="age" value="30"></property>
	</bean>
	<bean id="samsungPhone" class="org.egovframe.lab.ioc.SamsungPhone"></bean>
	<bean id="applePhone" class="org.egovframe.lab.ioc.ApplePhone"></bean>
```

- bean tag를 다 지우고 실행해 보니, 오류가 발생합니다.
- ApplicationContext에 어디서부터 component들을 scan하라고 알려주어야 합니다.
- context:component-scan tag를 적어준 후, base-package 를 알려주면 그 package 안에서 자동으로 찾아서 bean을 등록해 줍니다.

```
<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
				http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">

	<context:component-scan base-package="org.egovframe.lab.ioc" />

</beans>
```

- This is autoscan based on annotation.
- @Component 대신에 @Service 나 @Controller 또는 @Repository로 바꾸어도 동일하게 작동합니다.
- @Service, @Controller, @Repository 모두 @Component를 상속받아 만든 annotation이기 때문입니다.

## Java Config

- XML configuration file 자체를 없애고, 이를 대체하는 java 파일을 만들 수도 있습니다.
- xml 파일 beans.xml을 삭제하고, UserConfig class 를 생성합니다.

```
package org.egovframe.lab.ioc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.egovframe.lab.ioc")
public class UserConfig {

}
```

- Main 함수에서 ApplicationContext를 AnnotationConfigApplicatinContext로 교체합니다.
- close() 할 때도 AnnotationConfigApplicatinContext로 변환되도록 수정합니다.

```
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(UserConfig.class);
		System.out.println("beans loaded successfully");
		User user = (User)context.getBean("user");
		user.getPhone().call();
		System.out.println(user.toString());

		((AnnotationConfigApplicationContext)context).close();
	}

# result
dependency injected via setPhone setter
init method
beans loaded successfully
calling via Apple phone
User [name=null, age=0]
destroy method

```

## @Bean

- User class 의 @Component annotation을 없애면, 오류가 발생한다.
- UserConfig classdp @Bean 을 선언한 후 실행하면 정상작동한다.

```
package org.egovframe.lab.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.egovframe.lab.ioc")
public class UserConfig {

	@Bean
	public User user() {
		User user = new User();
		return user;
	}

}
```

- 마찬가지로, SamsingPhone class 와 ApplePhone class 의 @Component annotation 을 제거하고,
- UserConfig class에 @Bean 설정을 추가한다.

```
package org.egovframe.lab.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.egovframe.lab.ioc")
public class UserConfig {

	@Bean
	public User user() {
		User user = new User();
		return user;
	}

	@Bean
	public Phone samsungPhone() {
		Phone phone = new SamsungPhone();
		return phone;
	}

	@Bean
	public Phone applePhone() {
		Phone phone = new ApplePhone();
		return phone;
	}
}
```

- samsungPhone() 이 bean id가 됨을 알 수 있다.
- bean name을 별도로 줄 수도 있다.

```
	@Bean(name="samsungPhone")
	public Phone phone1() {
		Phone phone = new SamsungPhone();
		return phone;
	}

	@Bean(name="applePhone")
	public Phone phone2() {
		Phone phone = new ApplePhone();
		return phone;
	}
```

- 여러개의 bean 이름을 줄 수도 있다.

```
	@Bean(name={"samsungPhone","samsung"})
	public Phone phone1() {
		Phone phone = new SamsungPhone();
		return phone;
	}
```

## @Value

- Primative or String field에 @Value 에 초기값을 설정할 수 있다.

```
	@Value("Chris")
	private String name;

	@Value("52")
	private int age;

```

## @PropertySource

- resources main source 폴더에 New > File 을 추가하여 이름을 'user-info.properties' 로 한다.

```
# user-info.properties
user.Name = "Chris Yoon"
```

- User class의 name field @Value annotation에 ${user.Name}

```
	@Value("${user.Name}")
	private String name;

# result
User [name=${user.Name}, age=55]
```

- UserConfig class 에 @PropertySource("classpath:user-info.properties") 를 추가한다.

```
@Configuration
@ComponentScan(basePackages = "org.egovframe.lab.ioc")
@PropertySource("classpath:user-info.properties")
public class UserConfig {

	@Bean
	public User user() {
		User user = new User();
		return user;
	}

	@Bean(name={"samsungPhone","samsung"})
	public Phone phone1() {
		Phone phone = new SamsungPhone();
		return phone;
	}

	@Bean(name="applePhone")
	public Phone phone2() {
		Phone phone = new ApplePhone();
		return phone;
	}
}

# result
User [name="Chris Yoon", age=55]
```

## JUnitTest for Java Config

- pom.xml에 테스트 관련된 dependency 를 추가합니다.

```
		<!-- Test -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-test</artifactId>
			<version>${spring.maven.artifact.version}</version>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<artifactId>commons-logging</artifactId>
					<groupId>commons-logging</groupId>
				</exclusion>
			</exclusions>
		</dependency>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.13</version>
			<scope>test</scope>
		</dependency>
```

- resources test source folder에 UserPhoneText class 를 생성 후,
- @ContextConfiguration annotation을 추가하여 ApplicationContext에 UserConfig.class 를 통해 bean을 생성해 달라고 설정합니다.
- SpringJUnit4ClassRunner라는 JUnit용 테스트 컨텍스트 프레임워크 확장 클래스를 지정해 줍니다.

```
package org.egovframe.lab.ioc;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserConfig.class})
public class UserPhoneTest {

	@Resource(name="user")
	private User user;

	@Test
	public void callTest() {
		System.out.println(user.toString());

		Assert.assertEquals("User [name=\"eGovFrame\", age=12]", user.toString());
	}

}

# result
dependency injected via setPhone setter
init method
User [name="eGovFrame", age=12]
destroy method
```

## JUnitTest for XML based Configuration

- XML based Configuration 의 경우에는 xml 파일을 지정해 줍니다.

```
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath*:META-INF/spring/context-common.xml"
    ,"classpath*:META-INF/spring/context-emp.xml"
public class UserPhoneTest {

...
```

## Singleton Scope 

- When we define a bean with the singleton scope, When we define a bean with the singleton scope
- every time the same object is getting returned
- singletone scope is default value so we don't need to specify it

```
	@Resource(name="user")
	private User user;

	@Resource(name="user")
	private User user2;

	@Test
	public void callTest() {
		System.out.println(user + ", " + user2);
		
		Assert.assertEquals("User [name=\"eGovFrame\", age=12]", user.toString()); 
	}

# result
dependency injected via setPhone setter
init method
org.egovframe.lab.ioc.SamsungPhone@1f760b47, org.egovframe.lab.ioc.SamsungPhone@1f760b47
destroy method
```

## Prototype Scope

- A bean with the prototype scope will return a different instance every time it is requested from the container.
- when we define the bean with scope annotation like @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
- we will get a brand new object, each time we call a prototype scoped bean

```
# UserConfig.java
	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public User user() {
		User user = new User();
		return user;
	}

# result
dependency injected via setPhone setter
init method
dependency injected via setPhone setter
init method
org.egovframe.lab.ioc.SamsungPhone@461ad730, org.egovframe.lab.ioc.SamsungPhone@461ad730
```


## Proxy object

- User 를 Singleton 으로 하고, Phone을 Prototype 으로 하더라도, Phone 객체는 동일하다.
- 하지만, Proxy

```
@Configuration
@ComponentScan(basePackages = "org.egovframe.lab.ioc")
@PropertySource("classpath:user-info.properties")
public class UserConfig {
	
	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public User user() {
		User user = new User();
		return user;
	}

	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
	@Bean(name={"samsungPhone","samsung"})
	public Phone phone1() {
		Phone phone = new SamsungPhone();
		return phone;
	}

	@Bean(name="applePhone")
	public Phone phone2() {
		Phone phone = new ApplePhone();
		return phone;
	}
}
```