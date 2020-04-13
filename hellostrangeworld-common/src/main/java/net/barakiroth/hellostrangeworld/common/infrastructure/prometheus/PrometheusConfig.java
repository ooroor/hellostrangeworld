package net.barakiroth.hellostrangeworld.common.infrastructure.prometheus;

import io.prometheus.client.Gauge;
import net.barakiroth.hellostrangeworld.common.IGeneralConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.prometheus.IPrometheusConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrometheusConfig implements IPrometheusConfig {

  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");

  private final IGeneralConfig generalConfig;
  private       Gauge          getResourceDurationGauge;
  private       Gauge          lastGetResourceSuccessGauge;
  
  private static PrometheusConfig singleton = null;
  
  private PrometheusConfig(final IGeneralConfig generalConfig) {

    enteringMethodHeaderLogger.debug(null);
    
    this.generalConfig = generalConfig;
    
    leavingMethodHeaderLogger.debug(null);
  }
  
  public static IPrometheusConfig getSingleton(final IGeneralConfig generalConfig) {
    if (PrometheusConfig.singleton == null) {
      PrometheusConfig.singleton = createsingleton(generalConfig);
    }
    return PrometheusConfig.singleton;
  }
  
  private static PrometheusConfig createsingleton(final IGeneralConfig generalConfig) {
    return new PrometheusConfig(generalConfig);
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
            .name(this.generalConfig
                .getRequiredString(IPrometheusConfig.METR_RESOURCE_DURATION_GAUGE_NAME_KEY))
            .help(this.generalConfig
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
            .name(this.generalConfig
                .getRequiredString(IPrometheusConfig.METR_RESOURCE_SUCCESS_GAUGE_NAME_KEY))
            .help(this.generalConfig
                .getRequiredString(IPrometheusConfig.METR_RESOURCE_SUCCESS_GAUGE_HELP_KEY))
            .register()
    ;

    leavingMethodHeaderLogger.debug(null);

    return lastGetResourceSuccessGauge;
  }
}