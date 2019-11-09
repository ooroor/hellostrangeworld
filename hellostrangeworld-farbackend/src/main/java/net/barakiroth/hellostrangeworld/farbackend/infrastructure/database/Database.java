package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import com.querydsl.sql.Configuration;
import com.querydsl.sql.H2Templates;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.OracleTemplates;
import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.zaxxer.hikari.HikariDataSource;
import io.vavr.Tuple3;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.Callable;
import javax.sql.DataSource;
import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.farbackend.Config;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.tables.QGreetingDescription;
import net.barakiroth.hellostrangeworld.farbackend.util.ExceptionSoftener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database {
  
  private enum DbBrand {
    ORACLE(new OracleTemplates()),
    H2(new H2Templates()),
    MYSQL(new MySQLTemplates()),
    POSTGRESQL(new PostgreSQLTemplates());
    
    @Getter(AccessLevel.PUBLIC)
    private final SQLTemplates sqlTemplates;
    
    private DbBrand(final SQLTemplates sqlTemplates) {
      this.sqlTemplates = sqlTemplates;
    }
    
    public static Optional<DbBrand> optionalOf(final String dbBrand) {
      return Optional.ofNullable(DbBrand.valueOf(dbBrand));
    }
  }

  private static final Logger log =
      LoggerFactory.getLogger(Database.class);
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");

  public  static final QGreetingDescription greetingDescriptionTable =
      QGreetingDescription.greetingDescription;
  
  private final DatabaseConfig     databaseConfig;
  private       DataSource         dataSource         = null;
  private       TransactionManager transactionManager = null;
  @Getter(AccessLevel.PUBLIC)
  private       SQLQueryFactory    sqlQueryFactory    = null;
  
  /**
   * Create an instance and set its relevant configuration.
   * @param config Relevant configuration.
   */
  public Database(final Config config) {
    
    enteringMethodHeaderLogger.debug(null);
    
    final DatabaseConfig databaseConfig = config.getDatabaseConfig();
    this.databaseConfig = databaseConfig;
    
    leavingMethodHeaderLogger.debug(null);
  }
  
  /**
   * Must be run before the class instance may be used.
   * Establishes and configures connection(s) and datasource(s)
   */
  public void start() {
    
    enteringMethodHeaderLogger.debug(null);
    
    final Tuple3<DataSource, SQLQueryFactory, TransactionManager> configResult =
        configure();
    
    this.dataSource = configResult._1();
    this.sqlQueryFactory = configResult._2();
    this.transactionManager = configResult._3();
    
    leavingMethodHeaderLogger.debug(null);
  }

  /**
   * Returns whether a successful
   * {@link this#start()} has bee
   * n carried out.
   * @return whether a successful
   * {@link this#start()} has been carried out.
   */
  public boolean isStarted() {
    return 
           (this.databaseConfig     != null)
        && (this.dataSource         != null)
        && (this.transactionManager != null)
        && (this.sqlQueryFactory       != null);
  }

  /**
   * Cleans up after use.
   */
  public void stop() {

    enteringMethodHeaderLogger.debug(null);
      
    if (isStarted()) {
      this.dataSource         = null;
      this.transactionManager = null;
      this.sqlQueryFactory       = null;
    } else {
      log.warn("Asked to stop when not connected dataSource == null etc.");
    }

    leavingMethodHeaderLogger.debug(null);
  }

  /**
   * Do the things provided by the callable 
   * within one and only one database SQL transaction.
   * 
   * @param <V> The return type of the provided callable.
   * @param callable The actions to be performed as contained in the callable.
   * @return The outcome of the execution of the provided callable.
   */
  public <V> V doInTransaction(final Callable<V> callable) {
    
    enteringMethodHeaderLogger.debug(null);
    
    if (!isStarted()) {
      start();
    }

    final V theThing = this.transactionManager.doInTransaction(callable);
    
    leavingMethodHeaderLogger.debug(null);
    
    return theThing;
  }
  
  private final Tuple3<DataSource, SQLQueryFactory, TransactionManager> configure() {

    enteringMethodHeaderLogger.debug(null);
    
    final DataSource dataSource = createDataSource();
    createAndPopulateAndMigrate(dataSource);
    
    final String dbBrandFromConnection;
    {
      String dbBrandFromConnectionTemp;
      try {
        dbBrandFromConnectionTemp =
          dataSource
            .getConnection()
            .getMetaData()
            .getDatabaseProductName();
      } catch (SQLException e) {
        ExceptionSoftener.uncheck(e);
        dbBrandFromConnectionTemp = null;
      }    
      dbBrandFromConnection = dbBrandFromConnectionTemp;
      log.debug("DATABASE IS \"{}\"", dbBrandFromConnection);
    }

    final TransactionalConnectionProvider transactionalConnectionProvider =
        new TransactionalConnectionProvider(dataSource);
    final SQLQueryFactory sqlQueryFactory =
        new SQLQueryFactory(
          new Configuration(
            DbBrand
              .optionalOf(dbBrandFromConnection)
              .orElseThrow(
                () -> new UnknownDatabaseBrandException(dbBrandFromConnection)
              )
              .getSqlTemplates()
          ), 
          transactionalConnectionProvider
        );
    final TransactionManager transactionManager =
        new TransactionManager(transactionalConnectionProvider);
    
    final Tuple3<DataSource, SQLQueryFactory, TransactionManager> configResult =
        new Tuple3<DataSource, SQLQueryFactory, TransactionManager>(
            dataSource, sqlQueryFactory, transactionManager
        );
    
    leavingMethodHeaderLogger.debug(null);
    
    return configResult;
  }

  private  DataSource createDataSource() {

    enteringMethodHeaderLogger.debug(null);
    
    final HikariDataSource dataSource = new HikariDataSource();

    dataSource.setUsername(getUser());
    dataSource.setPassword(getPwd());
    dataSource.setJdbcUrl(getUrl());
    dataSource.setConnectionTimeout(getConnectionTimeout()); 
    dataSource.setMaxLifetime(getMaxLifetime()); 
    dataSource.setLeakDetectionThreshold(getLeakDetectionThreshold()); 
    dataSource.setMaximumPoolSize(getMaximumPoolSize()); 

    log.info("Opprettet datasource for: {}", dataSource.getJdbcUrl());

    leavingMethodHeaderLogger.debug(null);

    return dataSource;
  }

  private void createAndPopulateAndMigrate(final DataSource dataSource) {
    
    enteringMethodHeaderLogger.debug(null);
    
    new FlywayMigrator(dataSource, "classpath:/db/migration").migrate();
    
    leavingMethodHeaderLogger.debug(null);
  }
  
  private String getUser() {
    
    assert (this.databaseConfig != null);
    
    return this.databaseConfig.getUser();
  }
  
  private String getPwd() {
    
    assert (this.databaseConfig != null);
    
    return this.databaseConfig.getPwd();
  }
  
  private String getUrl() { 
    
    assert (this.databaseConfig != null);
    
    return this.databaseConfig.getUrl();
  }
  
  private int getConnectionTimeout() {
    
    assert (this.databaseConfig != null);
    
    return this.databaseConfig.getConnectionTimeout();
  }
  
  private int getMaxLifetime() {
    
    assert (this.databaseConfig != null);
    
    return this.databaseConfig.getMaxLifetime();
  }
  
  private int getLeakDetectionThreshold() {
    
    assert (this.databaseConfig != null);
    
    return this.databaseConfig.getLeakDetectionThreshold();
  }
  
  private int getMaximumPoolSize() {
    
    assert (this.databaseConfig != null);
    
    return this.databaseConfig.getMaximumPoolSize();
  }
}