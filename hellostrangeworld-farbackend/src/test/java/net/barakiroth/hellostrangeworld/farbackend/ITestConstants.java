package net.barakiroth.hellostrangeworld.farbackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ITestConstants {
  String INTEGRATION_TEST_ANNOTATION = "Integration";
  String UNIT_TEST_ANNOTATION = "Unit";
  Logger enteringTestHeaderLogger = LoggerFactory.getLogger("EnteringTestHeader");
}
