package net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import net.barakiroth.hellostrangeworld.farbackend.Config;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyManagerTest {

  private static final Logger log =
      LoggerFactory.getLogger(JettyManagerTest.class);

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @Test
  public void when_started_it_should_be_able_to_be_stopped_without_exceptions()
      throws InterruptedException {
    
    enteringTestHeaderLogger.debug(null);
    
    final Config config = Config.getSingletonInstance();
    final JettyManager jettyManager = config.getJettyManager();
    Thread thread = null;
    try {
      thread =
        new Thread(
            new Runnable() {
              public void run() {
                assertDoesNotThrow(
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
                );
              }
            }
        );
      thread.start();
    } finally {
      jettyManager.stop();
    }
  }
}