package net.barakiroth.hellostrangeworld.farbackend;

import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.common.AbstractConfig;
import net.barakiroth.hellostrangeworld.farbackend.domain.Repository;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.DatabaseConfig;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.prometheus.PrometheusConfig;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer.JettyManager;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer.JettyManagerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FarBackendConfig extends AbstractConfig implements IFarBackendConfig {

	private static final Logger log = LoggerFactory.getLogger(FarBackendConfig.class);
	private static final Logger enteringMethodHeaderLogger = LoggerFactory.getLogger("EnteringMethodHeader");
	private static final Logger leavingMethodHeaderLogger = LoggerFactory.getLogger("LeavingMethodHeader");

	@Getter(AccessLevel.PUBLIC)
	private static final FarBackendConfig singletonInstance = new FarBackendConfig();

	@Getter(AccessLevel.PUBLIC)
	private final DatabaseConfig databaseConfig;

	@Getter(AccessLevel.PUBLIC)
	private final Database database;

	@Getter(AccessLevel.PUBLIC)
	private final Repository repository;

	@Getter(AccessLevel.PUBLIC)
	private final JettyManagerConfig jettyManagerConfig;

	@Getter(AccessLevel.PUBLIC)
	private final JettyManager jettyManager;

	@Getter(AccessLevel.PUBLIC)
	private final PrometheusConfig prometheusConfig;

	private FarBackendConfig() {

		super();

		enteringMethodHeaderLogger.debug(null);

		this.jettyManagerConfig = JettyManagerConfig.createSingletonInstance(this);
		this.jettyManager = JettyManager.createSingletonInstance(this);
		this.databaseConfig = new DatabaseConfig(this);
		this.database = new Database(this);
		this.repository = new Repository(this);
		this.prometheusConfig = PrometheusConfig.createSingletonInstance(this);

		leavingMethodHeaderLogger.debug(null);
	}
}