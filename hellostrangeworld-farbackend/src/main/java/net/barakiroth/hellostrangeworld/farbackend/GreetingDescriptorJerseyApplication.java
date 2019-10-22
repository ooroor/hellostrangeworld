package net.barakiroth.hellostrangeworld.farbackend;

import net.barakiroth.hellostrangeworld.farbackend.greetingdescriptor.GreetingDescriptorResource;
import org.glassfish.jersey.server.ResourceConfig;

@SuppressWarnings("WeakerAccess") //Påkrevd public
public class GreetingDescriptorJerseyApplication extends ResourceConfig {
  
  private final Config config;
  
  /**
   * Used by the Jetty servlet container to configure
   * the main servlet.
   */
  public GreetingDescriptorJerseyApplication() {
    
    final Config config = Config.getSingletonInstance();
    registerGreetingDescriptorApiResources(config);
    this.config = config;
  }
  
  private void registerGreetingDescriptorApiResources(final Config config) {
    register(new GreetingDescriptorResource(config));
  }
}
