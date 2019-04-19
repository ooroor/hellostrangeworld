package net.barakiroth.hellostrangeworld.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.jdbc.JdbcSQLNonTransientConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.barakiroth.hellostrangeworld.HelloStrangeWorldApp;

public class Database {

	static private final Logger logger = LoggerFactory.getLogger(HelloStrangeWorldApp.class);

	private static final String DB_DRIVER_CLASS_NAME = "org.h2.Driver";
	private static final String DB_SCHEMA_NAME = "test";
	private static final String DB_DSNAME_NOT_EXISTING = "jdbc:h2:mem:" + DB_SCHEMA_NAME + ";DB_CLOSE_DELAY=-1";
	private static final String DB_DSNAME_EXISTING = DB_DSNAME_NOT_EXISTING + ";IFEXISTS=TRUE";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	Connection connection = null;
	
	public Database() {
	}

	public String describeGreetee() {

		try {
			final Statement stmt = getDBConnection().createStatement();
			final ResultSet rs = stmt.executeQuery("SELECT * FROM description WHERE id = 1");
			rs.next();
			final String greeteeDescription = rs.getString("value");
			stmt.close();
			return greeteeDescription;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void disconnect() {
		try {
			getDBConnection().close();
			this.connection = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Connection getDBConnection() {

		if (this.connection == null) {
			try {
				Class.forName(DB_DRIVER_CLASS_NAME);
				try
				{
					this.connection = DriverManager.getConnection(DB_DSNAME_EXISTING, DB_USER, DB_PASSWORD);
				} catch(final JdbcSQLNonTransientConnectionException e) {
					this.connection = DriverManager.getConnection(DB_DSNAME_NOT_EXISTING, DB_USER, DB_PASSWORD);
					createAndPopulate();
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}

		return this.connection;
	}

	private void createAndPopulate() {

		try {
			this.connection.setAutoCommit(true);
			final Statement stmt = connection.createStatement();
			stmt.execute("CREATE TABLE description(id INT PRIMARY KEY, value VARCHAR(255))");
			stmt.execute("INSERT INTO description(id, value) VALUES(1, 'strange')");
			final ResultSet rs = stmt.executeQuery("SELECT * FROM description");
			System.out.println("H2 In-Memory Database inserted through Statement");
			while (rs.next()) {
				logger.debug("Id: {}, value: {}", rs.getInt("id"), rs.getString("value"));
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}