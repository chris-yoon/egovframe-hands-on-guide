package lab;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {	"classpath*:META-INF/spring/context-*.xml"})
public class CustomerServiceTest {
	
	@Resource(name="customerXML") //xml
	CustomerXMLServiceImpl xmlcustomer; 

	@Resource(name="customer") //Annotation
	CustomerAnnotationServiceImpl customer;
	
	@Test
	public void testMain() {
		
		assertEquals("1 eGovFrame Annotation", customer.getCustomerName("1"));
		assertEquals("1 S Annotation", customer.getCustomerGrade("1"));
		
		assertEquals("1 eGovFrame XML", xmlcustomer.getCustomerName("1"));
		assertEquals("1 S XML", xmlcustomer.getCustomerGrade("1"));
	}
}
