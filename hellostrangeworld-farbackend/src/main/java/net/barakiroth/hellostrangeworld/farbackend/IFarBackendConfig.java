package net.barakiroth.hellostrangeworld.farbackend;

import net.barakiroth.hellostrangeworld.common.IConfig;
import net.barakiroth.hellostrangeworld.farbackend.domain.Repository;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.DatabaseConfig;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.prometheus.PrometheusConfig;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer.JettyManager;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.servletcontainer.JettyManagerConfig;

public interface IFarBackendConfig extends IConfig {

	DatabaseConfig     getDatabaseConfig();
	Database           getDatabase();
	Repository         getRepository();
	JettyManager       getJettyManager();
	JettyManagerConfig getJettyManagerConfig();
	PrometheusConfig   getPrometheusConfig();
}