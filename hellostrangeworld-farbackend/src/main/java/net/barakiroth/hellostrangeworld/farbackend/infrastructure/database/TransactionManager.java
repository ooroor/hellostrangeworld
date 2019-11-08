package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionManager {

  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");

  private final TransactionalConnectionProvider transactionalConnectionProvider;

  /**
   * Constructor storing the connection provider for later use.
   * 
   * @param transactionalConnectionProvider Provides connections to be used in transactions.
   */
  public TransactionManager(final TransactionalConnectionProvider transactionalConnectionProvider) {

    enteringMethodHeaderLogger.debug(null);
    
    this.transactionalConnectionProvider = transactionalConnectionProvider;
    
    leavingMethodHeaderLogger.debug(null);
  }

  /**
   * Do the things provided by the callable 
   * within one and only one database SQL transaction.
   * 
   * @param <V> The return type of the provided callable.
   * @param callable The actions to be performed as contained in the callable.
   * @return The outcome of the execution of the provided callable.
   */
  public <V> V doInTransaction(final Callable<V> callable) {
    
    enteringMethodHeaderLogger.debug(null);
    
    V theThing;
    try (
        final Connection transactionalConnection =
            transactionalConnectionProvider.getTransactionalConnection()
    ) {
      theThing = executeTx(callable, transactionalConnection);
    } catch (LockException e) {
      throw e;
    } catch (Exception e) {
      throw new DatabaseException(e);
    } finally {
      transactionalConnectionProvider.transactionCompleted();
    }

    leavingMethodHeaderLogger.debug(null);
    
    return theThing;
  }

  private <V> V executeTx(
      final Callable<V> operation,
      final Connection connection) throws SQLException {
    
    enteringMethodHeaderLogger.debug(null);
    
    V result = null;
    try {
      result = operation.call();
      connection.commit();
    } catch (RuntimeException e) {
      connection.rollback();
      throw e;
    } catch (Exception e) {
      connection.rollback();
      throw new DatabaseException(e);
    }

    leavingMethodHeaderLogger.debug(null);
    
    return result;
  }
}
