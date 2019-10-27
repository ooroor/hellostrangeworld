package net.barakiroth.hellostrangeworld.backend;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreetingPhraserTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  private static GreetingPhraser greetingPhraser;
  
  @BeforeAll
  public static void beforeAll() {
    GreetingPhraserTest.greetingPhraser = new GreetingPhraser();
  }
  
  @AfterAll
  public static void afterAll() {
    //GreetingPhraserTest.greetingPhraser.disconnect();
  }
  
  @Test
  public void should_return_a_given_phrase_unconditionally() {
    
    enteringTestHeaderLogger.debug(null);
    
    assertThat(
        greetingPhraser.getGreetingPhrasePrefix(), 
        anyOf(is("Hello strange "), is("Hello very strange "), is("Hello immensely strange ")));
  }
  
  @Test
  public void should_not_throw_upon_two_subsequent_calls_without_intermediate_disconnect() {
    
    enteringTestHeaderLogger.debug(null);
    
    assertDoesNotThrow(() -> GreetingPhraserTest.greetingPhraser.getGreetingPhrasePrefix());
    assertDoesNotThrow(() -> GreetingPhraserTest.greetingPhraser.getGreetingPhrasePrefix());
  }
}
