package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import javax.sql.DataSource;

import net.barakiroth.hellostrangeworld.farbackend.ITestConst;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag(ITestConst.UNIT_TEST_ANNOTATION)
@ExtendWith(MockitoExtension.class)
public class TransactionalConnectionProviderUnitTest {
  
  @Mock
  private DataSource mockedDataSource;
}
