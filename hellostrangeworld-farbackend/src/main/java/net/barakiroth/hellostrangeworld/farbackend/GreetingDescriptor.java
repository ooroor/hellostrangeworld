package net.barakiroth.hellostrangeworld.farbackend;

import net.barakiroth.hellostrangeworld.infrastructure.Database;

public class GreetingDescriptor {	

	private Database database = null;

	public String describeGreetee() {
		return getDatabase().describeGreetee();
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
