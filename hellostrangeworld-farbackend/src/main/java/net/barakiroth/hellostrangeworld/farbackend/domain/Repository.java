package net.barakiroth.hellostrangeworld.farbackend.domain;

import com.querydsl.core.Tuple;
import com.querydsl.sql.SQLQueryFactory;
import java.util.Optional;
import java.util.Random;
import net.barakiroth.hellostrangeworld.farbackend.Config;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: Split into service and domain independent database.
 */
public class Repository {

  private static final Logger log =
      LoggerFactory.getLogger(Repository.class);
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");
  
  private final Config config;
  
  /**
   * Create an instance and set its relevant configuration.
   * @param config Relevant configuration.
   */
  public Repository(final Config config) {
    this.config = config;
  }

  /**
   * Return the adjective used in the greeting.
   * 
   * @return the adjective used in the greeting.
   */
  public String getGreetingDescription() {

    enteringMethodHeaderLogger.debug(null);
    
    // =========================================================================
    final Database              database            = this.config.getDatabase();
    final Optional<Description> optionalDescription =
        database.doInTransaction(() -> getDescription(database));
    // =========================================================================
    
    log.debug("Retrieved: {}", optionalDescription);
    final String descriptionValue = optionalDescription.orElseThrow().value;
    log.debug("About to return descriptionValue: {}", descriptionValue);
    
    leavingMethodHeaderLogger.debug(null);
    
    return descriptionValue;
  }

  private  Optional<Description> getDescription(final Database database) {

    enteringMethodHeaderLogger.debug(null);
    
    final int             id              = new Random(System.currentTimeMillis()).nextInt(3) + 1;
    final SQLQueryFactory sqlQueryFactory = database.getSqlQueryFactory();

    final Tuple descriptionDatabaseRow =
        sqlQueryFactory
          .select(Database.descriptionTable.all())
          .from(Database.descriptionTable)
          .where(Database.descriptionTable.id.eq(id))
          .fetchOne()
    ;
    final Optional<Description> optionalDescription =
        Optional.ofNullable(
          (descriptionDatabaseRow == null)
          ?
          null
          :
          new Description(
              descriptionDatabaseRow.get(Database.descriptionTable.id),
              descriptionDatabaseRow.get(Database.descriptionTable.value)
          )
        )
    ;
    
    leavingMethodHeaderLogger.debug(null);
    
    return optionalDescription;
  }
}