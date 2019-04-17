package net.barakiroth.hellostrangeworld;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.barakiroth.hellostrangeworld.frontend.UserPrompter;

public class HelloStrangeWorldApp {
	
	static private final Logger logger = LoggerFactory.getLogger(HelloStrangeWorldApp.class);
	
	public static void main(String[] args) {

		logger.debug("Parameters received: {}", (Object[])args);
		
		final UserPrompter userPrompter = new UserPrompter();
		final String greetee = userPrompter.askWhomTheUserWantsToGreet();
		System.err.println(MessageFormat.format("Hello strange {0}!", greetee));
	}
}