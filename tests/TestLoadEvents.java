package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import model.events.Event;
import model.events.Sport;
import model.venues.Venue;
import utility.DataManager;

/**
 * JUnit tests for DataManager.loadEvents().
 * Verifies that events are correctly loaded from the CSV file.
 */
public class TestLoadEvents {

    private static HashMap<Integer, Event> events;

    @BeforeAll
    public static void setUpAll() {
        DataManager dm = new DataManager();
        HashMap<Integer, Venue> venueMap = dm.loadVenues("tests/resources/Venue_List_Test.csv");
        events = dm.loadEvents("tests/resources/Event_List_Test.csv", venueMap);
    }

    @Test
    public void testLoadEventsCount() {
        assertNotNull(events);
        assertEquals(2, events.size(), "Should load exactly 2 events from test resource");
    }

    @Test
    public void testLoadEventDetails() {
        Event first = events.get(1);
        assertNotNull(first, "Event with ID 1 should exist");
        assertEquals("UTEP Football 1", first.getEventName());
        assertTrue(first instanceof Sport, "Event 1 should be an instance of Sport");
        assertEquals("Sun Bowl Stadium", first.getVenue());
    }
}
