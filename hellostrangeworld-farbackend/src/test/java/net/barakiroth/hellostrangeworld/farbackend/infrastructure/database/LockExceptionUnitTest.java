package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import net.barakiroth.hellostrangeworld.farbackend.ITestConstants;

@Tag(ITestConstants.UNIT_TEST_ANNOTATION)
public class LockExceptionUnitTest {

  @Test
  void when_instantiated_with_a_message_then_the_same_message_should_be_retrieved() {

    ITestConstants.enteringTestHeaderLogger.debug(null);

    final String        expectedMessage = "abc123";
    final LockException lockException   = new LockException(expectedMessage);
    assertThat(lockException.getMessage()).isEqualTo(expectedMessage);
  }
}