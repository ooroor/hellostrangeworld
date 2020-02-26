package net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer;

import net.barakiroth.hellostrangeworld.common.IConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.IJettyManagerConfig;

public class JettyManagerConfig implements IJettyManagerConfig {
  
  private static final String JETTY_SERVER_PORT_KEY     = "jetty.port";
  private static final int    JETTY_SERVER_PORT_DEFAULT = 8079;

  private static final String JETTY_ROOT_CONTEXT_PATH_KEY = "jetty.root.path.spec";
  private static final String JETTY_ROOT_CONTEXT_PATH_DEFAULT = "/";
  
  private static final String JETTY_DEFAULT_CONTEXT_PATH_KEY = "jetty.default.path.spec";
  private static final String JETTY_DEFAULT_CONTEXT_PATH_DEFAULT = "/*";
  
  private static final String JETTY_METRICS_CONTEXT_PATH_KEY = "jetty.metrics.path.spec";
  private static final String JETTY_METRICS_CONTEXT_PATH_DEFAULT = "/internal/metrics"; 

  private static final String JETTY_RESOURCE_PATH_SPEC_KEY = "jetty.path.spec";
  private static final String JETTY_RESOURCE_PATH_SPEC_DEFAULT = "/someResource/*";
  
  private static JettyManagerConfig singletonInstance = null;
  
  private final IConfig config;
    
  public static IJettyManagerConfig getSingletonInstance() {
	  return JettyManagerConfig.singletonInstance;
  }

  private JettyManagerConfig(final IConfig config) {
    this.config = config;
  }
  
  public static IJettyManagerConfig createSingletonInstance(final IConfig config) {
    
    if (JettyManagerConfig.singletonInstance != null) {
      throw new IllegalStateException("Singleton already created");
    }
    JettyManagerConfig.singletonInstance = new JettyManagerConfig(config);
    return JettyManagerConfig.getSingletonInstance();
  }

  @Override
  public int getServerPort() {
    final int port = this.config.getInt(JETTY_SERVER_PORT_KEY, JETTY_SERVER_PORT_DEFAULT);
    return port;
  }
  
  @Override
  public String getResourcePathSpec() {
    final String contextPath = 
        this.config.getString(JETTY_RESOURCE_PATH_SPEC_KEY, JETTY_RESOURCE_PATH_SPEC_DEFAULT);
    return contextPath;
  }
  
  @Override
  public String getRootContextPath() {
    final String contextPath =
        this.config.getString(JETTY_ROOT_CONTEXT_PATH_KEY, JETTY_ROOT_CONTEXT_PATH_DEFAULT);
    return contextPath;
  }
  
  @Override
  public String getDefaultPathSpec() {
    final String contextPath =
        this.config.getString(JETTY_DEFAULT_CONTEXT_PATH_KEY, JETTY_DEFAULT_CONTEXT_PATH_DEFAULT);
    return contextPath;
  }
  
  @Override
  public String getMetricsContextPath() {
    final String contextPath =
        this.config.getString(JETTY_METRICS_CONTEXT_PATH_KEY, JETTY_METRICS_CONTEXT_PATH_DEFAULT);
    return contextPath;
  }
}