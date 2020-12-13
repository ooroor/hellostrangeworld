package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import net.barakiroth.hellostrangeworld.farbackend.ITestConst;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(ITestConst.UNIT_TEST_ANNOTATION)
public class DatabaseExceptionUnitTest {

  @Test
  void when_instantiated_with_a_cause_then_the_same_cause_should_be_retrieved() {

    ITestConst.enteringTestHeaderLogger.debug(null);

    final Throwable         expectedCause     = new IOException();
    final DatabaseException databaseException = new DatabaseException(expectedCause);
    assertThat(databaseException.getCause()).isEqualTo(expectedCause);
    assertThat(databaseException.getCause()).isSameAs(expectedCause);
  }
}