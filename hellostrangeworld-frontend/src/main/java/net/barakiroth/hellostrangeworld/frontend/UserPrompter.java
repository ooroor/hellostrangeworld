package net.barakiroth.hellostrangeworld.frontend;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserPrompter {
  
  private static final Logger enteringMethodHeaderLogger =
      LoggerFactory.getLogger("EnteringMethodHeader");
  private static final Logger leavingMethodHeaderLogger =
      LoggerFactory.getLogger("LeavingMethodHeader");
  
  /**
   * Print a prompt and retrieve the user's greetings object.
   * @return the greetings object.
   */
  public String askWhomTheUserWantsToGreet() {
      
    enteringMethodHeaderLogger.debug(null);
      
    System.out.print("Whom do you want to greet? ");
    Scanner scanner = null;
    final String greetee;
    try {
      scanner = new Scanner(System.in);
      greetee = scanner.next();
    } finally {
      scanner.close();
    }
      
    leavingMethodHeaderLogger.debug(null);
      
    return greetee;
  }
}
