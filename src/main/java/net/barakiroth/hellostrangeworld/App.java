package net.barakiroth.hellostrangeworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
	
	static private final Logger logger = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {
		
		logger.debug("Parameters received: {}", (Object[])args);
		
		System.err.println("Hello strange world!");
	}
}