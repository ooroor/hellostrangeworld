package net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer;

import net.barakiroth.hellostrangeworld.common.IGeneralConfig;

public interface IJettyManagerConfig {

  static final int JETTY_SERVER_PORT_DEFAULT = 8079;
  static final String JETTY_ROOT_CONTEXT_PATH_DEFAULT = "/";
  static final String JETTY_DEFAULT_CONTEXT_PATH_DEFAULT = "/*";
  static final String JETTY_METRICS_CONTEXT_PATH_DEFAULT = "/internal/metrics";
  static final String JETTY_RESOURCE_PATH_SPEC_DEFAULT = "/someResource/*";
  
  static final String JERSEY_APPLICATION_CLASS_NAME_KEY = "jersey.application.class.name";
  static final String JETTY_SERVER_PORT_KEY = "jetty.port";
  static final String JETTY_ROOT_CONTEXT_PATH_KEY = "jetty.root.path.spec";
  static final String JETTY_DEFAULT_CONTEXT_PATH_KEY = "jetty.default.path.spec";
  static final String JETTY_METRICS_CONTEXT_PATH_KEY = "jetty.metrics.path.spec";
  static final String JETTY_RESOURCE_PATH_SPEC_KEY = "jetty.path.spec";
  
  JettyManager getJettyManager(final IGeneralConfig generalConfig);
  int getServerPort();
  String getResourcePathSpec();
  String getRootContextPath();
  String getDefaultPathSpec();
  String getMetricsContextPath();
  String getJerseyApplicationClassName();
}