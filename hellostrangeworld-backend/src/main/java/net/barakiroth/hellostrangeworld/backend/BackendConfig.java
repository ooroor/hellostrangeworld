package net.barakiroth.hellostrangeworld.backend;

import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.backend.infrastructure.prometheus.PrometheusConfig;
import net.barakiroth.hellostrangeworld.backend.infrastructure.servletcontainer.JettyManager;
import net.barakiroth.hellostrangeworld.common.AbstractConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.IJettyManagerConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.JettyManagerConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackendConfig extends AbstractConfig implements IBackendConfig {

	private static final Logger log = LoggerFactory.getLogger(BackendConfig.class);
	private static final Logger enteringMethodHeaderLogger = LoggerFactory.getLogger("EnteringMethodHeader");
	private static final Logger leavingMethodHeaderLogger = LoggerFactory.getLogger("LeavingMethodHeader");

	@Getter(AccessLevel.PUBLIC)
	private static final IBackendConfig singletonInstance = new BackendConfig();

	@Getter(AccessLevel.PUBLIC)
	private final IJettyManagerConfig jettyManagerConfig;

	@Getter(AccessLevel.PUBLIC)
	private final JettyManager jettyManager;

	@Getter(AccessLevel.PUBLIC)
	private final PrometheusConfig prometheusConfig;

	private BackendConfig() {

		super();

		enteringMethodHeaderLogger.debug(null);

		this.jettyManagerConfig = JettyManagerConfig.createSingletonInstance(this);
		this.jettyManager = JettyManager.getSingletonInstance(this);
		this.prometheusConfig = PrometheusConfig.createSingletonInstance(this);

		leavingMethodHeaderLogger.debug(null);
	}
}