package net.barakiroth.hellostrangeworld.backend;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.MessageFormat;
import net.barakiroth.hellostrangeworld.backend.HelloStrangeWorldApp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloStrangeWorldAppTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;
  private final InputStream originalIn = System.in;

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  /**
   * Reset the standard and error ins and outs.
   */
  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
    System.setIn(originalIn);
  }
  
  @Test
  public void should_print_correct_string_to_stdout_unconditionally() {
    
    enteringTestHeaderLogger.debug(null);
    
    final String expectedGreetee = "universe";
    final ByteArrayInputStream in = new ByteArrayInputStream(expectedGreetee.getBytes());
    System.setIn(in);
    HelloStrangeWorldApp.main(new String[] {"Some rubbish"});
    
    final String expectedString1 =
        MessageFormat.format("Hello strange {0}!\r\n", expectedGreetee);
    final String expectedString2 =
        MessageFormat.format("Hello very strange {0}!\r\n", expectedGreetee);
    final String expectedString3 =
        MessageFormat.format("Hello immensely strange {0}!\r\n", expectedGreetee);
    
    assertThat(
        errContent.toString(),
        anyOf(is(expectedString1), 
              is(expectedString2), 
              is(expectedString3)));
  }

  @Test
  public void should_not_throw_when_instantiating() {
    
    enteringTestHeaderLogger.debug(null);
    
    assertDoesNotThrow(() -> new HelloStrangeWorldApp());
  }
}