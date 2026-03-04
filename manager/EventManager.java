package manager;

import model.Event;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages all events in the system.
 * Stores Event objects that match the Event CSV structure.
 */
public class EventManager {

    private List<Event> events;

    public EventManager() {
        events = new ArrayList<>();

        // Placeholder sample event so the program compiles and runs
        events.add(new Event(
                "1",
                "Sport",
                "UTEP Football 1",
                "09/04/2025",
                "7:05 PM",
                26.25,
                22.5,
                18.75,
                16.25,
                12.5
        ));
    }

    public List<Event> getAllEvents() {
        return events;
    }

    public void addEvent(Event e) {
        events.add(e);
    }

    public Event findEventById(String id) {
        for (Event e : events) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }
}
