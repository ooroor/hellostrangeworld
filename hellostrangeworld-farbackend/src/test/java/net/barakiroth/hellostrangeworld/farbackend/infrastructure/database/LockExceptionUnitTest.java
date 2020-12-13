package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import static org.assertj.core.api.Assertions.assertThat;

import net.barakiroth.hellostrangeworld.farbackend.ITestConst;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(ITestConst.UNIT_TEST_ANNOTATION)
public class LockExceptionUnitTest {

  @Test
  void when_instantiated_with_a_message_then_the_same_message_should_be_retrieved() {

    ITestConst.enteringTestHeaderLogger.debug(null);

    final String        expectedMessage = "abc123";
    final LockException lockException   = new LockException(expectedMessage);
    assertThat(lockException.getMessage()).isEqualTo(expectedMessage);
  }
}