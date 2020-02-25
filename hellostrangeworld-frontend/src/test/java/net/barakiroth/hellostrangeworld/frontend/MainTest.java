package net.barakiroth.hellostrangeworld.frontend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doReturn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.MessageFormat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.barakiroth.hellostrangeworld.frontend.consumer.InitialPartConsumer;
import net.barakiroth.hellostrangeworld.frontend.consumer.InitialPartDo;

@ExtendWith(MockitoExtension.class)
public class MainTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;
  private final InputStream originalIn = System.in;
    
  @Mock
  private GreeteePrompter mockedGreeteePrompter;
  
  @Mock
  private InitialPartConsumer mockedInitialPartConsumer;

  @BeforeEach
  void setUpStreams() throws IllegalArgumentException, ReflectiveOperationException {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
    final Main main = Main.getSingletonInstance();
    main.setGreeteePrompter(null);
    main.setInitialPartConsumer(null);
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
    
    final InitialPartDo expectedInitialPartDo = new InitialPartDo("Hello, very intensive");
    final String expectedGreetee = "galaxy";
    final ByteArrayInputStream in = new ByteArrayInputStream(expectedGreetee.getBytes());
    System.setIn(in);
    
    final Main main = Main.getSingletonInstance();
    
    doReturn(expectedInitialPartDo)
      .when(mockedInitialPartConsumer).getInitialPartDo();
    main.setInitialPartConsumer(mockedInitialPartConsumer);
    
    doReturn(expectedGreetee).when(mockedGreeteePrompter).getGreetee();
    main.setGreeteePrompter(mockedGreeteePrompter);
    
    Main.main(new String[0]);
    
    final String expectedGreeting =
        MessageFormat
          .format(
                expectedInitialPartDo.getInitialPart()
              + " "
              + expectedGreetee
              + "!\r\n", expectedGreetee
          );
    
    assertThat(errContent.toString()).isEqualTo(expectedGreeting);
  }

  @Test
  public void should_not_throw_when_instantiating() {
    
    enteringTestHeaderLogger.debug(null);    
 
    assertThatCode(() -> Main.getSingletonInstance()).doesNotThrowAnyException();
  }

  @Test
  public void when_GreeteePrompter_is_implicitly_instantiated_then_no_exception_should_be_thrown() {
    
    enteringTestHeaderLogger.debug(null);
    
    Main.getSingletonInstance().setGreeteePrompter(null);
    final IFrontendConfig config = FrontendConfig.getSingletonInstance();
 
    assertThatCode(() -> Main.getSingletonInstance().getGreeteePrompter(config)).doesNotThrowAnyException();
  }

  @Test
  public void when_GreeteePrompter_is_implicitly_instantiated_then_it_should_not_be_null() {
    
    enteringTestHeaderLogger.debug(null);
    
    Main.getSingletonInstance().setGreeteePrompter(null);
    final IFrontendConfig config = FrontendConfig.getSingletonInstance();
    
    assertThat(Main.getSingletonInstance().getGreeteePrompter(config)).isNotNull();
  }

  @Test
  public void
      when_InitialPartConsumer_is_implicitly_instantiated_then_no_exception_should_be_thrown() {
    
    enteringTestHeaderLogger.debug(null);
    
    Main.getSingletonInstance().setInitialPartConsumer(null);
    final IFrontendConfig config = FrontendConfig.getSingletonInstance();
    
    assertThatCode(
        () -> Main.getSingletonInstance().getInitialPartConsumer(config)
    ).doesNotThrowAnyException();
  }

  @Test
  public void when_InitialPartConsumer_is_implicitly_instantiated_then_it_should_not_be_null() {
    
    enteringTestHeaderLogger.debug(null);
 
    Main.getSingletonInstance().setInitialPartConsumer(null);
    final IFrontendConfig config = FrontendConfig.getSingletonInstance();
    
    assertThat(
    		Main.getSingletonInstance().getInitialPartConsumer(config)
    ).isNotNull();
  }
  
}