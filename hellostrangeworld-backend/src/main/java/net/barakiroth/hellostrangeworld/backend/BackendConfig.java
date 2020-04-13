package net.barakiroth.hellostrangeworld.backend;

import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.common.CommonConfig;

public class BackendConfig extends CommonConfig implements IBackendConfig {

  @Getter(AccessLevel.PUBLIC)
  private static final IBackendConfig singleton = new BackendConfig();

  private BackendConfig() {
    super();
  }
}