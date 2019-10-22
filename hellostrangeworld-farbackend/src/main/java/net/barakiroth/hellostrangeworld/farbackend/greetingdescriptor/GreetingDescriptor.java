package net.barakiroth.hellostrangeworld.farbackend.greetingdescriptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.barakiroth.hellostrangeworld.farbackend.Config;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;

public class GreetingDescriptor {
  
  private final Config config;
  
  /**
   * A hack until he config may be supplied by the resource.
   */
  public GreetingDescriptor() {
    this(Config.getSingletonInstance());
  }
  
  public GreetingDescriptor(final Config config) {
    this.config = config;
  }
  
  /** 
   * Returns a json representation of the describing adjective.
   * @return a json representation of the describing adjective.
   */
  public String describeGreetee() {
    
    final String greeteeDescription = getDatabase().describeGreetee();
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

  private Database getDatabase() {

    final Database database = this.config.getDatabase();
    if (!database.isStarted()) {
      database.start();
    }
    
    return database;
  }
}
