package net.barakiroth.hellostrangeworld.backend;

import net.barakiroth.hellostrangeworld.backend.infrastructure.servletcontainer.JettyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");

  /**
   * Start the embedded servlet container and make ready for REST calls.
   * @param args Not used
   */
  public static void main(final String[] args) {

    enteringMethodHeaderLogger.debug(null);

    final BackendConfig config = BackendConfig.getSingletonInstance();
    final Main   main   = new Main();
    main.run(config);

    leavingMethodHeaderLogger.debug(null);
  }

  private void run(final BackendConfig config) {
    
    enteringMethodHeaderLogger.debug(null);
    
    final JettyManager jettyManager = config.getJettyManager();
    jettyManager.start();
    
    leavingMethodHeaderLogger.debug(null);
  }
}