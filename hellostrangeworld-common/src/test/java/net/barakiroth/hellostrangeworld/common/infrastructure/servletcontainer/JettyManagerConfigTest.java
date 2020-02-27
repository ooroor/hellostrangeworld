package net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer;

import static org.assertj.core.api.Assertions.assertThat;

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
}