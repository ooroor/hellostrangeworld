package net.barakiroth.hellostrangeworld.farbackend;

import net.barakiroth.hellostrangeworld.farbackend.domain.ModifierResource;
import org.glassfish.jersey.server.ResourceConfig;

@SuppressWarnings("WeakerAccess") //Påkrevd public
public class JerseyApplication extends ResourceConfig {
  
  private final FarBackendConfig config;
  
  /**
   * Used by the Jetty servlet container to configure
   * the main servlet.
   */
  public JerseyApplication() {
    
    final FarBackendConfig config = FarBackendConfig.getSingletonInstance();
    registerGreetingDescriptionApiResources(config);
    this.config = config;
  }
  
  private void registerGreetingDescriptionApiResources(final FarBackendConfig config) {
    register(new ModifierResource(config));
  }
}
