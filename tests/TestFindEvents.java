package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import model.events.Concert;
import model.events.Event;
import utility.DataManager;

/**
 * JUnit tests for DataManager.findEvents().
 * Verifies event search by ID, name, and the not-found case.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public class TestFindEvents {

    private DataManager dm;
    private HashMap<Integer, Event> eventMap;

    @BeforeEach
    public void setUp() {
        dm = new DataManager();
        eventMap = new HashMap<>();
        eventMap.put(1, new Concert(1, "Concert", "Rock Fest", "05/10/2026", "07:00 PM",
                50.0, 40.0, 30.0, 20.0, 10.0));
    }

    @Test
    public void testFindByValidId() {
        List<Event> results = dm.findEvents(eventMap, "1");
        assertEquals(1, results.size());
        assertEquals("Rock Fest", results.get(0).getEventName());
    }

    @Test
    public void testFindByName() {
        List<Event> results = dm.findEvents(eventMap, "rock fest");
        assertEquals(1, results.size());
        assertEquals(1, results.get(0).getEventID());
    }

    @Test
    public void testEventNotFound() {
        List<Event> results = dm.findEvents(eventMap, "999");
        assertTrue(results.isEmpty());
    }
}
