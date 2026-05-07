package utility;

/**
 * Interface defining event management operations for the TicketMiner system.
 * Any class that manages events (adding, viewing, updating, deleting) should implement this interface.
 * Implemented by both AdminMenu and OrganizerMenu classes.
 *
 * <p><b>Design Pattern: Strategy Pattern</b></p>
 * <p>This interface is the core of the Strategy design pattern used in TicketMiner.
 * The Strategy pattern defines a family of algorithms (event management behaviors),
 * encapsulates each one behind a common interface, and makes them interchangeable.
 * In this system, both {@code AdminMenu} and {@code OrganizerMenu} implement
 * {@code EventManageable}, providing their own strategies for adding, viewing,
 * updating, and deleting events. The login system acts as the context, selecting
 * which concrete strategy (AdminMenu or OrganizerMenu) to use based on the
 * logged-in user's role. This allows new role-based event management behaviors
 * to be added without modifying existing code.</p>
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public interface EventManageable {

    /**
     * Adds a new event to the system.
     */
    void addEvent();

    /**
     * Displays event information (all events or a searched event).
     */
    void viewEvent();

    /**
     * Updates an existing event's information.
     */
    void updateEvent();

    /**
     * Deletes an event from the system.
     */
    void deleteEvent();
}
