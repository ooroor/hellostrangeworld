package net.barakiroth.hellostrangeworld.farbackend;

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

  private static final Main singleton = new Main();

  private IFarBackendConfig farBackendConfig;

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

    final Main main = getSingleton();
    main.run();

    leavingMethodHeaderLogger.debug(null);
  }

  static Main getSingleton() {
    return Main.singleton;
  }

  private static IFarBackendConfig createFarBackendConfig() {
    return FarBackendConfig.getSingleton();
  }

  void setFarBackendConfig(final IFarBackendConfig farBackendConfig) {
    this.farBackendConfig = farBackendConfig;
  }

  IFarBackendConfig getFarBackendConfig() {
    if (this.farBackendConfig == null) {
      setFarBackendConfig(Main.createFarBackendConfig());
    }
    return this.farBackendConfig;
  }

  JettyManager getJettyManager(final IFarBackendConfig farBackendConfig) {

    final JettyManager jettyManager =
        farBackendConfig.getJettyManagerConfig().getJettyManager();
    return jettyManager;
  }

  Database getDatabase(final IFarBackendConfig farBackendConfig) {
    
    return farBackendConfig.getDatabase();
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
}