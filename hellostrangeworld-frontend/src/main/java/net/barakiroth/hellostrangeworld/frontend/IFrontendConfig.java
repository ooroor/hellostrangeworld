package net.barakiroth.hellostrangeworld.frontend;

import net.barakiroth.hellostrangeworld.common.IConfig;
import net.barakiroth.hellostrangeworld.frontend.consumer.InitialPartConsumer;

public interface IFrontendConfig extends IConfig {

	final String DOWNSTREAM_RESOURCE_ENDPOINT_PROTOCOL_KEY = "downsream.resource.endpoint.protocol";
	final String DOWNSTREAM_RESOURCE_ENDPOINT_HOSTNAME_KEY = "downsream.resource.endpoint.hostname";
	final String DOWNSTREAM_RESOURCE_ENDPOINT_PORT_KEY = "downsream.resource.endpoint.port";
	final String DOWNSTREAM_RESOURCE_ENDPOINT_CONTEXT_KEY = "downsream.resource.endpoint.context";

	default String getDownstreamResourceEndpointUriString() {
		return getRequiredString(DOWNSTREAM_RESOURCE_ENDPOINT_PROTOCOL_KEY) + "://"
				+ getRequiredString(DOWNSTREAM_RESOURCE_ENDPOINT_HOSTNAME_KEY) + ":"
				+ getRequiredInt(DOWNSTREAM_RESOURCE_ENDPOINT_PORT_KEY) + "/"
				+ getRequiredString(DOWNSTREAM_RESOURCE_ENDPOINT_CONTEXT_KEY);
	}
	
	GreeteePrompter getGreeteePrompter();
	
	InitialPartConsumer getInitialPartConsumer();	
}