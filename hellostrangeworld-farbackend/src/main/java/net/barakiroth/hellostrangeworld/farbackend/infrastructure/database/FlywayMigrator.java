package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import javax.sql.DataSource;

import net.barakiroth.hellostrangeworld.farbackend.util.ProdConstants;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlywayMigrator {
  
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");
  
  private final DataSource dataSource;
  private final String[]   locations;

  /**
   * Uses {@link Flyway} for database creator and maintainer.
   */
  public FlywayMigrator(
      final DataSource dataSource,
      final String  runtimeEnvironmentSpecificLocation
  ) {
    
    enteringMethodHeaderLogger.debug(null);
  
    this.dataSource = dataSource;
    if (runtimeEnvironmentSpecificLocation == null) {
      this.locations = new String[]{ProdConstants.DB_FLYWAY_MIGR_PATH_COMMON};
    } else {
      this.locations = new String[]{ProdConstants.DB_FLYWAY_MIGR_PATH_COMMON, runtimeEnvironmentSpecificLocation};
    }

    leavingMethodHeaderLogger.debug(null);
  }
  public FlywayMigrator(
          final DataSource dataSource
  ) {
    this(dataSource, null);

    leavingMethodHeaderLogger.debug(null);
  }

  /**
   * Create and/or migrate the database.
   */
  public void migrate() {
    
    enteringMethodHeaderLogger.debug(null);
    
    final Flyway flyway = 
        new FluentConfiguration()
          .locations(getLocations())
          .dataSource(getDataSource())
          .load();
    
    flyway.migrate();

    leavingMethodHeaderLogger.debug(null);
  }
  
  String[] getLocations() {
    return this.locations;
  }
  
  private DataSource getDataSource() {
    return this.dataSource;
  }
}