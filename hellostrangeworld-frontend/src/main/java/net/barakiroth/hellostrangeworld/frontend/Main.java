package net.barakiroth.hellostrangeworld.frontend;

import java.text.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.barakiroth.hellostrangeworld.frontend.consumer.InitialPartConsumer;

public class Main {
  
  private static final Logger logger =
      LoggerFactory.getLogger(Main.class);
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");

  GreeteePrompter     greeteePrompter;
  InitialPartConsumer initialPartConsumer;

  private static final Main main = new Main();
  
  private Main() {}
  
  public static void main(final String[] args) {
    
    enteringMethodHeaderLogger.debug(null);
    
    logger.debug("Parameters received: {}", (Object[])args);
    
    final Main main = getSingletonInstance();
    main.run();
      
    leavingMethodHeaderLogger.debug(null);
  }
  
  static Main getSingletonInstance() {
    return Main.main;
  }

  private void run() {
    
    enteringMethodHeaderLogger.debug(null);
    
    final GreeteePrompter greeteePrompter = getGreeteePrompter();
    final String greetee = greeteePrompter.getGreetee();
    
    final InitialPartConsumer initialPartConsumer = getInitialPartConsumer();
    final String initialPart = initialPartConsumer.getInitialPartDo().getInitialPart();
    final String greeting = MessageFormat.format(initialPart + " {0}!", greetee);
        
    System.err.println(greeting);
      
    leavingMethodHeaderLogger.debug(null);
  }
  
  void setGreeteePrompter(final GreeteePrompter userPrompter) {
    this.greeteePrompter = userPrompter;
  }
  
  GreeteePrompter getGreeteePrompter() {
    if (this.greeteePrompter == null) {
      setGreeteePrompter(new GreeteePrompter());
    }
    return this.greeteePrompter;
  }

  void setInitialPartConsumer(final InitialPartConsumer initialPartConsumer) {
    this.initialPartConsumer = initialPartConsumer;
  }
  
  InitialPartConsumer getInitialPartConsumer() {
    if (this.initialPartConsumer == null) {
      setInitialPartConsumer(new InitialPartConsumer());
    }
    return this.initialPartConsumer;
  }
  
}