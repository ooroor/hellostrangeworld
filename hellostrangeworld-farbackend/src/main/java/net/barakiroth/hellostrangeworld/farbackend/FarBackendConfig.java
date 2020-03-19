package net.barakiroth.hellostrangeworld.farbackend;

import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.common.AbstractConfig;
import net.barakiroth.hellostrangeworld.farbackend.domain.Repository;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.DatabaseConfig;

public class FarBackendConfig extends AbstractConfig implements IFarBackendConfig {

  @Getter(AccessLevel.PUBLIC)
  private static final IFarBackendConfig singletonInstance = new FarBackendConfig();
  
  private DatabaseConfig databaseConfig;
  private Database database;
  private Repository repository;

  private FarBackendConfig() {
    super();
  }

  private void setDatabaseConfig(final DatabaseConfig databaseConfig) {
    this.databaseConfig = databaseConfig;
  }

  @Override
  public DatabaseConfig getDatabaseConfig() {
    if (this.databaseConfig == null) {
      // TODO: Should call the getter, not the constructor:
      final DatabaseConfig databaseConfig = new DatabaseConfig(this);
      setDatabaseConfig(databaseConfig);
    }
    return this.databaseConfig;
  }

  private void setDatabase(final Database database) {
    this.database = database;
  }

  @Override
  public Database getDatabase() {
    if (this.database == null) {
      // TODO: Should call the getter, not the constructor:
      final Database database = new Database(this);
      setDatabase(database);
    }
    return this.database;
  }

  private void setRepository(final Repository repository) {
    this.repository = repository;
  }

  @Override
  public Repository getRepository() {
    if (this.repository == null) {
      // TODO: Should call the getter, not the constructor:
      final Repository repository = new Repository(this);
      setRepository(repository);
    }
    return this.repository;
  }

}
