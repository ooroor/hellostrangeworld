package net.barakiroth.hellostrangeworld.backend.consumer;

import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import net.barakiroth.hellostrangeworld.backend.BackendConfig;
import net.barakiroth.hellostrangeworld.backend.IBackendConfig;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModifierConsumer {
  
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");
  
  private final IBackendConfig config;
  
  public ModifierConsumer() {
    this(BackendConfig.getSingletonInstance());
  }
  
  public ModifierConsumer(final IBackendConfig config) {
    super();
    this.config = config;
  }

  /**
   * Returns the modifier as a string, like e.g. "very strange".
   * @return the modifier as a string, like e.g. "very strange".
   */
  public ModifierDo getModifierDo() {
    
    enteringMethodHeaderLogger.debug(null);

    final ModifierDo modifierDo;
    try {
      final CloseableHttpClient httpClient = HttpClients.createDefault();
      final String urlString = 
          this.config.getModifierResourceEndpointUriString();
      final URI endpointUri = new URIBuilder(urlString).build();
      final HttpUriRequest request = new HttpGet(endpointUri);
      request.addHeader(ACCEPT, APPLICATION_JSON.getMimeType());
      final CloseableHttpResponse response = httpClient.execute(request);
      final HttpEntity httpEntity = response.getEntity();
      final ObjectMapper objectMapper = new ObjectMapper();
      modifierDo =
          objectMapper
            .readValue(httpEntity.getContent(), ModifierDo.class);
    } catch (UnsupportedOperationException | URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }

    leavingMethodHeaderLogger.debug(null);
    
    return modifierDo;    
  }
}