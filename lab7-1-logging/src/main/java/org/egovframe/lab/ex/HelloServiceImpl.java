package org.egovframe.lab.ex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloServiceImpl implements HelloService {

	private static final Logger LOGGER = LoggerFactory.getLogger("org.egovframe.lab.ex");

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
