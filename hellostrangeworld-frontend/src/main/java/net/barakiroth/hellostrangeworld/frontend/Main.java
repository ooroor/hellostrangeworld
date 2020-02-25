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

	IFrontendConfig     config;
	GreeteePrompter     greeteePrompter;
	InitialPartConsumer initialPartConsumer;

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

	private void run() {

		enteringMethodHeaderLogger.debug(null);

		final IFrontendConfig config = getConfig();

		final GreeteePrompter greeteePrompter = getGreeteePrompter(config);
		final String greetee = greeteePrompter.getGreetee();

		final InitialPartConsumer initialPartConsumer = getInitialPartConsumer(config);
		final String initialPart = initialPartConsumer.getInitialPartDo().getInitialPart();
		final String greeting = MessageFormat.format(initialPart + " {0}!", greetee);

		System.err.println(greeting);

		leavingMethodHeaderLogger.debug(null);
	}

	private void setConfig(final IFrontendConfig config) {
		this.config = config;
	}

	private IFrontendConfig getConfig() {
		if (this.config == null) {
			final IFrontendConfig config = FrontendConfig.getSingletonInstance();
			setConfig(config);
		}
		return this.config;
	}

	void setGreeteePrompter(final GreeteePrompter greeteePrompter) {
		this.greeteePrompter = greeteePrompter;
	}

	GreeteePrompter getGreeteePrompter(final IFrontendConfig config) {
		if (this.greeteePrompter == null) {
			final GreeteePrompter greeteePrompter = config.getGreeteePrompter();
			setGreeteePrompter(greeteePrompter);
		}
		return this.greeteePrompter;
	}

	void setInitialPartConsumer(final InitialPartConsumer initialPartConsumer) {
		this.initialPartConsumer = initialPartConsumer;
	}

	InitialPartConsumer getInitialPartConsumer(final IFrontendConfig config) {
		if (this.initialPartConsumer == null) {
			final InitialPartConsumer initialPartConsumer = config.getInitialPartConsumer();
			setInitialPartConsumer(initialPartConsumer);
		}
		return this.initialPartConsumer;
	}

}