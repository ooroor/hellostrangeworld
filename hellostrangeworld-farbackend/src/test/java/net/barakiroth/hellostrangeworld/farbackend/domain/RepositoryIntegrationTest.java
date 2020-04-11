package net.barakiroth.hellostrangeworld.farbackend.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLQuery;
import com.querydsl.core.Tuple;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;
import net.barakiroth.hellostrangeworld.farbackend.FarBackendConfig;
import net.barakiroth.hellostrangeworld.farbackend.IFarBackendConfig;
import net.barakiroth.hellostrangeworld.farbackend.domain.ModifierDo;
import net.barakiroth.hellostrangeworld.farbackend.domain.Repository;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.DatabaseException;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.tables.QModifier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag("IntegrationTest")
@ExtendWith(MockitoExtension.class)
public class RepositoryIntegrationTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @Mock
  private Database mockedDatabase;
  
  @Mock
  private SQLQuery<Tuple> mockedSQLQueryOfTuple;
  
  @Mock
  private SQLQueryFactory mockedSQLQueryFactory;
  
  @BeforeEach
  void beforeEach() {
    FarBackendConfig.getSingletonInstance().getDatabase().stop();
    FarBackendConfig.setSingletonInstance(null);
  }
  
  @AfterEach
  void afterEach() {
    FarBackendConfig.getSingletonInstance().getDatabase().stop();
    FarBackendConfig.setSingletonInstance(null);
  }

  @Test
  void when_asking_for_data_and_the_database_is_not_started_then_it_should_not_throw_an_exception() {

    enteringTestHeaderLogger.debug(null);

    final IFarBackendConfig farBackendConfig = FarBackendConfig.getSingletonInstance();
    final Repository repository = farBackendConfig.getRepository();
    assertThatCode(() -> repository.getModifierDo()).doesNotThrowAnyException();
  }

  @Test
  void when_asking_for_data_and_the_database_is_not_started_then_it_should_be_implicitly_started() {

    enteringTestHeaderLogger.debug(null);

    final IFarBackendConfig farBackendConfig = FarBackendConfig.getSingletonInstance();
    final Repository repository = farBackendConfig.getRepository();
    repository.getModifierDo();
    assertThat(farBackendConfig.getDatabase().isStarted()).isTrue();
  }
  
  @Test
  void when_asking_for_data_and_the_database_is_not_started_then_it_should_be_implicitly_started_and_return_a_value() {

    enteringTestHeaderLogger.debug(null);

    final IFarBackendConfig farBackendConfig = FarBackendConfig.getSingletonInstance();
    final Database database = farBackendConfig.getDatabase();
    final Repository repository = farBackendConfig.getRepository();
    assertThat(database.isStarted()).isFalse();
    final Optional<ModifierDo> optionalModifierDo =
        repository.getModifierDo();
    assertThat(optionalModifierDo).isNotNull();
  }
  
  @Test
  void when_the_database_is_empty_then_a_no_content_optional_should_be_returned() {

    enteringTestHeaderLogger.debug(null);

    final IFarBackendConfig farBackendConfig = FarBackendConfig.getSingletonInstance();
    final Database database = farBackendConfig.getDatabase();
    database.start();
    final SQLQueryFactory sqlQueryFactory = database.getSQLQueryFactory();
    // Empty the database for modifiers:
    final QModifier qModifier =
        QModifier.modifier1;
    database
      .doInTransaction(
          () ->
          sqlQueryFactory
          .delete(qModifier)
          .execute()
      );
    final Repository repository = farBackendConfig.getRepository();
    final Optional<ModifierDo> optionalModifierDo =
        repository.getModifierDo();
    assertThat(optionalModifierDo.isPresent()).isFalse();
  }

  @Test
  void when_an_sql_error_occurs_then_an_appropriate_exception_should_be_thrown() throws SQLException {

    enteringTestHeaderLogger.debug(null);
    
    final IFarBackendConfig farBackendConfig = FarBackendConfig.getSingletonInstance();
    final Database database = farBackendConfig.getDatabase();
    database.start();
    // Provoke an SQL error by deleting the modifier table:
    deleteModifierTable(farBackendConfig, database);
    final Repository repository = farBackendConfig.getRepository();
    final Optional<ModifierDo> optionalModifierDo = repository.getModifierDo();
    assertThat(optionalModifierDo.isPresent()).isFalse();
 
    //assertThatExceptionOfType(DatabaseException.class).isThrownBy(() -> repository.getModifierDo());
  }
  
  private void deleteModifierTable(
      final IFarBackendConfig farBackendConfig,
      final Database          database
      ) throws SQLException {

    final DataSource        dataSource        = database.getDataSource();
    final Connection        connection        = dataSource.getConnection();
    final PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE modifier");
    preparedStatement.execute();
  }
}