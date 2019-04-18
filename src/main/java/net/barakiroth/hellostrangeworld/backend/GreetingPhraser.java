package net.barakiroth.hellostrangeworld.backend;

import java.text.MessageFormat;

import net.barakiroth.hellostrangeworld.farbackend.GreetingDescriptor;

public class GreetingPhraser {
	
	public String getGreetingPhrasePrefix() {
		
		final GreetingDescriptor greetingDescriptor = new GreetingDescriptor();
		final String description = greetingDescriptor.describeGreetee();
		return MessageFormat.format("Hello {0} ", description);
	}
}
