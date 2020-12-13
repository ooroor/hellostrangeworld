package net.barakiroth.hellostrangeworld.farbackend.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static io.restassured.RestAssured.given;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.IJettyManagerConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.JettyManager;
import net.barakiroth.hellostrangeworld.farbackend.FarBackendConfig;
import net.barakiroth.hellostrangeworld.farbackend.IFarBackendConfig;
import net.barakiroth.hellostrangeworld.farbackend.ITestConst;
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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Ref.:
 * https://phauer.com/2016/testing-restful-services-java-best-practices/#use-assertj-to-check-the-returned-pojos
 */
@Tag(ITestConst.INTEGRATION_TEST_ANNOTATION)
@ExtendWith(MockitoExtension.class)
public class ModifierResourceIntegrationTest {

  private static RequestSpecification requestSpecification =
      new RequestSpecBuilder()
        .setContentType(ContentType.JSON)
        .setBaseUri("http://localhost:8099/")
        // log request and response for better debugging.
        // You can also only log if a requests fails.
        .addFilter(new ResponseLoggingFilter()) 
        .addFilter(new RequestLoggingFilter())
        .build();

  @Mock
  private IFarBackendConfig mockedFarBackendConfig;

  @Mock
  private ObjectMapper mockedObjectMapper;

  @Mock
  private Repository mockedRepository;

  @BeforeAll
  static void beforeAll() {
    ModifierResourceIntegrationTest.startDatabase();
    ModifierResourceIntegrationTest.startServletContainer();
  }

  @AfterAll
  static void afterAll() {
    ModifierResourceIntegrationTest.stopServletContainer();
    ModifierResourceIntegrationTest.stopDatabase();
  }
  
  @Test
  void when_getting_the_modifier_resource_then_it_must_be_in_the_expected_ones() {

    ITestConst.enteringTestHeaderLogger.debug(null);
    
    final Set<ModifierDo> expectedModifierDos = createExpectedModifierDos();
    final ModifierDo actualModifierDo = createResource2("/api/Modifier", ModifierDo.class);

    assertThat(actualModifierDo).isIn(expectedModifierDos);
  }
  
  @Test
  void when_instantiating_without_parms_then_the_config_file_should_be_implicitly_created_without_an_exception() {

    ITestConst.enteringTestHeaderLogger.debug(null);

    assertThatCode(() -> new ModifierResource()).doesNotThrowAnyException();
  }

  @Test
  void when_asking_for_a_modifier_then_one_such_with_expected_values_should_be_received()
      throws UnsupportedOperationException, IOException {

    ITestConst.enteringTestHeaderLogger.debug(null);

    ITestConst.enteringTestHeaderLogger.debug(null);

    final CloseableHttpClient httpClient =
        Assertions.assertDoesNotThrow(() -> HttpClients.createDefault());
    final String urlString = "http://localhost:8099/api/Modifier";
    final URI endpointUri = Assertions.assertDoesNotThrow(() -> new URIBuilder(urlString).build());
    final HttpUriRequest request = new HttpGet(endpointUri);
    final CloseableHttpResponse response =
        Assertions.assertDoesNotThrow(() -> httpClient.execute(request));
    final HttpEntity httpEntity = response.getEntity();
    final ObjectMapper objectMapper = new ObjectMapper();
    final ModifierDo modifierDo = Assertions.assertDoesNotThrow(
        () -> objectMapper.readValue(httpEntity.getContent(), ModifierDo.class));
    final int id = modifierDo.getId();
    final String modifier = modifierDo.getModifier();

    assertThat(id).isIn(1, 2, 3);
    assertThat(modifier).isIn("very strange", "strange", "immensely strange");
  }

  /**
   * TODO: Should really the exception be propagated?
   * 
   * @throws JsonProcessingException
   */
  @Test
  public void when_getModifier_and_the_ObjectMapper_throws_an_unexpected_exception_then_the_exception_should_be_propagated()
      throws JsonProcessingException {

    ITestConst.enteringTestHeaderLogger.debug(null);

    final ModifierResource modifierResource = new ModifierResource();

    modifierResource.setFarBackendConfig(FarBackendConfig.getSingleton());

    final String expectedModifier = "fairly good";
    final ModifierDo expectedModifierDo = new ModifierDo(19, expectedModifier);
    final Optional<ModifierDo> expectedOptionalModifierDo = Optional.of(expectedModifierDo);
    doReturn(expectedOptionalModifierDo).when(mockedRepository).getModifierDo();
    modifierResource.setRepository(this.mockedRepository);

    final String expectedMsg = "Hello test!";
    final RuntimeException runtimeException = new RuntimeException(expectedMsg);
    doThrow(runtimeException).when(this.mockedObjectMapper)
        .writeValueAsString(Mockito.any(ModifierDo.class));

    modifierResource.setObjectMapper(this.mockedObjectMapper);

    final RuntimeException actualRuntimeException =
        Assertions.assertThrows(RuntimeException.class, () -> modifierResource.getModifier());
    assertThat(actualRuntimeException.getMessage()).isEqualTo(expectedMsg);
  }

