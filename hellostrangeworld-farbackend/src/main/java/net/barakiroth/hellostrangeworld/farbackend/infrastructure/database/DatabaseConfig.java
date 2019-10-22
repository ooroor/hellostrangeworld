package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import net.barakiroth.hellostrangeworld.farbackend.Config;

public class DatabaseConfig {

  private static final String DB_DRIVER_CLASS_NAME_KEY         = 
      "db.driver.class.name";
  private static final String DB_DRIVER_CLASS_NAME_DEFAULT     = 
      "org.h2.Driver";
  private static final String DB_DS_SCHEMA_EXISTS_KEY         = 
      "db.ds.schema.exists";
  private static final String DB_DS_SCHEMA_EXISTS_DEFAULT     = 
      "jdbc:h2:mem:hellostrangeworld;DB_CLOSE_DELAY=-1;IFEXISTS=TRUE";
  private static final String DB_DS_SCHEMA_NOT_EXISTS_KEY     = 
      "db.ds.schema.not.exists";
  private static final String DB_DS_SCHEMA_NOT_EXISTS_DEFAULT = 
      "jdbc:h2:mem:hellostrangeworld;DB_CLOSE_DELAY=-1";
  private static final String DB_USER_KEY = "db.user";
  private static final String DB_USER_DEFAULT = "";
  private static final String DB_PWD_KEY = "db.pwd";
  private static final String DB_PWD_DEFAULT = "";
  
  private final Config config;

  public DatabaseConfig(final Config config) {
    this.config = config;
  }
  
  String getDriverClassName() {
    final String driverClassName =
        this.config.getString(DB_DRIVER_CLASS_NAME_KEY, DB_DRIVER_CLASS_NAME_DEFAULT);
    return driverClassName;
  }
  
  String getDataSourceForExistingSchema() {
    final String dataSourceForExistingSchema =
        this.config.getString(DB_DS_SCHEMA_EXISTS_KEY, DB_DS_SCHEMA_EXISTS_DEFAULT);
    return dataSourceForExistingSchema;
  }
  
  String getDataSourceForNonExistingSchema() {
    final String dataSourceForNonExistingSchema =
        this.config.getString(DB_DS_SCHEMA_NOT_EXISTS_KEY, DB_DS_SCHEMA_NOT_EXISTS_DEFAULT);
    return dataSourceForNonExistingSchema;
  }
  
  String getUser() {
    final String user = this.config.getString(DB_USER_KEY, DB_USER_DEFAULT);
    return user;
  }
  
  String getPwd() {
    final String pwd = this.config.getString(DB_PWD_KEY, DB_PWD_DEFAULT);
    return pwd;
  }
}