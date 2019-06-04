package net.barakiroth.hellostrangeworld.farbackend.infrastructure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class HelloStrangeWorldFarBackendAppTest {
	
	@Test
	public void when_application_is_started_it_should_not_crash() throws InterruptedException {
		
		JettyManager jettyManager = null;
		try {
			jettyManager = HelloStrangeWorldFarBackendApp.getJettyManager();
			new Thread(
					new Runnable() {
						public void run() {
							assertDoesNotThrow(() -> HelloStrangeWorldFarBackendApp.main(null));
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