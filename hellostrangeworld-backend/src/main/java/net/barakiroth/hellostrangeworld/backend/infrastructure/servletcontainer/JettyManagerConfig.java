package net.barakiroth.hellostrangeworld.backend.infrastructure.servletcontainer;

import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.backend.IBackendConfig;

public class JettyManagerConfig {
  
  private static final String JETTY_SERVER_PORT_KEY     = "jetty.port";
  private static final int    JETTY_SERVER_PORT_DEFAULT = 8088;

  private static final String JETTY_ROOT_CONTEXT_PATH_KEY = "jetty.root.path.spec";
  private static final String JETTY_ROOT_CONTEXT_PATH_DEFAULT = "/"; 
  
  private static final String JETTY_DEFAULT_CONTEXT_PATH_KEY = "jetty.default.path.spec";
  private static final String JETTY_DEFAULT_CONTEXT_PATH_DEFAULT = "/*";
  
  private static final String JETTY_METRICS_CONTEXT_PATH_KEY = "jetty.metrics.path.spec";
  private static final String JETTY_METRICS_CONTEXT_PATH_DEFAULT = "/internal/metrics";

  private static final String JETTY_RESOURCE_PATH_SPEC_KEY = "jetty.greetings.descriptor.path.spec";
  private static final String JETTY_RESOURCE_PATH_SPEC_DEFAULT = "/greetings/*";
  
  @Getter(AccessLevel.PUBLIC)
  private static JettyManagerConfig singletonInstance = null;
  
  private final IBackendConfig config;

  private JettyManagerConfig(final IBackendConfig config) {
    this.config = config;
  }
  
  public static JettyManagerConfig createSingletonInstance(final IBackendConfig config) {
    
    if (JettyManagerConfig.singletonInstance != null) {
      throw new IllegalStateException("Singleton already created");
    }
    JettyManagerConfig.singletonInstance = new JettyManagerConfig(config);
    return JettyManagerConfig.getSingletonInstance();
  }

  int getServerPort() {
    final int port = this.config.getInt(JETTY_SERVER_PORT_KEY, JETTY_SERVER_PORT_DEFAULT);
    return port;
  }

  String getResourcePathSpec() {
    final String contextPath = 
        this.config.getString(JETTY_RESOURCE_PATH_SPEC_KEY, JETTY_RESOURCE_PATH_SPEC_DEFAULT);
    return contextPath;
  }

  String getRootContextPath() {
    final String contextPath =
        this.config.getString(JETTY_ROOT_CONTEXT_PATH_KEY, JETTY_ROOT_CONTEXT_PATH_DEFAULT);
    return contextPath;
  }

  String getDefaultPathSpec() {
    final String contextPath =
        this.config.getString(JETTY_DEFAULT_CONTEXT_PATH_KEY, JETTY_DEFAULT_CONTEXT_PATH_DEFAULT);
    return contextPath;
  }

  String getMetricsContextPath() {
    final String contextPath =
        this.config.getString(JETTY_METRICS_CONTEXT_PATH_KEY, JETTY_METRICS_CONTEXT_PATH_DEFAULT);
    return contextPath;
  }
}