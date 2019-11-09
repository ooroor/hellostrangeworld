package net.barakiroth.hellostrangeworld.farbackend.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import net.barakiroth.hellostrangeworld.farbackend.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GreetingDescriptionResource {

  private static final Logger log =
      LoggerFactory.getLogger(GreetingDescriptionResource.class);
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");
  
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
    
    enteringMethodHeaderLogger.debug(null);;
    
    final Optional<GreetingDescription> optionalGreetingDescription =
        this.config.getRepository().getGreetingDescription();
    
    //final String adjective = optionalGreetingDescription.get().getAdjective();
    final ObjectMapper objectMapper = new ObjectMapper();

    String greetingDescriptionAsJson = null;
    try {
      greetingDescriptionAsJson =
          objectMapper
            .writeValueAsString(
                optionalGreetingDescription
                  .orElse(new GreetingDescription(-1, ""))
            );
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block, return a outcome code instead.
      e.printStackTrace();
    }
    
    log.debug("About to respond:\n\n{}", greetingDescriptionAsJson);
    
    leavingMethodHeaderLogger.debug(null);;
    
    return greetingDescriptionAsJson;
  }
}