package net.barakiroth.hellostrangeworld.farbackend.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.IJettyManagerConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.JettyManager;
import net.barakiroth.hellostrangeworld.farbackend.FarBackendConfig;
import net.barakiroth.hellostrangeworld.farbackend.IFarBackendConfig;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: Use RestAssured
 */
@ExtendWith(MockitoExtension.class)
public class ModifierResourceTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @Mock
  private  IFarBackendConfig mockedFarBackendConfig;

  @Mock
  private ObjectMapper mockedObjectMapper;

  @Mock
  private Repository mockedRepository;
  
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

    final IFarBackendConfig farBackendConfig = FarBackendConfig.getSingleton();
    final IJettyManagerConfig jettyManagerConfig = farBackendConfig.getJettyManagerConfig();
    final JettyManager jettyManager = jettyManagerConfig.getJettyManager(farBackendConfig);
    if (!jettyManager.isStarted()) {
      jettyManager.start();
    }
  }

  private static void stopServletContainer() {
    
    final IFarBackendConfig farBackendConfig = FarBackendConfig.getSingleton();
    final IJettyManagerConfig jettyManagerConfig = farBackendConfig.getJettyManagerConfig();
    final JettyManager jettyManager = jettyManagerConfig.getJettyManager(farBackendConfig);
    if (jettyManager.isStarted()) {
      jettyManager.stop();
    }
  }

  private static void startDatabase() {
    final Database database = FarBackendConfig.getSingleton().getDatabase();
    if (!database.isStarted()) {
      database.start();
    }
  }

  private static void stopDatabase() {
    final Database database = FarBackendConfig.getSingleton().getDatabase();
    if (database.isStarted()) {
      database.stop();
    }
  }
  
  @Test
  void when_instantiating_without_parms_then_the_config_file_should_be_implicitly_created_without_an_exception() {
    
    enteringTestHeaderLogger.debug(null);
    
    assertThatCode(() -> new ModifierResource()).doesNotThrowAnyException();
  }
  
  @Test
  void when_asking_for_a_modifier_then_one_such_with_expected_values_should_be_received() throws UnsupportedOperationException, IOException {
    
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
    final String modifier = modifierDo.getModifier();

    assertThat(id).isIn(1, 2, 3);
    assertThat(modifier).isIn("very strange", "strange", "immensely strange");
  }

  /**
   * TODO: Should really the exception be propagated?
   * @throws JsonProcessingException 
   */
  @Test
  public void when_getModifier_and_the_ObjectMapper_throws_an_unexpected_exception_then_the_exception_should_be_propagated() throws JsonProcessingException {
    
    enteringTestHeaderLogger.debug(null);
    
    final ModifierResource modifierResource = new ModifierResource();
    
    modifierResource.setFarBackendConfig(FarBackendConfig.getSingleton());

    final String expectedModifier = "fairly good";
    final ModifierDo expectedModifierDo = new ModifierDo(19, expectedModifier);
    final Optional<ModifierDo> expectedOptionalModifierDo =
        Optional.of(expectedModifierDo);
    doReturn(expectedOptionalModifierDo).when(mockedRepository).getModifierDo();
    modifierResource.setRepository(this.mockedRepository);

    final String expectedMsg = "Hello test!";
    final RuntimeException runtimeException = new RuntimeException(expectedMsg);
    doThrow(runtimeException).when(this.mockedObjectMapper).writeValueAsString(Mockito.any(ModifierDo.class));

    modifierResource.setObjectMapper(this.mockedObjectMapper);
    
    final RuntimeException actualRuntimeException =
        Assertions.assertThrows(
            RuntimeException.class,
            () -> modifierResource.getModifier()
        );
    assertThat(actualRuntimeException.getMessage()).isEqualTo(expectedMsg);
  }
  
  /**
   * TODO: Should really the exception NOT be propagated?
   * @throws JsonProcessingException 
   */
  @Test
  public void when_getModifier_and_the_ObjectMapper_throws_a_JsonProcessingException_then_no_exception_should_be_propagated() throws JsonProcessingException {

    enteringTestHeaderLogger.debug(null);

    final ModifierResource modifierResource = new ModifierResource();

    modifierResource.setFarBackendConfig(FarBackendConfig.getSingleton());

    final String expectedModifier = "fairly good";
    final ModifierDo expectedModifierDo = new ModifierDo(19, expectedModifier);
    final Optional<ModifierDo> expectedOptionalModifierDo =
        Optional.of(expectedModifierDo);
    doReturn(expectedOptionalModifierDo).when(mockedRepository).getModifierDo();
    modifierResource.setRepository(this.mockedRepository);

    doThrow(JsonProcessingException.class).when(this.mockedObjectMapper).writeValueAsString(Mockito.any(ModifierDo.class));
    modifierResource.setObjectMapper(this.mockedObjectMapper);

    assertThatCode(() -> modifierResource.getModifier()).doesNotThrowAnyException();
  }
  
  /**
   * TODO: Should really the exception NOT be propagated?
   * TODO: Should really a null string be returned?????
   * @throws JsonProcessingException 
   */
  @Test
  public void when_getModifier_and_the_ObjectMapper_throws_a_JsonProcessingException_then_a_null_modifier_should_be_returned() throws JsonProcessingException {
    
    enteringTestHeaderLogger.debug(null);

    final ModifierResource modifierResource = new ModifierResource();
    
    modifierResource.setFarBackendConfig(FarBackendConfig.getSingleton());

    final String expectedModifier = "fairly good";
    final ModifierDo expectedModifierDo = new ModifierDo(19, expectedModifier);
    final Optional<ModifierDo> expectedOptionalModifierDo =
        Optional.of(expectedModifierDo);
    doReturn(expectedOptionalModifierDo).when(this.mockedRepository).getModifierDo();
    modifierResource.setRepository(this.mockedRepository);

    doThrow(JsonProcessingException.class).when(this.mockedObjectMapper).writeValueAsString(Mockito.any(ModifierDo.class));
    modifierResource.setObjectMapper(this.mockedObjectMapper);
    
    final String modifier = modifierResource.getModifier();

    assertThat(modifier).isNull();
  }

  @Test
  public void when_getFarBackendConfig_and_the_config_is_not_set_then_an_instance_should_implicitly_be_created() {
    
    enteringTestHeaderLogger.debug(null);
    
    final ModifierResource modifierResource = new ModifierResource();
    
    modifierResource.setFarBackendConfig(null);
    
    final IFarBackendConfig farBackendConfig = modifierResource.getFarBackendConfig();
    assertThat(farBackendConfig).isNotNull();
  }

  @Test
  public void when_getFarBackendConfig_and_and_the_config_is_not_set_then_an_instance_should_implicitly_be_created_without_an_exception() {
    
    enteringTestHeaderLogger.debug(null);
    
    final ModifierResource modifierResource = new ModifierResource();
    modifierResource.setFarBackendConfig(null);
    
    assertThatCode(() -> modifierResource.getFarBackendConfig()).doesNotThrowAnyException();
  }
}