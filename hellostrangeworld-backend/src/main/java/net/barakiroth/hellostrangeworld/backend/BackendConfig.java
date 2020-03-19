package net.barakiroth.hellostrangeworld.backend;

import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.common.AbstractConfig;

public class BackendConfig extends AbstractConfig implements IBackendConfig {

  @Getter(AccessLevel.PUBLIC)
  private static final IBackendConfig singletonInstance = new BackendConfig();

  private BackendConfig() {
    super();
  }
}