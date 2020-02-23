package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseExceptionTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");

  @Test
  void when_instantiated_with_a_cause_then_the_same_cause_should_be_retrieved() {

    enteringTestHeaderLogger.debug(null);

    final Throwable         expectedCause     = new IOException();
    final DatabaseException databaseException = new DatabaseException(expectedCause);
    assertThat(databaseException.getCause()).isEqualTo(expectedCause);
    assertThat(databaseException.getCause()).isSameAs(expectedCause);
  }
}