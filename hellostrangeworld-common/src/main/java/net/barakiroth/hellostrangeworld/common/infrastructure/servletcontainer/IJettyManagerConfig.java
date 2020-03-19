package net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer;

public interface IJettyManagerConfig {
  
  JettyManager getJettyManager();

  int getServerPort();

  String getResourcePathSpec();

  String getRootContextPath();

  String getDefaultPathSpec();

  String getMetricsContextPath();

  String getJerseyApplicationClassName();
}