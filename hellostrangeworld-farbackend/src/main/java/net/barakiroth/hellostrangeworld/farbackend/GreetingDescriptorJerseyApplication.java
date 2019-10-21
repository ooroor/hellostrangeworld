package net.barakiroth.hellostrangeworld.farbackend;

import org.glassfish.jersey.server.ResourceConfig;

import net.barakiroth.hellostrangeworld.farbackend.greetingdescriptor.GreetingDescriptorResource;

@SuppressWarnings("WeakerAccess") //Påkrevd public
public class GreetingDescriptorJerseyApplication extends ResourceConfig {
	
	private final Config config;
	
	public GreetingDescriptorJerseyApplication() {
		
		final Config config = Config.getSingletonInstance();
		registerGreetingDescriptorApiResources(config);
		this.config = config;
	}
	
	private void registerGreetingDescriptorApiResources(final Config config) {
		register(new GreetingDescriptorResource(config));
	}
}
