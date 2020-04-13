package net.barakiroth.hellostrangeworld.frontend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.MessageFormat;
import net.barakiroth.hellostrangeworld.frontend.consumer.InitialPartConsumer;
import net.barakiroth.hellostrangeworld.frontend.consumer.InitialPartDo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag("Unit")
@ExtendWith(MockitoExtension.class)
public class MainUnitTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
  
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;
  private final InputStream originalIn = System.in;
  
  @Mock
  private IFrontendConfig mockedFrontendConfig; 
    
  @Mock
  private GreeteePrompter mockedGreeteePrompter;
  
  @Mock
  private InitialPartConsumer mockedInitialPartConsumer;

  @BeforeEach
  void setUpStreams() throws IllegalArgumentException, ReflectiveOperationException {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
    
    lenient().doReturn(this.mockedInitialPartConsumer).when(this.mockedFrontendConfig).getInitialPartConsumer();
    lenient().doReturn(this.mockedGreeteePrompter).when(this.mockedFrontendConfig).getGreeteePrompter();
    
    final Main main = Main.getSingleton();
    main.setFrontendConfig(this.mockedFrontendConfig);
  }

  @AfterEach
  void afterEach() throws IllegalArgumentException, ReflectiveOperationException {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
    final Main main = Main.getSingleton();
    main.setFrontendConfig(null);
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
  public void when_running_main_then_an_expected_greeting_should_be_produced() {
    
    enteringTestHeaderLogger.debug(null);
    
    final InitialPartDo expectedInitialPartDo = new InitialPartDo("Hello, very intensive");
    final String expectedGreetee = "galaxy";
    final ByteArrayInputStream in = new ByteArrayInputStream(expectedGreetee.getBytes());
    System.setIn(in);
    
    doReturn(expectedInitialPartDo).when(this.mockedInitialPartConsumer).getInitialPartDo();
    doReturn(expectedGreetee).when(this.mockedGreeteePrompter).getGreetee();
    
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
  public void when_implcitly_creating_a_main_instance_then_no_exception_should_be_thrown() { 
    
    enteringTestHeaderLogger.debug(null);    
 
    assertThatCode(() -> Main.getSingleton()).doesNotThrowAnyException();
  }

  @Test
  public void when_GreeteePrompter_is_explicitly_set_then_it_should_be_returned_by_a_subsequent_get() {
    
    enteringTestHeaderLogger.debug(null);
    
    doReturn(this.mockedGreeteePrompter).when(this.mockedFrontendConfig).getGreeteePrompter();
    
    final Main main = Main.getSingleton();
 
    assertThat(this.mockedGreeteePrompter).isEqualTo(main.getGreeteePrompter(this.mockedFrontendConfig));
  }

  @Test
  public void when_GreeteePrompter_is_implicitly_instantiated_then_no_exception_should_be_thrown() {
    
    enteringTestHeaderLogger.debug(null);
    
    final IFrontendConfig frontendConfig = FrontendConfig.getSingleton();
 
    assertThatCode(() -> Main.getSingleton().getGreeteePrompter(frontendConfig)).doesNotThrowAnyException();
  }

  @Test
  public void when_GreeteePrompter_is_implicitly_instantiated_then_it_should_not_be_null() {
    
    enteringTestHeaderLogger.debug(null);
    
    final IFrontendConfig frontendConfig = FrontendConfig.getSingleton();
    
    assertThat(Main.getSingleton().getGreeteePrompter(frontendConfig)).isNotNull();
  }

  @Test
  public void when_InitialPartConsumer_is_explicitly_set_then_it_should_be_returned_by_a_subsequent_get() {
    
    enteringTestHeaderLogger.debug(null);
    
    final Main main = Main.getSingleton();
 
    assertThat(main.getInitialPartConsumer(this.mockedFrontendConfig)).isEqualTo(this.mockedInitialPartConsumer);
  }

  @Test
  public void
      when_InitialPartConsumer_is_implicitly_instantiated_then_no_exception_should_be_thrown() {
    
    enteringTestHeaderLogger.debug(null);
    
    assertThatCode(
        () -> Main.getSingleton().getInitialPartConsumer(this.mockedFrontendConfig)
    ).doesNotThrowAnyException();
  }

  @Test
  public void when_InitialPartConsumer_is_implicitly_instantiated_then_it_should_not_be_null() {
    
    enteringTestHeaderLogger.debug(null);
 
    assertThat(
    		Main.getSingleton().getInitialPartConsumer(this.mockedFrontendConfig)
    ).isNotNull();
  }

  @Test
  public void when_getting_the_config_twice_then_the_same_config_should_be_returned() {
    
    enteringTestHeaderLogger.debug(null);
 
    final Main main = Main.getSingleton();
    final IFrontendConfig frontendConfig1 = main.getFrontendConfig();
    final IFrontendConfig frontendConfig2 = main.getFrontendConfig();
    
    assertThat(frontendConfig1).isEqualTo(frontendConfig2);
  }
  
}