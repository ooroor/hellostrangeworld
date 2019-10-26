package net.barakiroth.hellostrangeworld.farbackend;

import net.barakiroth.hellostrangeworld.farbackend.greetingdescriptor.GreetingDescriptionResource;
import org.glassfish.jersey.server.ResourceConfig;

@SuppressWarnings("WeakerAccess") //Påkrevd public
public class JerseyApplication extends ResourceConfig {
  
  private final Config config;
  
  /**
   * Used by the Jetty servlet container to configure
   * the main servlet.
   */
  public JerseyApplication() {
    
    final Config config = Config.getSingletonInstance();
    registerGreetingDescriptionApiResources(config);
    this.config = config;
  }
  
  private void registerGreetingDescriptionApiResources(final Config config) {
    register(new GreetingDescriptionResource(config));
  }
}
