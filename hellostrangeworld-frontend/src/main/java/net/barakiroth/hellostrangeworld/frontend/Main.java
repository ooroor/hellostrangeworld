package net.barakiroth.hellostrangeworld.frontend;

import java.text.MessageFormat;
import net.barakiroth.hellostrangeworld.frontend.consumer.InitialPartConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);
	private static final Logger enteringMethodHeaderLogger = LoggerFactory.getLogger("EnteringMethodHeader");
	private static final Logger leavingMethodHeaderLogger = LoggerFactory.getLogger("LeavingMethodHeader");

	private static final Main singleton = new Main();

	private IFrontendConfig     frontendConfig;

	private Main() {
	}

	public static void main(final String[] args) {

		enteringMethodHeaderLogger.debug(null);

		logger.debug("Parameters received: {}", (Object[]) args);

		final Main main = getSingleton();
		main.run();

		leavingMethodHeaderLogger.debug(null);
	}

	static Main getSingleton() {
		return Main.singleton;
	}
    
    private static IFrontendConfig createFrontendConfig() {
      return FrontendConfig.getSingleton();
    }

    GreeteePrompter getGreeteePrompter(final IFrontendConfig frontendConfig) {
        return frontendConfig.getGreeteePrompter();
    }

    void setFrontendConfig(final IFrontendConfig frontendConfig) {
        this.frontendConfig = frontendConfig;
    }

    IFrontendConfig getFrontendConfig() {
        if (this.frontendConfig == null) {
            final IFrontendConfig frontendConfig = Main.createFrontendConfig();
            setFrontendConfig(frontendConfig);
        }
        return this.frontendConfig;
    }
    
    InitialPartConsumer getInitialPartConsumer(final IFrontendConfig frontendConfig) {
        return frontendConfig.getInitialPartConsumer();
    }

    private void run() {

        enteringMethodHeaderLogger.debug(null);

        final IFrontendConfig frontendConfig = getFrontendConfig();

        final GreeteePrompter greeteePrompter = getGreeteePrompter(frontendConfig);
        final String greetee = greeteePrompter.getGreetee();

        final InitialPartConsumer initialPartConsumer = getInitialPartConsumer(frontendConfig);
        final String initialPart = initialPartConsumer.getInitialPartDo().getInitialPart();
        final String greeting = MessageFormat.format(initialPart + " {0}!", greetee);

        System.err.println(greeting);

        leavingMethodHeaderLogger.debug(null);
    }
}