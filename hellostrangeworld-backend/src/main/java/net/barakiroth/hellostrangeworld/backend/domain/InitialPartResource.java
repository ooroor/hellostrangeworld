package net.barakiroth.hellostrangeworld.backend.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.prometheus.client.Gauge;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import net.barakiroth.hellostrangeworld.backend.BackendConfig;
import net.barakiroth.hellostrangeworld.backend.IBackendConfig;
import net.barakiroth.hellostrangeworld.backend.consumer.ModifierConsumer;
import net.barakiroth.hellostrangeworld.backend.consumer.ModifierDo;
import net.barakiroth.hellostrangeworld.common.infrastructure.prometheus.IPrometheusConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SwaggerDefinition(
    info = @Info(
      title       = "Oppgave API",
      version     = "1",
      description =   "Service for supplying a modifier to be used in a greeting."
                    + "It is assumed that the header <strong>\"X-Correlation-ID\"</strong> is set. "
                    + "Generate it e.g. by the following Javva call: UUID.randomUUID()",
      contact = @Contact(name = "John Doe")
    )
)
public class InitialPartResource {

  private static final Logger log =
      LoggerFactory.getLogger(InitialPartResource.class);
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");

  private IBackendConfig   backendConfig;
  private ModifierConsumer modifierConsumer;
  private ObjectMapper objectMapper;

  public InitialPartResource() {
    this(InitialPartResource.createBackendConfig());
  }

  public InitialPartResource(final IBackendConfig backendConfig) {
    setBackendConfig(backendConfig);
  }

  private static IBackendConfig createBackendConfig() {
    return BackendConfig.getSingleton();
  }

  private static ModifierConsumer createModifierConsumer() {
    return new ModifierConsumer();
  }

  private static ObjectMapper createObjectMapper() {
    return new ObjectMapper();
  }
  
  /**
   * REST API resource, getting the initial part of a greeting.
   * 
   * @return the initial part to be used in a greeting.
   */
  @GET
  @Path("/InitialPart")
  @Produces({MediaType.APPLICATION_JSON})
  @ApiOperation(
      value    = "Retrieves the initial part of a greeting",
      response = InitialPartDo.class
  )
  @ApiImplicitParams(
      {
        @ApiImplicitParam(
            name      = "X-Correlation-ID",
            required  = true,
            dataType  = "string",
            paramType = "header")
      }
  )
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Server error")
      }
  )
  public String getInitialPart() {
    
    enteringMethodHeaderLogger.debug(null);

    final IPrometheusConfig prometheusConfig =
        getBackendConfig().getPrometheusConfig();
    final Gauge.Timer getInitialPartDurationGaugeTimer =
            prometheusConfig.getGetResourceDurationGauge().startTimer();
    
    final ModifierConsumer modifierConsumer = getModifierConsumer();
    final String interjection = "Hello";
    final ModifierDo modifierDo = modifierConsumer.getModifierDo();
    
    final InitialPartDo initialPartDo = new InitialPartDo(interjection + ", " + modifierDo.getModifier());
    
    final ObjectMapper objectMapper = getObjectMapper();

    String initialPartAsJson = null;
    try {
      initialPartAsJson = objectMapper.writeValueAsString(initialPartDo);
      prometheusConfig.getLastGetResourceSuccessGauge().setToCurrentTime();
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block, return a outcome code or rethrow instead.
      e.printStackTrace();
    } finally {
      getInitialPartDurationGaugeTimer.setDuration();
    }
    
    log.info("About to respond:\n\n{}", initialPartAsJson);
    
    leavingMethodHeaderLogger.debug(null);;
    
    return initialPartAsJson;
  }
  
  void setModifierConsumer(final ModifierConsumer modifierConsumer) {
    this.modifierConsumer = modifierConsumer;
  }
  
  ModifierConsumer getModifierConsumer() {
    
    enteringMethodHeaderLogger.debug(null);
    
    if (this.modifierConsumer == null) {
      final ModifierConsumer modifierConsumer =
          InitialPartResource.createModifierConsumer();
      setModifierConsumer(modifierConsumer);
    }
    
    leavingMethodHeaderLogger.debug(null);;
    
    return this.modifierConsumer;
  }
  
  void setBackendConfig(final IBackendConfig backendConfig) {
    this.backendConfig = backendConfig;
  }
  
  IBackendConfig getBackendConfig() {
    
    enteringMethodHeaderLogger.debug(null);
    
    if (this.backendConfig == null) {
      final IBackendConfig backendConfig =
          InitialPartResource.createBackendConfig();
      setBackendConfig(backendConfig);
    }
    
    leavingMethodHeaderLogger.debug(null);;
    
    return this.backendConfig;
  }
  
  void setObjectMapper(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }
  
  ObjectMapper getObjectMapper() {
    
    enteringMethodHeaderLogger.debug(null);
    
    if (this.objectMapper == null) {
      final ObjectMapper objectMapper =
          InitialPartResource.createObjectMapper();
      setObjectMapper(objectMapper);
    }
    
    leavingMethodHeaderLogger.debug(null);;
    
    return this.objectMapper;
  }
  
}