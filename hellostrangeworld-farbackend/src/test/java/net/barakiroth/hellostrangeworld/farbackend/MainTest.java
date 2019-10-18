package net.barakiroth.hellostrangeworld.farbackend;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainTest {

	private static final Logger enteringTestHeaderLogger =
			LoggerFactory.getLogger("EnteringTestHeader");
	
	@Test
	public void when_application_is_started_it_should_not_crash() throws InterruptedException {
		
		enteringTestHeaderLogger.debug(null);
		
		Thread thread = null;
		try {
			thread =
				new Thread(
						new Runnable() {
							public void run() {
								assertDoesNotThrow(() -> Main.main(null));
							};
						}
				);
			thread.start();
		} finally {
			thread.stop();
		}
	}
}