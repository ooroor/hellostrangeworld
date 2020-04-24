package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import javax.sql.DataSource;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import net.barakiroth.hellostrangeworld.farbackend.ITestConstants;

@Tag(ITestConstants.UNIT_TEST_ANNOTATION)
@ExtendWith(MockitoExtension.class)
public class TransactionalConnectionProviderUnitTest {
  
  @Mock
  private DataSource mockedDataSource;
}
