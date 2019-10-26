package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlywayMigrator {
  
  private static final Logger log =
      LoggerFactory.getLogger(FlywayMigrator.class);
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");

  private final DataSource dataSource;
  private final String[] locations;

  /**
   * Uses {@link Flyway} for database creator and maintainer.
   */
  public FlywayMigrator(final DataSource dataSource, final String... locations) {
    
    enteringMethodHeaderLogger.debug(null);
  
    this.dataSource = dataSource;
    if (locations.length == 0) {
      this.locations = new String[]{"classpath:/db/migration"};
    } else {
      this.locations = locations;
    }

    leavingMethodHeaderLogger.debug(null);
  }

  /**
   * Create and/or migrate the database.
   */
  public void migrate() {
    
    enteringMethodHeaderLogger.debug(null);
      
    final Flyway flyway = new Flyway();
    flyway.setLocations(this.locations);
    flyway.setDataSource(this.dataSource);
    flyway.migrate();

    leavingMethodHeaderLogger.debug(null);
  }
}