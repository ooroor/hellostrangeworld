package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import java.sql.Connection;
import java.sql.SQLException;
import javax.inject.Provider;
import javax.sql.DataSource;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionalConnectionProvider implements Provider<Connection> {
  
  private static final Logger log =
      LoggerFactory.getLogger(TransactionalConnectionProvider.class);
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");
  
  private static final ThreadLocal<Connection> threadLocalConnection =
      new ThreadLocal<>();
  
  private final DataSource dataSource;

  public TransactionalConnectionProvider(final DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Connection get() {
    
    enteringMethodHeaderLogger.debug(null);

    final Connection transactionalConnection = threadLocalConnection.get();
    Validate.notNull(transactionalConnection, "The query must be run in a transaction");
    
    leavingMethodHeaderLogger.debug(null);
    
    return transactionalConnection;
  }

  Connection getTransactionalConnection() throws SQLException {
    
    enteringMethodHeaderLogger.debug(null);
    
    final Connection transactionalConnection = getDataSource().getConnection();
    log.debug("Starter ny transaksjon for: {}", transactionalConnection);
    transactionalConnection.setAutoCommit(false);
    threadLocalConnection.set(transactionalConnection);
    
    leavingMethodHeaderLogger.debug(null);
    
    return transactionalConnection;
  }

  void transactionCompleted() {
    threadLocalConnection.remove();
  }
  
  private DataSource getDataSource() {
    return this.dataSource;
  }
}