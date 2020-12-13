package net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer;

import net.barakiroth.hellostrangeworld.common.IGeneralConfig;

public class JettyManagerConfig implements IJettyManagerConfig {
  
  private static IJettyManagerConfig singleton = null;

  private final IGeneralConfig generalConfig;
  
  private JettyManagerConfig(final IGeneralConfig generalConfig) {
    this.generalConfig = generalConfig;
  }

  public static void createAndSetSingleton(final IGeneralConfig generalConfig) {
    final IJettyManagerConfig jettyManagerConfig = JettyManagerConfig.createSingleton(generalConfig);
    JettyManagerConfig.setSingleton(jettyManagerConfig);
  }

  public static IJettyManagerConfig getSingleton() {
    return JettyManagerConfig.singleton;
  }

  private static void setSingleton(final IJettyManagerConfig jettyManagerConfig) {
    JettyManagerConfig.singleton = jettyManagerConfig;
  }

  private static IJettyManagerConfig createSingleton(final IGeneralConfig generalConfig) {
    return new JettyManagerConfig(generalConfig);
  }

  @Override
  public JettyManager getJettyManager() {

    if (JettyManager.getSingleton() == null) {
      createJettyManager();
    };
    return JettyManager.getSingleton();
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

  private void createJettyManager() {
    JettyManager.createAndSetSingleton(this);
  }

  private IGeneralConfig getGeneralConfig() {
    return this.generalConfig;
  }
}