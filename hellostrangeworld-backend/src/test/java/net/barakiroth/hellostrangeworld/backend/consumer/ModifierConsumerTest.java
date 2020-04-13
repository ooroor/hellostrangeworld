package net.barakiroth.hellostrangeworld.backend.consumer;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import java.net.URISyntaxException;
import net.barakiroth.hellostrangeworld.backend.consumer.ModifierDo;
import net.barakiroth.hellostrangeworld.backend.BackendConfig;
import net.barakiroth.hellostrangeworld.backend.IBackendConfig;
import net.barakiroth.hellostrangeworld.backend.consumer.ModifierConsumer;
import org.apache.http.conn.HttpHostConnectException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ref.: https://github.com/navikt/okosynk/blob/master/src/test/java/no/nav/okosynk/consumer/aktoer/OidcStsClientTest.java
 */
public class ModifierConsumerTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  private static ModifierConsumer modifierConsumer;
  
  private static WireMockServer wireMockServer = null;

  @BeforeAll
  static void beforeAll() {
    final IBackendConfig config = BackendConfig.getSingleton();
    ModifierConsumerTest.modifierConsumer = new ModifierConsumer(config);
    ModifierConsumerTest.wireMockServer =
        new WireMockServer(
            config.getRequiredInt(IBackendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_PORT_KEY)
        );
    ModifierConsumerTest.wireMockServer.start();
  }
  
  @AfterAll
  static void afterAll() {
    ModifierConsumerTest.wireMockServer.resetAll();
    ModifierConsumerTest.wireMockServer.stop();
    ModifierConsumerTest.wireMockServer = null;
  }
  
  private static void setupStub(
      final WireMockServer wireMockServer,
      final String         urlIncludingParms,
      final String         responseBody) {

    wireMockServer
        .stubFor(
            get(urlEqualTo(urlIncludingParms))
                .withHeader("Accept", matching("application/json"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(responseBody)
                )
    );
  }
  
  @BeforeEach
  void beforeEach() {
    ModifierConsumerTest.wireMockServer.resetAll();
  }
  
  @AfterEach
  void afterEach() {
    ModifierConsumerTest.wireMockServer.resetAll();
  }

  @Test
  void when_no_endpoint_is_running_then_an_exception_should_be_thrown() {

    enteringTestHeaderLogger.debug(null);
    
    System.setProperty(IBackendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_PROTOCOL_KEY, "http"); 
    System.setProperty(IBackendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_HOSTNAME_KEY, "localhost");
    System.setProperty(IBackendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_PORT_KEY, "12345");
    System.setProperty(IBackendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_CONTEXT_KEY, "api/Modifier");

    final RuntimeException runtimeException =
        Assertions.assertThrows(
            RuntimeException.class,
            () -> modifierConsumer.getModifierDo()
        );
    
    assertThat(runtimeException.getCause()).isNotNull();
    assertThat(runtimeException.getCause()).isInstanceOf(HttpHostConnectException.class);
  }

  @Test
  void when_the_endpoint_is_configured_with_rubbish_then_an_exception_should_be_thrown() {

    enteringTestHeaderLogger.debug(null);
    
    System.setProperty(
    		IBackendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_PROTOCOL_KEY, "65d65d5656d65d65d65d65d"); 
    System.setProperty(
    		IBackendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_HOSTNAME_KEY, "dd6d56d65d65d65d65d65");
    System.setProperty(
    		IBackendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_PORT_KEY, "6787");
    System.setProperty(
    		IBackendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_CONTEXT_KEY,":-\\//:ooo``kub8756¤");

    final RuntimeException runtimeException =
        Assertions.assertThrows(
            RuntimeException.class,
            () -> modifierConsumer.getModifierDo()
        );
    assertThat(runtimeException.getCause()).isNotNull();
    assertThat(runtimeException.getCause()).isInstanceOf(URISyntaxException.class);
  }
  
  @Test
  void when_the_endpoint_is_up_then_a_given_phrase_should_be_returned()
      throws JsonProcessingException {
    
    enteringTestHeaderLogger.debug(null);
    
    setupStubWithOkUrlAndOkResponseEntity();
    
    assertThat(modifierConsumer.getModifierDo().getModifier()).isEqualTo("nice");
  }
  
  @Test
  void when_created_without_parms_then_no_exception_should_be_thrown()
      throws JsonProcessingException {
    
    enteringTestHeaderLogger.debug(null);
     
    assertThatCode(() -> new ModifierConsumer()).doesNotThrowAnyException();
  }

  private void setupStubWithOkUrlAndOkResponseEntity()
      throws JsonProcessingException {

    final ModifierDo modifier = new ModifierDo();
    modifier.setId(1);
    modifier.setModifier("nice");
    final String presumableJsonOfModifier =
        new ObjectMapper().writeValueAsString(modifier);

    ModifierConsumerTest.setupStub(
        ModifierConsumerTest.wireMockServer,
        "/api/Modifier",
        presumableJsonOfModifier
    );
  }
}