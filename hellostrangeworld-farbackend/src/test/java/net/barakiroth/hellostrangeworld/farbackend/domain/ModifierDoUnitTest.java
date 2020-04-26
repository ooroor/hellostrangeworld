package net.barakiroth.hellostrangeworld.farbackend.domain;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.stream.Stream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import net.barakiroth.hellostrangeworld.farbackend.ITestConstants;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


/**
 * Consider:
 <!-- https://mvnrepository.com/artifact/nl.jqno.equalsverifier/equalsverifier -->
<dependency>
    <groupId>nl.jqno.equalsverifier</groupId>
    <artifactId>equalsverifier</artifactId>
    <version>3.1.13</version>
    <scope>test</scope>
</dependency>
@Test
public void equalsContract() {
    EqualsVerifier.forClass(Foo.class).verify();
}
 */

/**
 * Refs: 
 * https://jqno.nl/equalsverifier/errormessages/coverage-is-not-100-percent/
 * https://projectlombok.org/features/EqualsAndHashCode
 * https://www.baeldung.com/parameterized-tests-junit-5
 * @author oor
 *
 */
@Tag(ITestConstants.UNIT_TEST_ANNOTATION)
@ExtendWith(MockitoExtension.class)
public class ModifierDoUnitTest {
  
  private static Stream<Arguments> provideModifierDoFieldValues() {
    return Stream.of(
      Arguments.of(1, "a", 1, "a", true),
      Arguments.of(1, "a", 1, "b", false),
      Arguments.of(1, "a", 2, "a", false),
      Arguments.of(1, null, 1, null, true),
      Arguments.of(1, null, 1, "a", false),
      Arguments.of(1, "a", 1, null, false),
      Arguments.of(1, "", 1, "", true),
      Arguments.of(1, " ", 1, " ", true),
      Arguments.of(1, "", 1, " ", false),
      Arguments.of(1, " ", 1, "", false)
    );
  }
  
  private static Stream<Arguments> provideModifierDoObjects() {
    
    final ModifierDo modifierDo1a = new ModifierDo(1, "x");
    final ModifierDo modifierDo1b = new ModifierDo(1, "x");
    //final ModifierDo modifierDo1c = new ModifierDo(1, "x") {{}};
    final ModifierDo modifierDo2a = new ModifierDo(1, "y");
    //final ModifierDo modifierDo2b = new ModifierDo(1, "y") {{}};
    final ModifierDo modifierDo3 = new ModifierDo(1, "y");
    final ModifierDo modifierDo4 = new ModifierDo(2, "x");
    final ModifierDo modifierDo5 = new ModifierDo(2, "x");
    final ModifierDo modifierDo6 = new ModifierDo(2, "y");
    final ModifierDo modifierDo7 = new ModifierDo(2, "y");

    return Stream.of(
      Arguments.of(modifierDo1a, new String(), false),
      Arguments.of(modifierDo1a, null, false),
      Arguments.of(modifierDo1a, modifierDo1a, true),
      Arguments.of(modifierDo1a, modifierDo1b, true),
      Arguments.of(modifierDo1b, modifierDo1a, true),
      Arguments.of(modifierDo1a, modifierDo2a, false),
      //Arguments.of(modifierDo1a, modifierDo2b, false),
      Arguments.of(modifierDo2a, modifierDo1a, false),
      //Arguments.of(modifierDo2b, modifierDo1a, false),
      Arguments.of(modifierDo1a, modifierDo3, false),
      Arguments.of(modifierDo1a, modifierDo4, false),
      Arguments.of(modifierDo1a, modifierDo5, false),
      Arguments.of(modifierDo1a, modifierDo6, false),
      Arguments.of(modifierDo1a, modifierDo7, false)
      //Arguments.of(modifierDo1a, modifierDo1c, true),
      //Arguments.of(modifierDo1c, modifierDo1a, true)
    );
  }

  @Test
  public void equalsContract() {
      EqualsVerifier
        .forClass(ModifierDo.class)
        .suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }
  
  @Test
  void when_instantiated_with_values_then_the_field_values_should_be_returned_by_their_getters() {

    ITestConstants.enteringTestHeaderLogger.debug(null);
    
    final int expectedId = 7;
    final String expectedModifier = "jjjj";
    final ModifierDo modifierDo = new ModifierDo(expectedId, expectedModifier);
    
    assertThat(modifierDo.getId()).isEqualTo(expectedId);
    assertThat(modifierDo.getModifier()).isEqualTo(expectedModifier);
  }
  
  @ParameterizedTest
  @MethodSource("net.barakiroth.hellostrangeworld.farbackend.domain.ModifierDoUnitTest#provideModifierDoFieldValues")
  void when_two_modifiers_contain_the_same_field_values_then_they_should_equal_1(
      final int id1, 
      final String modifier1, 
      final int id2, 
      final String modifier2, 
      final boolean expectedEquality) {

    ITestConstants.enteringTestHeaderLogger.debug(null);
    
    final ModifierDo modifierDo1 = new ModifierDo(id1, modifier1);
    final ModifierDo modifierDo2 = new ModifierDo(id2, modifier2);
    
    assertThat(modifierDo1.equals(modifierDo2)).isEqualTo(expectedEquality);
  }
  
  @ParameterizedTest
  @MethodSource("net.barakiroth.hellostrangeworld.farbackend.domain.ModifierDoUnitTest#provideModifierDoObjects")
  <T extends ModifierDo> void when_two_modifiers_contain_the_same_field_values_then_they_should_equal_2(
      final T modifier, 
      final Object object, 
      final boolean expectedEquality) {

    ITestConstants.enteringTestHeaderLogger.debug(null);
    
    assertThat(modifier.equals(object)).isEqualTo(expectedEquality);
  }
}
