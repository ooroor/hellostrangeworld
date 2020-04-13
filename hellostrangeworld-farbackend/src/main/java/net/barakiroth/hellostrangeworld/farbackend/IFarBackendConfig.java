package net.barakiroth.hellostrangeworld.farbackend;

import net.barakiroth.hellostrangeworld.common.ICommonConfig;
import net.barakiroth.hellostrangeworld.common.IGeneralConfig;
import net.barakiroth.hellostrangeworld.farbackend.domain.Repository;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.DatabaseConfig;

public interface IFarBackendConfig extends IGeneralConfig, ICommonConfig {
	DatabaseConfig      getDatabaseConfig();
	Database            getDatabase();
	Repository          getRepository();
}