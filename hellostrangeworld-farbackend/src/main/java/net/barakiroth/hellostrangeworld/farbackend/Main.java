package net.barakiroth.hellostrangeworld.farbackend;

import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.IJettyManagerConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.JettyManager;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");

  private static final Main main = new Main();

  private IFarBackendConfig farBackendConfig;
  private JettyManager jettyManager;
  private Database database;

  private Main() {
  }

  /**
   * Start the embedded servlet container and database and make ready for REST calls.
   * 
   * @param args Not used
   */
  public static void main(final String[] args) {

    enteringMethodHeaderLogger.debug(null);

    logger.debug("Parameters received: {}", (Object[]) args);

    final Main main = getSingletonInstance();
    main.run();

    leavingMethodHeaderLogger.debug(null);
  }

  static Main getSingletonInstance() {
    return Main.main;
  }

  private void run() {

    enteringMethodHeaderLogger.debug(null);

    final IFarBackendConfig config = getFarBackendConfig();

    final JettyManager jettyManager = getJettyManager(config);
    jettyManager.start();

    final Database database = getDatabase(config);
    database.start();

    leavingMethodHeaderLogger.debug(null);
  }

  private void setFarBackendConfig(final IFarBackendConfig farBackendConfig) {
    this.farBackendConfig = farBackendConfig;
  }

  IFarBackendConfig getFarBackendConfig() {
    if (this.farBackendConfig == null) {
      final IFarBackendConfig farBackendConfig = FarBackendConfig.getSingletonInstance();
      setFarBackendConfig(farBackendConfig);
    }
    return this.farBackendConfig;
  }

  private void setJettyManager(final JettyManager jettyManager) {
    this.jettyManager = jettyManager;
  }

  JettyManager getJettyManager(final IFarBackendConfig farBackendConfig) {
    if (this.jettyManager == null) {
      final IJettyManagerConfig jettyManagerConfig = farBackendConfig.getJettyManagerConfig();
      final JettyManager jettyManager = jettyManagerConfig.getJettyManager();
      setJettyManager(jettyManager);
    }
    return this.jettyManager;
  }

  private void setDatabase(final Database database) {
    this.database = database;
  }

  Database getDatabase(final IFarBackendConfig config) {
    if (this.database == null) {
      final Database database = config.getDatabase();
      setDatabase(database);
    }
    return this.database;
  }
}