  /**
   * TODO: Should really the exception NOT be propagated?
   * 
   * @throws JsonProcessingException
   */
  @Test
  public void when_getModifier_and_the_ObjectMapper_throws_a_JsonProcessingException_then_no_exception_should_be_propagated()
      throws JsonProcessingException {

    ITestConst.enteringTestHeaderLogger.debug(null);

    final ModifierResource modifierResource = new ModifierResource();

    modifierResource.setFarBackendConfig(FarBackendConfig.getSingleton());

    final String expectedModifier = "fairly good";
    final ModifierDo expectedModifierDo = new ModifierDo(19, expectedModifier);
    final Optional<ModifierDo> expectedOptionalModifierDo = Optional.of(expectedModifierDo);
    doReturn(expectedOptionalModifierDo).when(mockedRepository).getModifierDo();
    modifierResource.setRepository(this.mockedRepository);

    doThrow(JsonProcessingException.class).when(this.mockedObjectMapper)
        .writeValueAsString(Mockito.any(ModifierDo.class));
    modifierResource.setObjectMapper(this.mockedObjectMapper);

    assertThatCode(() -> modifierResource.getModifier()).doesNotThrowAnyException();
  }

  /**
   * TODO: Should really the exception NOT be propagated? TODO: Should really a null string be
   * returned?????
   * 
   * @throws JsonProcessingException
   */
  @Test
  public void when_getModifier_and_the_ObjectMapper_throws_a_JsonProcessingException_then_a_null_modifier_should_be_returned()
      throws JsonProcessingException {

    ITestConst.enteringTestHeaderLogger.debug(null);

    final ModifierResource modifierResource = new ModifierResource();

    modifierResource.setFarBackendConfig(FarBackendConfig.getSingleton());

    final String expectedModifier = "fairly good";
    final ModifierDo expectedModifierDo = new ModifierDo(19, expectedModifier);
    final Optional<ModifierDo> expectedOptionalModifierDo = Optional.of(expectedModifierDo);
    doReturn(expectedOptionalModifierDo).when(this.mockedRepository).getModifierDo();
    modifierResource.setRepository(this.mockedRepository);

    doThrow(JsonProcessingException.class).when(this.mockedObjectMapper)
        .writeValueAsString(Mockito.any(ModifierDo.class));
    modifierResource.setObjectMapper(this.mockedObjectMapper);

    final String modifier = modifierResource.getModifier();

    assertThat(modifier).isNull();
  }

  @Test
  public void when_getFarBackendConfig_and_the_config_is_not_set_then_an_instance_should_implicitly_be_created() {

    ITestConst.enteringTestHeaderLogger.debug(null);

    final ModifierResource modifierResource = new ModifierResource();

    modifierResource.setFarBackendConfig(null);

    final IFarBackendConfig farBackendConfig = modifierResource.getFarBackendConfig();
    assertThat(farBackendConfig).isNotNull();
  }

  @Test
  public void when_getFarBackendConfig_and_and_the_config_is_not_set_then_an_instance_should_implicitly_be_created_without_an_exception() {

    ITestConst.enteringTestHeaderLogger.debug(null);

    final ModifierResource modifierResource = new ModifierResource();
    modifierResource.setFarBackendConfig(null);

    assertThatCode(() -> modifierResource.getFarBackendConfig()).doesNotThrowAnyException();
  }

  private static void startServletContainer() {

    final IFarBackendConfig farBackendConfig = FarBackendConfig.getSingleton();
    final IJettyManagerConfig jettyManagerConfig = farBackendConfig.getJettyManagerConfig();
    final JettyManager jettyManager = jettyManagerConfig.getJettyManager();
    if (!jettyManager.isStarted()) {
      jettyManager.start();
    }
  }

  private static void stopServletContainer() {

    final IFarBackendConfig farBackendConfig = FarBackendConfig.getSingleton();
    final IJettyManagerConfig jettyManagerConfig = farBackendConfig.getJettyManagerConfig();
    final JettyManager jettyManager = jettyManagerConfig.getJettyManager();
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
  
  private <T> T createResource2(final String path, final Class<T> responseClass) {
    return 
        given()
          .spec(requestSpecification)
          .when()
          .get(path)
          .then()
          .statusCode(200)
          .extract()
          .as(responseClass);
  }

  private Set<ModifierDo> createExpectedModifierDos() {

    final Set<ModifierDo> expectedModifierDos = new HashSet<ModifierDo>();
    expectedModifierDos.add(new ModifierDo(1, "strange"));
    expectedModifierDos.add(new ModifierDo(2, "very strange"));
    expectedModifierDos.add(new ModifierDo(3, "immensely strange"));

    return expectedModifierDos;
  }
}