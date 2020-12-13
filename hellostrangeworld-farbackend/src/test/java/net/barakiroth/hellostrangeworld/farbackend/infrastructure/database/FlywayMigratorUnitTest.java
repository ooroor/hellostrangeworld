package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import net.barakiroth.hellostrangeworld.farbackend.ITestConst;
import net.barakiroth.hellostrangeworld.farbackend.util.ProdConstants;
import net.barakiroth.hellostrangeworld.farbackend.util.TestConstants;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag(ITestConst.UNIT_TEST_ANNOTATION)
@ExtendWith(MockitoExtension.class)
public class FlywayMigratorUnitTest {
  
  @Mock
  private DataSource mockedDataSource;
  
  @Test
  void when_instantiated_with_1_location_then_it_should_have_two_locations() {
    
    ITestConst.enteringTestHeaderLogger.debug(null);
    
    final FlywayMigrator flywayMigrator = new FlywayMigrator(mockedDataSource, TestConstants.DB_FLYWAY_MIGR_PATH_RUNTIME_ENV_SPECIFIC);
    final String[] locations = flywayMigrator.getLocations();
    assertThat(locations.length).isEqualTo(2);
    assertThat(locations[0]).isEqualTo(ProdConstants.DB_FLYWAY_MIGR_PATH_COMMON);
    assertThat(locations[0]).isEqualTo(TestConstants.DB_FLYWAY_MIGR_PATH_RUNTIME_ENV_SPECIFIC);
  }
}