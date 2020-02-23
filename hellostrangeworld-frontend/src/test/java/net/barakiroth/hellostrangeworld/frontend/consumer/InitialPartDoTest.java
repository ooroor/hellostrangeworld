package net.barakiroth.hellostrangeworld.frontend.consumer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitialPartDoTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @Test
  void when_constructing_initialPart_with_value_then_the_same_value_should_be_retrieved() {
    
    enteringTestHeaderLogger.debug(null);
    
    final InitialPartDo initialPartDo = new InitialPartDo("x9x");
    
    assertThat(initialPartDo.getInitialPart()).isEqualTo("x9x");
  }
  
  @Test
  void when_constructing_initialPart_without_value_then_the_same_value_should_be_retrieved() {
    
    enteringTestHeaderLogger.debug(null);
    
    final InitialPartDo initialPartDo = new InitialPartDo();
    
    assertThat(initialPartDo.getInitialPart()).isNull();
  }

}
