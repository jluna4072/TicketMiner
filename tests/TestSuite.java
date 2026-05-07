package tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * JUnit Test Suite that runs all test case files for the TicketMiner system.
 * Executes tests for price calculation, event search, and confirmation number generation.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
@Suite
@SelectClasses({
    TestCalculateTotalCost.class,
    TestFindEvents.class,
    TestConfirmationNumber.class
})
public class TestSuite {
}
