package net.barakiroth.hellostrangeworld.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ITestConst {
    String INTEGRATION_TEST_ANNOTATION = "Integration";
    String UNIT_TEST_ANNOTATION = "Unit";
    Logger enteringTestHeaderLogger = LoggerFactory.getLogger("EnteringTestHeader");
}