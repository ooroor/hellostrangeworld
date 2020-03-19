package net.barakiroth.hellostrangeworld.backend;

import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.JettyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");

  private static final Main main = new Main();

  private IBackendConfig backendConfig;
  private JettyManager   jettyManager;

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

    final Main main = getSingletonInstance();
    main.run();

    leavingMethodHeaderLogger.debug(null);
  }

  static Main getSingletonInstance() {
    return Main.main;
  }

  private static IBackendConfig createBackendConfig() {
    return BackendConfig.getSingletonInstance();
  }

  private static JettyManager createJettyManager(final IBackendConfig backendConfig) {
    return backendConfig.getJettyManagerConfig().getJettyManager();
  }

  IBackendConfig getBackendConfig() {
    if (this.backendConfig == null) {
      final IBackendConfig backendConfig = Main.createBackendConfig();
      setBackendConfig(backendConfig);
    }
    return this.backendConfig;
  }

  void setJettyManager(final JettyManager jettyManager) {
    this.jettyManager = jettyManager;
  }

  JettyManager getJettyManager(final IBackendConfig backendConfig) {
    if (this.jettyManager == null) {
      final JettyManager jettyManager = Main.createJettyManager(backendConfig);
      setJettyManager(jettyManager);
    }
    return this.jettyManager;
  }

  private void run() {

    enteringMethodHeaderLogger.debug(null);

    final IBackendConfig config = getBackendConfig();

    final JettyManager jettyManager = getJettyManager(config);
    jettyManager.start();

    leavingMethodHeaderLogger.debug(null);
  }

  private void setBackendConfig(final IBackendConfig backendConfig) {
    this.backendConfig = backendConfig;
  }
}
