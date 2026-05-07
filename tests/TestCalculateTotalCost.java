package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import menus.CustomerMenu;
import model.events.Event;
import model.users.Customer;
import utility.DataManager;

/**
 * JUnit tests for CustomerMenu.calculateTotalCost().
 * Verifies Texas sales tax application and the 10% member discount applied before tax.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public class TestCalculateTotalCost {

    private CustomerMenu menuWithMember;
    private CustomerMenu menuWithoutMember;

    @BeforeEach
    public void setUp() {
        HashMap<Integer, Event> eventMap = new HashMap<>();
        DataManager dm = new DataManager();
        Customer member = new Customer(1, "John", "Doe", "johndoe", "pass", "Customer", 1000.0, true, 0);
        Customer nonMember = new Customer(2, "Jane", "Smith", "janesmith", "pass", "Customer", 1000.0, false, 0);
        menuWithMember = new CustomerMenu(null, eventMap, dm, member);
        menuWithoutMember = new CustomerMenu(null, eventMap, dm, nonMember);
    }

    @Test
    public void testNonMemberTaxApplied() {
        //1 ticket at $100 -> $100 * 1.0825 = $108.25
        assertEquals(108.25, menuWithoutMember.calculateTotalCost(1, 100.0), 0.01);
    }

    @Test
    public void testMemberDiscountAppliedBeforeTax() {
        //1 ticket at $100 -> discount to $90 -> $90 * 1.0825 = $97.43
        assertEquals(97.43, menuWithMember.calculateTotalCost(1, 100.0), 0.01);
    }
}
