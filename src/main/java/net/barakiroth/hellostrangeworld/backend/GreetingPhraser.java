package net.barakiroth.hellostrangeworld.backend;

import java.text.MessageFormat;

import net.barakiroth.hellostrangeworld.farbackend.GreetingDescriptor;

public class GreetingPhraser {
	
	GreetingDescriptor greetingDescriptor = null;
	
	public String getGreetingPhrasePrefix() {
		
		final String description = getGreetingDescriptor().describeGreetee();
		return MessageFormat.format("Hello {0} ", description);
	}

	public void disconnect() {
		this.greetingDescriptor.disconnect();
		this.greetingDescriptor = null;
	}

	private GreetingDescriptor getGreetingDescriptor() {

		if (this.greetingDescriptor == null) {
			this.greetingDescriptor = new GreetingDescriptor();
		}

		return this.greetingDescriptor;
	}
}
