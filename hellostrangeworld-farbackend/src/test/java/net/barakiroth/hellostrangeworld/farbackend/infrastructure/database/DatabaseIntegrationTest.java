package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doThrow;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import javax.sql.DataSource;
import net.barakiroth.hellostrangeworld.farbackend.FarBackendConfig;
import net.barakiroth.hellostrangeworld.farbackend.ITestConst;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag(ITestConst.INTEGRATION_TEST_ANNOTATION)
@ExtendWith(MockitoExtension.class)
public class DatabaseIntegrationTest {
  
  @BeforeEach
  void beforeEach() {
    FarBackendConfig.getSingleton().getDatabase().stop();
  }
  
  @AfterEach
  void afterEach() {
    FarBackendConfig.getSingleton().getDatabase().stop();
  }
  
  @Mock
  private DataSource mockedDataSource;
  
  @Mock
  private Connection mockedConnection;
  
  @Mock
  private PreparedStatement mockedPreparedStatement;
  
  @Mock
  private ResultSet mockedResultSet;
  
  @Mock
  DatabaseMetaData mockedDatabaseMetaData;

  @Test
  void when_shutting_down_the_database_and_an_sql_exception_is_thrown_then_no_exception_should_be_rethrown() throws SQLException {

    ITestConst.enteringTestHeaderLogger.debug(null);

    Database.createAndSetSingleton(FarBackendConfig.getSingleton().getDatabaseConfig());
    final Database database = Database.getSingleton();
    DataSource savedDataSource = null;
    try {
      database.start();
      savedDataSource = database.getDataSource();
      doThrow(new SQLException("DUMMY TEST EXCEPTION")).when(this.mockedDataSource).getConnection();

      database.setDataSource(this.mockedDataSource);

      assertThatCode(() -> database.stop()).doesNotThrowAnyException();
    } finally {
      if (savedDataSource != null) {
        database.setDataSource(savedDataSource);
      }
    }
  }

  @Test
  void when_doing_sql_and_the_database_is_not_started_then_the_database_should_be_implicitly_started() {

    ITestConst.enteringTestHeaderLogger.debug(null);

    Database.createAndSetSingleton(FarBackendConfig.getSingleton().getDatabaseConfig());
    final Database database = Database.getSingleton();
    final Callable<String> callable = () -> "DUMMY";
    database.doInTransaction(callable);
    
    assertThat(database.isStarted()).isTrue();
  }

  @Test
  void when_starting_then_no_exception_should_be_thrown() {

    ITestConst.enteringTestHeaderLogger.debug(null);

    Database.createAndSetSingleton(FarBackendConfig.getSingleton().getDatabaseConfig());
    final Database expectedDatabase = Database.getSingleton();
    try {
      assertThatCode(() -> expectedDatabase.start()).doesNotThrowAnyException();
    } finally {
      expectedDatabase.stop();
    }
  }
  
  @Test
  void when_started_then_the_returned_database_should_equal_the_one_started() {

    ITestConst.enteringTestHeaderLogger.debug(null);

    Database.createAndSetSingleton(FarBackendConfig.getSingleton().getDatabaseConfig());
    final Database expectedDatabase = Database.getSingleton();
    
    try {
      final Database actualDatabase = expectedDatabase.start();
      assertThat(actualDatabase).isEqualTo(expectedDatabase);
      assertThat(actualDatabase).isSameAs(expectedDatabase);
      assertThat(actualDatabase.isStarted()).isTrue();
    } finally {
      expectedDatabase.stop();
    }
  }

  @Test
  void when_starting_a_database_twice_then_no_exception_should_be_thrown() {

    ITestConst.enteringTestHeaderLogger.debug(null);

    Database.createAndSetSingleton(FarBackendConfig.getSingleton().getDatabaseConfig());
    final Database expectedDatabase = Database.getSingleton();
    
    try {
      expectedDatabase.start();
      assertThatCode(() -> expectedDatabase.start()).doesNotThrowAnyException();
      assertThat(expectedDatabase.isStarted()).isTrue();
    } finally {
      expectedDatabase.stop();
    }
  }

  @Test
  void when_stopping_a_started_database_then_no_exception_should_be_thrown() {

    ITestConst.enteringTestHeaderLogger.debug(null);
    Database.createAndSetSingleton(FarBackendConfig.getSingleton().getDatabaseConfig());
    final Database expectedDatabase = Database.getSingleton();
    expectedDatabase.start();
    assertThatCode(() -> expectedDatabase.stop()).doesNotThrowAnyException();
    assertThat(expectedDatabase.isStarted()).isFalse();
  }
  
  @Test
  void when_stopping_a_non_started_database_then_no_exception_should_be_thrown() {

    ITestConst.enteringTestHeaderLogger.debug(null);

    Database.createAndSetSingleton(FarBackendConfig.getSingleton().getDatabaseConfig());
    final Database expectedDatabase = Database.getSingleton();
    assertThat(expectedDatabase.isStarted()).isFalse();
    assertThatCode(() -> expectedDatabase.stop()).doesNotThrowAnyException();
    assertThat(expectedDatabase.isStarted()).isFalse(); 
  }

  @Test
  void when_stopping_a_started_database_then_the_returned_database_should_equal_the_one_stopped() {

    ITestConst.enteringTestHeaderLogger.debug(null);

    Database.createAndSetSingleton(FarBackendConfig.getSingleton().getDatabaseConfig());
    final Database expectedDatabase = Database.getSingleton();
    expectedDatabase.start();
    final Database actualDatabase = expectedDatabase.stop();
    assertThat(actualDatabase).isEqualTo(expectedDatabase);
    assertThat(actualDatabase).isSameAs(expectedDatabase);
  }

  @Test
  void when_stopping_a_database_twice_then_no_exception_should_be_thrown() {

    ITestConst.enteringTestHeaderLogger.debug(null);

    Database.createAndSetSingleton(FarBackendConfig.getSingleton().getDatabaseConfig());
    final Database expectedDatabase = Database.getSingleton();
    expectedDatabase.start();
    expectedDatabase.stop();
    assertThatCode(() -> expectedDatabase.stop()).doesNotThrowAnyException();
    assertThat(expectedDatabase.isStarted()).isFalse();
  }
}