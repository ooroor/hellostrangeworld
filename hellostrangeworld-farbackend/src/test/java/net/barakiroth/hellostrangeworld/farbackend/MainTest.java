package net.barakiroth.hellostrangeworld.farbackend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.JettyManager;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;

public class MainTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @Test
  public void when_application_is_started_it_should_not_crash() throws InterruptedException {
    
    enteringTestHeaderLogger.debug(null);
    
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
  public void when_FarBackendConfig_is_asked_for_twice_then_the_same_instance_should_be_returned() {
    
    enteringTestHeaderLogger.debug(null);
    
    final Main main = Main.getSingletonInstance();
    
    final IFarBackendConfig farBackendConfig1 = main.getFarBackendConfig();
    final IFarBackendConfig farBackendConfig2 = main.getFarBackendConfig();
    
    assertThat(farBackendConfig1).isEqualTo(farBackendConfig2);
    assertThat(farBackendConfig1).isSameAs(farBackendConfig2);
  }

  @Test
  public void when_JettyManager_is_asked_for_twice_then_the_same_instance_should_be_returned() {
    
    enteringTestHeaderLogger.debug(null);
    
    final Main main = Main.getSingletonInstance();
    
    final IFarBackendConfig farBackendConfig = FarBackendConfig.getSingletonInstance();
    
    final JettyManager  jettyManager1  = main.getJettyManager(farBackendConfig);
    final JettyManager  jettyManager2  = main.getJettyManager(farBackendConfig);
    
    assertThat(jettyManager1).isEqualTo(jettyManager2);
    assertThat(jettyManager1).isSameAs(jettyManager2);
  }

  @Test
  public void when_Database_is_asked_for_twice_then_the_same_instance_should_be_returned() {
    
    enteringTestHeaderLogger.debug(null);
    
    final Main main = Main.getSingletonInstance();
    
    final IFarBackendConfig farBackendConfig = FarBackendConfig.getSingletonInstance();
    
    final Database database1  = main.getDatabase(farBackendConfig);
    final Database database2  = main.getDatabase(farBackendConfig);
    
    assertThat(database1).isEqualTo(database2);
    assertThat(database1).isSameAs(database2);
  }
}