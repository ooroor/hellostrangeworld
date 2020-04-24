package net.barakiroth.hellostrangeworld.farbackend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.JettyManager;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(ITestConstants.INTEGRATION_TEST_ANNOTATION)
public class MainIntegrationTest {
  
  @BeforeEach
  void beforeEach() {
    final Main main = Main.getSingleton();
    main.setFarBackendConfig(null);
  }
  
  @AfterEach
  void afterEach() {
    final Main main = Main.getSingleton();
    main.setFarBackendConfig(null);
  }
  
  @Test
  public void when_application_is_started_and_self_configures_then_it_should_not_crash() throws InterruptedException {
    
    ITestConstants.enteringTestHeaderLogger.debug(null);
    
    Thread thread = null;
    try {
      thread =
        new Thread(
            new Runnable() {
              public void run() {
                assertThatCode(() -> Main.main(null)).doesNotThrowAnyException();
              }
            }
        );
      thread.start();
      Thread.sleep(400);
      thread.join();
    } finally {
      thread.stop();
    }
  }

  @Test
  public void when_JettyManager_is_asked_for_twice_then_the_same_instance_should_be_returned() {
    
    ITestConstants.enteringTestHeaderLogger.debug(null);
    
    final Main main = Main.getSingleton();
    
    final IFarBackendConfig farBackendConfig = FarBackendConfig.getSingleton();
    
    final JettyManager  jettyManager1  = main.getJettyManager(farBackendConfig);
    final JettyManager  jettyManager2  = main.getJettyManager(farBackendConfig);
    
    assertThat(jettyManager1).isEqualTo(jettyManager2);
    assertThat(jettyManager1).isSameAs(jettyManager2);
  }

  @Test
  public void when_Database_is_asked_for_twice_then_the_same_instance_should_be_returned() {
    
    ITestConstants.enteringTestHeaderLogger.debug(null);
    
    final Main main = Main.getSingleton();
    
    final IFarBackendConfig farBackendConfig = FarBackendConfig.getSingleton();
    
    final Database database1  = main.getDatabase(farBackendConfig);
    final Database database2  = main.getDatabase(farBackendConfig);
    
    assertThat(database1).isEqualTo(database2);
    assertThat(database1).isSameAs(database2);
  }

}
