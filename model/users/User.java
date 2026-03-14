package model.users;

/**
 * Abstract base class representing a generic user in the TicketMiner system.
 * Stores common identity and authentication fields shared by all user types.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public abstract class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private int userID;
    private String userType;

    public User() {
    }

    public User(int userID, String firstName, String lastName, String username, String password, String userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.userID = userID;
        this.userType = userType;
    }

    /**
     * Returns the first name of this user.
     *
     * @return the user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of this user.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of this user.
     *
     * @return the user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of this user.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the username used to identify and authenticate this user.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for this user.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password for this user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for this user.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the unique numeric identifier for this user.
     *
     * @return the user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the unique numeric identifier for this user.
     *
     * @param userID the new user ID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Returns the role/type of this user (e.g., Customer, Organizer, Admin).
     *
     * @return the user type string
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Sets the role/type of this user.
     *
     * @param userType the new user type string
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * Returns a formatted string representation of this user including ID, full name, username, and type.
     *
     * @return a human-readable summary of the user
     */
    @Override
    public String toString() {
        return "ID: " + userID + " | Name: " + firstName + " " + lastName
             + " | Username: " + username + " | Type: " + userType;
    }
}
