package utility;

/**
 * Interface defining event management operations for the TicketMiner system.
 * Any class that manages events (adding, viewing, updating, deleting) should implement this interface.
 * Implemented by both RunTicketMiner (Admin menu) and OrganizerMenu classes.
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
