package net.barakiroth.hellostrangeworld.farbackend;

import net.barakiroth.hellostrangeworld.common.CommonConfig;
import net.barakiroth.hellostrangeworld.farbackend.domain.Repository;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.DatabaseConfig;

public class FarBackendConfig extends CommonConfig implements IFarBackendConfig {

  private static IFarBackendConfig singleton;

  private FarBackendConfig() {
    super();
  }
  
  public static void createAndSetSingleton() {
    FarBackendConfig.setSingleton(createSingleton());
  }

  public static IFarBackendConfig getSingleton() {
    if (FarBackendConfig.singleton == null) {
      createAndSetSingleton();
    }
    return FarBackendConfig.singleton;
  }
  
  public static void setSingleton(final IFarBackendConfig farBackendConfig) {
    FarBackendConfig.singleton = farBackendConfig; 
  }
  
  private static FarBackendConfig createSingleton() {
    return new FarBackendConfig();
  }
  
  private static void createAndSetDatabaseConfig(final IFarBackendConfig farBackendConfig) {
    DatabaseConfig.createAndSetSingleton(farBackendConfig);
  }
  
  private static Repository createRepository(final IFarBackendConfig farBackendConfig) {
    
    return new Repository(farBackendConfig);
  }

  @Override
  public DatabaseConfig getDatabaseConfig() {
    if (DatabaseConfig.getSingleton() == null) {
      FarBackendConfig.createAndSetDatabaseConfig(this);
    }
    return DatabaseConfig.getSingleton();
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