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
    public Organizer() {
        super();
    }
    public Organizer(int userID, String firstName, String lastName, String username, String password, String userType) {
        super(userID, firstName, lastName, username, password, userType);
    }
}
