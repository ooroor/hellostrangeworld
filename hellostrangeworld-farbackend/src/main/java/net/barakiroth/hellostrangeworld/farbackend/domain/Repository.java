package net.barakiroth.hellostrangeworld.farbackend.domain;

import com.querydsl.core.Tuple;
import com.querydsl.sql.SQLQueryFactory;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
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
   * TODO: Adding resilience confuscates the code considerably. Refactor.
   * 
   * @return the adjective used in the greeting.
   */
  public Optional<GreetingDescription> getGreetingDescription() {

    enteringMethodHeaderLogger.debug(null);
    
    final Database database = this.config.getDatabase();
    final int id = new Random(System.currentTimeMillis()).nextInt(3) + 1;
    final Supplier<Optional<GreetingDescription>> selectGreetingDescriptionSupplier =
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
        TimeLimiter.of("selectGreetingDescription", TimeLimiterConfig.ofDefaults());
    final CompletableFuture<Optional<GreetingDescription>> completableFuture =
        CompletableFuture.supplyAsync(selectGreetingDescriptionSupplier);
    final Callable<Optional<GreetingDescription>> selectGreetingDescriptionWithTimeLimiterCallable =
        TimeLimiter
          .decorateFutureSupplier(
              timeLimiter, 
              () -> completableFuture
          );
    
    // Resilience: Add circuit breaker:
    final CircuitBreaker circuitBreaker =
        CircuitBreaker.ofDefaults("selectGreetingDescription");
    final Callable<Optional<GreetingDescription>>
        selectGreetingDescriptionWithTimeLimiterAndCircuitBreakerCallable =
            CircuitBreaker
              .decorateCallable(
                  circuitBreaker,
                  selectGreetingDescriptionWithTimeLimiterCallable
              );
    
    // Resilience: Add retry:
    final Retry retry =
        Retry.ofDefaults("selectGreetingDescription");
    final Callable<Optional<GreetingDescription>> 
        selectGreetingDescriptionWithTimeLimiterAndCircuitBreakerAndRetryCallable =
          Retry
            .decorateCallable(
                retry,
                selectGreetingDescriptionWithTimeLimiterAndCircuitBreakerCallable
            );

    // Resilience: Do the actual call to the database:
    final Optional<GreetingDescription> optionalGreetingDescription =
        Try
          .ofCallable(selectGreetingDescriptionWithTimeLimiterAndCircuitBreakerAndRetryCallable)
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