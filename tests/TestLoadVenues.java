package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import model.venues.Arena;
import model.venues.Auditorium;
import model.venues.Stadium;
import model.venues.Venue;
import utility.DataManager;

/**
 * JUnit tests for DataManager.loadVenues().
 * Verifies that venues are correctly loaded from the CSV file.
 */
public class TestLoadVenues {

    private static HashMap<Integer, Venue> venues;

    @BeforeAll
    public static void setUpAll() {
        DataManager dm = new DataManager();
        venues = dm.loadVenues("tests/resources/Venue_List_Test.csv");
    }

    @Test
    public void testLoadVenuesCount() {
        assertNotNull(venues);
        assertEquals(2, venues.size(), "Should load exactly 2 venues from test resource");
    }

    @Test
    public void testLoadArena() {
        Venue v1 = venues.get(1);
        assertNotNull(v1, "Venue ID 1 should exist");
        assertEquals("Don Haskins Center", v1.getName());
        assertTrue(v1 instanceof Arena, "Venue 1 should be an Arena");
    }

    @Test
    public void testLoadStadium() {
        Venue v2 = venues.get(2);
        assertNotNull(v2, "Venue ID 2 should exist");
        assertEquals("Sun Bowl Stadium", v2.getName());
        assertTrue(v2 instanceof Stadium, "Venue 2 should be a Stadium");
    }
}
