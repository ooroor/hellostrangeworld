package net.barakiroth.hellostrangeworld.backend;

import net.barakiroth.hellostrangeworld.backend.domain.InitialPartResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("WeakerAccess") // Påkrevd public
public class JerseyApplication extends ResourceConfig {

  private static final Logger log =
      LoggerFactory.getLogger(JerseyApplication.class);
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");


  /**
   * Used by the Jetty servlet container to configure the main servlet.
   */
  public JerseyApplication() {

    enteringMethodHeaderLogger.debug(null);

    final IBackendConfig config = BackendConfig.getSingletonInstance();
    registerApiResources(config);

    leavingMethodHeaderLogger.debug(null);
  }

  private void registerApiResources(final IBackendConfig config) {
    register(new InitialPartResource(config));
  }
}
