package net.barakiroth.hellostrangeworld.frontend;

import java.text.MessageFormat;
import net.barakiroth.hellostrangeworld.frontend.consumer.InitialPartConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);
	private static final Logger enteringMethodHeaderLogger = LoggerFactory.getLogger("EnteringMethodHeader");
	private static final Logger leavingMethodHeaderLogger = LoggerFactory.getLogger("LeavingMethodHeader");

	private static final Main main = new Main();

	private IFrontendConfig     frontendConfig;
	private GreeteePrompter     greeteePrompter;
	private InitialPartConsumer initialPartConsumer;

	private Main() {
	}

	public static void main(final String[] args) {

		enteringMethodHeaderLogger.debug(null);

		logger.debug("Parameters received: {}", (Object[]) args);

		final Main main = getSingletonInstance();
		main.run();

		leavingMethodHeaderLogger.debug(null);
	}

	static Main getSingletonInstance() {
		return Main.main;
	}
    
    private static IFrontendConfig createFrontendConfig() {
      return FrontendConfig.getSingletonInstance();
    }
    
    private static GreeteePrompter createGreeteePrompter(final IFrontendConfig frontendConfig) {
      final GreeteePrompter greeteePrompter = frontendConfig.getGreeteePrompter();
      return greeteePrompter;
    }

    private static InitialPartConsumer createInitialPartConsumer(final IFrontendConfig config) {
      return config.getInitialPartConsumer();
    }

    void setGreeteePrompter(final GreeteePrompter greeteePrompter) {
        this.greeteePrompter = greeteePrompter;
    }

    GreeteePrompter getGreeteePrompter(final IFrontendConfig frontendConfig) {
        if (this.greeteePrompter == null) {
            final GreeteePrompter greeteePrompter = Main.createGreeteePrompter(frontendConfig);
            setGreeteePrompter(greeteePrompter);
        }
        return this.greeteePrompter;
    }

    IFrontendConfig getFrontendConfig() {
        if (this.frontendConfig == null) {
            final IFrontendConfig frontendConfig = Main.createFrontendConfig();
            setFrontendConfig(frontendConfig);
        }
        return this.frontendConfig;
    }

    void setInitialPartConsumer(final InitialPartConsumer initialPartConsumer) {
        this.initialPartConsumer = initialPartConsumer;
    }
    
    InitialPartConsumer getInitialPartConsumer(final IFrontendConfig config) {
        if (this.initialPartConsumer == null) {
            final InitialPartConsumer initialPartConsumer = Main.createInitialPartConsumer(config);
            setInitialPartConsumer(initialPartConsumer);
        }
        return this.initialPartConsumer;
    }

    private void run() {

        enteringMethodHeaderLogger.debug(null);

        final IFrontendConfig config = getFrontendConfig();

        final GreeteePrompter greeteePrompter = getGreeteePrompter(config);
        final String greetee = greeteePrompter.getGreetee();

        final InitialPartConsumer initialPartConsumer = getInitialPartConsumer(config);
        final String initialPart = initialPartConsumer.getInitialPartDo().getInitialPart();
        final String greeting = MessageFormat.format(initialPart + " {0}!", greetee);

        System.err.println(greeting);

        leavingMethodHeaderLogger.debug(null);
    }

    private void setFrontendConfig(final IFrontendConfig frontendConfig) {
        this.frontendConfig = frontendConfig;
    }
}