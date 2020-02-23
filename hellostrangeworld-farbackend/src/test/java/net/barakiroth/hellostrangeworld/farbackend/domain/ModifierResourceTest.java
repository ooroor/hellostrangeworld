package net.barakiroth.hellostrangeworld.farbackend.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import net.barakiroth.hellostrangeworld.farbackend.FarBackendConfig;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer.JettyManager;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: Use RestAssured
 */
public class ModifierResourceTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @BeforeAll
  static void beforeAll() {
    startDatabase();
    startServletContainer();
  }

  @AfterAll
  static void afterAll() {
    stopServletContainer();
    stopDatabase();
  }

  private static void startServletContainer() {
    
    final JettyManager jettyManager = FarBackendConfig.getSingletonInstance().getJettyManager();
    if (!jettyManager.isStarted()) {
      jettyManager.start();
    }
  }

  private static void stopServletContainer() {
    
    final JettyManager jettyManager = FarBackendConfig.getSingletonInstance().getJettyManager();
    if (jettyManager.isStarted()) {
      jettyManager.stop();
    }
  }

  private static void startDatabase() {
    final Database database = FarBackendConfig.getSingletonInstance().getDatabase();
    if (!database.isStarted()) {
      database.start();
    }
  }

  private static void stopDatabase() {
    final Database database = FarBackendConfig.getSingletonInstance().getDatabase();
    if (database.isStarted()) {
      database.stop();
    }
  }
  
  @Test
  void when_asking_for_a_GreetingDescriptionResource_then_one_such_with_expected_values_should_be_received() {
    
    enteringTestHeaderLogger.debug(null);

    ModifierResourceTest.enteringTestHeaderLogger.debug(null);

    final CloseableHttpClient httpClient = 
        Assertions.assertDoesNotThrow(() -> HttpClients.createDefault());
    final String urlString = "http://localhost:8099/api/Modifier";
    final URI endpointUri =
        Assertions.assertDoesNotThrow(() -> new URIBuilder(urlString).build());
    final HttpUriRequest request = new HttpGet(endpointUri);
    final CloseableHttpResponse response =
        Assertions.assertDoesNotThrow(() -> httpClient.execute(request));
    final HttpEntity httpEntity = response.getEntity();
    final ObjectMapper objectMapper = new ObjectMapper();
    final ModifierDo modifierDo =
        Assertions.assertDoesNotThrow(
            () -> objectMapper.readValue(httpEntity.getContent(), ModifierDo.class)
        );
    final int id = modifierDo.getId();
    final String adjective = modifierDo.getModifier();

    assertThat(adjective).isIn("very strange", "strange", "immensely strange");
    assertThat(id).isIn(1, 2, 3);
  }
}