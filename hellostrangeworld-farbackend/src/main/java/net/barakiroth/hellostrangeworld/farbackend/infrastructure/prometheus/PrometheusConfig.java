package net.barakiroth.hellostrangeworld.farbackend.infrastructure.prometheus;

import io.prometheus.client.Gauge;
import net.barakiroth.hellostrangeworld.farbackend.Config;
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

  private final Config config;
  private final Gauge  getGreetingDescriptionDurationGauge;
  private final Gauge  lastGetGreetingDescriptionSuccessGauge;

  /**
   * Stores constans and the different Prometheus collectors, like Gauges etc.
   * 
   * @param config The ubiquitous general configuration.
   */
  public PrometheusConfig(final Config config) {

    enteringMethodHeaderLogger.debug(null);

    // The sequence below is paramount, being taken care of by the separate asserts:
    this.config                        = config;
    this.getGreetingDescriptionDurationGauge    = createGetGreetingDescriptionDurationGauge();
    this.lastGetGreetingDescriptionSuccessGauge = createLastGetGreetingDescriptionSuccessGauge();

    leavingMethodHeaderLogger.debug(null);
  }

  public Gauge getGetGreetingDescriptionDurationGauge() {

    return this.getGreetingDescriptionDurationGauge;
  }

  public Gauge getLastGetGreetingDescriptionSuccessGauge() {

    return this.lastGetGreetingDescriptionSuccessGauge;
  }

  private Gauge createGetGreetingDescriptionDurationGauge() {

    enteringMethodHeaderLogger.debug(null);

    assert (this.config != null);

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

  private Gauge createLastGetGreetingDescriptionSuccessGauge() {

    enteringMethodHeaderLogger.debug(null);

    assert (this.config != null);

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