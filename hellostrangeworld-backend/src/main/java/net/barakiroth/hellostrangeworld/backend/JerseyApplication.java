package net.barakiroth.hellostrangeworld.backend;

import net.barakiroth.hellostrangeworld.backend.domain.InitialPartResource;
import org.glassfish.jersey.server.ResourceConfig;

@SuppressWarnings("WeakerAccess") //Påkrevd public
public class JerseyApplication extends ResourceConfig {
  
  /**
   * Used by the Jetty servlet container to configure
   * the main servlet.
   */
  public JerseyApplication() {
    
    final IBackendConfig config = BackendConfig.getSingletonInstance();
    registerApiResources(config);
  }
  
  private void registerApiResources(final IBackendConfig config) {
    register(new InitialPartResource(config));
  }
}