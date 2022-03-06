package org.egovframe.lab.ex;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloApp {

	public static void main(String[] args) throws Exception {
		String configLocation = "context-*.xml"; 
		ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
		HelloService helloService = (HelloService)context.getBean("helloService");
		
		System.out.println("RESULT="+helloService.sayHello());
//		helloworld.sayError();
	}
}
