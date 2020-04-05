package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
public class TransactionManagerUnitTest {
  
  @Mock  
  private TransactionalConnectionProvider mockedTransactionalConnectionProvider;
  
  @Mock
  private Connection mockedConnection;
  
  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @Test
  void when_the_provided_callable_throws_a_LockException_then_the_same_exception_should_be_rethrown()
        throws SQLException {
    
    enteringTestHeaderLogger.debug(null);
    
    final TransactionManager transactionManager =
        new TransactionManager(mockedTransactionalConnectionProvider);
    
    doReturn(mockedConnection)
      .when(mockedTransactionalConnectionProvider)
      .getTransactionalConnection();
    
    assertThatThrownBy(
        () ->
        transactionManager.doInTransaction(
            () ->
            {
              throw new LockException("DUMMY TEST EXCEPTION");
            }
        )
    )
    .isInstanceOf(LockException.class);
  }
  
  @Test
  void when_the_provided_callable_throws_an_Exception_then_a_DatabaseException_should_be_thrown()
        throws SQLException {
    
    enteringTestHeaderLogger.debug(null);
    
    final TransactionManager transactionManager =
        new TransactionManager(mockedTransactionalConnectionProvider);
    
    doReturn(mockedConnection)
      .when(mockedTransactionalConnectionProvider)
      .getTransactionalConnection();
    
    assertThatThrownBy(
        () ->
        transactionManager.doInTransaction(
            () ->
            {
              throw new Exception();
            }
        )
    )
    .isInstanceOf(DatabaseException.class);
  }
}