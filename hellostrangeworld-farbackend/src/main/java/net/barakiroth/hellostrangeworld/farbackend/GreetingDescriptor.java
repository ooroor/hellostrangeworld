package net.barakiroth.hellostrangeworld.farbackend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.barakiroth.hellostrangeworld.infrastructure.Database;

public class GreetingDescriptor {	

	private Database database = null;

	public String describeGreetee() {
		
		final String greeteeDescription = getDatabase().describeGreetee();
		final ObjectMapper objectMapper = new ObjectMapper();
		
		String greeteeDescriptionAsJson = null;
		try {
			greeteeDescriptionAsJson =
					objectMapper.writeValueAsString(greeteeDescription);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return greeteeDescriptionAsJson;
	}

	public void disconnect() {

		this.database.disconnect();
		this.database = null;
	}

	private Database getDatabase() {

		if (this.database == null) {
			this.database = new Database();
		}

		return this.database;
	}
}
