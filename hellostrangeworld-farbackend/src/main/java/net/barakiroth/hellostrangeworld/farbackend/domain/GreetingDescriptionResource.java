package net.barakiroth.hellostrangeworld.farbackend.domain;

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
import java.util.Optional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import net.barakiroth.hellostrangeworld.farbackend.Config;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.prometheus.PrometheusConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SwaggerDefinition(
    info = @Info(
      title       = "Oppgave API",
      version     = "1",
      description =   "Service for ornamenting a greeting."
                    + "It is assumed that the header <strong>\"X-Correlation-ID\"</strong> is set. "
                    + "Generate it e.g. by the following Javva call: UUID.randomUUID()",
      contact = @Contact(name = "John Doe")
    )
)
public class GreetingDescriptionResource {

  private static final Logger log =
      LoggerFactory.getLogger(GreetingDescriptionResource.class);
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");
  
  private final Config config;
  
  public GreetingDescriptionResource() {
    this(Config.getSingletonInstance());
  }
  
  public GreetingDescriptionResource(final Config config) {
    this.config = config;
  }

  /**
   * REST API resource, to get the adjective to be used in the greeting.
   * 
   * @return the adjective to be used in the greeting.
   */
  @GET
  @Path("/GreetingDescription")
  @Produces({MediaType.APPLICATION_JSON})
  @ApiOperation(
      value    = "Henter en random greeting description",
      response = GreetingDescription.class
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
  public String getGreetingDescription() {
    
    enteringMethodHeaderLogger.debug(null);
    
    final PrometheusConfig prometheusConfig = this.config.getPrometheusConfig();
    final Gauge.Timer getGreetingDescriptionDurationGaugeTimer =
            prometheusConfig.getGetGreetingDescriptionDurationGauge().startTimer();

    final Optional<GreetingDescription> optionalGreetingDescription =
        this.config.getRepository().getGreetingDescription();
    
    final ObjectMapper objectMapper = new ObjectMapper();

    String greetingDescriptionAsJson = null;
    try {
      greetingDescriptionAsJson =
          objectMapper
            .writeValueAsString(
                optionalGreetingDescription
                  .orElse(new GreetingDescription(-1, ""))
            );
      prometheusConfig.getLastGetGreetingDescriptionSuccessGauge().setToCurrentTime();
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block, return a outcome code instead.
      e.printStackTrace();
    } finally {
      getGreetingDescriptionDurationGaugeTimer.setDuration();
    }
    
    log.debug("About to respond:\n\n{}", greetingDescriptionAsJson);
    
    leavingMethodHeaderLogger.debug(null);;
    
    return greetingDescriptionAsJson;
  }
}