package net.barakiroth.hellostrangeworld.farbackend.greetingdescriptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.barakiroth.hellostrangeworld.farbackend.Config;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Repository;

public class GreetingDescriptor {
  
  private final Config config;
  
  /**
   * TODO> A hack until he config may be supplied by the resource.
   * TODO: Should be made a service?
   */
  public GreetingDescriptor() {
    this(Config.getSingletonInstance()); // TODO:
  }
  
  public GreetingDescriptor(final Config config) {
    this.config = config;
  }
  
  /** 
   * Returns a json representation of the describing adjective.
   * @return a json representation of the describing adjective.
   */
  public String describeGreetee() {
    
    final String greeteeDescription = getRepository().getGreetingDescription();
    final ObjectMapper objectMapper = new ObjectMapper();
    
    String greeteeDescriptionAsJson = null;
    try {
      greeteeDescriptionAsJson =
          objectMapper.writeValueAsString(greeteeDescription);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return greeteeDescriptionAsJson;
  }

  public void disconnect() {
  }

  private Repository getRepository() {

    final Repository repository = this.config.getRepository();
    if (!repository.isStarted()) {
      repository.start();
    }
    
    return repository;
  }
}
