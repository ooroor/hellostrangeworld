package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import net.barakiroth.hellostrangeworld.farbackend.FarBackendConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @BeforeEach
  void beforeEach() {
    FarBackendConfig.getSingletonInstance().getDatabase().stop();
  }
  
  @AfterEach
  void afterEach() {
    FarBackendConfig.getSingletonInstance().getDatabase().stop();
  }

  @Test
  void when_instantiating_then_no_exception_should_be_thrown() {

    enteringTestHeaderLogger.debug(null);

    assertThatCode(() -> new Database(FarBackendConfig.getSingletonInstance())).doesNotThrowAnyException();
        
  }

  @Test
  void when_starting_then_no_exception_should_be_thrown() {

    enteringTestHeaderLogger.debug(null);

    final Database database = new Database(FarBackendConfig.getSingletonInstance());
    try {
      assertThatCode(() -> database.start()).doesNotThrowAnyException();
    } finally {
      database.stop();
    }
  }
  
  @Test
  void when_started_then_the_returned_database_should_equal_the_one_started() {

    enteringTestHeaderLogger.debug(null);

    final Database expectedDatabase = new Database(FarBackendConfig.getSingletonInstance());
    
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

    enteringTestHeaderLogger.debug(null);

    final Database expectedDatabase = new Database(FarBackendConfig.getSingletonInstance());
    
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

    enteringTestHeaderLogger.debug(null);
    final Database expectedDatabase = new Database(FarBackendConfig.getSingletonInstance());
    expectedDatabase.start();
    assertThatCode(() -> expectedDatabase.stop()).doesNotThrowAnyException();
    assertThat(expectedDatabase.isStarted()).isFalse();
  }
  
  @Test
  void when_stopping_a_non_started_database_then_no_exception_should_be_thrown() {

    enteringTestHeaderLogger.debug(null);

    final Database expectedDatabase = new Database(FarBackendConfig.getSingletonInstance());
    assertThat(expectedDatabase.isStarted()).isFalse();
    assertThatCode(() -> expectedDatabase.stop()).doesNotThrowAnyException();
    assertThat(expectedDatabase.isStarted()).isFalse(); 
  }

  @Test
  void when_stopping_a_started_database_then_the_returned_database_should_equal_the_one_stopped() {

    enteringTestHeaderLogger.debug(null);

    final Database expectedDatabase = new Database(FarBackendConfig.getSingletonInstance());
    expectedDatabase.start();
    final Database actualDatabase = expectedDatabase.stop();
    assertThat(actualDatabase).isEqualTo(expectedDatabase);
    assertThat(actualDatabase).isSameAs(expectedDatabase);
  }

  @Test
  void when_stopping_a_database_twice_then_no_exception_should_be_thrown() {

    enteringTestHeaderLogger.debug(null);

    final Database expectedDatabase = new Database(FarBackendConfig.getSingletonInstance());
    expectedDatabase.start();
    expectedDatabase.stop();
    assertThatCode(() -> expectedDatabase.stop()).doesNotThrowAnyException();
    assertThat(expectedDatabase.isStarted()).isFalse();
  }
  
}