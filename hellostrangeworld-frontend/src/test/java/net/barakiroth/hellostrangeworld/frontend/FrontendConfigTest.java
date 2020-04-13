package net.barakiroth.hellostrangeworld.frontend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag("Unit")
@ExtendWith(MockitoExtension.class)
public class FrontendConfigTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");

  @Test
  public void when_getting_the_InitialPartConsumer_then_it_should_not_be_null() {
    
    enteringTestHeaderLogger.debug(null);
    
    final IFrontendConfig frontendConfig = new FrontendConfig();
    
    assertThat(frontendConfig.getInitialPartConsumer()).isNotNull();
  }

  @Test
  public void when_getting_the_GreeteePrompter_then_it_should_not_be_null() {
    
    enteringTestHeaderLogger.debug(null);
    
    final IFrontendConfig frontendConfig = new FrontendConfig();
    
    assertThat(frontendConfig.getGreeteePrompter()).isNotNull();
  }
}