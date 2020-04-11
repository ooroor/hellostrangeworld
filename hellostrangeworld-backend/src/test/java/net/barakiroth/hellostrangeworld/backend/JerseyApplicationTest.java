package net.barakiroth.hellostrangeworld.backend;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JerseyApplicationTest {
  
  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @Test
  void when_an_instance_is_created_then_no_exceptions_should_be_thrown() {
    
    enteringTestHeaderLogger.debug(null);
    
    assertThatCode(() -> new JerseyApplication()).doesNotThrowAnyException();
  }
}