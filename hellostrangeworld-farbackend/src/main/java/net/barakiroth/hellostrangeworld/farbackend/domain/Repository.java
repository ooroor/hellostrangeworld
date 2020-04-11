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
  
  public Repository() {
    this(createFarBackendConfig());
  }
  
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
   * Return the modifier used in the greeting.
   * TODO: Adding resilience confuscates the code considerably. Refactor.
   * 
   * @return the modifier used in the greeting.
   */
  public Optional<ModifierDo> getModifierDo() {
    
    enteringMethodHeaderLogger.debug(null);
    
    // Database.modifierTable.all();
    
    final Database database = getDatabase();
    if (!database.isStarted()) {
      database.start();
    }
    final int id = new Random(System.currentTimeMillis()).nextInt(3) + 1;
    final Supplier<Optional<ModifierDo>> selectModifierSupplier =
        new Supplier<>() {
          private final Database        databaseParm        = database;
          private final SQLQueryFactory sqlQueryFactoryParm = databaseParm.getSQLQueryFactory();
          private final int             idParm              = id;
          
          @Override
          public Optional<ModifierDo> get() {

            final Tuple modifierDatabaseRow =
                databaseParm
                  .doInTransaction(
                      () ->
                      sqlQueryFactoryParm
                        .select(Database.modifierTable.all())
                        .from(Database.modifierTable)
                        .where(Database.modifierTable.id.eq(idParm))
                        .fetchOne());
            
            final Optional<ModifierDo> optionalModifier =
                Optional.ofNullable(
                  (modifierDatabaseRow == null)
                  ?
                  null
                  :
                  new ModifierDo(
                      modifierDatabaseRow
                        .get(Database.modifierTable.id),
                      modifierDatabaseRow
                        .get(Database.modifierTable.modifier)
                  )
                );
            
            return optionalModifier;
          }
    };
    
    // Resilience: Add timeout:
    final TimeLimiter timeLimiter =
        TimeLimiter.of("selectModifier", TimeLimiterConfig.ofDefaults());
    final CompletableFuture<Optional<ModifierDo>> completableFuture =
        CompletableFuture.supplyAsync(selectModifierSupplier);
    final Callable<Optional<ModifierDo>> selectmodifierWithTimeLimiterCallable =
        TimeLimiter
          .decorateFutureSupplier(
              timeLimiter, 
              () -> completableFuture
          );
    
    // Resilience: Add circuit breaker:
    final CircuitBreaker circuitBreaker =
        CircuitBreaker.ofDefaults("selectModifier");
    final Callable<Optional<ModifierDo>>
        selectModifierWithTimeLimiterAndCircuitBreakerCallable =
            CircuitBreaker
              .decorateCallable(
                  circuitBreaker,
                  selectmodifierWithTimeLimiterCallable
              );
    
    // Resilience: Add retry:
    final Retry retry =
        Retry.ofDefaults("selectModifier");
    final Callable<Optional<ModifierDo>> 
        selectModifierWithTimeLimiterAndCircuitBreakerAndRetryCallable =
          Retry
            .decorateCallable(
                retry,
                selectModifierWithTimeLimiterAndCircuitBreakerCallable
            );

    // Resilience: Do the actual call to the database:
    final Optional<ModifierDo> optionalModifier =
        Try
          .ofCallable(selectModifierWithTimeLimiterAndCircuitBreakerAndRetryCallable)
          .recover(
              // TODO: Should an appropriate exception be thrown?
              throwable -> {
                log.error("Exception received when accessing the database.", throwable);
                return Optional.ofNullable(null);
              }
          )
          .get();
    
    log.debug("Retrieved: {}", optionalModifier);
    
    leavingMethodHeaderLogger.debug(null);
    
    return optionalModifier;
  }
  
  void setFarBackendConfig(final IFarBackendConfig farBackendConfig) {
    this.farBackendConfig = farBackendConfig;
  }
  
  IFarBackendConfig getFarBackendConfig() {
    if (this.farBackendConfig == null) {
      final IFarBackendConfig farBackendConfig = Repository.createFarBackendConfig();
      setFarBackendConfig(farBackendConfig);
    }
    return this.farBackendConfig;
  }
  
  private void setDatabase(final Database database) {
    this.database = database;
  }
  
  Database getDatabase() {
    if (this.database == null) {
      final Database database = Repository.createDatabase(getFarBackendConfig());
      setDatabase(database);
    }
    return this.database;
  }
  
}