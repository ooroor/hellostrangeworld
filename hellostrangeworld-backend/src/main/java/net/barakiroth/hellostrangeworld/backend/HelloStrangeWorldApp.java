package net.barakiroth.hellostrangeworld.backend;

import java.text.MessageFormat;
import net.barakiroth.hellostrangeworld.frontend.UserPrompter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloStrangeWorldApp {
  
  private static final Logger logger =
      LoggerFactory.getLogger(HelloStrangeWorldApp.class);
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");
  
  /**
   * Run the prompter and present the concatenated complete greetings string.
   * 
   * @param args Not used
   */
  public static void main(final String[] args) {
      
    enteringMethodHeaderLogger.debug(null);
      
    logger.debug("Parameters received: {}", (Object[])args);
    
    final UserPrompter userPrompter = new UserPrompter();
    final String greetee = userPrompter.askWhomTheUserWantsToGreet();
    
    final GreetingPhraser greetingPhraser = new GreetingPhraser();
    final String phrasePrefix = greetingPhraser.getGreetingPhrasePrefix();
    
    greetingPhraser.disconnect();
    
    System.err.println(MessageFormat.format(phrasePrefix + "{0}!", greetee));
      
    leavingMethodHeaderLogger.debug(null);
  }
}