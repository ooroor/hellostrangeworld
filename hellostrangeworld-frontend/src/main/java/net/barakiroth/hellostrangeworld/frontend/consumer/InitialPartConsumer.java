package net.barakiroth.hellostrangeworld.frontend.consumer;

import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import net.barakiroth.hellostrangeworld.frontend.FrontendConfig;
import net.barakiroth.hellostrangeworld.frontend.IFrontendConfig;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitialPartConsumer {

	private static final Logger enteringMethodHeaderLogger = LoggerFactory.getLogger("EnteringMethodHeader");
	private static final Logger leavingMethodHeaderLogger = LoggerFactory.getLogger("LeavingMethodHeader");

	private final IFrontendConfig frontendConfig;

	public InitialPartConsumer() {
		this(FrontendConfig.getSingleton());
	}

	public InitialPartConsumer(final IFrontendConfig frontendConfig) {
		super();
		this.frontendConfig = frontendConfig;
	}

	public InitialPartDo getInitialPartDo() {

		enteringMethodHeaderLogger.debug(null);

		final InitialPartDo initialPartDo;
		try {
			final CloseableHttpClient httpClient = HttpClients.createDefault();
			final String urlString = this.frontendConfig.getDownstreamResourceEndpointUriString();
			final URI endpointUri = new URIBuilder(urlString).build();
			final HttpUriRequest request = new HttpGet(endpointUri);
			request.addHeader(ACCEPT, APPLICATION_JSON.getMimeType());
			final CloseableHttpResponse response = httpClient.execute(request);
			final HttpEntity httpEntity = response.getEntity();
			final ObjectMapper objectMapper = new ObjectMapper();
			initialPartDo = objectMapper.readValue(httpEntity.getContent(), InitialPartDo.class);
		} catch (UnsupportedOperationException | URISyntaxException | IOException e) {
			throw new RuntimeException(e);
		}

		leavingMethodHeaderLogger.debug(null);

		return initialPartDo;
	}

}
