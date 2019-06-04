package net.barakiroth.hellostrangeworld.farbackend.infrastructure;

import lombok.AccessLevel;
import lombok.Getter;
import org.eclipse.jetty.server.Server;

public class JettyManager {
	
	private final Server jettyServletContainer;
	
	@Getter(AccessLevel.PACKAGE)
	private static final JettyManager instance = new JettyManager();
	
	private JettyManager() {
		final Server jettyServletContainer = new Server(8680);
		this.jettyServletContainer = jettyServletContainer;
	}
	
	boolean isStarted() {
		return this.jettyServletContainer.isStarted();
	}
	
	void startServletContainer() {
		
	    try {
			this.jettyServletContainer.start();
			this.jettyServletContainer.dumpStdErr();
			this.jettyServletContainer.join();
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}

	void stopServletContainer() {
		
		try {
			this.jettyServletContainer.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
