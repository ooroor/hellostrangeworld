package net.barakiroth.hellostrangeworld.backend;

import net.barakiroth.hellostrangeworld.common.IConfig;
import net.barakiroth.hellostrangeworld.backend.infrastructure.servletcontainer.JettyManager;
import net.barakiroth.hellostrangeworld.backend.infrastructure.servletcontainer.JettyManagerConfig;
import net.barakiroth.hellostrangeworld.backend.infrastructure.prometheus.PrometheusConfig;

public interface IBackendConfig extends IConfig {

	public final String DOWNSTREAM_RESOURCE_ENDPOINT_PROTOCOL_KEY = "downstream.resource.endpoint.protocol";
	public final String DOWNSTREAM_RESOURCE_ENDPOINT_HOSTNAME_KEY = "downstream.resource.endpoint.hostname";
	public final String DOWNSTREAM_RESOURCE_ENDPOINT_PORT_KEY = "downstream.resource.endpoint.port";
	public final String DOWNSTREAM_RESOURCE_ENDPOINT_CONTEXT_KEY = "downstream.resource.endpoint.context";

	JettyManager getJettyManager();

	JettyManagerConfig getJettyManagerConfig();

	PrometheusConfig getPrometheusConfig();

	default String getModifierResourceEndpointUriString() {
		return getRequiredString(DOWNSTREAM_RESOURCE_ENDPOINT_PROTOCOL_KEY) + "://"
				+ getRequiredString(DOWNSTREAM_RESOURCE_ENDPOINT_HOSTNAME_KEY) + ":"
				+ getRequiredInt(DOWNSTREAM_RESOURCE_ENDPOINT_PORT_KEY) + "/"
				+ getRequiredString(DOWNSTREAM_RESOURCE_ENDPOINT_CONTEXT_KEY);
	}
}