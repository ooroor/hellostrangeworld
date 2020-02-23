package net.barakiroth.hellostrangeworld.frontend;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreeteePrompterTest {
  
  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  @Test
  public void should_return_input_when_standard_in_receives_input() {
    
    enteringTestHeaderLogger.debug(null);
    
    final String expectedGreetee = "universe";
    final ByteArrayInputStream in = new ByteArrayInputStream(expectedGreetee.getBytes());
    System.setIn(in);
    final GreeteePrompter greeteePrompter = new GreeteePrompter();
    final String actualGreetee = greeteePrompter.getGreetee();
    assertThat(actualGreetee).isEqualTo(expectedGreetee);
  }
}