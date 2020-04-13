package net.barakiroth.hellostrangeworld.farbackend;

import net.barakiroth.hellostrangeworld.farbackend.domain.ModifierResource;
import org.glassfish.jersey.server.ResourceConfig;

@SuppressWarnings("WeakerAccess") //Påkrevd public
public class JerseyApplication extends ResourceConfig {
  
  /**
   * Used by the Jetty servlet container to configure
   * the main servlet.
   */
  public JerseyApplication() {
    
    final IFarBackendConfig config = FarBackendConfig.getSingleton();
    registerApiResources(config);
  }
  
  private void registerApiResources(final IFarBackendConfig config) {
    register(new ModifierResource(config));
  }
}
