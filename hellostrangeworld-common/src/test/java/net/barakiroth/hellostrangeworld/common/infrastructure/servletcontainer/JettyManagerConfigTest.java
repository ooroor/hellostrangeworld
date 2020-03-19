package net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.barakiroth.hellostrangeworld.common.AbstractConfig;
import net.barakiroth.hellostrangeworld.common.IConfig;

public class JettyManagerConfigTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");

  @Test
  void when_getting_the_singleton_then_it_should_not_be_null() {
    
    enteringTestHeaderLogger.debug(null);
    
    assertThat(JettyManagerConfig.getSingletonInstance(new AbstractConfig() {{}})).isNotNull();
  }

  @Test
  void when_getting_the_server_port_then_the_default_one_should_be_returned() {
    
    enteringTestHeaderLogger.debug(null);
    
    final IConfig config = new AbstractConfig() {{}};
    final IJettyManagerConfig jettyManagerConfig = JettyManagerConfig.getSingletonInstance(config);
    
    assertThat(jettyManagerConfig.getServerPort()).isEqualTo(JettyManagerConfig.JETTY_SERVER_PORT_DEFAULT);
  }

  @Test
  void when_getting_the_resourcePathSpec_then_the_default_one_should_be_returned() {
    
    enteringTestHeaderLogger.debug(null);
    
    final IConfig config = new AbstractConfig() {{}};
    final IJettyManagerConfig jettyManagerConfig = JettyManagerConfig.getSingletonInstance(config);
    
    assertThat(jettyManagerConfig.getResourcePathSpec()).isEqualTo(JettyManagerConfig.JETTY_RESOURCE_PATH_SPEC_DEFAULT);
  }
  
  @Test
  void when_getting_the_rootContextPath_then_the_default_one_should_be_returned() {
    
    enteringTestHeaderLogger.debug(null);
    
    final IConfig config = new AbstractConfig() {{}};
    final IJettyManagerConfig jettyManagerConfig = JettyManagerConfig.getSingletonInstance(config);
    
    assertThat(jettyManagerConfig.getRootContextPath()).isEqualTo(JettyManagerConfig.JETTY_ROOT_CONTEXT_PATH_DEFAULT);
  }
  
  @Test
  void when_getting_the_defaultPathSpec_then_the_default_one_should_be_returned() {
    
    enteringTestHeaderLogger.debug(null);
    
    final IConfig config = new AbstractConfig() {{}};
    final IJettyManagerConfig jettyManagerConfig = JettyManagerConfig.getSingletonInstance(config);
    
    assertThat(jettyManagerConfig.getDefaultPathSpec()).isEqualTo(JettyManagerConfig.JETTY_DEFAULT_CONTEXT_PATH_DEFAULT);
  }
  
  @Test
  void when_getting_the_metricsContextPath_then_the_default_one_should_be_returned() {
    
    enteringTestHeaderLogger.debug(null);
    
    final IConfig config = new AbstractConfig() {{}};
    final IJettyManagerConfig jettyManagerConfig = JettyManagerConfig.getSingletonInstance(config);
    
    assertThat(jettyManagerConfig.getMetricsContextPath()).isEqualTo(JettyManagerConfig.JETTY_METRICS_CONTEXT_PATH_DEFAULT);
  }

  @Test
  void when_getting_the_jersey_application_class_name_then_the_value_of_the_apropriate_sys_prop_should_be_returned() {
    
    enteringTestHeaderLogger.debug(null);
    
    final IConfig config = new AbstractConfig() {{}};
    final IJettyManagerConfig jettyManagerConfig = JettyManagerConfig.getSingletonInstance(config);
    final String expectedJerseyApplicationClassName = "someRubbishForTestingPurposes";
    System.setProperty(JettyManagerConfig.JERSEY_APPLICATION_CLASS_NAME_KEY, expectedJerseyApplicationClassName);
    
    assertThat(jettyManagerConfig.getJerseyApplicationClassName()).isEqualTo(expectedJerseyApplicationClassName);
  }

  @Test
  void when_getting_the_jersey_application_class_name_when_sys_prop_not_set_then_an_exception_should_be_thrown() {
    
    enteringTestHeaderLogger.debug(null);
    
    final IConfig config = new AbstractConfig() {{}};
    final IJettyManagerConfig jettyManagerConfig = JettyManagerConfig.getSingletonInstance(config);
    System.clearProperty(JettyManagerConfig.JERSEY_APPLICATION_CLASS_NAME_KEY);
    
    assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> jettyManagerConfig.getJerseyApplicationClassName());
  }

  @Test
  void when_getting_jetty_manager_for_the_first_time_then_a_new_one_should_be_created() {

    enteringTestHeaderLogger.debug(null);

    final IConfig config = new AbstractConfig() {
      {
      }
    };
    assertThat(config.getJettyManagerConfig().getJettyManager()).isNotNull();
  }

  @Test
  void when_getting_jetty_manager_twice_then_the_second_one_should_be_the_same_as_the_first_one() {

    enteringTestHeaderLogger.debug(null);

    final IConfig config = new AbstractConfig() {
      {
      }
    };
    
    final IJettyManagerConfig jettyManagerConfig = config.getJettyManagerConfig();
    
    final JettyManager jettyManager = jettyManagerConfig.getJettyManager();
    assertThat(jettyManagerConfig.getJettyManager()).isSameAs(jettyManager);
  }
}