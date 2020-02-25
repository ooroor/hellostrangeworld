package net.barakiroth.hellostrangeworld.backend;

import net.barakiroth.hellostrangeworld.backend.domain.InitialPartResource;
import org.glassfish.jersey.server.ResourceConfig;

@SuppressWarnings("WeakerAccess") //Påkrevd public
public class JerseyApplication extends ResourceConfig {
  
  private final IBackendConfig config;
  
  /**
   * Used by the Jetty servlet container to configure
   * the main servlet.
   */
  public JerseyApplication() {
    
    final IBackendConfig config = BackendConfig.getSingletonInstance();
    registerModifierApiResources(config);
    this.config = config;
  }
  
  private void registerModifierApiResources(final IBackendConfig config) {
    register(new InitialPartResource(config));
  }
}
