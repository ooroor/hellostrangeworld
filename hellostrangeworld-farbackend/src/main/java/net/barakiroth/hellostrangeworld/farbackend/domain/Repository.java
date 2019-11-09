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
 * Domain dependent interface to database.
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
  public Optional<GreetingDescription> getGreetingDescription() {

    enteringMethodHeaderLogger.debug(null);
    
    // =========================================================================
    final Database                      database                    =
        this.config.getDatabase();
    final Optional<GreetingDescription> optionalGreetingDescription =
        database.doInTransaction(() -> getGreetingDescription(database));
    // =========================================================================
    
    log.debug("Retrieved: {}", optionalGreetingDescription);
    
    leavingMethodHeaderLogger.debug(null);
    
    return optionalGreetingDescription;
  }

  private  Optional<GreetingDescription> getGreetingDescription(final Database database) {

    enteringMethodHeaderLogger.debug(null);
    
    final int             id              = new Random(System.currentTimeMillis()).nextInt(3) + 1;
    final SQLQueryFactory sqlQueryFactory = database.getSqlQueryFactory();

    final Tuple greetingDescriptionDatabaseRow =
        sqlQueryFactory
          .select(Database.greetingDescriptionTable.all())
          .from(Database.greetingDescriptionTable)
          .where(Database.greetingDescriptionTable.id.eq(id))
          .fetchOne()
    ;
    final Optional<GreetingDescription> optionalGreetingDescription =
        Optional.ofNullable(
          (greetingDescriptionDatabaseRow == null)
          ?
          null
          :
          new GreetingDescription(
              greetingDescriptionDatabaseRow.get(Database.greetingDescriptionTable.id),
              greetingDescriptionDatabaseRow.get(Database.greetingDescriptionTable.adjective)
          )
        )
    ;
    
    leavingMethodHeaderLogger.debug(null);
    
    return optionalGreetingDescription;
  }
}