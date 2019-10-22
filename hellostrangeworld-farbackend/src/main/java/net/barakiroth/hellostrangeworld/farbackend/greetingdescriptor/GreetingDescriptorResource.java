package net.barakiroth.hellostrangeworld.farbackend.greetingdescriptor;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import net.barakiroth.hellostrangeworld.farbackend.Config;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GreetingDescriptorResource {
  
  private final Config config;
  
  public GreetingDescriptorResource(final Config config) {
    this.config = config;
  }

  /**
   * REST API resource, to get the adjective to be used in the greeting.
   * 
   * @return the adjective to be used in the greeting.
   */
  @GET
  @Path("/GreetingDescriptor")
  @Produces({MediaType.APPLICATION_JSON})
  public String getGreetingDescriptor() {
    
    final GreetingDescriptor greetingDescriptor =
        new GreetingDescriptor();
    
    final String greeteeDescriptionAsJson =
        greetingDescriptor.describeGreetee();
    
    return greeteeDescriptionAsJson;
  }
}