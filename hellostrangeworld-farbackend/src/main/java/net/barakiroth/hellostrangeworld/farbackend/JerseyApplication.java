package net.barakiroth.hellostrangeworld.farbackend;

import net.barakiroth.hellostrangeworld.farbackend.domain.ModifierResource;
import org.glassfish.jersey.server.ResourceConfig;

@SuppressWarnings("WeakerAccess") //Påkrevd public
public class JerseyApplication extends ResourceConfig {
  
  private final IFarBackendConfig farBackendConfig;
  
  /**
   * Used by the Jetty servlet container to configure
   * the main servlet.
   */
  public JerseyApplication() {
    
    final IFarBackendConfig farBackendConfig = FarBackendConfig.getSingletonInstance();
    registerGreetingDescriptionApiResources(farBackendConfig);
    this.farBackendConfig = farBackendConfig;
  }
  
  private void registerGreetingDescriptionApiResources(final IFarBackendConfig farBackendConfig) {
    register(new ModifierResource(farBackendConfig));
  }
}
