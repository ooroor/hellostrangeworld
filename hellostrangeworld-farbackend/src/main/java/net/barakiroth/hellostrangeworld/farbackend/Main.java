package net.barakiroth.hellostrangeworld.farbackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer.JettyManager;

public class Main {

	private static final Logger enteringMethodHeaderLogger = LoggerFactory.getLogger("EnteringMethodHeader");
	private static final Logger leavingMethodHeaderLogger  = LoggerFactory.getLogger("LeavingMethodHeader");

	private static final Main main = new Main();

	private IFarBackendConfig config;
	private JettyManager      jettyManager;
	private Database          database;

	private Main() {
	}

	/**
	 * Start the embedded servlet container and database and make ready for REST
	 * calls.
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

		final IFarBackendConfig config = getConfig();

		final JettyManager jettyManager = getJettyManager(config);
		jettyManager.start();

		final Database database = getDatabase(config);
		database.start();

		leavingMethodHeaderLogger.debug(null);
	}

	private void setConfig(final IFarBackendConfig config) {
		this.config = config;
	}

	private IFarBackendConfig getConfig() {
		if (this.config == null) {
			final IFarBackendConfig config = FarBackendConfig.getSingletonInstance();
			setConfig(config);
		}
		return this.config;
	}

	private void setJettyManager(final JettyManager jettyManager) {
		this.jettyManager = jettyManager;
	}

	private JettyManager getJettyManager(final IFarBackendConfig config) {
		if (this.jettyManager == null) {
			final JettyManager jettyManager = config.getJettyManager();
			setJettyManager(jettyManager);
		}
		return this.jettyManager;
	}

	private void setDatabase(final Database database) {
		this.database = database;
	}

	private Database getDatabase(final IFarBackendConfig config) {
		if (this.database == null) {
			final Database database = config.getDatabase();
			setDatabase(database);
		}
		return this.database;
	}
}