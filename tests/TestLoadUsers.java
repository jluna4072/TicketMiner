package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import model.users.Customer;
import model.users.User;
import utility.DataManager;

/**
 * JUnit tests for DataManager.loadUsers().
 * Verifies that users are correctly loaded from the CSV file.
 */
public class TestLoadUsers {

    private static HashMap<String, User> users;

    @BeforeAll
    public static void setUpAll() {
        DataManager dm = new DataManager();
        users = dm.loadUsers("tests/resources/Customer_List_Test.csv");
    }

    @Test
    public void testLoadUsersNotNull() {
        assertNotNull(users);
        assertEquals(3, users.size(), "Should load exactly 3 users from test resource");
    }

    @Test
    public void testLoadCustomerDetails() {
        User jacob = users.get("jluna33");
        assertNotNull(jacob, "User 'jluna33' should exist");
        assertEquals("Jacob", jacob.getFirstName());
        assertEquals("Luna", jacob.getLastName());
        assertTrue(jacob instanceof Customer, "jluna33 should be a Customer instance");
    }

    @Test
    public void testLoadAdminDetails() {
        User angela = users.get("angelawalker");
        assertNotNull(angela, "User 'angelawalker' should exist");
        assertEquals("Admin", angela.getClass().getSimpleName(), "angelawalker should be an Admin");
    }
}
