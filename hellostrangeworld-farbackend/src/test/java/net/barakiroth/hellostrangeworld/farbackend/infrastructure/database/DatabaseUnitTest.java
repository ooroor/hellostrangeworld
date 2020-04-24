package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import javax.sql.DataSource;
import net.barakiroth.hellostrangeworld.farbackend.FarBackendConfig;
import net.barakiroth.hellostrangeworld.farbackend.ITestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

@Tag(ITestConstants.UNIT_TEST_ANNOTATION)
@ExtendWith(MockitoExtension.class)
public class DatabaseUnitTest {
  
  @BeforeEach
  void beforeEach() {
    final Database database =
        FarBackendConfig.getSingleton().getDatabase();
    //database.setDataSource(null);
    database.stop();
  }
  
  @Mock
  private DataSource mockedDataSource;
  
  @Mock
  Connection mockedConnection;
  
  @Mock
  private DatabaseMetaData mockedDatabaseMetaData;

  @Test
  void when_instantiating_then_no_exception_should_be_thrown() {

    ITestConstants.enteringTestHeaderLogger.debug(null);
    
    assertThatCode(() -> Database.getSingleton(FarBackendConfig.getSingleton())).doesNotThrowAnyException();
  }
  
  @Test
  void when_getting_an_SQLQueryFactory_and_the_connect_throws_then_a_RuntimeException_should_be_rethrown() throws SQLException {

    ITestConstants.enteringTestHeaderLogger.debug(null);
    
    final Database expectedDatabase =
        Database.getSingleton(FarBackendConfig.getSingleton());
    doThrow(SQLException.class).when(mockedDataSource).getConnection();
    expectedDatabase.setDataSource(mockedDataSource);
        
    assertThatThrownBy(() -> expectedDatabase.getSQLQueryFactory()).isInstanceOf(RuntimeException.class);
  }

  @Test
  void when_the_connection_reports_an_unsupported_database_brand_then_an_appropriate_exception_should_be_thrown() throws SQLException {

    ITestConstants.enteringTestHeaderLogger.debug(null);
    
    doReturn(mockedConnection).when(mockedDataSource).getConnection();
    doReturn(mockedDatabaseMetaData).when(mockedConnection).getMetaData();
    doReturn("RUBBISH_DB_BRAND_TO_PROVOKE_AN_EXCEPTION_WHEN_TESTING").when(mockedDatabaseMetaData).getDatabaseProductName();
    
    final Database expectedDatabase =
        Database.getSingleton(FarBackendConfig.getSingleton());
    expectedDatabase.setDataSource(mockedDataSource);

    assertThatThrownBy(
        () -> expectedDatabase.getSQLQueryFactory()
    )
    .isInstanceOf(UnknownDatabaseBrandException.class);
  }
}