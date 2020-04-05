package net.barakiroth.hellostrangeworld.farbackend;

import net.barakiroth.hellostrangeworld.common.AbstractConfig;
import net.barakiroth.hellostrangeworld.farbackend.domain.Repository;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.DatabaseConfig;

public class FarBackendConfig extends AbstractConfig implements IFarBackendConfig {

  private static IFarBackendConfig singletonInstance; 
  
  private DatabaseConfig databaseConfig;
  
  public static IFarBackendConfig getSingletonInstance() {
    if (FarBackendConfig.singletonInstance == null) {
      setSingletonInstance(createSingletonInstance());
    }
    return FarBackendConfig.singletonInstance; 
  }
  
  public static void setSingletonInstance(final IFarBackendConfig farBackendConfig) {
    FarBackendConfig.singletonInstance = farBackendConfig; 
  }
  
  private static FarBackendConfig createSingletonInstance() {
    return new FarBackendConfig();
  }
  
  private static DatabaseConfig createDatabaseConfig(final IFarBackendConfig farBackendConfig) {
    return DatabaseConfig.getSingletonInstance(farBackendConfig);
  }

  private FarBackendConfig() {
    super();
  }
  
  private static Repository createRepository(final IFarBackendConfig farBackendConfig) {
    
    return new Repository(farBackendConfig);
  }

  private void setDatabaseConfig(final DatabaseConfig databaseConfig) {
    this.databaseConfig = databaseConfig;
  }

  @Override
  public DatabaseConfig getDatabaseConfig() {
    if (this.databaseConfig == null) {
      final DatabaseConfig databaseConfig = FarBackendConfig.createDatabaseConfig(this);
      setDatabaseConfig(databaseConfig);
    }
    return this.databaseConfig;
  }

  @Override
  public Database getDatabase() {
    return getDatabaseConfig().getDatabase();
  }

  @Override
  public Repository getRepository() {
    return FarBackendConfig.createRepository(this);
  }
}