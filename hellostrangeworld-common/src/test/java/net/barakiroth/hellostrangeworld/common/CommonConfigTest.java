package net.barakiroth.hellostrangeworld.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.barakiroth.hellostrangeworld.common.infrastructure.prometheus.IPrometheusConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.IJettyManagerConfig;

@Tag(ITestConst.UNIT_TEST_ANNOTATION)
public class CommonConfigTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");

  @Test
  void when_getting_the_string_value_from_a_non_defined_sys_prop_then_the_default_value_should_be_returned() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    final String defaultValue = "Some dummy test value";
    final String expectedValue = defaultValue;
    System.clearProperty(key);
    final IGeneralConfig generalConfig = new CommonConfig() {
      {
      }
    };
    assertThat(generalConfig.getString(key, defaultValue)).isEqualTo(expectedValue);
  }

  @Test
  void when_getting_the_string_value_from_a_defined_sys_prop_with_default_then_the_set_value_should_be_returned() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    final String defaultValue = "Some dummy test value";
    final String expectedValue = "Some dummy test value";
    System.setProperty(key, expectedValue);
    final IGeneralConfig generalConfig = new CommonConfig() {
      {
      }
    };
    assertThat(generalConfig.getString(key, defaultValue)).isEqualTo(expectedValue);
  }

  @Test
  void when_getting_a_required_string_value_from_a_defined_sys_prop_then_the_set_value_should_be_returned() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    final String expectedValue = "Some dummy test value";
    System.setProperty(key, expectedValue);
    
    final IGeneralConfig generalConfig = new CommonConfig() {
      {
      }
    };
    assertThat(generalConfig.getRequiredString(key)).isEqualTo(expectedValue);
  }

  @Test
  void when_getting_a_required_string_value_from_a_non_defined_sys_prop_then_an_exception_should_be_thrown() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    System.clearProperty(key);
    final IGeneralConfig generalConfig = new CommonConfig() {
      {
      }
    };
    assertThatIllegalStateException().isThrownBy(() -> generalConfig.getRequiredString(key));
  }

  @Test
  void when_getting_the_int_value_from_a_non_defined_sys_prop_then_the_default_value_should_be_returned() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    final int defaultValue = 17;
    final int expectedValue = defaultValue;
    System.clearProperty(key);
    final IGeneralConfig generalConfig = new CommonConfig() {
      {
      }
    };
    assertThat(generalConfig.getInt(key, defaultValue)).isEqualTo(expectedValue);
  }

  @Test
  void when_getting_the_int_value_from_a_defined_sys_prop_with_default_then_the_set_value_should_be_returned() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    final int defaultValue = 19;
    final int expectedValue = 21;
    System.setProperty(key, String.valueOf(expectedValue));
    final IGeneralConfig generalConfig = new CommonConfig() {
      {
      }
    };
    assertThat(generalConfig.getInt(key, defaultValue)).isEqualTo(expectedValue);
  }

  @Test
  void when_getting_a_required_int_value_from_a_defined_sys_prop_then_the_set_value_should_be_returned() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    final int expectedValue = 23;
    System.setProperty(key, String.valueOf(expectedValue));
    final IGeneralConfig generalConfig = new CommonConfig() {
      {
      }
    };
    assertThat(generalConfig.getRequiredInt(key)).isEqualTo(expectedValue);
  }

  @Test
  void when_getting_a_required_int_value_from_a_non_defined_sys_prop_then_an_exception_should_be_thrown() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    System.clearProperty(key);
    final IGeneralConfig generalConfig = new CommonConfig() {
      {
      }
    };
    assertThatIllegalStateException().isThrownBy(() -> generalConfig.getRequiredInt(key));
  }

  @Test
  void when_getting_the_int_value_from_a_non_defined_sys_prop_then_null_should_be_returned() {

    enteringTestHeaderLogger.debug(null);
    
    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    System.clearProperty(key);
    final IGeneralConfig generalConfig = new CommonConfig() {
      {
      }
    };
    assertThat(generalConfig.getInt(key)).isEqualTo(0);
  }

  @Test
  void when_getting_the_int_value_from_a_defined_sys_prop_then_set_value_should_be_returned() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    final int expectedValue = 31;
    System.setProperty(key, String.valueOf(expectedValue));
    final IGeneralConfig generalConfig = new CommonConfig() {
      {
      }
    };
    assertThat(generalConfig.getInt(key)).isEqualTo(expectedValue);
  }

  @Test
  void when_getting_jetty_manager_config_for_the_first_time_then_a_new_one_should_implicitly_be_created() {

    enteringTestHeaderLogger.debug(null);
    

    final ICommonConfig config = new CommonConfig() {
      {
      }
    };
    assertThat(config.getJettyManagerConfig()).isNotNull();
  }

  @Test
  void when_getting_jetty_manager_config_twice_then_the_second_one_should_be_the_same_as_the_first_one() {

    enteringTestHeaderLogger.debug(null);

    final ICommonConfig config = new CommonConfig() {
      {
      }
    };
    final IJettyManagerConfig jettyManagerConfig = config.getJettyManagerConfig();
    assertThat(config.getJettyManagerConfig()).isSameAs(jettyManagerConfig);
  }

  @Test
  void when_getting_PrometheusConfig_for_the_first_time_then_a_new_one_should_implicitly_be_created() {

    enteringTestHeaderLogger.debug(null);
    

    final ICommonConfig config = new CommonConfig() {
      {
      }
    };
    assertThat(config.getPrometheusConfig()).isNotNull();
  }

  @Test
  void when_getting_PrometheusConfig_twice_then_the_second_one_should_be_the_same_as_the_first_one() {

    enteringTestHeaderLogger.debug(null);

    final ICommonConfig config = new CommonConfig() {
      {
      }
    };
    final IPrometheusConfig prometheusConfig = config.getPrometheusConfig();
    assertThat(config.getPrometheusConfig()).isSameAs(prometheusConfig);
  }
}