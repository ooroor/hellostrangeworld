package net.barakiroth.hellostrangeworld.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.common.AbstractConfig;

public class FrontendConfig extends AbstractConfig implements IFrontendConfig {

	private static final Logger log = LoggerFactory.getLogger(FrontendConfig.class);
	private static final Logger enteringMethodHeaderLogger = LoggerFactory.getLogger("EnteringMethodHeader");
	private static final Logger leavingMethodHeaderLogger = LoggerFactory.getLogger("LeavingMethodHeader");

	@Getter(AccessLevel.PUBLIC)
	private static final FrontendConfig singletonInstance = new FrontendConfig();

	public FrontendConfig() {
		super();
	}
}
