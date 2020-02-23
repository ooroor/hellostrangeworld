package net.barakiroth.hellostrangeworld.backend;

import net.barakiroth.hellostrangeworld.backend.domain.InitialPartResource;
import org.glassfish.jersey.server.ResourceConfig;

@SuppressWarnings("WeakerAccess") //Påkrevd public
public class JerseyApplication extends ResourceConfig {
  
  private final BackendConfig config;
  
  /**
   * Used by the Jetty servlet container to configure
   * the main servlet.
   */
  public JerseyApplication() {
    
    final BackendConfig config = BackendConfig.getSingletonInstance();
    registerModifierApiResources(config);
    this.config = config;
  }
  
  private void registerModifierApiResources(final BackendConfig config) {
    register(new InitialPartResource(config));
  }
}
