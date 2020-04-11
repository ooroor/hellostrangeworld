package net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import net.barakiroth.hellostrangeworld.common.AbstractConfig;
import net.barakiroth.hellostrangeworld.common.IConfig;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
public class JettyManagerTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @Mock
  private Server mockedServer;
  
  @Test
  public void when_starting_then_no_exception_should_be_thrown()
      throws Exception {
    
    enteringTestHeaderLogger.debug(null);
    
    final IConfig config = new AbstractConfig() {{}};
    final IJettyManagerConfig jettyManagerConfig = config.getJettyManagerConfig();
    final JettyManager jettyManager = jettyManagerConfig.getJettyManager();
    doNothing().when(mockedServer).setHandler(any(Handler.class));
    // The following method (start) is final, which calls on a
    // file named org.mockito.plugins.MockMaker in the
    // src/test/resources/mockito-extensions directory
    // se ref.: https://www.baeldung.com/mockito-final
    doNothing().when(mockedServer).start();
    jettyManager.setServer(mockedServer);
    System.setProperty(JettyManagerConfig.JERSEY_APPLICATION_CLASS_NAME_KEY, "someClassNamePlaceHolderForTest");

    assertThatCode(() -> jettyManager.start()).doesNotThrowAnyException();
  }

  @Test
  // TODO: A hacky thing to increase coverage only. Fix error situations in general!
  public void when_starting_and_an_exception_is_thrown_then_an_exception_should_be_rethrown()
      throws Exception {
    
    enteringTestHeaderLogger.debug(null);
    
    final IConfig config = new AbstractConfig() {{}};
    final IJettyManagerConfig jettyManagerConfig = config.getJettyManagerConfig();
    final JettyManager jettyManager = jettyManagerConfig.getJettyManager();
    doNothing().when(mockedServer).setHandler(any(Handler.class));
    // The following method (start) is final, which calls on a
    // file named org.mockito.plugins.MockMaker in the
    // src/test/resources/mockito-extensions directory
    // se ref.: https://www.baeldung.com/mockito-final
    doThrow(Exception.class).when(mockedServer).start();
    jettyManager.setServer(mockedServer);
    System.setProperty(JettyManagerConfig.JERSEY_APPLICATION_CLASS_NAME_KEY, "someClassNamePlaceHolderForTest");

    assertThatExceptionOfType(Throwable.class).isThrownBy(() -> jettyManager.start());
  }

  @Test
  public void when_started_then_it_should_return_that_it_is_started()
      throws Exception {
    
    enteringTestHeaderLogger.debug(null);
    
    final IConfig config = new AbstractConfig() {{}};
    final IJettyManagerConfig jettyManagerConfig = config.getJettyManagerConfig();
    final JettyManager jettyManager = jettyManagerConfig.getJettyManager();
    doNothing().when(mockedServer).setHandler(any(Handler.class));
    // The following method (start) is final, which calls on a
    // file named org.mockito.plugins.MockMaker in the
    // src/test/resources/mockito-extensions directory
    // se ref.: https://www.baeldung.com/mockito-final
    doNothing().when(mockedServer).start();
    doReturn(true).when(mockedServer).isStarted();
    jettyManager.setServer(mockedServer);
    System.setProperty(JettyManagerConfig.JERSEY_APPLICATION_CLASS_NAME_KEY, "someClassNamePlaceHolderForTest");
    jettyManager.start();

    assertThat(jettyManager.isStarted()).isTrue();
  }
  
  @Test
  public void when_stopping_a_non_started_manager_then_no_exception_should_be_thrown()
      throws Exception {
    
    enteringTestHeaderLogger.debug(null);
    
    final IConfig config = new AbstractConfig() {{}};
    final IJettyManagerConfig jettyManagerConfig = config.getJettyManagerConfig();
    final JettyManager jettyManager = jettyManagerConfig.getJettyManager();
    jettyManager.setServer(mockedServer);
    System.setProperty(JettyManagerConfig.JERSEY_APPLICATION_CLASS_NAME_KEY, "someClassNamePlaceHolderForTest");

    assertThatCode(() -> jettyManager.stop()).doesNotThrowAnyException();
  }
  
  @Test
  public void when_stopping_a_started_manager_then_no_exception_should_be_thrown()
      throws Exception {
    
    enteringTestHeaderLogger.debug(null);
    
    final IConfig config = new AbstractConfig() {{}};
    final IJettyManagerConfig jettyManagerConfig = config.getJettyManagerConfig();
    final JettyManager jettyManager = jettyManagerConfig.getJettyManager();
    doReturn(false).when(mockedServer).isStarted();
    jettyManager.setServer(mockedServer);
    System.setProperty(JettyManagerConfig.JERSEY_APPLICATION_CLASS_NAME_KEY, "someClassNamePlaceHolderForTest");

    jettyManager.start();
    jettyManager.stop();

    assertThat(jettyManager.isStarted()).isFalse();
  }
  
  @Test
  public void when_stopping_and_an_exception_is_thrown_then_an_exception_should_be_rethrown()
      throws Exception {
    
    enteringTestHeaderLogger.debug(null);
    
    final IConfig config = new AbstractConfig() {{}};
    final IJettyManagerConfig jettyManagerConfig = config.getJettyManagerConfig();
    final JettyManager jettyManager = jettyManagerConfig.getJettyManager();
    
    doThrow(Exception.class).when(mockedServer).stop();
    jettyManager.setServer(mockedServer);
    System.setProperty(JettyManagerConfig.JERSEY_APPLICATION_CLASS_NAME_KEY, "someClassNamePlaceHolderForTest");

    assertThatExceptionOfType(Throwable.class).isThrownBy(() -> jettyManager.stop());
  }
  
  @Test
  public void when_getting_the_server_port_then_appririate_sys_prop_should_be_returned()
      throws Exception {
    
    enteringTestHeaderLogger.debug(null);
    
    final IConfig config = new AbstractConfig() {{}};
    final IJettyManagerConfig jettyManagerConfig = config.getJettyManagerConfig();
    final JettyManager jettyManager = jettyManagerConfig.getJettyManager();
    final int expectedServerPort = 178;
    System.setProperty(JettyManagerConfig.JETTY_SERVER_PORT_KEY, String.valueOf(expectedServerPort));

    assertThat(jettyManager.getServerPort(jettyManagerConfig)).isEqualTo(expectedServerPort);
  }
  
  @Test
  public void when_getting_the_server_through_an_implicit_creation_then_no_exception_should_be_thrown()
      throws Exception {
    
    enteringTestHeaderLogger.debug(null);
    
    final IConfig config = new AbstractConfig() {{}};
    
    final IJettyManagerConfig jettyManagerConfig = config.getJettyManagerConfig();
    final JettyManager jettyManager = jettyManagerConfig.getJettyManager();
    jettyManager.setServer(null);

    assertThatCode(() -> jettyManager.getServer()).doesNotThrowAnyException();
  }
  
  @Test
  public void when_getting_the_singleton_twice_then_they_should_be_the_same()
      throws Exception {
    
    enteringTestHeaderLogger.debug(null);
    
    final IConfig config = new AbstractConfig() {{}};
    final JettyManager jettyManager1 = JettyManager.getSingletonInstance(config);
    final JettyManager jettyManager2 = JettyManager.getSingletonInstance(config);

    assertThat(jettyManager1).isSameAs(jettyManager2);
  }

  /*
  @Test
  public void when_started_it_should_be_able_to_be_stopped_without_exceptions()
      throws InterruptedException {
    
    enteringTestHeaderLogger.debug(null);
    
    final IConfig config = new AbstractConfig() {{}};
    final JettyManager jettyManager = config.getJettyManager();
    Thread thread = null;
    try {
      thread =
        new Thread(
            new Runnable() {
              public void run() {
                assertThatCode(
                    () -> {
                    jettyManager.start();
                    while (!jettyManager.isStarted()) {
                      try {
                        Thread.sleep(50);
                      } catch (InterruptedException e) {
                        log.warn("Exception received when sleeping "
                            + "waiting for the Jetty server to start", e); 
                      }
                    }
                  }
                )
                .doesNotThrowAnyException();
              }
            }
        );
      thread.start();
    } finally {
      jettyManager.stop();
    }
  }
  */
}