package net.barakiroth.hellostrangeworld.backend;

import java.io.IOException;
import java.text.MessageFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.barakiroth.hellostrangeworld.farbackend.GreetingDescriptor;

public class GreetingPhraser {
	
	GreetingDescriptor greetingDescriptor = null;
	
	public String getGreetingPhrasePrefix() {
		
		final String descriptionJson = getGreetingDescriptor().describeGreetee();
		final ObjectMapper objectMapper = new ObjectMapper();
		String description = null;
		try {
			description = objectMapper.readValue(descriptionJson, String.class);
		} catch (IOException e) {
			e.printStackTrace();
		}  
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
