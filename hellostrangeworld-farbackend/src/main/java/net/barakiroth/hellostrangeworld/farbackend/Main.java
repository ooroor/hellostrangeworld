package net.barakiroth.hellostrangeworld.farbackend;

import net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer.JettyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger enteringMethodHeaderLogger = LoggerFactory.getLogger("EnteringMethodHeader");
    private static final Logger leavingMethodHeaderLogger = LoggerFactory.getLogger("LeavingMethodHeader");

	public static void main(final String[] args) {

        enteringMethodHeaderLogger.debug(null);

        final Config config = new Config();
        final Main main = new Main();
        main.run(config);

        leavingMethodHeaderLogger.debug(null);
	}

	private void run(final Config config) {
		
		enteringMethodHeaderLogger.debug(null);
		
		final JettyManager jettyManager = config.getJettyManager();
		jettyManager.start();
		
		leavingMethodHeaderLogger.debug(null);
	}
}