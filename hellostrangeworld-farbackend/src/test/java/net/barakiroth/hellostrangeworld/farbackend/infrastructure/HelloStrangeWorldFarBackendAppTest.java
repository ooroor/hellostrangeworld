package net.barakiroth.hellostrangeworld.farbackend.infrastructure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloStrangeWorldFarBackendAppTest {

	private static final Logger enteringTestHeaderLogger =
			LoggerFactory.getLogger("EnteringTestHeader");
	
	@Test
	public void when_application_is_started_it_should_not_crash() throws InterruptedException {
		
		enteringTestHeaderLogger.debug(null);
		
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