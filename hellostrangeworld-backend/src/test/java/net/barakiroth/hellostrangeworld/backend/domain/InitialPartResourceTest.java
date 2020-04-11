package net.barakiroth.hellostrangeworld.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.barakiroth.hellostrangeworld.backend.consumer.ModifierConsumer;
import net.barakiroth.hellostrangeworld.backend.consumer.ModifierDo;

@ExtendWith(MockitoExtension.class)
public class InitialPartResourceTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @Mock
  private ModifierConsumer mockedModifierConsumer;
  
  @Mock
  private ObjectMapper mockedObjectMapper;
  
  @Test
  public void when_created_without_parms_then_an_implicit_config_should_be_created_without_an_exception() {
    
    enteringTestHeaderLogger.debug(null);
    
    assertThatCode(() -> new InitialPartResource()).doesNotThrowAnyException();
  }
  
  @Test
  public void when_getInitialPart_then_the_expected_string_should_be_returned() throws JsonProcessingException {
    
    enteringTestHeaderLogger.debug(null);
    
    final InitialPartResource initialPartResource = new InitialPartResource();
    
    final ModifierDo expectedModifierDo = new ModifierDo();
    expectedModifierDo.setId(73);
    final String expectedModifier = "fairly good";
    expectedModifierDo.setModifier(expectedModifier);    
    doReturn(expectedModifierDo).when(mockedModifierConsumer).getModifierDo();
    initialPartResource.setModifierConsumer(mockedModifierConsumer);

    initialPartResource.setObjectMapper(null);
    
    final String initialPartJsonString = initialPartResource.getInitialPart();
    
    final ObjectMapper objectMapper = new ObjectMapper();
    
    final InitialPartDo actualInitialPartDo =
        objectMapper.readValue(initialPartJsonString, InitialPartDo.class);
    
    final String expectedInitialPart = "Hello, " + expectedModifier;
    
    assertThat(actualInitialPartDo.getInitialPart()).isEqualTo(expectedInitialPart);
  }
  
  /**
   * TODO: Should really the exception be propagated?
   * @throws JsonProcessingException 
   */
  @Test
  public void when_getInitialPart_and_the_ObjectMapper_throws_an_unexpected_exception_then_the_exception_should_be_propagated() throws JsonProcessingException {
    
    enteringTestHeaderLogger.debug(null);
    
    final InitialPartResource initialPartResource = new InitialPartResource();
    
    final ModifierDo expectedModifierDo = new ModifierDo();
    expectedModifierDo.setId(73);
    final String expectedModifier = "fairly good";
    expectedModifierDo.setModifier(expectedModifier);    
    doReturn(expectedModifierDo).when(mockedModifierConsumer).getModifierDo();
    initialPartResource.setModifierConsumer(mockedModifierConsumer);
    
    final String expectedMsg = "Hello test!";
    RuntimeException runtimeException = new RuntimeException(expectedMsg);
    
    doThrow(runtimeException).when(mockedObjectMapper).writeValueAsString(Mockito.any(InitialPartDo.class));
    initialPartResource.setObjectMapper(this.mockedObjectMapper);

    final RuntimeException actualRuntimeException =
        Assertions.assertThrows(
            RuntimeException.class,
            () -> initialPartResource.getInitialPart()
        );
    
    assertThat(actualRuntimeException.getMessage()).isEqualTo(expectedMsg);
  }
  
  /**
   * TODO: Should really the exception NOT be propagated?
   * @throws JsonProcessingException 
   */
  @Test
  public void when_getInitialPart_and_the_ObjectMapper_throws_a_JsonProcessingException_then_no_exception_should_be_propagated() throws JsonProcessingException {
    
    enteringTestHeaderLogger.debug(null);
    
    final InitialPartResource initialPartResource = new InitialPartResource();
    
    final ModifierDo expectedModifierDo = new ModifierDo();
    expectedModifierDo.setId(73);
    final String expectedModifier = "fairly good";
    expectedModifierDo.setModifier(expectedModifier);    
    doReturn(expectedModifierDo).when(mockedModifierConsumer).getModifierDo();
    initialPartResource.setModifierConsumer(mockedModifierConsumer);
    
    doThrow(JsonProcessingException.class).when(mockedObjectMapper).writeValueAsString(Mockito.any(InitialPartDo.class));
    initialPartResource.setObjectMapper(this.mockedObjectMapper);

    assertThatCode(() -> initialPartResource.getInitialPart()).doesNotThrowAnyException();
  }
  
  /**
   * TODO: Should really the exception NOT be propagated?
   * TODO: Should really a null string be returned?????
   * @throws JsonProcessingException 
   */
  @Test
  public void when_getInitialPart_and_the_ObjectMapper_throws_a_JsonProcessingException_then_a_null_initial_part_should_be_returned() throws JsonProcessingException {
    
    enteringTestHeaderLogger.debug(null);
    
    final InitialPartResource initialPartResource = new InitialPartResource();
    
    final ModifierDo expectedModifierDo = new ModifierDo();
    expectedModifierDo.setId(73);
    final String expectedModifier = "fairly good";
    expectedModifierDo.setModifier(expectedModifier);    
    doReturn(expectedModifierDo).when(mockedModifierConsumer).getModifierDo();
    initialPartResource.setModifierConsumer(mockedModifierConsumer);
    
    doThrow(JsonProcessingException.class).when(mockedObjectMapper).writeValueAsString(Mockito.any(InitialPartDo.class));
    initialPartResource.setObjectMapper(this.mockedObjectMapper);

    assertThat(initialPartResource.getInitialPart()).isNull();
  }
  
  @Test
  public void when_getBackendConfig_then_the_default_created_should_be_constructed_without_an_exception() throws JsonProcessingException {

    enteringTestHeaderLogger.debug(null);

    final InitialPartResource initialPartResource = new InitialPartResource();
    initialPartResource.setBackendConfig(null);

    assertThatCode(() -> initialPartResource.getBackendConfig()).doesNotThrowAnyException();
  }
  
  @Test
  public void when_getBackendConfig_then_the_default_created_should_be_constructed() throws JsonProcessingException {

    enteringTestHeaderLogger.debug(null);

    final InitialPartResource initialPartResource = new InitialPartResource();
    initialPartResource.setModifierConsumer(null);

    assertThat(initialPartResource.getModifierConsumer()).isNotNull();
  }
  
  
  @Test
  public void when_getModifierConsumer_then_the_default_created_should_be_constructed_without_an_exception() throws JsonProcessingException {

    enteringTestHeaderLogger.debug(null);

    final InitialPartResource initialPartResource = new InitialPartResource();
    initialPartResource.setModifierConsumer(null);

    assertThatCode(() -> initialPartResource.getModifierConsumer()).doesNotThrowAnyException();
  }
  
  @Test
  public void when_getModifierConsumer_then_the_default_created_should_be_constructed() throws JsonProcessingException {

    enteringTestHeaderLogger.debug(null);

    final InitialPartResource initialPartResource = new InitialPartResource();
    initialPartResource.setModifierConsumer(null);

    assertThat(initialPartResource.getModifierConsumer()).isNotNull();
  }
}