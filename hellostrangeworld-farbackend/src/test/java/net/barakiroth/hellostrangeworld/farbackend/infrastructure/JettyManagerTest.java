package net.barakiroth.hellostrangeworld.farbackend.infrastructure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class JettyManagerTest {
	
	@Test
	public void when_started_it_should_be_able_to_be_stopped_without_exceptions() throws InterruptedException {
		
		final JettyManager jettyManager = JettyManager.getInstance();
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