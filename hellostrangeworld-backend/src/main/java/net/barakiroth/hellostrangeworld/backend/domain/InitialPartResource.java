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
import net.barakiroth.hellostrangeworld.backend.infrastructure.prometheus.PrometheusConfig;
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
  
  private final IBackendConfig config;
  
  public InitialPartResource() {
    this(BackendConfig.getSingletonInstance());
  }
  
  public InitialPartResource(final IBackendConfig config) {
    this.config = config;
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
    
    final PrometheusConfig prometheusConfig = this.config.getPrometheusConfig();
    final Gauge.Timer getInitialPartDurationGaugeTimer =
            prometheusConfig.getGetInitialPartDurationGauge().startTimer();
    
    final ModifierConsumer modifierConsumer = new ModifierConsumer();
    final String interjection = "Hello";
    final ModifierDo modifierDo = modifierConsumer.getModifierDo();
    
    // TODO: Name object and string consistently across layers.
    final InitialPartDo initialPart = new InitialPartDo(interjection + ", " + modifierDo.getModifier());
    
    final ObjectMapper objectMapper = new ObjectMapper();

    String initialPartAsJson = null;
    try {
      initialPartAsJson = objectMapper.writeValueAsString(initialPart);
      prometheusConfig.getLastGetInitialPartSuccessGauge().setToCurrentTime();
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block, return a outcome code instead.
      e.printStackTrace();
    } finally {
      getInitialPartDurationGaugeTimer.setDuration();
    }
    
    log.debug("About to respond:\n\n{}", initialPartAsJson);
    
    leavingMethodHeaderLogger.debug(null);;
    
    return initialPartAsJson;
  }
}