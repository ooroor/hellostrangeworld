package net.barakiroth.hellostrangeworld.backend.consumer;

import static org.assertj.core.api.Assertions.assertThat;

import net.barakiroth.hellostrangeworld.backend.consumer.ModifierDo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModifierDoTest {

  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");

  @Test
  void when_created_without_parms_then_the_getters_should_reflect_that() {

    enteringTestHeaderLogger.debug(null);
    
    final ModifierDo modifierDo = new ModifierDo();
    
    assertThat(modifierDo.getId()).isEqualTo(0);
    assertThat(modifierDo.getModifier()).isNull();
  }

  @Test
  void when_created_wit_parms_then_the_getters_should_reflect_that() {

    enteringTestHeaderLogger.debug(null);
    
    final int expectedId = 17;
    final String expectedModifier = "kbkbkbkj";
    
    final ModifierDo modifierDo = new ModifierDo(expectedId, expectedModifier);
    
    assertThat(modifierDo.getId()).isEqualTo(expectedId);
    assertThat(modifierDo.getModifier()).isEqualTo(expectedModifier);
  }
}
