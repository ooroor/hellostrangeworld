package net.barakiroth.hellostrangeworld.backend;

import net.barakiroth.hellostrangeworld.common.IGeneralConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.JettyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");

  private static final Main singleton = new Main();

  private IBackendConfig backendConfig;

  private Main() {
  }

  /**
   * Start the embedded servlet container and make ready for REST calls.
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

  private static IBackendConfig createBackendConfig() {
    return BackendConfig.getSingleton();
  }

  void setBackendConfig(final IBackendConfig backendConfig) {
    this.backendConfig = backendConfig;
  }

  IBackendConfig getBackendConfig() {
    if (this.backendConfig == null) {
      setBackendConfig(Main.createBackendConfig());
    }
    return this.backendConfig;
  }

  JettyManager getJettyManager(final IGeneralConfig generalConfig) {
    return getBackendConfig().getJettyManagerConfig().getJettyManager(generalConfig);
  }

  private void run() {

    enteringMethodHeaderLogger.debug(null);

    final IBackendConfig config = getBackendConfig();

    final JettyManager jettyManager = getJettyManager(config);
    jettyManager.start();

    leavingMethodHeaderLogger.debug(null);
  }
}
