package net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.barakiroth.hellostrangeworld.farbackend.Config;
import net.barakiroth.hellostrangeworld.farbackend.greetingdescriptor.GreetingDescriptorResource;
import net.barakiroth.hellostrangeworld.farbackend.util.ExceptionSoftener;

public class JettyManager {
	
    private static final Logger log = LoggerFactory.getLogger(JettyManager.class);
    private static final Logger enteringMethodHeaderLogger = LoggerFactory.getLogger("EnteringMethodHeader");
    private static final Logger leavingMethodHeaderLogger = LoggerFactory.getLogger("LeavingMethodHeader");
	
    private final JettyManagerConfig jettyManagerConfig;
	private final Server             jettyServer;
	
	public JettyManager(final Config config) {
		
		enteringMethodHeaderLogger.debug(null);
		
		this.jettyManagerConfig = config.getJettyManagerConfig();
		
		final int serverPort = getServerPort(config);
		this.jettyServer = new Server(serverPort);
		
		leavingMethodHeaderLogger.debug(null);
	}
	
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
		
		registerGreetingsDescriptorResourceServlet(jettyManagerConfig, servletContextHandler);
		
        this.jettyServer.setHandler(servletContextHandler);
		
		leavingMethodHeaderLogger.debug(null);
		
		return this.jettyServer;
	}
	
    private void registerGreetingsDescriptorResourceServlet(final JettyManagerConfig jettyManagerConfig, final ServletContextHandler servletContextHandler) {

        enteringMethodHeaderLogger.debug(null);
        
        final String greetingsDescriptorResourcePathSpec =
        		this.jettyManagerConfig.getGreetingsDescriptorResourcePathSpec();
        final ServletHolder servletHolder = 
        	servletContextHandler.addServlet(ServletContainer.class, greetingsDescriptorResourcePathSpec);
        servletHolder.setInitParameter(
                "jersey.config.server.provider.classnames",
                GreetingDescriptorResource.class.getCanonicalName());

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