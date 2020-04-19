package net.barakiroth.hellostrangeworld.frontend;

import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.common.CommonConfig;
import net.barakiroth.hellostrangeworld.frontend.consumer.InitialPartConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrontendConfig extends CommonConfig implements IFrontendConfig {

  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");

  @Getter(AccessLevel.PUBLIC)
  private static final IFrontendConfig singleton = new FrontendConfig();

  FrontendConfig() {
    super();
  }

  @Override
  public GreeteePrompter getGreeteePrompter() {

    enteringMethodHeaderLogger.debug(null);

    // TODO: Introduce singleton:
    final GreeteePrompter greeteePrompter = new GreeteePrompter();

    leavingMethodHeaderLogger.debug(null);

    return greeteePrompter;
  }

  @Override
  public InitialPartConsumer getInitialPartConsumer() {

    enteringMethodHeaderLogger.debug(null);

    // TODO: Introduce singleton:
    final InitialPartConsumer initialPartConsumer = new InitialPartConsumer();

    leavingMethodHeaderLogger.debug(null);

    return initialPartConsumer;
  }
}
