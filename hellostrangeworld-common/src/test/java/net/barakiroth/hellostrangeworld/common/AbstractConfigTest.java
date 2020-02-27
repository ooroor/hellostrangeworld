package net.barakiroth.hellostrangeworld.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractConfigTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");

  @Test
  void when_getting_the_string_value_from_a_non_defined_sys_prop_then_the_default_value_should_be_returned() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    final String defaultValue = "Some dummy test value";
    final String expectedValue = defaultValue;
    System.clearProperty(key);
    final IConfig config = new AbstractConfig() {
      {
      }
    };
    assertThat(config.getString(key, defaultValue)).isEqualTo(expectedValue);
  }

  @Test
  void when_getting_the_string_value_from_a_defined_sys_prop_with_default_then_the_set_value_should_be_returned() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    final String defaultValue = "Some dummy test value";
    final String expectedValue = "Some dummy test value";
    System.setProperty(key, expectedValue);
    final IConfig config = new AbstractConfig() {
      {
      }
    };
    assertThat(config.getString(key, defaultValue)).isEqualTo(expectedValue);
  }

  @Test
  void when_getting_a_required_string_value_from_a_defined_sys_prop_then_the_set_value_should_be_returned() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    final String expectedValue = "Some dummy test value";
    System.setProperty(key, expectedValue);
    final IConfig config = new AbstractConfig() {
      {
      }
    };
    assertThat(config.getRequiredString(key)).isEqualTo(expectedValue);
  }

  @Test
  void when_getting_a_required_string_value_from_a_non_defined_sys_prop_then_an_exception_should_be_thrown() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    System.clearProperty(key);
    final IConfig config = new AbstractConfig() {
      {
      }
    };
    assertThatIllegalStateException().isThrownBy(() -> config.getRequiredString(key));
  }

  @Test
  void when_getting_the_int_value_from_a_non_defined_sys_prop_then_the_default_value_should_be_returned() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    final int defaultValue = 17;
    final int expectedValue = defaultValue;
    System.clearProperty(key);
    final IConfig config = new AbstractConfig() {
      {
      }
    };
    assertThat(config.getInt(key, defaultValue)).isEqualTo(expectedValue);
  }

  @Test
  void when_getting_the_int_value_from_a_defined_sys_prop_with_default_then_the_set_value_should_be_returned() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    final int defaultValue = 19;
    final int expectedValue = 21;
    System.setProperty(key, String.valueOf(expectedValue));
    final IConfig config = new AbstractConfig() {
      {
      }
    };
    assertThat(config.getInt(key, defaultValue)).isEqualTo(expectedValue);
  }

  @Test
  void when_getting_a_required_int_value_from_a_defined_sys_prop_then_the_set_value_should_be_returned() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    final int expectedValue = 23;
    System.setProperty(key, String.valueOf(expectedValue));
    final IConfig config = new AbstractConfig() {
      {
      }
    };
    assertThat(config.getRequiredInt(key)).isEqualTo(expectedValue);
  }

  @Test
  void when_getting_a_required_int_value_from_a_non_defined_sys_prop_then_an_exception_should_be_thrown() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    System.clearProperty(key);
    final IConfig config = new AbstractConfig() {
      {
      }
    };
    assertThatIllegalStateException().isThrownBy(() -> config.getRequiredInt(key));
  }

  @Test
  void when_getting_the_int_value_from_a_non_defined_sys_prop_then_null_should_be_returned() {

    enteringTestHeaderLogger.debug(null);
    
    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    System.clearProperty(key);
    final IConfig config = new AbstractConfig() {
      {
      }
    };
    assertThat(config.getInt(key)).isEqualTo(0);
  }

  @Test
  void when_getting_the_int_value_from_a_defined_sys_prop_then_set_value_should_be_returned() {

    enteringTestHeaderLogger.debug(null);

    final String key = "ThisReallyShouldNotExistAsASystemProperty";
    final int expectedValue = 31;
    System.setProperty(key, String.valueOf(expectedValue));
    final IConfig config = new AbstractConfig() {
      {
      }
    };
    assertThat(config.getInt(key)).isEqualTo(expectedValue);
  }
}