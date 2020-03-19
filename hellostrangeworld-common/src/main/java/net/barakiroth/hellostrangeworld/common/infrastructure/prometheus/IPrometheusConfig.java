package net.barakiroth.hellostrangeworld.common.infrastructure.prometheus;

import io.prometheus.client.Gauge;

public interface IPrometheusConfig {
  
  public static final String METR_RESOURCE_DURATION_GAUGE_NAME_KEY =
      "prometheus.resource.duration.gauge.name";
  public static final String METR_RESOURCE_DURATION_GAUGE_HELP_KEY =
      "prometheus.resource.duration.gauge.help";
  public static final String METR_RESOURCE_SUCCESS_GAUGE_NAME_KEY  =
      "prometheus.resource.success.gauge.name";
  public static final String METR_RESOURCE_SUCCESS_GAUGE_HELP_KEY  =
      "prometheus.resource.success.gauge.help";
  
  public Gauge getGetResourceDurationGauge();
  public Gauge getLastGetResourceSuccessGauge();
}
