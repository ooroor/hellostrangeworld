package net.barakiroth.hellostrangeworld.frontend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag("Integration")
public class MainIntegrationTest {
  
  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @Test
  public void when_getting_the_IFrontendConfig_then_an_implicitly_created_instance_should_be_returned() {
    
    enteringTestHeaderLogger.debug(null);
    
    assertThat(Main.getSingleton().getFrontendConfig()).isNotNull();
  }
}