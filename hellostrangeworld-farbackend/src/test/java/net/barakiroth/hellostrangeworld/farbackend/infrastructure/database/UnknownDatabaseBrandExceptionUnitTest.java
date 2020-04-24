package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import net.barakiroth.hellostrangeworld.farbackend.ITestConstants;

@Tag(ITestConstants.UNIT_TEST_ANNOTATION)
public class UnknownDatabaseBrandExceptionUnitTest {

  @Test
  void when_instantiated_with_a_db_brand_then_the_same_db_brand_should_be_referenced_in_the_message() {

    ITestConstants.enteringTestHeaderLogger.debug(null);

    final String expectedDbBrand = "CrunchyBase";
    
    final UnknownDatabaseBrandException unknownDatabaseBrandException =
        new UnknownDatabaseBrandException(expectedDbBrand);
    assertThat(unknownDatabaseBrandException.getMessage()).contains(expectedDbBrand);
  }
}
