package lab;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomerServiceApp {
	public static void main(String[] args) {
		String configLocation = "classpath*:META-INF/spring/context-*.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
		
		CustomerService customerXML=(CustomerService)context.getBean("customerXML");
		System.out.println("[XML]");
		System.out.println("NAME="+customerXML.getCustomerName("1"));
		System.out.println("GRADE="+customerXML.getCustomerGrade("1"));
		
		CustomerService customerAnnotation = (CustomerService)context.getBean("customer");
		
		System.out.println("[Annotation]");
		System.out.println("NAME="+customerAnnotation.getCustomerName("2"));
		System.out.println("GRADE="+customerAnnotation.getCustomerGrade("2"));
	}
}
