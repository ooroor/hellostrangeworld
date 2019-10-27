package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import net.barakiroth.hellostrangeworld.farbackend.Config;
import net.barakiroth.hellostrangeworld.farbackend.domain.Repository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositoryTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");

  @Test
  public void when_instantiated_no_exception_should_be_thrown()
      throws InterruptedException {

    enteringTestHeaderLogger.debug(null);

    final Config     config     = Config.getSingletonInstance();
    final Repository repository = config.getRepository();
  }
}