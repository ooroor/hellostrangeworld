package net.barakiroth.hellostrangeworld.backend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.JettyManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
public class MainTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
    
  @Mock
  private JettyManager mockedJettyManager;

  @BeforeEach
  void setUpStreams() throws IllegalArgumentException, ReflectiveOperationException {
    final Main main = Main.getSingletonInstance();
    main.setJettyManager(null);
  }
  
  @Test
  public void when_running_main_then_an_expected_initialPart_should_be_produced() {
    
    enteringTestHeaderLogger.debug(null);
    
    final Main main = Main.getSingletonInstance();
    main.setJettyManager(mockedJettyManager);
    
    assertThatCode(() -> Main.main(null)).doesNotThrowAnyException();
  }

  @Test
  public void should_not_throw_when_instantiating() {
    
    enteringTestHeaderLogger.debug(null);    
 
    assertThatCode(() -> Main.getSingletonInstance()).doesNotThrowAnyException();
  }

  @Test
  public void when_JettyManager_is_explicitly_set_then_it_should_be_returned_by_a_subsequent_get() {
    
    enteringTestHeaderLogger.debug(null);
    
    final Main main = Main.getSingletonInstance();
    final IBackendConfig backendConfig = main.getBackendConfig();
    main.setJettyManager(mockedJettyManager);
 
    assertThat(mockedJettyManager).isEqualTo(main.getJettyManager(backendConfig));
  }

  @Test
  public void when_JettyManager_is_implicitly_instantiated_then_no_exception_should_be_thrown() {
    
    enteringTestHeaderLogger.debug(null);
    
    final Main main = Main.getSingletonInstance();
    main.setJettyManager(null);
    final IBackendConfig backendConfig = main.getBackendConfig();
 
    assertThatCode(() -> main.getJettyManager(backendConfig)).doesNotThrowAnyException();
  }

  @Test
  public void when_JettyManager_is_implicitly_instantiated_then_it_should_not_be_null() {
    
    enteringTestHeaderLogger.debug(null);
    
    final Main main = Main.getSingletonInstance();
    main.setJettyManager(null);
    final IBackendConfig backendConfig = main.getBackendConfig();
    
    assertThat(main.getJettyManager(backendConfig)).isNotNull();
  }

  @Test
  public void when_getting_the_config_twice_then_the_same_config_should_be_returned() {
    
    enteringTestHeaderLogger.debug(null);
 
    final Main main = Main.getSingletonInstance();
    final IBackendConfig backendConfig1 = main.getBackendConfig();
    final IBackendConfig backendConfig2 = main.getBackendConfig();
    
    assertThat(backendConfig1).isEqualTo(backendConfig2);
  }
}