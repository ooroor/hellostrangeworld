package net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer;

import io.prometheus.client.exporter.MetricsServlet;
import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.IJettyManagerConfig;
import net.barakiroth.hellostrangeworld.farbackend.IFarBackendConfig;
import net.barakiroth.hellostrangeworld.farbackend.JerseyApplication;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer.JettyManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyManager {

	private static final Logger log = LoggerFactory.getLogger(JettyManager.class);
	private static final Logger enteringMethodHeaderLogger = LoggerFactory.getLogger("EnteringMethodHeader");
	private static final Logger leavingMethodHeaderLogger = LoggerFactory.getLogger("LeavingMethodHeader");

	@Getter(AccessLevel.PUBLIC)
	private static JettyManager singletonInstance = null;

	private final IJettyManagerConfig jettyManagerConfig;
	private final Server jettyServer;

	private JettyManager(final IFarBackendConfig config) {

		enteringMethodHeaderLogger.debug(null);

		this.jettyManagerConfig = config.getJettyManagerConfig();

		final int serverPort = getServerPort(this.jettyManagerConfig);
		this.jettyServer = new Server(serverPort);

		leavingMethodHeaderLogger.debug(null);
	}

	public static JettyManager createSingletonInstance(final IFarBackendConfig config) {

		if (JettyManager.singletonInstance != null) {
			throw new IllegalStateException("Singleton already created");
		}
		JettyManager.singletonInstance = new JettyManager(config);

		return JettyManager.getSingletonInstance();
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
			throw new RuntimeException(e);
		}

		leavingMethodHeaderLogger.debug(null);
	}

	private Server configure() {

		enteringMethodHeaderLogger.debug(null);

		final String rootContextPath = getRootContextPath(this.jettyManagerConfig);
		final ServletContextHandler servletContextHandler =
			new ServletContextHandler(
				this.jettyServer,
				rootContextPath);

		registerDefaultServlet(this.jettyManagerConfig, servletContextHandler);

		// registerGreetingsDescriptorResourceServlet(jettyManagerConfig,
		// servletContextHandler);
		registerJerseyApplication(this.jettyManagerConfig, servletContextHandler);
		registerMetricsServlet(this.jettyManagerConfig, servletContextHandler);

		this.jettyServer.setHandler(servletContextHandler);

		leavingMethodHeaderLogger.debug(null);

		return this.jettyServer;
	}

	void registerJerseyApplication(
			final IJettyManagerConfig   jettyManagerConfig,
			final ServletContextHandler servletContextHandler) {

		enteringMethodHeaderLogger.debug(null);

		final String resourcePathSpec = getResourcePathSpec(jettyManagerConfig);
		final ServletHolder servletHolder = new ServletHolder(new ServletContainer());
		final String greetingDescriptorJerseyApplicationClassName = JerseyApplication.class.getName();
		servletHolder.setInitParameter("javax.ws.rs.Application", greetingDescriptorJerseyApplicationClassName);
		servletContextHandler.addServlet(servletHolder, resourcePathSpec);

		leavingMethodHeaderLogger.debug(null);
	}

	private void registerDefaultServlet(
			final IJettyManagerConfig jettyManagerConfig,
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
	 * private void registerGreetingsDescriptorResourceServlet( final
	 * JettyManagerConfig jettyManagerConfig, final ServletContextHandler
	 * servletContextHandler) {
	 * 
	 * enteringMethodHeaderLogger.debug(null);
	 * 
	 * final String greetingsDescriptorResourcePathSpec =
	 * this.jettyManagerConfig.getGreetingsDescriptorResourcePathSpec(); final
	 * ServletHolder servletHolder = servletContextHandler.addServlet(
	 * ServletContainer.class, greetingsDescriptorResourcePathSpec);
	 * servletHolder.setInitParameter( "jersey.config.server.provider.classnames",
	 * GreetingDescriptionResource.class.getCanonicalName());
	 * 
	 * leavingMethodHeaderLogger.debug(null); }
	 */

	private String getRootContextPath(final IJettyManagerConfig jettyManagerConfig) {

		final String rootContextPath = jettyManagerConfig.getRootContextPath();
		log.debug("rootContextPath: {}", rootContextPath);

		return rootContextPath;
	}

	private int getServerPort(final IJettyManagerConfig jettyManagerConfig) {

		final int port = jettyManagerConfig.getServerPort();
		log.debug("port: {}", port);

		return port;
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

}