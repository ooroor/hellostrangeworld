package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import com.querydsl.sql.Configuration;
import com.querydsl.sql.H2Templates;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.OracleTemplates;
import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.Callable;
import javax.sql.DataSource;
import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.common.IGeneralConfig;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.tables.QModifier;
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
    
    DbBrand(final SQLTemplates sqlTemplates) {
      this.sqlTemplates = sqlTemplates;
    }
    
    public static Optional<DbBrand> optionalOf(final String dbBrandName) {
      
      final Optional<DbBrand> dbBrand =
        Arrays
          .stream(DbBrand.values())
          .filter(
              (actualDbBrand) ->
              actualDbBrand.name().equals(dbBrandName)
          )
          .findFirst();
      
      return dbBrand;
    }
  }

  private static final Logger log =
      LoggerFactory.getLogger(Database.class);
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");
  
  public  static final QModifier modifierTable = QModifier.modifier1;
  private static       Database  singleton;
  
  private DatabaseConfig                  databaseConfig                  = null;
  private DataSource                      dataSource                      = null;
  private TransactionManager              transactionManager              = null;
  private SQLQueryFactory                 sqlQueryFactory                 = null;
  private TransactionalConnectionProvider transactionalConnectionProvider = null;
  private boolean                         isStarted                       = false;
  
  /**
   * Create an instance and set its relevant configuration.
   * @param generalConfig Relevant configuration.
   */
  private Database(final IGeneralConfig generalConfig) {
    
    this(Database.createDatabaseConfig(generalConfig));
    
    leavingMethodHeaderLogger.debug(null);
  }
  
  private Database(final DatabaseConfig databaseConfig) {
    
    enteringMethodHeaderLogger.debug(null);
    
    setDatabaseConfig(databaseConfig);
    
    leavingMethodHeaderLogger.debug(null);
  }
  
  static Database getSingleton(final IGeneralConfig generalConfig) {
    if (Database.singleton == null) {
      final Database database = createSingleton(generalConfig);
      Database.singleton = database;
    }
    return Database.singleton;
  }
  
  private static Database createSingleton(final IGeneralConfig generalConfig) {
    return new Database(generalConfig);
  }

  private static void setSingleton(final Database database) {
    Database.singleton = database;
  }
  
  private static DatabaseConfig createDatabaseConfig(final IGeneralConfig generalConfig) {
    return DatabaseConfig.getSingleton(generalConfig);
  }

  private static DataSource createDataSource(final DatabaseConfig databaseConfig) {

    enteringMethodHeaderLogger.debug(null);
    
    final HikariDataSource dataSource = new HikariDataSource();

    dataSource.setUsername(databaseConfig.getUser());
    dataSource.setPassword(databaseConfig.getPwd());
    dataSource.setJdbcUrl(databaseConfig.getUrl());
    dataSource.setConnectionTimeout(databaseConfig.getConnectionTimeout());
    dataSource.setMaxLifetime(databaseConfig.getMaxLifetime()); 
    dataSource.setLeakDetectionThreshold(databaseConfig.getLeakDetectionThreshold()); 
    dataSource.setMaximumPoolSize(databaseConfig.getMaximumPoolSize()); 

    log.info("Opprettet datasource for: {}", dataSource.getJdbcUrl());

    leavingMethodHeaderLogger.debug(null);

    return dataSource;
  }

  private static void createAndPopulateAndMigrate(final DataSource dataSource) {
    
    enteringMethodHeaderLogger.debug(null);
    
    new FlywayMigrator(dataSource).migrate();
    
    leavingMethodHeaderLogger.debug(null);
  }
  
  private static TransactionalConnectionProvider createTransactionalConnectionProvider(final DataSource dataSource) {
    return new TransactionalConnectionProvider(dataSource);
  }

  private static TransactionManager createTransactionManager(final TransactionalConnectionProvider transactionalConnectionProvider) {
    return new TransactionManager(transactionalConnectionProvider);
  }
  
  private static String getDbBrandName(final DataSource dataSource) {
    
    final String dbBrandName;
    {
      String dbBrandFromConnection;
      try {
        dbBrandFromConnection =
          dataSource
            .getConnection()
            .getMetaData()
            .getDatabaseProductName();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }    
      dbBrandName = dbBrandFromConnection;
      log.debug("dbBrandName from connection is: \"{}\"", dbBrandName);
    }
    return dbBrandName;
  }

  private static SQLQueryFactory createSQLQueryFactory(
      final DataSource                      dataSource,
      final TransactionalConnectionProvider transactionalConnectionProvider
  ) {
    final String dbBrand = Database.getDbBrandName(dataSource);
    final SQLQueryFactory sqlQueryFactory =
      new SQLQueryFactory(
        new Configuration(
          DbBrand
            .optionalOf(dbBrand)
            .orElseThrow(
              () -> new UnknownDatabaseBrandException(dbBrand)
            )
            .getSqlTemplates()
        ), 
        transactionalConnectionProvider
      );
    return sqlQueryFactory;
  }
  
  private static void shutdown(final DataSource dataSource) {
  
    enteringMethodHeaderLogger.debug(null);

    try {
      final Connection connection = dataSource.getConnection();
      final PreparedStatement preparedStatement = connection.prepareStatement("SHUTDOWN");
      preparedStatement.execute();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    leavingMethodHeaderLogger.debug(null);
  }

  /**
   * Must be run before the class instance may be used.
   * Establishes and configures connection(s) and datasource(s)
   */
  public Database start() {
    
    enteringMethodHeaderLogger.debug(null);
    
    Database.createAndPopulateAndMigrate(getDataSource());
    this.isStarted = true;
    
    leavingMethodHeaderLogger.debug(null);
    
     return this;
  }

  /**
   * Returns whether a successful
   * {@link this#start()} has bee
   * n carried out.
   * @return whether a successful
   * {@link this#start()} has been carried out.
   */
  public boolean isStarted() {
    return this.isStarted;
  }

  /**
   * Cleans up after use.
   */
  public Database stop() {

    enteringMethodHeaderLogger.debug(null);
      
    if (isStarted()) {      
      
      Database.shutdown(getDataSource());
      setDataSource(null);
      setSQLQueryFactory(null);
      setTransactionManager(null);
      Database.setSingleton(null);
      this.isStarted = false;
    } else {
      log.warn("Asked to stop when not connected dataSource == null etc.");
    }

    leavingMethodHeaderLogger.debug(null);
    
    return this;
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

    final V theThing = getTransactionManager().doInTransaction(callable);
    
    leavingMethodHeaderLogger.debug(null);
    
    return theThing;
  }
  
  public DataSource getDataSource() {
    if (this.dataSource == null) {
      final DataSource dataSource =
          Database.createDataSource(getDatabaseConfig());
      setDataSource(dataSource);
    }
    return this.dataSource;
  }

  public SQLQueryFactory getSQLQueryFactory() {

    if (this.sqlQueryFactory == null) {
      final SQLQueryFactory sqlQueryFactory =
          Database.createSQLQueryFactory(
              getDataSource(), 
              getTransactionalConnectionProvider());
      setSQLQueryFactory(sqlQueryFactory);
    }
    return this.sqlQueryFactory;
  }
  
  void setDataSource(final DataSource dataSource) {
    this.dataSource = dataSource;
  }
  
  private void setSQLQueryFactory(final SQLQueryFactory sqlQueryFactory) {
    this.sqlQueryFactory = sqlQueryFactory;
  }
  
  private void setTransactionManager(final TransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }
  
  private TransactionManager getTransactionManager() {
    
    if (this.transactionManager == null) {
      final TransactionManager transactionManager = 
          Database.createTransactionManager(getTransactionalConnectionProvider());
      setTransactionManager(transactionManager);
    }
    return this.transactionManager;
  }
  
  private void setTransactionalConnectionProvider(final TransactionalConnectionProvider transactionalConnectionProvider) {
    this.transactionalConnectionProvider = transactionalConnectionProvider;
  }
  
  private TransactionalConnectionProvider getTransactionalConnectionProvider() {
    if (this.transactionalConnectionProvider == null) {
      final TransactionalConnectionProvider transactionalConnectionProvider =
          Database.createTransactionalConnectionProvider(getDataSource());
      setTransactionalConnectionProvider(transactionalConnectionProvider);
    }
    return this.transactionalConnectionProvider;
  }
  
  private void setDatabaseConfig(final DatabaseConfig databaseConfig) {
    this.databaseConfig = databaseConfig;
  }
  
  private DatabaseConfig getDatabaseConfig() {
    return this.databaseConfig;
  }
}