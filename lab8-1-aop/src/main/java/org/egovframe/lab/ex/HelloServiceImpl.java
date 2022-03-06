package org.egovframe.lab.ex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HelloServiceImpl implements HelloService{

    Logger logger1 = LoggerFactory.getLogger(HelloServiceImpl.class);

	private String name;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String sayHello(String message) {
		logger1.debug("sayHello executed");
		return "Hello " + name + " "+message ;
	}
	
	public void sayError() {
        double i = 100/0;
	}
}
