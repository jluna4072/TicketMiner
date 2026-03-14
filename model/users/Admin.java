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

    /**
     * Constructs a default Admin with no initial field values.
     */
    public Admin() {
        super();
    }

    /**
     * Constructs an Admin with the specified identity and authentication details.
     *
     * @param userID the unique numeric identifier for the user
     * @param firstName the admin's first name
     * @param lastName the admin's last name
     * @param username the username used for login
     * @param password the password used for login
     * @param userType the role/type of the user
     */
    public Admin(int userID, String firstName, String lastName, String username, String password, String userType) {
        super(userID, firstName, lastName, username, password, userType);
    }
}
