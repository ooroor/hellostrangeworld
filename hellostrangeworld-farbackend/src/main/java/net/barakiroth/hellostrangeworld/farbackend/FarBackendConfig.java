package net.barakiroth.hellostrangeworld.farbackend;

import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.common.AbstractConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.IJettyManagerConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.JettyManagerConfig;
import net.barakiroth.hellostrangeworld.farbackend.domain.Repository;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.DatabaseConfig;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.prometheus.PrometheusConfig;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer.JettyManager;

public class FarBackendConfig extends AbstractConfig implements IFarBackendConfig {

  @Getter(AccessLevel.PUBLIC)
  private static final IFarBackendConfig singletonInstance = new FarBackendConfig();

  private IJettyManagerConfig jettyManagerConfig;
  private JettyManager jettyManager;
  private DatabaseConfig databaseConfig;
  private Database database;
  private Repository repository;
  private PrometheusConfig prometheusConfig;

  private FarBackendConfig() {
    super();
  }

  private void setJettyManagerConfig(final IJettyManagerConfig jettyManagerConfig) {
    this.jettyManagerConfig = jettyManagerConfig;
  }

  @Override
  public IJettyManagerConfig getJettyManagerConfig() {
    if (this.jettyManagerConfig == null) {
      // TODO: Should call the getter, not the creator:
      final IJettyManagerConfig jettyManagerConfig =
          JettyManagerConfig.createSingletonInstance(this);
      setJettyManagerConfig(jettyManagerConfig);
    }
    return this.jettyManagerConfig;
  }

  private void setJettyManager(final JettyManager jettyManager) {
    this.jettyManager = jettyManager;
  }

  @Override
  public JettyManager getJettyManager() {
    if (this.jettyManager == null) {
      // TODO: Should call the getter, not the creator:
      final JettyManager jettyManager =
          JettyManager.getSingletonInstance(this, JerseyApplication.class.getName());
      setJettyManager(jettyManager);
    }
    return this.jettyManager;
  }

  private void setPrometheusConfig(final PrometheusConfig prometheusConfig) {
    this.prometheusConfig = prometheusConfig;
  }

  @Override
  public PrometheusConfig getPrometheusConfig() {
    if (this.prometheusConfig == null) {
      // TODO: Should call the getter, not the creator:
      final PrometheusConfig prometheusConfig = PrometheusConfig.createSingletonInstance(this);
      setPrometheusConfig(prometheusConfig);
    }
    return this.prometheusConfig;
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
