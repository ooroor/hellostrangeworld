package net.barakiroth.hellostrangeworld.backend;

import net.barakiroth.hellostrangeworld.common.ICommonConfig;
import net.barakiroth.hellostrangeworld.common.IGeneralConfig;

public interface IBackendConfig extends IGeneralConfig, ICommonConfig {

	final String DOWNSTREAM_RESOURCE_ENDPOINT_PROTOCOL_KEY = "downstream.resource.endpoint.protocol";
	final String DOWNSTREAM_RESOURCE_ENDPOINT_HOSTNAME_KEY = "downstream.resource.endpoint.hostname";
	final String DOWNSTREAM_RESOURCE_ENDPOINT_PORT_KEY = "downstream.resource.endpoint.port";
	final String DOWNSTREAM_RESOURCE_ENDPOINT_CONTEXT_KEY = "downstream.resource.endpoint.context";

	default String getModifierResourceEndpointUriString() {
		return getRequiredString(DOWNSTREAM_RESOURCE_ENDPOINT_PROTOCOL_KEY) + "://"
				+ getRequiredString(DOWNSTREAM_RESOURCE_ENDPOINT_HOSTNAME_KEY) + ":"
				+ getRequiredInt(DOWNSTREAM_RESOURCE_ENDPOINT_PORT_KEY) + "/"
				+ getRequiredString(DOWNSTREAM_RESOURCE_ENDPOINT_CONTEXT_KEY);
	}
}