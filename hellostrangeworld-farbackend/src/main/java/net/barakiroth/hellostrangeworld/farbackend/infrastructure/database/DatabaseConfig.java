package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import net.barakiroth.hellostrangeworld.farbackend.Config;

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

  private final Config config;

  public DatabaseConfig(final Config config) {
    this.config = config;
  }
  
  String getUrl() {
    
    assert (this.config != null);
    
    final String url = this.config.getString(DB_URL_KEY, DB_URL_DEFAULT);

    return url;
  }
  
  String getUser() {
    
    assert (this.config != null);
    
    final String user = this.config.getString(DB_USER_KEY, DB_USER_DEFAULT);
    
    return user;
  }
  
  String getPwd() {
    
    assert (this.config != null);
    
    final String pwd = this.config.getString(DB_PWD_KEY, DB_PWD_DEFAULT);
    
    return pwd;
  }
  
  int getConnectionTimeout() {
    
    assert (this.config != null);
    
    int connectionTimeout = this.config.getInteger(
          DB_CONNECTION_TIMEOUT_KEY, 
          DB_CONNECTION_TIMEOUT_DEFAULT);
    
    return connectionTimeout;
  }
  
  int getMaxLifetime() {
    
    assert (this.config != null);
    
    final int maxLifetime =
        this.config.getInteger(
          DB_MAX_LIFETIME_KEY, 
          DB_MAX_LIFETIME_DEFAULT);
    return maxLifetime;
  }

  int getLeakDetectionThreshold() {
    
    assert (this.config != null);
    
    final int leakDetectionThreshold =
        this.config.getInteger(
          DB_LEAK_DETECTION_THRESHOLD_KEY, 
          DB_LEAK_DETECTION_THRESHOLD_DEFAULT);
    return leakDetectionThreshold;
  }
  
  int getMaximumPoolSize() {
    
    assert (this.config != null);
    
    final int maximumPoolSize =
        this.config.getInteger(
          DB_MAXIMUM_POOLSIZE_KEY, 
          DB_MAXIMUM_POOLSIZE_DEFAULT);
    return maximumPoolSize;
  }
}