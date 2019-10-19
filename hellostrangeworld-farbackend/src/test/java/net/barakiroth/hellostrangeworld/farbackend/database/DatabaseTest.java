package net.barakiroth.hellostrangeworld.farbackend.database;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.barakiroth.hellostrangeworld.farbackend.Config;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;

public class DatabaseTest {

	private static final Logger log =
			LoggerFactory.getLogger(DatabaseTest.class);

	private static final Logger enteringTestHeaderLogger =
			LoggerFactory.getLogger("EnteringTestHeader");
	
	@Test
	public void when_started_it_should_be_able_to_be_stopped_without_exceptions() throws InterruptedException {

		enteringTestHeaderLogger.debug(null);

		final Config config = new Config();

		assertDoesNotThrow(
				() -> {
					final Database database = config.getDatabase();
					database.start();
					database.stop();
				}
			);
	}
}
