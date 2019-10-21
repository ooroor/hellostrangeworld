package net.barakiroth.hellostrangeworld.farbackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer.JettyManager;

public class Main {

	private static final Logger enteringMethodHeaderLogger = LoggerFactory.getLogger("EnteringMethodHeader");
    private static final Logger leavingMethodHeaderLogger = LoggerFactory.getLogger("LeavingMethodHeader");

	public static void main(final String[] args) {

        enteringMethodHeaderLogger.debug(null);

        final Config config = Config.getSingletonInstance();
        final Main main = new Main();
        main.run(config);

        leavingMethodHeaderLogger.debug(null);
	}

	private void run(final Config config) {
		
		enteringMethodHeaderLogger.debug(null);
		
		final JettyManager jettyManager = config.getJettyManager();
		jettyManager.start();
		
		final Database database = config.getDatabase();
		database.start();
		
		leavingMethodHeaderLogger.debug(null);
	}
}