package net.barakiroth.hellostrangeworld.backend;

import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.backend.infrastructure.prometheus.PrometheusConfig;
import net.barakiroth.hellostrangeworld.common.AbstractConfig;

public class BackendConfig extends AbstractConfig implements IBackendConfig {

  @Getter(AccessLevel.PUBLIC)
  private static final IBackendConfig singletonInstance = new BackendConfig();

  private PrometheusConfig    prometheusConfig;

  private BackendConfig() {
    super();
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