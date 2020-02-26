package net.barakiroth.hellostrangeworld.farbackend;

import net.barakiroth.hellostrangeworld.common.IConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.IJettyManagerConfig;
import net.barakiroth.hellostrangeworld.farbackend.domain.Repository;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.DatabaseConfig;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.prometheus.PrometheusConfig;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer.JettyManager;

public interface IFarBackendConfig extends IConfig {

	DatabaseConfig      getDatabaseConfig();
	Database            getDatabase();
	Repository          getRepository();
	JettyManager        getJettyManager();
	IJettyManagerConfig getJettyManagerConfig();
	PrometheusConfig    getPrometheusConfig();
}