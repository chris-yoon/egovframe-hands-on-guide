import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.egovframe.lab.ex.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {	"classpath*:META-INF/spring/context-*.xml"})
public class AspectTest {
	
	@Resource(name="helloService")
	private HelloService helloService;
	
    @Test
    public void testAdvice() throws Exception {

    	String resultStr = helloService.sayHello("Nice to meet you!");
    	assertEquals("Hello eGovFrame Nice to meet you!", resultStr);
    }

    @Test
    public void testAdviceWithException() throws Exception {

    	try {
    		helloService.sayError();
		} catch (Exception e) {

		}
    }
    
}
