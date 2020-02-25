package net.barakiroth.hellostrangeworld.backend.infrastructure.prometheus;

import io.prometheus.client.Gauge;
import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.common.IConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrometheusConfig {

  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");
  
  private static final String METR_GETGREETINGDESCRIPTION_DURATION_GAUGE_NAME_KEY =
      "prometheus.GetGreetingDescription.duration.gauge.name";
  private static final String METR_GETGREETINGDESCRIPTION_DURATION_GAUGE_HELP_KEY =
      "prometheus.GetGreetingDescription.duration.gauge.help";
  private static final String METR_GETGREETINGDESCRIPTION_SUCCESS_GAUGE_NAME_KEY  =
      "prometheus.GetGreetingDescription.success.gauge.name";
  private static final String METR_GETGREETINGDESCRIPTION_SUCCESS_GAUGE_HELP_KEY  =
      "prometheus.GetGreetingDescription.success.gauge.help";

  private final IConfig config;
  private final Gauge   getGetInitialPartDurationGauge;
  private final Gauge   lastGetInitialPartSuccessGauge;
  
  @Getter(AccessLevel.PUBLIC)
  private static PrometheusConfig singletonInstance = null;
  
  private PrometheusConfig(final IConfig config) {
    this.config                         = config;
    this.getGetInitialPartDurationGauge = createGetInitialPartDurationGauge();
    this.lastGetInitialPartSuccessGauge = createLastGetInitialPartSuccessGauge();
  }
  
  public static PrometheusConfig createSingletonInstance(final IConfig config) {
    if (PrometheusConfig.singletonInstance != null) {
      throw new IllegalStateException("Singleton already created");
    }
    PrometheusConfig.singletonInstance = new PrometheusConfig(config);
    return PrometheusConfig.getSingletonInstance();
  }

  public Gauge getGetInitialPartDurationGauge() {

    return this.getGetInitialPartDurationGauge;
  }

  public Gauge getLastGetInitialPartSuccessGauge() {

    return this.lastGetInitialPartSuccessGauge;
  }

  private Gauge createGetInitialPartDurationGauge() {

    enteringMethodHeaderLogger.debug(null);

    final Gauge getGreetingDescriptionDurationGauge =
        Gauge
            .build()
            .name(this.config
                    .getRequiredString(METR_GETGREETINGDESCRIPTION_DURATION_GAUGE_NAME_KEY))
            .help(this.config
                    .getRequiredString(METR_GETGREETINGDESCRIPTION_DURATION_GAUGE_HELP_KEY))
            .register();

    leavingMethodHeaderLogger.debug(null);

    return getGreetingDescriptionDurationGauge;
  }

  private Gauge createLastGetInitialPartSuccessGauge() {

    enteringMethodHeaderLogger.debug(null);

    final Gauge lastGetGreetingDescriptionSuccessGauge =
        Gauge
            .build()
            .name(this.config.getRequiredString(METR_GETGREETINGDESCRIPTION_SUCCESS_GAUGE_NAME_KEY))
            .help(this.config.getRequiredString(METR_GETGREETINGDESCRIPTION_SUCCESS_GAUGE_HELP_KEY))
            .register()
    ;

    leavingMethodHeaderLogger.debug(null);

    return lastGetGreetingDescriptionSuccessGauge;
  }
}