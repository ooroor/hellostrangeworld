package net.barakiroth.hellostrangeworld.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.MessageFormat;
import net.barakiroth.hellostrangeworld.farbackend.greetingdescriptor.GreetingDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreetingPhraser {
  
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");

  GreetingDescriptor greetingDescriptor = null;
  
  /**
   * Returns the type of greeting and the adjective to be used, like e.g. "Hello very strange"
   * @return the type of greeting and the adjective to be used, like e.g. "Hello very strange"
   */
  public String getGreetingPhrasePrefix() {
    
    final String greeteeDescriptionAsJson = getGreetingDescriptor().describeGreetee();
    
    final ObjectMapper objectMapper = new ObjectMapper();
    
    String description;
    try {
      description = objectMapper.readValue(greeteeDescriptionAsJson, String.class);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    
    return MessageFormat.format("Hello {0} ", description);
  }

  /**
   * Clean up after use.
   */
  public void disconnect() {
      
    enteringMethodHeaderLogger.debug(null);
      
    this.greetingDescriptor.disconnect();
    this.greetingDescriptor = null;
      
    leavingMethodHeaderLogger.debug(null);
  }

  private GreetingDescriptor getGreetingDescriptor() {

    if (this.greetingDescriptor == null) {
      this.greetingDescriptor = new GreetingDescriptor();
    }

    return this.greetingDescriptor;
  }
}
