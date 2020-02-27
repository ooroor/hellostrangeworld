package net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer;

import net.barakiroth.hellostrangeworld.common.IConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.IJettyManagerConfig;

public class JettyManagerConfig implements IJettyManagerConfig {

  static final int JETTY_SERVER_PORT_DEFAULT = 8079;
  static final String JETTY_ROOT_CONTEXT_PATH_DEFAULT = "/";
  static final String JETTY_DEFAULT_CONTEXT_PATH_DEFAULT = "/*";
  static final String JETTY_METRICS_CONTEXT_PATH_DEFAULT = "/internal/metrics";
  static final String JETTY_RESOURCE_PATH_SPEC_DEFAULT = "/someResource/*";
  
  private static final String JERSEY_APPLICATION_CLASS_NAME_KEY = "jersey.application.class.name";
  private static final String JETTY_SERVER_PORT_KEY = "jetty.port";
  private static final String JETTY_ROOT_CONTEXT_PATH_KEY = "jetty.root.path.spec";
  private static final String JETTY_DEFAULT_CONTEXT_PATH_KEY = "jetty.default.path.spec";
  private static final String JETTY_METRICS_CONTEXT_PATH_KEY = "jetty.metrics.path.spec";
  private static final String JETTY_RESOURCE_PATH_SPEC_KEY = "jetty.path.spec";
  
  private static IJettyManagerConfig singletonInstance = null;
  private final IConfig config;

  private JettyManagerConfig(final IConfig config) {
    this.config = config;
  }

  public static IJettyManagerConfig getSingletonInstance(final IConfig config) {
    
    if (JettyManagerConfig.singletonInstance == null) {
      final IJettyManagerConfig singletonInstance = createSingletonInstance(config);
      setSingletonInstance(singletonInstance);
    }
    return JettyManagerConfig.singletonInstance;
  }

  private static void setSingletonInstance(final IJettyManagerConfig jettyManagerConfig) {
    JettyManagerConfig.singletonInstance = jettyManagerConfig;
  }

  private static IJettyManagerConfig createSingletonInstance(final IConfig config) {
    return new JettyManagerConfig(config);
  }
  
  private IConfig getConfig() {
    return this.config;
  }

  @Override
  public int getServerPort() {
    final int port = getConfig().getInt(JETTY_SERVER_PORT_KEY, JETTY_SERVER_PORT_DEFAULT);
    return port;
  }

  @Override
  public String getResourcePathSpec() {
    final String contextPath =
        getConfig().getString(JETTY_RESOURCE_PATH_SPEC_KEY, JETTY_RESOURCE_PATH_SPEC_DEFAULT);
    return contextPath;
  }

  @Override
  public String getRootContextPath() {
    final String contextPath =
        getConfig().getString(JETTY_ROOT_CONTEXT_PATH_KEY, JETTY_ROOT_CONTEXT_PATH_DEFAULT);
    return contextPath;
  }

  @Override
  public String getDefaultPathSpec() {
    final String contextPath =
        getConfig().getString(JETTY_DEFAULT_CONTEXT_PATH_KEY, JETTY_DEFAULT_CONTEXT_PATH_DEFAULT);
    return contextPath;
  }
  
  @Override
  public String getMetricsContextPath() {
    final String contextPath =
        getConfig().getString(JETTY_METRICS_CONTEXT_PATH_KEY, JETTY_METRICS_CONTEXT_PATH_DEFAULT);
    return contextPath;
  }
  
  @Override
  public String getJerseyApplicationClassName() {
    final String jerseyApplicationClassName =
        getConfig().getRequiredString(JERSEY_APPLICATION_CLASS_NAME_KEY);
    return jerseyApplicationClassName;
  }
}