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
  private JettyManager      jettyManager;
  private Database          database;

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
  
  private static JettyManager createJettyManager(final IFarBackendConfig farBackendConfig) {
    
    enteringMethodHeaderLogger.debug(null);

    final IJettyManagerConfig jettyManagerConfig = farBackendConfig.getJettyManagerConfig();
    final JettyManager jettyManager = jettyManagerConfig.getJettyManager();
    
    leavingMethodHeaderLogger.debug(null);
    
    return jettyManager;
  }

  static Main getSingletonInstance() {
    return Main.main;
  }

  private void run() {

    enteringMethodHeaderLogger.debug(null);

    final IFarBackendConfig farBackendConfig = getFarBackendConfig();

    final JettyManager jettyManager = getJettyManager(farBackendConfig);
    jettyManager.start();

    final Database database = getDatabase(farBackendConfig);
    database.start();

    leavingMethodHeaderLogger.debug(null);
  }

  void setFarBackendConfig(final IFarBackendConfig farBackendConfig) {
    this.farBackendConfig = farBackendConfig;
  }

  IFarBackendConfig getFarBackendConfig() {
    if (this.farBackendConfig == null) {
      final IFarBackendConfig farBackendConfig = FarBackendConfig.getSingletonInstance();
      setFarBackendConfig(farBackendConfig);
    }
    return this.farBackendConfig;
  }

  void setJettyManager(final JettyManager jettyManager) {
    this.jettyManager = jettyManager;
  }

  void setDatabase(final Database database) {
    this.database = database;
  }

  JettyManager getJettyManager(final IFarBackendConfig farBackendConfig) {
    if (this.jettyManager == null) {
      setJettyManager(Main.createJettyManager(farBackendConfig));
    }
    return this.jettyManager;
  }

  Database getDatabase(final IFarBackendConfig farBackendConfig) {
    if (this.database == null) {
      final Database database = farBackendConfig.getDatabase();
      setDatabase(database);
    }
    return this.database;
  }
}
