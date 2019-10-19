package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.h2.jdbc.JdbcSQLNonTransientConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.barakiroth.hellostrangeworld.farbackend.Config;
import net.barakiroth.hellostrangeworld.farbackend.util.ExceptionSoftener;

public class Database {

	static private final Logger log = LoggerFactory.getLogger(Database.class);
	private static final Logger enteringMethodHeaderLogger = LoggerFactory.getLogger("EnteringMethodHeader");
    private static final Logger leavingMethodHeaderLogger = LoggerFactory.getLogger("LeavingMethodHeader");
	
	private DatabaseConfig databaseConfig;
	private Connection     connection = null;
	
	public Database(final Config config) {
		this.databaseConfig = config.getDatabaseConfig();
	}

	public void start() {
		
		enteringMethodHeaderLogger.debug(null);
		
		this.connection = configure();
		
		leavingMethodHeaderLogger.debug(null);
	}

	public String describeGreetee() {

        enteringMethodHeaderLogger.debug(null);
        
        assert(this.connection != null);

        String greeteeDescription = null;
		try {
			final Statement stmt = this.connection.createStatement();
			final ResultSet rs =
				stmt.executeQuery(
					  "SELECT * FROM description WHERE id = "
					+ String.valueOf((Math.abs(new Random().nextInt()) % 2) + 1)
				);
			rs.next();
			greeteeDescription = rs.getString("value");
			stmt.close();
		} catch (SQLException e) {
			log.error("Exception received when trying to retrieve data from the database", e);
			ExceptionSoftener.uncheck(e);
		}
		
		leavingMethodHeaderLogger.debug(null);
		
		return greeteeDescription;
	}
	
	public boolean isStarted() {
		return this.connection != null;
	}

	public void stop() {

        enteringMethodHeaderLogger.debug(null);
        
        if (this.connection == null) {
        	log.warn("Asked to disconnect when not connected (connection == null).");
        } else {            
    		try {
    			this.connection.close();
    			this.connection = null;
    		} catch (SQLException e) {
    			log.error("Exception received when trying to disconnect from the database", e);
    			ExceptionSoftener.uncheck(e);
    		}
        }

        leavingMethodHeaderLogger.debug(null);
	}

	private Connection configure() {

        enteringMethodHeaderLogger.debug(null);
        
        final Connection connection = createDBConnection();

		createAndPopulate(connection);

        leavingMethodHeaderLogger.debug(null);

		return connection;
	}

	private Connection createDBConnection() {

        enteringMethodHeaderLogger.debug(null);
        
        Connection connection = null;
		try {
			Class.forName(getDriverClassName());
			try
			{
				connection = DriverManager.getConnection(getDataSourceForExistingSchema(), getUser(), getPwd());
			} catch(final JdbcSQLNonTransientConnectionException e) {
				connection = DriverManager.getConnection(getDataSourceForNonExistingSchema(), getUser(), getPwd());
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error("Exception received when trying to get a database connection.", e);
			ExceptionSoftener.uncheck(e);
		}

        leavingMethodHeaderLogger.debug(null);

		return connection;
	}

	private void createAndPopulate(final Connection connection) {

        enteringMethodHeaderLogger.debug(null);

		try {
			connection.setAutoCommit(true);
			final Statement stmt = connection.createStatement();
			try {
				stmt.execute("SELECT * FROM description WHERE id != id");
				log.info("The database table description already exists, it will hence not be neither created nor populated");
			} catch (Throwable e) {
				log.info("Exception received when trying to select from the table description. That is taken as an indication that the table does not exists. Exception msg: {}. About to try to create and populate it...", e.getMessage());
				stmt.execute("CREATE TABLE description(id INT PRIMARY KEY, value VARCHAR(255))");
				stmt.execute("INSERT INTO description(id, value) VALUES(1, 'strange')");
				stmt.execute("INSERT INTO description(id, value) VALUES(2, 'very strange')");
				final ResultSet rs = stmt.executeQuery("SELECT * FROM description");
				System.out.println("H2 In-Memory Database inserted through Statement");
				while (rs.next()) {
					log.debug("Id: {}, value: {}", rs.getInt("id"), rs.getString("value"));
				}
				log.info("The table description is successfully created and populated", e);
			}
			stmt.close();
		} catch (SQLException e) {
			log.error("Exception received when trying to create and populate the database.", e);
			ExceptionSoftener.uncheck(e);
		}

        leavingMethodHeaderLogger.debug(null);
	}
	
	private String getDriverClassName() {
		
		assert(this.databaseConfig != null);
		
		return this.databaseConfig.getDriverClassName();
	}
	
	private String getDataSourceForNonExistingSchema() {
		
		assert(this.databaseConfig != null);
		
		return this.databaseConfig.getDataSourceForNonExistingSchema();
	}
	
	private String getUser() {
		
		assert(this.databaseConfig != null);
		
		return this.databaseConfig.getUser();
	}
	
	private String getPwd() {
		
		assert(this.databaseConfig != null);
		
		return this.databaseConfig.getPwd();}
	
	private String getDataSourceForExistingSchema() { 
		
		assert(this.databaseConfig != null);
		
		return this.databaseConfig.getDataSourceForExistingSchema();
	}
}