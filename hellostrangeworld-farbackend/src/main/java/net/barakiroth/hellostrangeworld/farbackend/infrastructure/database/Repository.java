package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import com.querydsl.core.Tuple;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.H2Templates;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.OracleTemplates;
import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;
import javax.sql.DataSource;
import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.farbackend.Config;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.tables.QDescription;
import net.barakiroth.hellostrangeworld.farbackend.util.ExceptionSoftener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Split into service and domain independent database
 * @author oor
 *
 */
public class Repository {
  
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
      LoggerFactory.getLogger(Repository.class);
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");
  
  private static final QDescription descriptionTable = QDescription.description;
  
  private DatabaseConfig     databaseConfig;
  private DataSource         dataSource         = null;
  private TransactionManager transactionManager = null;
  private SQLQueryFactory    queryFactory       = null;
  
  /**
   * Create an instance and set its relevant configuration.
   * @param config Relevant configuration.
   */
  public Repository(final Config config) {
    
    final DatabaseConfig databaseConfig = config.getDatabaseConfig();
    this.databaseConfig = databaseConfig;
  }
  
  /**
   * Must be run before the class instance may be used.
   * Establishes and configures connection(s) and datasource(s)
   */
  public void start() {
    
    enteringMethodHeaderLogger.debug(null);
    
    this.dataSource = configure(); // TODO: Tuple3<.......>
    
    /*
    // TODO: Tuple3<.......>
    this.dataSource         = tuple3(1);
    this.queryFactory       = tuple3(2);
    this.transactionManager = tuple3(3);
    */

    leavingMethodHeaderLogger.debug(null);
  }

  /**
   * Return the adjective used in the greeting.
   * 
   * @return the adjective used in the greeting.
   */
  public String getGreetingDescription() {

    enteringMethodHeaderLogger.debug(null);
    
    assert (this.dataSource != null);
    
    // ===========================================================================
    final Optional<Description> optionalDescription =
        this.transactionManager.doInTransaction(() -> getDescription());
    // ===========================================================================
    
    log.debug("Retrieved: {}", optionalDescription);
    final String descriptionValue = optionalDescription.orElseThrow().value;
    log.debug("About to return descriptionValue: {}", descriptionValue);
    
    leavingMethodHeaderLogger.debug(null);
    
    return descriptionValue;
  }

  private DataSource configure() { // TODO: Tuple3<.......>

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
    final SQLQueryFactory queryFactory =
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
    
    this.queryFactory       = queryFactory;
    this.transactionManager = transactionManager;
    
    leavingMethodHeaderLogger.debug(null);
    
    return dataSource;// TODO: Tuple3<.......>
  }
  
  public boolean isStarted() {
    return this.dataSource != null;
  }

  /**
   * Cleans up after use.
   */
  public void stop() {

    enteringMethodHeaderLogger.debug(null);
      
    if (this.dataSource == null) {
      log.warn("Asked to disconnect when not connected (dataSource == null).");
    } else {
      this.dataSource = null;
    }

    leavingMethodHeaderLogger.debug(null);
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

  private  Optional<Description> getDescription() {

    enteringMethodHeaderLogger.debug(null);
    
    assert (this.queryFactory != null);
    
    final int id = new Random(System.currentTimeMillis()).nextInt(3) + 1;

    final Tuple row =
        this.queryFactory
          .select(descriptionTable.all())
          .from(descriptionTable)
          .where(descriptionTable.id.eq(id))
          .fetchOne()
    ;
    final Optional<Description> optionalDescription =
        Optional.ofNullable(
          (row == null)
          ?
          null
          :
          new Description(
              row.get(descriptionTable.id),
              row.get(descriptionTable.value)
          )
        )
    ;
    
    leavingMethodHeaderLogger.debug(null);
    
    return optionalDescription;
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