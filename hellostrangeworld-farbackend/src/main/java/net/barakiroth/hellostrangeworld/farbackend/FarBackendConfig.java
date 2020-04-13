package net.barakiroth.hellostrangeworld.farbackend;

import net.barakiroth.hellostrangeworld.common.CommonConfig;
import net.barakiroth.hellostrangeworld.farbackend.domain.Repository;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.DatabaseConfig;

public class FarBackendConfig extends CommonConfig implements IFarBackendConfig {

  private static IFarBackendConfig singleton; 
  
  private DatabaseConfig databaseConfig;
  
  public static IFarBackendConfig getSingleton() {
    if (FarBackendConfig.singleton == null) {
      setsingleton(createsingleton());
    }
    return FarBackendConfig.singleton; 
  }
  
  public static void setsingleton(final IFarBackendConfig farBackendConfig) {
    FarBackendConfig.singleton = farBackendConfig; 
  }
  
  private static FarBackendConfig createsingleton() {
    return new FarBackendConfig();
  }
  
  private static DatabaseConfig createDatabaseConfig(final IFarBackendConfig farBackendConfig) {
    return DatabaseConfig.getSingleton(farBackendConfig);
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