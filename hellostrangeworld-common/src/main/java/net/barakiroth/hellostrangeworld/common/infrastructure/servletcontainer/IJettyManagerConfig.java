package net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer;

public interface IJettyManagerConfig {
	int getServerPort();
	String getResourcePathSpec();
	String getRootContextPath();
	String getDefaultPathSpec();
	String getMetricsContextPath();
}