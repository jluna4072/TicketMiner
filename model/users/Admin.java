package model.users;

/**
 * Represents an Admin user in the TicketMiner system.
 * Admins have full access to manage users, venues, and events.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(int userID, String firstName, String lastName, String username, String password, String userType) {
        super(userID, firstName, lastName, username, password, userType);
    }
}
