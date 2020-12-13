package net.barakiroth.hellostrangeworld.common.infrastructure.prometheus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import io.prometheus.client.Gauge;
import net.barakiroth.hellostrangeworld.common.CommonConfig;
import net.barakiroth.hellostrangeworld.common.ITestConst;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(ITestConst.UNIT_TEST_ANNOTATION)
@ExtendWith(MockitoExtension.class)
public class PrometheusConfigTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @Mock
  private Gauge mockedGetResourceDurationGauge;
  
  @Mock
  private Gauge mockedLastGetResourceSuccessGauge;

  @Test
  void when_getting_the_singleton_then_it_should_not_be_null() {
    
    enteringTestHeaderLogger.debug(null);
    
    assertThat(PrometheusConfig.getSingleton(new CommonConfig() {{}})).isNotNull();
  }

  @Test
  void when_getting_the_getResourceDurationGauge_then_it_should_be_implicitly_created_without_an_exception() {
    
    enteringTestHeaderLogger.debug(null);
    
    final IPrometheusConfig prometheusConfig =
        PrometheusConfig.getSingleton(new CommonConfig() {{}});
    System.setProperty(IPrometheusConfig.METR_RESOURCE_DURATION_GAUGE_NAME_KEY, "someDummyPlaceHolderDurationGaugeName");
    System.setProperty(IPrometheusConfig.METR_RESOURCE_DURATION_GAUGE_HELP_KEY, "someDummyPlaceHolderDurationGaugeHelp");
    
    assertThat(prometheusConfig.getGetResourceDurationGauge()).isNotNull();
  }

  @Test
  void when_implicitly_creating_the_getResourceDurationGauge_and_the_appriate_sys_props_are_not_set_then_an_exception_should_thrown() {
    
    enteringTestHeaderLogger.debug(null);
    
    final IPrometheusConfig prometheusConfig =
        PrometheusConfig.getSingleton(new CommonConfig() {{}});
    ((PrometheusConfig)prometheusConfig).setGetResourceDurationGauge(null);
    System.clearProperty(IPrometheusConfig.METR_RESOURCE_DURATION_GAUGE_NAME_KEY);
    System.clearProperty(IPrometheusConfig.METR_RESOURCE_DURATION_GAUGE_HELP_KEY);
    
    assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> prometheusConfig.getGetResourceDurationGauge());
  }

  @Test
  void when_setting_the_getResourceDurationGauge_then_the_same_one_should_be_returned_by_get() {
    
    enteringTestHeaderLogger.debug(null);
    
    final PrometheusConfig prometheusConfig =
        (PrometheusConfig)PrometheusConfig.getSingleton(new CommonConfig() {{}});
    
    prometheusConfig.setGetResourceDurationGauge(mockedGetResourceDurationGauge);
    final Gauge expectedResourceDurationGauge = mockedGetResourceDurationGauge;
    final Gauge actualResourceDurationGauge = prometheusConfig.getGetResourceDurationGauge();
    
    assertThat(actualResourceDurationGauge).isSameAs(expectedResourceDurationGauge);
  }

  @Test
  void when_getting_the_lastGetResourceSuccessGauge_then_it_should_be_implicitly_created_without_an_exception() {
    
    enteringTestHeaderLogger.debug(null);
    
    final IPrometheusConfig prometheusConfig =
        PrometheusConfig.getSingleton(new CommonConfig() {{}});
    
    ((PrometheusConfig)prometheusConfig).setLastGetResourceSuccessGauge(null);
    System.setProperty(IPrometheusConfig.METR_RESOURCE_SUCCESS_GAUGE_NAME_KEY, "someDummyPlaceHolderSuccessGaugeName");
    System.setProperty(IPrometheusConfig.METR_RESOURCE_SUCCESS_GAUGE_HELP_KEY, "someDummyPlaceHolderSuccessGaugeHelp");
    
    assertThat(prometheusConfig.getLastGetResourceSuccessGauge()).isNotNull();
  }

  @Test
  void when_implicitly_creating_the_lastGetResourceSuccessGauge_and_the_appriate_sys_props_are_not_set_then_an_exception_should_thrown() {
    
    enteringTestHeaderLogger.debug(null);
    
    final IPrometheusConfig prometheusConfig =
        PrometheusConfig.getSingleton(new CommonConfig() {{}});
    ((PrometheusConfig)prometheusConfig).setLastGetResourceSuccessGauge(null);
    System.clearProperty(IPrometheusConfig.METR_RESOURCE_SUCCESS_GAUGE_NAME_KEY);
    System.clearProperty(IPrometheusConfig.METR_RESOURCE_SUCCESS_GAUGE_HELP_KEY);
    
    assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> prometheusConfig.getLastGetResourceSuccessGauge());
  }

  @Test
  void when_setting_the_lastGetResourceSuccessGauge_then_the_same_one_should_be_returned_by_get() {
    
    enteringTestHeaderLogger.debug(null);
    
    final PrometheusConfig prometheusConfig =
        (PrometheusConfig)PrometheusConfig.getSingleton(new CommonConfig() {{}});
    
    prometheusConfig.setLastGetResourceSuccessGauge(mockedLastGetResourceSuccessGauge);
    final Gauge expectedLastGetResourceSuccessGauge = mockedLastGetResourceSuccessGauge;
    final Gauge actualLastGetResourceSuccessGauge = prometheusConfig.getLastGetResourceSuccessGauge();
    
    assertThat(actualLastGetResourceSuccessGauge).isSameAs(expectedLastGetResourceSuccessGauge);
  }
}