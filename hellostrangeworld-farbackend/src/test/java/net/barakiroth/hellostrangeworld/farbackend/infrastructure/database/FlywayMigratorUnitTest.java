package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
public class FlywayMigratorUnitTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @Mock
  private DataSource mockedDataSource;
  
  @Test
  void when_instantiated_without_locations_then_it_should_have_one_default_location() {
    
    enteringTestHeaderLogger.debug(null);
    
    final FlywayMigrator flywayMigrator = new FlywayMigrator(mockedDataSource);
    final String[] locations = flywayMigrator.getLocations();
    assertThat(locations.length).isEqualTo(1);
    assertThat(locations[0]).isEqualTo(FlywayMigrator.DEFAULT_LOCATION);
  }
}