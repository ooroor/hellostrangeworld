package net.barakiroth.hellostrangeworld.farbackend.infrastructure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyManagerTest {

	private static final Logger enteringTestHeaderLogger =
			LoggerFactory.getLogger("EnteringTestHeader");
	
	@Test
	public void when_started_it_should_be_able_to_be_stopped_without_exceptions() throws InterruptedException {
		
		enteringTestHeaderLogger.debug(null);
		
		final JettyManager jettyManager = JettyManager.create();
		try {
			new Thread(
					new Runnable() {
						public void run() {
							assertDoesNotThrow(() -> jettyManager.startServletContainer());
						};
					}
			).start();
		} finally {
			while (!jettyManager.isStarted()) {
				Thread.sleep(50);
			}
			jettyManager.stopServletContainer();
		}
	}
}