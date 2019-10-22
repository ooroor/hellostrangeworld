package net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer;

import net.barakiroth.hellostrangeworld.farbackend.Config;

public class JettyManagerConfig {
  
  private static final String JETTY_SERVER_PORT_KEY     = "jetty.port";
  private static final int    JETTY_SERVER_PORT_DEFAULT = 8080;

  private static final String JETTY_ROOT_CONTEXT_PATH_KEY = "jetty.root.context.path";
  private static final String JETTY_ROOT_CONTEXT_PATH_DEFAULT = "/";

  private static final String JETTY_GREETINGS_DESCRIPTOR_RESOURCE_PATH_SPEC_KEY = 
      "jetty.greetings.descriptor.path.spec";
  private static final String JETTY_GREETINGS_DESCRIPTOR_RESOURCE_PATH_SPEC_DEFAULT = 
      "/greetings/*";
  
  private final Config config;

  public JettyManagerConfig(final Config config) {
    this.config = config;
  }

  int getServerPort() {
    final int port = this.config.getInteger(JETTY_SERVER_PORT_KEY, JETTY_SERVER_PORT_DEFAULT);
    return port;
  }

  String getGreetingsDescriptorResourcePathSpec() {
    final String contextPath = 
        this.config.getString(
            JETTY_GREETINGS_DESCRIPTOR_RESOURCE_PATH_SPEC_KEY, 
            JETTY_GREETINGS_DESCRIPTOR_RESOURCE_PATH_SPEC_DEFAULT);
    return contextPath;
  }

  String getRootContextPath() {
    final String contextPath =
        this.config.getString(JETTY_ROOT_CONTEXT_PATH_KEY, JETTY_ROOT_CONTEXT_PATH_DEFAULT);
    return contextPath;
  }
}