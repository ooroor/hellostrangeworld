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
import net.barakiroth.hellostrangeworld.farbackend.FarBackendConfig;
import net.barakiroth.hellostrangeworld.farbackend.IFarBackendConfig;
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
  
  private IFarBackendConfig farBackendConfig;
  private Database          database;
  
  public Repository(final IFarBackendConfig farBackendConfig) {
    this.farBackendConfig = farBackendConfig;
  }

  private static Database createDatabase(final IFarBackendConfig farBackendConfig) {
    return farBackendConfig.getDatabase();
  }

  private static IFarBackendConfig createFarBackendConfig() {
    return FarBackendConfig.getSingletonInstance();
  }
  
  /**
   * Return the adjective used in the greeting.
   * TODO: Adding resilience confuscates the code considerably. Refactor.
   * 
   * @return the adjective used in the greeting.
   */
  public Optional<ModifierDo> getModifierDo() {

    enteringMethodHeaderLogger.debug(null);
    
    final Database database = getDatabase();
    final int id = new Random(System.currentTimeMillis()).nextInt(3) + 1;
    final Supplier<Optional<ModifierDo>> selectGreetingDescriptionSupplier =
        new Supplier<>() {
          private final Database        databaseParm        = database;
          private final SQLQueryFactory sqlQueryFactoryParm = databaseParm.getSqlQueryFactory();
          private final int             idParm              = id;

          @Override
          public Optional<ModifierDo> get() {

            final Tuple greetingDescriptionDatabaseRow =
                databaseParm
                  .doInTransaction(
                      () ->
                      sqlQueryFactoryParm
                        .select(Database.greetingDescriptionTable.all())
                        .from(Database.greetingDescriptionTable)
                        .where(Database.greetingDescriptionTable.id.eq(idParm))
                        .fetchOne());
            
            final Optional<ModifierDo> optionalGreetingDescription =
                Optional.ofNullable(
                  (greetingDescriptionDatabaseRow == null)
                  ?
                  null
                  :
                  new ModifierDo(
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
    final CompletableFuture<Optional<ModifierDo>> completableFuture =
        CompletableFuture.supplyAsync(selectGreetingDescriptionSupplier);
    final Callable<Optional<ModifierDo>> selectGreetingDescriptionWithTimeLimiterCallable =
        TimeLimiter
          .decorateFutureSupplier(
              timeLimiter, 
              () -> completableFuture
          );
    
    // Resilience: Add circuit breaker:
    final CircuitBreaker circuitBreaker =
        CircuitBreaker.ofDefaults("selectGreetingDescription");
    final Callable<Optional<ModifierDo>>
        selectGreetingDescriptionWithTimeLimiterAndCircuitBreakerCallable =
            CircuitBreaker
              .decorateCallable(
                  circuitBreaker,
                  selectGreetingDescriptionWithTimeLimiterCallable
              );
    
    // Resilience: Add retry:
    final Retry retry =
        Retry.ofDefaults("selectGreetingDescription");
    final Callable<Optional<ModifierDo>> 
        selectGreetingDescriptionWithTimeLimiterAndCircuitBreakerAndRetryCallable =
          Retry
            .decorateCallable(
                retry,
                selectGreetingDescriptionWithTimeLimiterAndCircuitBreakerCallable
            );

    // Resilience: Do the actual call to the database:
    final Optional<ModifierDo> optionalGreetingDescription =
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
  
  private void setDatabase(final Database database) {
    this.database = database;
  }
  
  private Database getDatabase() {
    if (this.database == null) {
      final Database database = Repository.createDatabase(getFarBackendConfig());
      setDatabase(database);
    }
    return this.database;
  }
  
  private void setFarBackendConfig(final IFarBackendConfig farBackendConfig) {
    this.farBackendConfig = farBackendConfig;
  }
  
  private IFarBackendConfig getFarBackendConfig() {
    if (this.farBackendConfig == null) {
      final IFarBackendConfig farBackendConfig = Repository.createFarBackendConfig();
      setFarBackendConfig(farBackendConfig);
    }
    return this.farBackendConfig;
  }
  
}