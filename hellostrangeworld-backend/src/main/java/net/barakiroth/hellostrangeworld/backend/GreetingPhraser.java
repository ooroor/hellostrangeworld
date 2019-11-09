package net.barakiroth.hellostrangeworld.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.MessageFormat;
import net.barakiroth.hellostrangeworld.farbackend.domain.GreetingDescription;
import net.barakiroth.hellostrangeworld.farbackend.domain.GreetingDescriptionResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreetingPhraser {
  
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");

  GreetingDescriptionResource greetingDescriptionResource = null;
  
  /**
   * Returns the type of greeting and the adjective to be used, like e.g. "Hello very strange"
   * @return the type of greeting and the adjective to be used, like e.g. "Hello very strange"
   */
  public String getGreetingPhrasePrefix() {
    
    enteringMethodHeaderLogger.debug(null);
    
    final String greeteeDescriptionAsJson = 
        getGreetingDescriptionResource().getGreetingDescription();
    
    final ObjectMapper objectMapper = new ObjectMapper();
    
    GreetingDescription greetingDescription;
    try {
      greetingDescription =
          objectMapper.readValue(greeteeDescriptionAsJson, GreetingDescription.class);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    
    final String greetingPhrasePrefix =
        MessageFormat.format("Hello {0} ", greetingDescription.getAdjective());
    
    leavingMethodHeaderLogger.debug(null);
    
    return greetingPhrasePrefix;
  }

  private GreetingDescriptionResource getGreetingDescriptionResource() {
    
    enteringMethodHeaderLogger.debug(null);
    

    if (this.greetingDescriptionResource == null) {
      this.greetingDescriptionResource = new GreetingDescriptionResource();
    }
    
    leavingMethodHeaderLogger.debug(null);

    return this.greetingDescriptionResource;
  }
}
