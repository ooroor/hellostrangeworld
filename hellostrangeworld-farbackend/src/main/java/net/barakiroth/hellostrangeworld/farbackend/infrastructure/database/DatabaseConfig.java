package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import net.barakiroth.hellostrangeworld.common.IGeneralConfig;

public class DatabaseConfig {
  
  private static final String DB_USER_KEY                         = "db.user";
  private static final String DB_USER_DEFAULT                     = "sa";
  private static final String DB_PWD_KEY                          = "db.pwd";
  private static final String DB_PWD_DEFAULT                      = "sa";
  private static final String DB_CONNECTION_TIMEOUT_KEY           = "db.connection.timeout";
  private static final int    DB_CONNECTION_TIMEOUT_DEFAULT       = 15001;
  private static final String DB_MAX_LIFETIME_KEY                 = "db.max.lifetime";
  private static final int    DB_MAX_LIFETIME_DEFAULT             = 1800001;
  private static final String DB_LEAK_DETECTION_THRESHOLD_KEY     = "db.leak.detection.threshold";
  private static final int    DB_LEAK_DETECTION_THRESHOLD_DEFAULT = 20001;
  private static final String DB_MAXIMUM_POOLSIZE_KEY             = "db.maximum.pool.size";
  private static final int    DB_MAXIMUM_POOLSIZE_DEFAULT         = 11;
  private static final String DB_URL_KEY                          = "db.url";
  private static final String DB_URL_DEFAULT                      = 
      "jdbc:h2:mem:hellostrangeworld;DB_CLOSE_DELAY=-1";

  private static DatabaseConfig singleton;
  
  private final IGeneralConfig generalConfig;
  
  private static DatabaseConfig createDatabaseConfig(final IGeneralConfig generalConfig) {
    return new DatabaseConfig(generalConfig);
  }

  private static void setsingleton(final DatabaseConfig databaseConfig) {
    DatabaseConfig.singleton = databaseConfig;
  }
  
  public static DatabaseConfig getSingleton(final IGeneralConfig generalConfig) {
    
    if (DatabaseConfig.singleton == null) {
      final DatabaseConfig databaseConfig =
          DatabaseConfig.createDatabaseConfig(generalConfig);
      DatabaseConfig.setsingleton(databaseConfig);
    }
    return DatabaseConfig.singleton;
  }
  
  private static Database createDatabase(final IGeneralConfig generalConfig) {
    return Database.getSingleton(generalConfig);
  }

  private DatabaseConfig(final IGeneralConfig generalConfig) {
    this.generalConfig = generalConfig;
  }
  
  public Database getDatabase() {
    return DatabaseConfig.createDatabase(getGeneralConfig());
  }
  
  private IGeneralConfig getGeneralConfig() {
    return this.generalConfig;
  }
  
  String getUrl() {
    
    final String url =
        getGeneralConfig().getString(
            DB_URL_KEY, 
            DB_URL_DEFAULT);

    return url;
  }
  
  String getUser() {
    
    final String user =
        getGeneralConfig().getString(
            DB_USER_KEY,
            DB_USER_DEFAULT);
    
    return user;
  }
  
  String getPwd() {
    
    final String pwd =
        getGeneralConfig().getString(
            DB_PWD_KEY,
            DB_PWD_DEFAULT);
    
    return pwd;
  }
  
  int getConnectionTimeout() {
    
    int connectionTimeout =
        getGeneralConfig().getInt(
          DB_CONNECTION_TIMEOUT_KEY, 
          DB_CONNECTION_TIMEOUT_DEFAULT);
    
    return connectionTimeout;
  }
  
  int getMaxLifetime() {
    
    final int maxLifetime =
        getGeneralConfig().getInt(
          DB_MAX_LIFETIME_KEY, 
          DB_MAX_LIFETIME_DEFAULT);
    return maxLifetime;
  }

  int getLeakDetectionThreshold() {
    
    final int leakDetectionThreshold =
        getGeneralConfig().getInt(
          DB_LEAK_DETECTION_THRESHOLD_KEY, 
          DB_LEAK_DETECTION_THRESHOLD_DEFAULT);
    return leakDetectionThreshold;
  }
  
  int getMaximumPoolSize() {
    
    final int maximumPoolSize =
        getGeneralConfig().getInt(
          DB_MAXIMUM_POOLSIZE_KEY, 
          DB_MAXIMUM_POOLSIZE_DEFAULT);
    return maximumPoolSize;
  }
}