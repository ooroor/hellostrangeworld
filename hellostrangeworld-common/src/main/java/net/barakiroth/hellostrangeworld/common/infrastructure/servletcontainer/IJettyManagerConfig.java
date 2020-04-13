package net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer;

import net.barakiroth.hellostrangeworld.common.IGeneralConfig;

public interface IJettyManagerConfig {
  
  JettyManager getJettyManager(final IGeneralConfig generalConfig);
  int getServerPort();
  String getResourcePathSpec();
  String getRootContextPath();
  String getDefaultPathSpec();
  String getMetricsContextPath();
  String getJerseyApplicationClassName();
}