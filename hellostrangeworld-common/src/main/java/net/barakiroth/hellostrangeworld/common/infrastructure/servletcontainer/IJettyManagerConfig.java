package net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer;

public interface IJettyManagerConfig {

  int JETTY_SERVER_PORT_DEFAULT = 8079;
  String JETTY_ROOT_CONTEXT_PATH_DEFAULT = "/";
  String JETTY_DEFAULT_CONTEXT_PATH_DEFAULT = "/*";
  String JETTY_METRICS_CONTEXT_PATH_DEFAULT = "/internal/metrics";
  String JETTY_RESOURCE_PATH_SPEC_DEFAULT = "/someResource/*";
  boolean JETTY_SHOULD_REPORT_DETAILED_STARTUP_DEFAULT = false;
  
  String JERSEY_APPLICATION_CLASS_NAME_KEY = "jersey.application.class.name";
  String JETTY_SERVER_PORT_KEY = "jetty.port";
  String JETTY_ROOT_CONTEXT_PATH_KEY = "jetty.root.path.spec";
  String JETTY_DEFAULT_CONTEXT_PATH_KEY = "jetty.default.path.spec";
  String JETTY_METRICS_CONTEXT_PATH_KEY = "jetty.metrics.path.spec";
  String JETTY_RESOURCE_PATH_SPEC_KEY = "jetty.path.spec";
  String JETTY_SHOULD_REPORT_DETAILED_STARTUP_KEY = "jetty.shouldReportDetailedStartup";
  
  JettyManager getJettyManager();
  int getServerPort();
  String getResourcePathSpec();
  String getRootContextPath();
  String getDefaultPathSpec();
  String getMetricsContextPath();
  String getJerseyApplicationClassName();
  boolean shouldReportDetailedStartup();
}