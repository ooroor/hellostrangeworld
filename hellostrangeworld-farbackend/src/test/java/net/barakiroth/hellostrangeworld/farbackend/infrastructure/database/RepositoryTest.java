package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doReturn;

import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLQuery;
import com.querydsl.core.Tuple;
import java.util.Optional;
import net.barakiroth.hellostrangeworld.farbackend.FarBackendConfig;
import net.barakiroth.hellostrangeworld.farbackend.IFarBackendConfig;
import net.barakiroth.hellostrangeworld.farbackend.domain.ModifierDo;
import net.barakiroth.hellostrangeworld.farbackend.domain.Repository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
public class RepositoryTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @Mock
  private Database mockedDatabase;
  
  @BeforeEach
  void beforeEach() {
    FarBackendConfig.getSingletonInstance().getDatabase().stop();
  }
  
  @AfterEach
  void afterEach() {
    FarBackendConfig.getSingletonInstance().getDatabase().stop();
  }

  @Test
  void when_instantiated_then_no_exception_should_be_thrown()
      throws InterruptedException {

    enteringTestHeaderLogger.debug(null);

    final IFarBackendConfig config = FarBackendConfig.getSingletonInstance();
    assertThatCode(() -> config.getRepository()).doesNotThrowAnyException();
  }

  @Test
  void when_asking_for_data_and_the_database_is_not_started_then_it_should_not_throw_an_exception()
      throws InterruptedException {

    enteringTestHeaderLogger.debug(null);

    final IFarBackendConfig config = FarBackendConfig.getSingletonInstance();
    final Repository repository = config.getRepository();
    try {
      assertThatCode(() -> repository.getModifierDo()).doesNotThrowAnyException();
    } finally {
      config.getDatabase().stop();
    }
  }

  @Test
  void when_asking_for_data_and_the_database_is_not_started_then_it_should_be_implicitly_started()
      throws InterruptedException {

    enteringTestHeaderLogger.debug(null);

    final IFarBackendConfig config = FarBackendConfig.getSingletonInstance();
    final Repository repository = config.getRepository();
    try {
      repository.getModifierDo();
      assertThat(config.getDatabase().isStarted()).isTrue();
    } finally {
      config.getDatabase().stop();
    }
  }
  
  @Test
  void when_asking_for_data_and_the_database_is_not_started_then_it_should_be_implicitly_started_and_return_a_value()
      throws InterruptedException {

    enteringTestHeaderLogger.debug(null);

    final IFarBackendConfig config = FarBackendConfig.getSingletonInstance();
    final Database database = config.getDatabase();
    final Repository repository = config.getRepository();
    assertThat(database.isStarted()).isFalse();
    try {
      final Optional<ModifierDo> greetingDescription =
          repository.getModifierDo();
      assertThat(greetingDescription).isNotNull();
    } finally {
      database.stop();
    }
  }
  
  @Mock
  private SQLQuery<Tuple> mockedSQLQueryOfTuple;
  
  @Mock
  private SQLQueryFactory mockedSQLQueryFactory;
  
  @Test
  void when_x_then_y() {
    
    /*
    mockedDatabase
    
    SQLQuery<Tuple>
    
    
    
    */
    
    /*
    doReturn(mockedSQLQueryFactory).when(mockedDatabase).getSqlQueryFactory();
    
    doReturn(mockedSQLQueryOfTuple).when(mockedSQLQueryFactory).select(any);
    doReturn(mockedSQLQueryOfTuple).when(mockedSQLQueryFactory).from(any);
    doReturn(mockedSQLQueryOfTuple).when(mockedSQLQueryFactory).where(any);
    doReturn(mockedSQLQueryOfTuple).when(mockedSQLQueryFactory).fe
    */
    
    
  }
}