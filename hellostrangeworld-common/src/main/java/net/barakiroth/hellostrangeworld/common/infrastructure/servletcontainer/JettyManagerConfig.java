package net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer;

import net.barakiroth.hellostrangeworld.common.IGeneralConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.IJettyManagerConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.JettyManager;

public class JettyManagerConfig implements IJettyManagerConfig {
  
  private static       IJettyManagerConfig singleton = null;

  private final IGeneralConfig generalConfig;
  private       JettyManager   jettyManager;
  
  private JettyManagerConfig(final IGeneralConfig generalConfig) {
    this.generalConfig = generalConfig;
  }

  public static IJettyManagerConfig getSingleton(final IGeneralConfig generalConfig) {
    
    if (JettyManagerConfig.singleton == null) {
      final IJettyManagerConfig singleton = createsingleton(generalConfig);
      setsingleton(singleton);
    }
    return JettyManagerConfig.singleton;
  }

  private static void setsingleton(final IJettyManagerConfig jettyManagerConfig) {
    JettyManagerConfig.singleton = jettyManagerConfig;
  }

  private static IJettyManagerConfig createsingleton(final IGeneralConfig generalConfig) {
    return new JettyManagerConfig(generalConfig);
  }
  
  private static JettyManager createJettyManager(final IGeneralConfig generalConfig) {
    return JettyManager.getSingleton(JettyManagerConfig.getSingleton(generalConfig));
  }
  
  private IGeneralConfig getGeneralConfig() {
    return this.generalConfig;
  }

  @Override
  public JettyManager getJettyManager(final IGeneralConfig generalConfig) {
    if (this.jettyManager == null) {
      final JettyManager jettyManager = createJettyManager(generalConfig);
      setJettyManager(jettyManager);
    }
    return this.jettyManager;
  }

  @Override
  public int getServerPort() {
    final int port = getGeneralConfig().getInt(IJettyManagerConfig.JETTY_SERVER_PORT_KEY, JETTY_SERVER_PORT_DEFAULT);
    return port;
  }

  @Override
  public String getResourcePathSpec() {
    final String contextPath =
        getGeneralConfig().getString(IJettyManagerConfig.JETTY_RESOURCE_PATH_SPEC_KEY, JETTY_RESOURCE_PATH_SPEC_DEFAULT);
    return contextPath;
  }

  @Override
  public String getRootContextPath() {
    final String contextPath =
        getGeneralConfig().getString(IJettyManagerConfig.JETTY_ROOT_CONTEXT_PATH_KEY, JETTY_ROOT_CONTEXT_PATH_DEFAULT);
    return contextPath;
  }

  @Override
  public String getDefaultPathSpec() {
    final String contextPath =
        getGeneralConfig().getString(IJettyManagerConfig.JETTY_DEFAULT_CONTEXT_PATH_KEY, JETTY_DEFAULT_CONTEXT_PATH_DEFAULT);
    return contextPath;
  }
  
  @Override
  public String getMetricsContextPath() {
    final String contextPath =
        getGeneralConfig().getString(IJettyManagerConfig.JETTY_METRICS_CONTEXT_PATH_KEY, JETTY_METRICS_CONTEXT_PATH_DEFAULT);
    return contextPath;
  }
  
  @Override
  public String getJerseyApplicationClassName() {
    final String jerseyApplicationClassName =
        getGeneralConfig().getRequiredString(IJettyManagerConfig.JERSEY_APPLICATION_CLASS_NAME_KEY);
    return jerseyApplicationClassName;
  }
  
  private void setJettyManager(final JettyManager jettyManager) {
    this.jettyManager = jettyManager;
  }
}



