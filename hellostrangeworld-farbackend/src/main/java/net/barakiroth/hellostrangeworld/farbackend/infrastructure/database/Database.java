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

	private static final String DB_DRIVER_CLASS_NAME = "org.h2.Driver";
	private static final String DB_SCHEMA_NAME = "hellostrangeworld";
	private static final String DB_DS_SCHEMA_NOT_EXISTS = "jdbc:h2:mem:" + DB_SCHEMA_NAME + ";DB_CLOSE_DELAY=-1";
	private static final String DB_DS_SCHEMA_EXISTS = DB_DS_SCHEMA_NOT_EXISTS + ";IFEXISTS=TRUE";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";
	
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

	public void disconnect() {

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
        
        assert(this.databaseConfig != null);
        
        final Connection connection = createDBConnection();

		createAndPopulate(connection);

        leavingMethodHeaderLogger.debug(null);

		return connection;
	}

	private Connection createDBConnection() {

        enteringMethodHeaderLogger.debug(null);
        
        assert(this.databaseConfig != null);
        
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
				stmt.execute("SELECT * FROM description");
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
		return DB_DRIVER_CLASS_NAME;
	}
	
	private String getDataSourceForNonExistingSchema() {
		return DB_DS_SCHEMA_NOT_EXISTS;
	}
	
	private String getUser() {
		return DB_USER;
	}
	
	private String getPwd() {
		return DB_PASSWORD;
	}
	
	private String getDataSourceForExistingSchema() {  
		return DB_DS_SCHEMA_EXISTS;
	}
}