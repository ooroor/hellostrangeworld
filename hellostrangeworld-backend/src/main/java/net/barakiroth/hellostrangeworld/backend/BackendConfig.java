package net.barakiroth.hellostrangeworld.backend;

import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.backend.infrastructure.prometheus.PrometheusConfig;
import net.barakiroth.hellostrangeworld.backend.infrastructure.servletcontainer.JettyManager;
import net.barakiroth.hellostrangeworld.common.AbstractConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.IJettyManagerConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.JettyManagerConfig;

public class BackendConfig extends AbstractConfig implements IBackendConfig {

  @Getter(AccessLevel.PUBLIC)
  private static final IBackendConfig singletonInstance = new BackendConfig();

  private JettyManager        jettyManager;
  private PrometheusConfig    prometheusConfig;

  private BackendConfig() {
    super();
  }  

  private void setJettyManager(final JettyManager jettyManager) {
    this.jettyManager = jettyManager;
  }

  @Override
  public JettyManager getJettyManager() {
    if (this.jettyManager == null) {
      // TODO: Should call the getter, not the creator:
      final JettyManager jettyManager =
          JettyManager.getSingletonInstance(this);
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
}