package net.barakiroth.hellostrangeworld.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AccessLevel;
import lombok.Getter;
import net.barakiroth.hellostrangeworld.common.AbstractConfig;
import net.barakiroth.hellostrangeworld.frontend.consumer.InitialPartConsumer;

public class FrontendConfig extends AbstractConfig implements IFrontendConfig {

  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");

  @Getter(AccessLevel.PUBLIC)
  private static final FrontendConfig singletonInstance = new FrontendConfig();

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
