package net.barakiroth.hellostrangeworld.farbackend.infrastructure;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import net.barakiroth.hellostrangeworld.farbackend.GreetingDescriptorResource;

public class JettyManager {
	
	private final Server server = new Server(8680);
	
	static JettyManager create() {
		return new JettyManager();
	}
	
	private JettyManager() {}

	public void configureServletContainer() {

		final ServletContextHandler servletContextHandler = 
                new ServletContextHandler(server, "/*");
		servletContextHandler.setContextPath("/*");
        this.server.setHandler(servletContextHandler);
        final ServletHolder servletHolder = 
        	servletContextHandler.addServlet(ServletContainer.class, "/rest/*");
        servletHolder.setInitOrder(1);
        
        servletHolder.setInitParameter(
                "jersey.config.server.provider.classnames",
                GreetingDescriptorResource.class.getCanonicalName());
	}
	
	boolean isStarted() {
		return this.server.isStarted();
	}
	
	void startServletContainer() {
		
	    try {
			this.server.start();
			this.server.dumpStdErr();
			this.server.join();
	    } catch (Exception e) {
			e.printStackTrace();
		} finally {
			// this.server.destroy();
        }
	}

	void stopServletContainer() {
		
		try {
			this.server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}