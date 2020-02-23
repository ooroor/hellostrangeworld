package net.barakiroth.hellostrangeworld.frontend.consumer;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import java.net.URISyntaxException;
import net.barakiroth.hellostrangeworld.frontend.FrontendConfig;
import net.barakiroth.hellostrangeworld.frontend.IFrontendConfig;
import org.apache.http.conn.HttpHostConnectException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitialPartConsumerTest {

	private static final Logger enteringTestHeaderLogger =
		LoggerFactory.getLogger("EnteringTestHeader");

	private static InitialPartConsumer initialPartConsumer;

	private static WireMockServer wireMockServer = null;

	@BeforeAll
	static void beforeAll() {
		final IFrontendConfig config = FrontendConfig.getSingletonInstance();
		InitialPartConsumerTest.initialPartConsumer = new InitialPartConsumer(config);
		InitialPartConsumerTest.wireMockServer = new WireMockServer(
				config.getRequiredInt(IFrontendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_PORT_KEY));
		InitialPartConsumerTest.wireMockServer.start();
	}

	@AfterAll
	static void afterAll() {
		InitialPartConsumerTest.wireMockServer.resetAll();
		InitialPartConsumerTest.wireMockServer.stop();
		InitialPartConsumerTest.wireMockServer = null;
	}

	private static void setupStub(
			final WireMockServer wireMockServer,
			final String         urlIncludingParms,
			final String         responseBody) {

		wireMockServer
			.stubFor(
				get(urlEqualTo(urlIncludingParms))
					.withHeader("Accept", matching("application/json"))
					.willReturn(aResponse()
					.withStatus(200)
					.withBody(responseBody))
			);
	}

	@BeforeEach
	void beforeEach() {
		InitialPartConsumerTest.wireMockServer.resetAll();
	}

	@AfterEach
	void afterEach() {
		InitialPartConsumerTest.wireMockServer.resetAll();
	}

	@Test
	void when_no_endpoint_is_running_then_an_exception_should_be_thrown() {

		enteringTestHeaderLogger.debug(null);

		System.setProperty(IFrontendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_PROTOCOL_KEY, "http");
		System.setProperty(IFrontendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_HOSTNAME_KEY, "localhost");
		System.setProperty(IFrontendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_PORT_KEY, "12345");
		System.setProperty(IFrontendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_CONTEXT_KEY, "api/InitialPart");

		final RuntimeException runtimeException =
			Assertions.assertThrows(
				RuntimeException.class,
				() -> initialPartConsumer.getInitialPartDo()
			);

		assertThat(runtimeException.getCause()).isNotNull();
		assertThat(runtimeException.getCause()).isInstanceOf(HttpHostConnectException.class);
	}

	  @Test
	  void when_the_endpoint_is_configured_with_rubbish_then_an_exception_should_be_thrown() {

	    enteringTestHeaderLogger.debug(null);
	    
	    System.setProperty(
	    		IFrontendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_PROTOCOL_KEY, "65d65d5656d65d65d65d65d"); 
	    System.setProperty(
	    		IFrontendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_HOSTNAME_KEY, "dd6d56d65d65d65d65d65");
	    System.setProperty(
	    		IFrontendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_PORT_KEY, "6787");
	    System.setProperty(
	    		IFrontendConfig.DOWNSTREAM_RESOURCE_ENDPOINT_CONTEXT_KEY,":-\\//:ooo``kub8756¤");

	    final RuntimeException runtimeException =
	        Assertions.assertThrows(
	            RuntimeException.class,
	            () -> initialPartConsumer.getInitialPartDo()
	        );
	    assertThat(runtimeException.getCause()).isNotNull();
	    assertThat(runtimeException.getCause()).isInstanceOf(URISyntaxException.class);
	  }
	  
	  @Test
	  void when_the_endpoint_is_up_then_a_given_phrase_should_be_returned()
	      throws JsonProcessingException {
	    
	    enteringTestHeaderLogger.debug(null);
	    
	    setupStubWithOkUrlAndOkResponseEntity();
	    
	    assertThat(initialPartConsumer.getInitialPartDo().getInitialPart()).isEqualTo("Hello, strange");
	  }

	  private void setupStubWithOkUrlAndOkResponseEntity()
	      throws JsonProcessingException {

	    final InitialPartDo initialPartDo = new InitialPartDo("Hello, strange");
	    final String presumableJsonOfModifier =
	        new ObjectMapper().writeValueAsString(initialPartDo);

	    InitialPartConsumerTest
	    	.setupStub(
		    	InitialPartConsumerTest.wireMockServer,
		        "/api/InitialPart",
		        presumableJsonOfModifier
			);
	  }
}