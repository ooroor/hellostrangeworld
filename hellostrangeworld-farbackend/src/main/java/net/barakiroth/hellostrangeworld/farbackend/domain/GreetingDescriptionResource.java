package net.barakiroth.hellostrangeworld.farbackend.domain;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.barakiroth.hellostrangeworld.farbackend.Config;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GreetingDescriptionResource {
  
  private final Config config;
  
  public GreetingDescriptionResource() {
    this(Config.getSingletonInstance());
  }
  
  public GreetingDescriptionResource(final Config config) {
    this.config = config;
  }

  /**
   * REST API resource, to get the adjective to be used in the greeting.
   * 
   * @return the adjective to be used in the greeting.
   */
  @GET
  @Path("/GreetingDescription")
  @Produces({MediaType.APPLICATION_JSON})
  public String getGreetingDescription() {
    
    final String greeteeDescription = this.config.getRepository().getGreetingDescription();
    final ObjectMapper objectMapper = new ObjectMapper();
    
    String greeteeDescriptionAsJson = null;
    try {
      greeteeDescriptionAsJson =
          objectMapper.writeValueAsString(greeteeDescription);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block, return a outcome code instead.
      e.printStackTrace();
    }
    
    return greeteeDescriptionAsJson;
  }
}