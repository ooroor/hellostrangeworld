package net.barakiroth.hellostrangeworld.farbackend.database;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import net.barakiroth.hellostrangeworld.farbackend.Config;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Repository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositoryTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @Test
  public void when_started_it_should_not_throw_an_exceptions()
      throws InterruptedException {

    enteringTestHeaderLogger.debug(null);

    final Config     config     = Config.getSingletonInstance();
    final Repository repository = config.getRepository();

    assertDoesNotThrow(() -> repository.start());
  }
}