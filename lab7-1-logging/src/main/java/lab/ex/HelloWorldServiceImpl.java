package lab.ex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
