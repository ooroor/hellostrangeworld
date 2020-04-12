package net.barakiroth.hellostrangeworld.farbackend.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import net.barakiroth.hellostrangeworld.farbackend.FarBackendConfig;
import net.barakiroth.hellostrangeworld.farbackend.IFarBackendConfig;
import net.barakiroth.hellostrangeworld.farbackend.domain.Repository;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag("Unit")
@ExtendWith(MockitoExtension.class)
public class RepositoryUnitTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @Test
  void when_instantiated_without_configuration_then_no_exception_should_be_thrown() {

    enteringTestHeaderLogger.debug(null);

    assertThatCode(() -> new Repository()).doesNotThrowAnyException();
  }

  @Test
  void when_instantiated_without_configuration_then_an_implicitly_created_config_should_be_returned() {

    enteringTestHeaderLogger.debug(null);
    
    final Repository repository = new Repository();
    
    assertThat(repository.getFarBackendConfig()).isNotNull();
  }

  @Test
  void when_nulling_the_configuration_then_a_new_one_should_be_implicitly_created() {

    enteringTestHeaderLogger.debug(null);
    
    final Repository repository = new Repository();
    repository.setFarBackendConfig(null);

    assertThat(repository.getFarBackendConfig()).isNotNull();
  }

  @Test
  void when_instantiated_then_no_exception_should_be_thrown() {

    enteringTestHeaderLogger.debug(null);

    final IFarBackendConfig config = FarBackendConfig.getSingletonInstance();
    assertThatCode(() -> config.getRepository()).doesNotThrowAnyException();
  }
  
  @Test
  void when_getting_the_database_twice_then_they_should_be_of_same_instance() {
    
    final IFarBackendConfig config = FarBackendConfig.getSingletonInstance();
    final Repository repository = config.getRepository();
    final Database actualDatabase1 = repository.getDatabase();
    final Database actualDatabase2 = repository.getDatabase();
    
    assertThat(actualDatabase2).isSameAs(actualDatabase1);
  }
}
