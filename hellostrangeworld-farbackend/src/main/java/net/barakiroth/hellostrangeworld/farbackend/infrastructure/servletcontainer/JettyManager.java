package net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer;

import net.barakiroth.hellostrangeworld.farbackend.Config;
import net.barakiroth.hellostrangeworld.farbackend.JerseyApplication;
import net.barakiroth.hellostrangeworld.farbackend.domain.GreetingDescriptionResource;
import net.barakiroth.hellostrangeworld.farbackend.util.ExceptionSoftener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyManager {
  
  private static final Logger log =
      LoggerFactory.getLogger(JettyManager.class);
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");

  private final JettyManagerConfig jettyManagerConfig;
  private final Server             jettyServer;
  
  /**
   * Configures and starts the Jetty servlet container .
   * @param config The application global configuration "central".
   */
  public JettyManager(final Config config) {
    
    enteringMethodHeaderLogger.debug(null);
    
    this.jettyManagerConfig = config.getJettyManagerConfig();
    
    final int serverPort = getServerPort(config);
    this.jettyServer = new Server(serverPort);
    
    leavingMethodHeaderLogger.debug(null);
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
      ExceptionSoftener.uncheck(e);
    }
    jettyServer.dumpStdErr();
    
    leavingMethodHeaderLogger.debug(null);
  }
  
  public boolean isStarted() {
    return this.jettyServer.isStarted();
  }

  /**
   * Stop the Jetty servlet container.
   */
  public void stop() {
    
    enteringMethodHeaderLogger.debug(null);
    
    try {
      this.jettyServer.stop();
    } catch (Exception e) {
      log.error("Exception received when trying to stop the servlet container", e);
      ExceptionSoftener.uncheck(e);
    }
    
    leavingMethodHeaderLogger.debug(null);
  }

  private Server configure() {
    
    enteringMethodHeaderLogger.debug(null);
    
    final String rootContextPath = getRootContextPath(this.jettyManagerConfig);
    final ServletContextHandler servletContextHandler =
        new ServletContextHandler(this.jettyServer, rootContextPath);
    
    //registerGreetingsDescriptorResourceServlet(jettyManagerConfig, servletContextHandler);
    registerGreetingDescriptorJerseyApplication(jettyManagerConfig, servletContextHandler);
    
    this.jettyServer.setHandler(servletContextHandler);
    
    leavingMethodHeaderLogger.debug(null);
    
    return this.jettyServer;
  }

  void registerGreetingDescriptorJerseyApplication(
      final JettyManagerConfig    jettyManagerConfig, 
      final ServletContextHandler servletContextHandler) {
    
    enteringMethodHeaderLogger.debug(null);
    
    final String greetingsDescriptorResourcePathSpec =
          this.jettyManagerConfig.getGreetingsDescriptorResourcePathSpec();
    
    final ServletHolder servletHolder = new ServletHolder(new ServletContainer());
    final String greetingDescriptorJerseyApplicationClassName = 
        JerseyApplication.class.getName();
    servletHolder.setInitParameter(
        "javax.ws.rs.Application", 
        greetingDescriptorJerseyApplicationClassName);
    servletContextHandler.addServlet(servletHolder, greetingsDescriptorResourcePathSpec);
    
    leavingMethodHeaderLogger.debug(null);
  }

  private void registerGreetingsDescriptorResourceServlet(
      final JettyManagerConfig jettyManagerConfig, 
      final ServletContextHandler servletContextHandler) {

    enteringMethodHeaderLogger.debug(null);
    
    final String greetingsDescriptorResourcePathSpec =
        this.jettyManagerConfig.getGreetingsDescriptorResourcePathSpec();
    final ServletHolder servletHolder = 
        servletContextHandler.addServlet(
            ServletContainer.class, 
            greetingsDescriptorResourcePathSpec);
    servletHolder.setInitParameter(
            "jersey.config.server.provider.classnames",
            GreetingDescriptionResource.class.getCanonicalName());
  
    leavingMethodHeaderLogger.debug(null);
  }

  private String getRootContextPath(final JettyManagerConfig jettyManagerConfig) {
    
    final String rootContextPath = this.jettyManagerConfig.getRootContextPath();
    
    return rootContextPath;
  }

  private int getServerPort(final Config config) {
    
    final int port = this.jettyManagerConfig.getServerPort();
    
    return port;
  }
}