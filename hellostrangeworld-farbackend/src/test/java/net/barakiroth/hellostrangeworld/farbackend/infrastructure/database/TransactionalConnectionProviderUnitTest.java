package net.barakiroth.hellostrangeworld.farbackend.infrastructure.database;

import javax.sql.DataSource;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag("Unit")
@ExtendWith(MockitoExtension.class)
public class TransactionalConnectionProviderUnitTest {
  
  @Mock
  private DataSource mockedDataSource;
  
  private static final Logger enteringTestHeaderLogger =
      LoggerFactory.getLogger("EnteringTestHeader");
}
