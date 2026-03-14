package model.users;

/**
 * Represents an Organizer user in the TicketMiner system.
 * Organizers are responsible for creating and managing events.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public class Organizer extends User {
    /**
     * Constructs a default Organizer with no initial field values.
     */
    public Organizer() {
        super();
    }

    /**
     * Constructs an Organizer with specified identity and authentication details.
     *
     * @param userID the unique numeric identifier for the user
     * @param firstName the organizer's first name
     * @param lastName the organizer's last name
     * @param username the username used for login
     * @param password the password used for login
     * @param userType the role/type of the user
     */
    public Organizer(int userID, String firstName, String lastName, String username, String password, String userType) {
        super(userID, firstName, lastName, username, password, userType);
    }
}
