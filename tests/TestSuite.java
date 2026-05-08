package tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * JUnit Test Suite that runs all test case files for the TicketMiner system.
 * Executes tests for price calculation, search functionality, and data loading.
 */
@Suite
@SelectClasses({
    TestCalculateTotalCost.class,
    TestLoadEvents.class,
    TestLoadUsers.class,
    TestLoadVenues.class,
    TestConfirmationNumber.class
})
public class TestSuite {
}
