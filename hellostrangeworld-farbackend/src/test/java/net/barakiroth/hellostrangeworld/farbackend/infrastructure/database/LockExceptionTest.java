package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockExceptionTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");

  @Test
  void when_instantiated_with_a_message_then_the_same_message_should_be_retrieved() {

    enteringTestHeaderLogger.debug(null);

    final String        expectedMessage = "abc123";
    final LockException lockException   = new LockException(expectedMessage);
    assertThat(lockException.getMessage()).isEqualTo(expectedMessage);
  }
}