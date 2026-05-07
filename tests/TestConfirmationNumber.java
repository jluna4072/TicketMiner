package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.DataManager;

/**
 * JUnit tests for DataManager.generateConfirmationNumber().
 * Verifies that confirmation numbers are positive and unique.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public class TestConfirmationNumber {

    private DataManager dm;

    @BeforeEach
    public void setUp() {
        dm = new DataManager();
    }

    @Test
    public void testConfirmationNumberIsPositive() {
        assertTrue(dm.generateConfirmationNumber() > 0);
    }

    @Test
    public void testConfirmationNumbersAreUnique() {
        int first = dm.generateConfirmationNumber();
        int second = dm.generateConfirmationNumber();
        assertNotEquals(first, second);
    }
}
