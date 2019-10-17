package net.barakiroth.hellostrangeworld.farbackend.infrastructure;

public class HelloStrangeWorldFarBackendApp {
	
	private static final HelloStrangeWorldFarBackendApp helloStrangeworldFarBackendApp =
			new HelloStrangeWorldFarBackendApp();
	
	private static final JettyManager jettyManager =
		JettyManager.create();

	public static void main(final String[] args) {
		HelloStrangeWorldFarBackendApp.helloStrangeworldFarBackendApp.start();
	}

	static JettyManager getJettyManager() {
		return HelloStrangeWorldFarBackendApp.jettyManager;
	}
	
	private void start() {
		HelloStrangeWorldFarBackendApp.jettyManager.configureServletContainer();
		HelloStrangeWorldFarBackendApp.jettyManager.startServletContainer(); // Will be hanging here until the Jetty server stops.
	}
}