package model.users;

/**
 * Represents a Customer user in the TicketMiner system.
 * Stores funds, membership status, and number of events attended.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public class Customer extends User {

    private double moneyAvailable;
    private boolean hasMembership;
    private int concertsPurchased;

    /**
     * Constructs a default Customer with no initial field values.
     */
    public Customer() {
    }

    /**
     * Constructs a Customer with the specified user details and account information.
     *
     * @param userID the unique numeric identifier for the user
     * @param firstName the customer's first name
     * @param lastName the customer's last name
     * @param username the username used for login
     * @param password the password used for authentication
     * @param userType the role/type of the user
     * @param moneyAvailable the available balance in the customer's account
     * @param hasMembership {@code true} if the customer holds an active membership; {@code false} otherwise
     * @param concertsPurchased the number of events the customer has purchased tickets for
     */
    public Customer(int userID, String firstName, String lastName, String username, String password, String userType, double moneyAvailable, boolean hasMembership, int concertsPurchased) {
        super(userID, firstName, lastName, username, password, userType); 
        this.moneyAvailable = moneyAvailable;
        this.hasMembership = hasMembership;
        this.concertsPurchased = concertsPurchased;
    }
    /**
     * Returns the amount of money currently available in this customer's account.
     *
     * @return the available balance
     */
    public double getMoneyAvailable() {
        return moneyAvailable;
    }

    /**
     * Sets the amount of money available in this customer's account.
     *
     * @param moneyAvailable the new balance to set
     */
    public void setMoneyAvailable(double moneyAvailable) {
        this.moneyAvailable = moneyAvailable;
    }

    /**
     * Returns whether this customer holds an active membership.
     *
     * @return {@code true} if the customer has a membership; {@code false} otherwise
     */
    public boolean hasMembership() {
        return hasMembership;
    }

    /**
     * Sets the membership status for this customer.
     *
     * @param hasMembership {@code true} to grant membership; {@code false} to revoke it
     */
    public void setMembership(boolean hasMembership) {
        this.hasMembership = hasMembership;
    }

    /**
     * Returns the number of events (concerts) this customer has purchased tickets for.
     *
     * @return the count of events purchased
     */
    public int getConcertsPurchased() {
        return concertsPurchased;
    }

    /**
     * Sets the number of events (concerts) this customer has purchased tickets for.
     *
     * @param concertsPurchased the new count of events purchased
     */
    public void setConcertsPurchased(int concertsPurchased) {
        this.concertsPurchased = concertsPurchased;
    }

    /**
     * Returns a formatted string representation of this customer, including inherited user
     * fields plus available funds, membership status, and number of events attended.
     *
     * @return a readable summary of the customer
     */
    @Override
    public String toString() {
        return super.toString() + " | Funds: $" + moneyAvailable + " | Member: " + hasMembership
             + " | Events Attended: " + concertsPurchased;
    }
}
