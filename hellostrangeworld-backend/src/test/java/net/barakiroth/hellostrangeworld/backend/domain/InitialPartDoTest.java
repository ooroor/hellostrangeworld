package net.barakiroth.hellostrangeworld.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitialPartDoTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @Test
  public void when_default_created_then_the_fields_should_be_null() {
    
    enteringTestHeaderLogger.debug(null);
    
    final InitialPartDo initialPartDo = new InitialPartDo();
    
    assertThat(initialPartDo.getInitialPart()).isNull();
  }
  
  @Test
  public void when_created_with_fiels_then_the_fields_should_be_set_as_expected() {
    
    enteringTestHeaderLogger.debug(null);
    
    final String expectedInitialPart = "Hello, very good";
    
    final InitialPartDo initialPartDo = new InitialPartDo(expectedInitialPart);
    
    assertThat(initialPartDo.getInitialPart()).isEqualTo(expectedInitialPart);
  }

}
