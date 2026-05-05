package utility;

/**
 * Custom exception thrown when an event cannot be found in the system.
 * This is a user-defined exception for domain-specific error handling.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public class EventNotFoundException extends Exception {
    /**
     * Constructs an EventNotFoundException with a specified detail message.
     *
     * @param message the detail message describing why the event was not found
     */
    public EventNotFoundException(String message) {
        super(message);
    }
}
