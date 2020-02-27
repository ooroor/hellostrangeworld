package net.barakiroth.hellostrangeworld.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.JettyManager;

public class Main {

	private static final Logger enteringMethodHeaderLogger = LoggerFactory.getLogger("EnteringMethodHeader");
	private static final Logger leavingMethodHeaderLogger = LoggerFactory.getLogger("LeavingMethodHeader");

	private static final Main main = new Main();

	private IBackendConfig config;
	private JettyManager jettyManager;

	private Main() {
	}

	/**
	 * Start the embedded servlet container and make ready for REST calls.
	 * 
	 * @param args Not used
	 */
	public static void main(final String[] args) {

		enteringMethodHeaderLogger.debug(null);

		final Main main = getSingletonInstance();
		main.run();

		leavingMethodHeaderLogger.debug(null);
	}

	static Main getSingletonInstance() {
		return Main.main;
	}

	private void run() {

		enteringMethodHeaderLogger.debug(null);

		final IBackendConfig config = getConfig();

		final JettyManager jettyManager = getJettyManager(config);
		jettyManager.start();

		leavingMethodHeaderLogger.debug(null);
	}

	private void setConfig(final IBackendConfig config) {
		this.config = config;
	}

	private IBackendConfig getConfig() {
		if (this.config == null) {
			final IBackendConfig config = BackendConfig.getSingletonInstance();
			setConfig(config);
		}
		return this.config;
	}

	private void setJettyManager(final JettyManager jettyManager) {
		this.jettyManager = jettyManager;
	}

	private JettyManager getJettyManager(final IBackendConfig config) {
		if (this.jettyManager == null) {
			final JettyManager jettyManager = config.getJettyManager();
			setJettyManager(jettyManager);
		}
		return this.jettyManager;
	}
}