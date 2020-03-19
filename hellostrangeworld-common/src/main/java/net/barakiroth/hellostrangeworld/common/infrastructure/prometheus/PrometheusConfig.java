package net.barakiroth.hellostrangeworld.common.infrastructure.prometheus;

import io.prometheus.client.Gauge;
import net.barakiroth.hellostrangeworld.common.IConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.prometheus.IPrometheusConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrometheusConfig implements IPrometheusConfig {

  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");

  private final IConfig config;
  private       Gauge   getResourceDurationGauge;
  private       Gauge   lastGetResourceSuccessGauge;
  
  private static PrometheusConfig singletonInstance = null;
  
  private PrometheusConfig(final IConfig config) {

    enteringMethodHeaderLogger.debug(null);
    
    this.config = config;
    
    leavingMethodHeaderLogger.debug(null);
  }
  
  public static IPrometheusConfig getSingletonInstance(final IConfig config) {
    if (PrometheusConfig.singletonInstance == null) {
      PrometheusConfig.singletonInstance = createSingletonInstance(config);
    }
    return PrometheusConfig.singletonInstance;
  }
  
  private static PrometheusConfig createSingletonInstance(final IConfig config) {
    return new PrometheusConfig(config);
  }

  void setGetResourceDurationGauge(final Gauge getResourceDurationGauge) {
    this.getResourceDurationGauge = getResourceDurationGauge;
  }

  @Override
  public Gauge getGetResourceDurationGauge() {
    if (this.getResourceDurationGauge == null) {
      final Gauge getResourceDurationGauge = createGetResourceDurationGauge();
      setGetResourceDurationGauge(getResourceDurationGauge);
    }
    return this.getResourceDurationGauge;
  }

  void setLastGetResourceSuccessGauge(final Gauge lastGetResourceSuccessGauge) {
    this.lastGetResourceSuccessGauge =lastGetResourceSuccessGauge ;
  }

  @Override
  public Gauge getLastGetResourceSuccessGauge() {
    if (this.lastGetResourceSuccessGauge == null) {
      final Gauge lastGetResourceSuccessGauge = createLastGetResourceSuccessGauge();
      setLastGetResourceSuccessGauge(lastGetResourceSuccessGauge);
    }
    return this.lastGetResourceSuccessGauge;
  }

  private Gauge createGetResourceDurationGauge() {

    enteringMethodHeaderLogger.debug(null);

    final Gauge getResourceDurationGauge =
        Gauge
            .build()
            .name(this.config
                .getRequiredString(IPrometheusConfig.METR_RESOURCE_DURATION_GAUGE_NAME_KEY))
            .help(this.config
                .getRequiredString(IPrometheusConfig.METR_RESOURCE_DURATION_GAUGE_HELP_KEY))
            .register();

    leavingMethodHeaderLogger.debug(null);

    return getResourceDurationGauge;
  }

  private Gauge createLastGetResourceSuccessGauge() {

    enteringMethodHeaderLogger.debug(null);

    final Gauge lastGetResourceSuccessGauge =
        Gauge
            .build()
            .name(this.config
                .getRequiredString(IPrometheusConfig.METR_RESOURCE_SUCCESS_GAUGE_NAME_KEY))
            .help(this.config
                .getRequiredString(IPrometheusConfig.METR_RESOURCE_SUCCESS_GAUGE_HELP_KEY))
            .register()
    ;

    leavingMethodHeaderLogger.debug(null);

    return lastGetResourceSuccessGauge;
  }
}