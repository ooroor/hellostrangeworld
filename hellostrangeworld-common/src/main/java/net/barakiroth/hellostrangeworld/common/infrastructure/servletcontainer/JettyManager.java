package net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer;

import io.prometheus.client.exporter.MetricsServlet;

import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.IJettyManagerConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyManager {

  private static final Logger log = LoggerFactory.getLogger(JettyManager.class);
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");

  private static JettyManager singleton = null;

  private final IJettyManagerConfig jettyManagerConfig;
  
  private Server jettyServer;

  private JettyManager(final IJettyManagerConfig jettyManagerConfig) {

    enteringMethodHeaderLogger.debug(null);

    this.jettyManagerConfig = jettyManagerConfig;

    leavingMethodHeaderLogger.debug(null);
  }
  
  private static JettyManager createJettyManager(final IJettyManagerConfig jettyManagerConfig) {
    return new JettyManager(jettyManagerConfig);
  }

  private static void setSingleton(final JettyManager jettyManager) {
    JettyManager.singleton = jettyManager;
  }

  public static JettyManager getSingleton(final IJettyManagerConfig jettyManagerConfig) {

    if (JettyManager.singleton == null) {
      final JettyManager jettyManager = JettyManager.createJettyManager(jettyManagerConfig);
      JettyManager.setSingleton(jettyManager);
    }
    return JettyManager.singleton;
  }

  /**
   * Configure and start the Jetty servlet container.
   */
  public void start() {

    enteringMethodHeaderLogger.debug(null);

    final Server jettyServer = configure();
    try {
      log.info("About to start the Jetty server ...");
      jettyServer.start();
      log.info("The Jetty server successfully started.");
    } catch (Exception e) {
      log.error("Exception received when trying to start the servlet container", e);
      throw new RuntimeException(e);
    }
    jettyServer.dumpStdErr();

    leavingMethodHeaderLogger.debug(null);
  }

  public boolean isStarted() {
    return getServer().isStarted();
  }

  /**
   * Stop the Jetty servlet container.
   */
  public void stop() {

    enteringMethodHeaderLogger.debug(null);

    try {
      getServer().stop();
    } catch (Exception e) {
      log.error("Exception received when trying to stop the servlet container", e);
      throw new RuntimeException(e);
    }

    leavingMethodHeaderLogger.debug(null);
  }

  int getServerPort(final IJettyManagerConfig jettyManagerConfig) {

    final int port = jettyManagerConfig.getServerPort();
    log.debug("port: {}", port);

    return port;
  }

  void setServer(final Server jettyServer) {
    this.jettyServer = jettyServer;
  }

  Server getServer() {

    if (this.jettyServer == null) {
      final Server jettyServer = new Server(getServerPort(getJettyManagerConfig()));
      setServer(jettyServer);
    }
    return this.jettyServer;
  }

  private void registerJerseyApplication(final IJettyManagerConfig jettyManagerConfig,
      final ServletContextHandler servletContextHandler) {

    enteringMethodHeaderLogger.debug(null);

    final String resourcePathSpec = getResourcePathSpec(jettyManagerConfig);
    final ServletHolder servletHolder = new ServletHolder(new ServletContainer());
    final String jerseyApplicationClassName = getJerseyApplicationClassName();
    servletHolder.setInitParameter("javax.ws.rs.Application", jerseyApplicationClassName);
    servletContextHandler.addServlet(servletHolder, resourcePathSpec);

    leavingMethodHeaderLogger.debug(null);
  }

  private Server configure() {

    enteringMethodHeaderLogger.debug(null);

    final String rootContextPath = getRootContextPath(getJettyManagerConfig());
    final ServletContextHandler servletContextHandler =
        new ServletContextHandler(getServer(), rootContextPath);

    registerDefaultServlet(getJettyManagerConfig(), servletContextHandler);

    // registerGreetingsDescriptorResourceServlet(jettyManagerConfig, servletContextHandler);
    registerJerseyApplication(getJettyManagerConfig(), servletContextHandler);
    registerMetricsServlet(getJettyManagerConfig(), servletContextHandler);

    getServer().setHandler(servletContextHandler);

    leavingMethodHeaderLogger.debug(null);

    return getServer();
  }

  private void registerDefaultServlet(final IJettyManagerConfig jettyManagerConfig,
      final ServletContextHandler servletContextHandler) {

    enteringMethodHeaderLogger.debug(null);

    final ServletHolder defaultServletHolder = new ServletHolder(new DefaultServlet());
    final String defaultPathSpec = getDefaultPathSpec(jettyManagerConfig);
    servletContextHandler.addServlet(defaultServletHolder, defaultPathSpec);

    leavingMethodHeaderLogger.debug(null);
  }

  private void registerMetricsServlet(final IJettyManagerConfig jettyManagerConfig,
      final ServletContextHandler servletContextHandler) {

    enteringMethodHeaderLogger.debug(null);

    final String metricsResourcePathSpec = getMetricsContextPath(jettyManagerConfig);
    final ServletHolder metricsServletHolder = new ServletHolder(new MetricsServlet());
    servletContextHandler.addServlet(metricsServletHolder, metricsResourcePathSpec);

    leavingMethodHeaderLogger.debug(null);
  }

  /*
   * private void registerGreetingsDescriptorResourceServlet( final JettyManagerConfig
   * jettyManagerConfig, final ServletContextHandler servletContextHandler) {
   * 
   * enteringMethodHeaderLogger.debug(null);
   * 
   * final String modifierResourcePathSpec =
   * this.jettyManagerConfig.getModifierResourcePathSpec(); final ServletHolder
   * servletHolder = servletContextHandler.addServlet( ServletContainer.class,
   * greetingsDescriptorResourcePathSpec); servletHolder.setInitParameter(
   * "jersey.config.server.provider.classnames",
   * ModifierResource.class.getCanonicalName());
   * 
   * leavingMethodHeaderLogger.debug(null); }
   */

  private IJettyManagerConfig getJettyManagerConfig() {
    return this.jettyManagerConfig;
  };

  private String getRootContextPath(final IJettyManagerConfig jettyManagerConfig) {

    final String rootContextPath = jettyManagerConfig.getRootContextPath();
    log.debug("rootContextPath: {}", rootContextPath);

    return rootContextPath;
  }

  private String getMetricsContextPath(final IJettyManagerConfig jettyManagerConfig) {

    final String metricsResourcePathSpec = jettyManagerConfig.getMetricsContextPath();
    log.debug("metricsResourcePathSpec: {}", metricsResourcePathSpec);

    return metricsResourcePathSpec;
  }

  private String getResourcePathSpec(final IJettyManagerConfig jettyManagerConfig) {

    final String resourcePathSpec = jettyManagerConfig.getResourcePathSpec();
    log.debug("resourcePathSpec: {}", resourcePathSpec);

    return resourcePathSpec;
  }

  private String getDefaultPathSpec(final IJettyManagerConfig jettyManagerConfig) {

    final String defaultPathSpec = jettyManagerConfig.getDefaultPathSpec();
    log.debug("defaultPathSpec: {}", defaultPathSpec);

    return defaultPathSpec;
  }

  private String getJerseyApplicationClassName() {
    return getJettyManagerConfig().getJerseyApplicationClassName();
  }
}
