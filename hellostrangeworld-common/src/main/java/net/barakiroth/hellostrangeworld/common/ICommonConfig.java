package net.barakiroth.hellostrangeworld.common;

import net.barakiroth.hellostrangeworld.common.infrastructure.prometheus.IPrometheusConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.IJettyManagerConfig;

public interface ICommonConfig {
	  IJettyManagerConfig getJettyManagerConfig();
	  IPrometheusConfig getPrometheusConfig();
}