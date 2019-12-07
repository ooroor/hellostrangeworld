package net.barakiroth.hellostrangeworld.farbackend.domain;

import com.querydsl.core.Tuple;
import com.querydsl.sql.SQLQueryFactory;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.vavr.concurrent.Future;
import io.vavr.control.Try;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
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
   * TODO: Addition of resilience confuscates the code considerably. Refactor.
   * 
   * @return the adjective used in the greeting.
   */
  public Optional<GreetingDescription> getGreetingDescription() {

    enteringMethodHeaderLogger.debug(null);
    
    final Database database = this.config.getDatabase();
    final int id = new Random(System.currentTimeMillis()).nextInt(3) + 1;
    final Supplier<Optional<GreetingDescription>> selectRow =
        new Supplier<>() {
          private final Database        databaseParm        = database;
          private final SQLQueryFactory sqlQueryFactoryParm = databaseParm.getSqlQueryFactory();
          private final int             idParm              = id;          
          
          @Override
          public Optional<GreetingDescription> get() {
            
            final Tuple greetingDescriptionDatabaseRow =
                databaseParm
                  .doInTransaction(
                      () ->
                      sqlQueryFactoryParm
                        .select(Database.greetingDescriptionTable.all())
                        .from(Database.greetingDescriptionTable)
                        .where(Database.greetingDescriptionTable.id.eq(idParm))
                        .fetchOne());
            
            final Optional<GreetingDescription> optionalGreetingDescription =
                Optional.ofNullable(
                  (greetingDescriptionDatabaseRow == null)
                  ?
                  null
                  :
                  new GreetingDescription(
                      greetingDescriptionDatabaseRow
                        .get(Database.greetingDescriptionTable.id),
                      greetingDescriptionDatabaseRow
                        .get(Database.greetingDescriptionTable.adjective)
                  )
                );
            
            return optionalGreetingDescription;
          }
    };
    
    // Resilience: Add timeout:
    final TimeLimiter timeLimiter =
        TimeLimiter.of("backendName", TimeLimiterConfig.ofDefaults());
    final CompletableFuture<Optional<GreetingDescription>> completableFuture =
        CompletableFuture.supplyAsync(selectRow);
    final Callable<Optional<GreetingDescription>> selectRowWithtimeLimiter =
        TimeLimiter.decorateFutureSupplier(timeLimiter, () -> completableFuture);
    
    // Resilience: Add circuit breaker:
    final CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("backendName");
    final Callable<Optional<GreetingDescription>> selectRowDecoratedWithCircuitBreaker =
        CircuitBreaker.decorateCallable(circuitBreaker, selectRowWithtimeLimiter);
    
    // Resilience: Add circuit retry:
    final Retry retry = Retry.ofDefaults("backendName");
    final Callable<Optional<GreetingDescription>> selectRowDecoratedWithCircuitBreakerAndRetry =
        Retry
          .decorateCallable(retry, selectRowDecoratedWithCircuitBreaker);
    
    // Resilience: Do the actual call to the database:
    final Optional<GreetingDescription> optionalGreetingDescription =
        Try
          .ofCallable(selectRowDecoratedWithCircuitBreakerAndRetry)
          .recover(
              throwable -> {
                log.error("Exception received when accessing the database.", throwable);
                return Optional.ofNullable(null);
              }
          )
          .get();
    
    log.debug("Retrieved: {}", optionalGreetingDescription);
    
    leavingMethodHeaderLogger.debug(null);
    
    return optionalGreetingDescription;
  }
}