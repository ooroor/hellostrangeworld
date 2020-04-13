package net.barakiroth.hellostrangeworld.farbackend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;

import net.barakiroth.hellostrangeworld.common.IGeneralConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.IJettyManagerConfig;
import net.barakiroth.hellostrangeworld.common.infrastructure.servletcontainer.JettyManager;
import net.barakiroth.hellostrangeworld.farbackend.infrastructure.database.Database;
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
  
  @BeforeEach
  void beforeEach() {
    final Main main = Main.getSingleton();
    main.setFarBackendConfig(null);
  }
  
  @AfterEach
  void afterEach() {
    final Main main = Main.getSingleton();
    main.setFarBackendConfig(null);
  }
  
  @Mock
  private IFarBackendConfig mockedFarBackendConfig;
  
  @Mock
  private IJettyManagerConfig mockedJettyManagerConfig;
  
  @Mock
  private JettyManager mockedJettyManager;
  
  @Mock
  private Database mockedDatabase;
  
  @Mock
  private IGeneralConfig mockedGeneralConfig;

  @Test
  public void when_main_is_started_and_JettyManager_and_Database_behave_well_then_no_exception_should_be_thrown() {
    
    enteringTestHeaderLogger.debug(null);
    
    doReturn(mockedJettyManagerConfig).when(mockedFarBackendConfig).getJettyManagerConfig();
    doReturn(mockedJettyManager).when(mockedJettyManagerConfig).getJettyManager(any(IGeneralConfig.class));
    doReturn(mockedDatabase).when(mockedFarBackendConfig).getDatabase();

    final Main main = Main.getSingleton();
    main.setFarBackendConfig(mockedFarBackendConfig);
    
    assertThatCode(() -> Main.main(null)).doesNotThrowAnyException();
  }

  @Test
  public void when_FarBackendConfig_is_asked_for_twice_then_the_same_instance_should_be_returned() {
    
    enteringTestHeaderLogger.debug(null);
    
    final Main main = Main.getSingleton();
    
    final IFarBackendConfig farBackendConfig1 = main.getFarBackendConfig();
    final IFarBackendConfig farBackendConfig2 = main.getFarBackendConfig();
    
    assertThat(farBackendConfig1).isEqualTo(farBackendConfig2);
    assertThat(farBackendConfig1).isSameAs(farBackendConfig2);
  }
}