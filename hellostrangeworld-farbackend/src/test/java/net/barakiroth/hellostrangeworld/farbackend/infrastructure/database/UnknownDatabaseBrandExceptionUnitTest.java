package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import static org.assertj.core.api.Assertions.assertThat;

import net.barakiroth.hellostrangeworld.farbackend.ITestConst;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(ITestConst.UNIT_TEST_ANNOTATION)
public class UnknownDatabaseBrandExceptionUnitTest {

  @Test
  void when_instantiated_with_a_db_brand_then_the_same_db_brand_should_be_referenced_in_the_message() {

    ITestConst.enteringTestHeaderLogger.debug(null);

    final String expectedDbBrand = "CrunchyBase";
    
    final UnknownDatabaseBrandException unknownDatabaseBrandException =
        new UnknownDatabaseBrandException(expectedDbBrand);
    assertThat(unknownDatabaseBrandException.getMessage()).contains(expectedDbBrand);
  }
}
