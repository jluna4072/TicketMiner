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

    public Customer() {
    }
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
     * @return a human-readable summary of the customer
     */
    @Override
    public String toString() {
        return super.toString() + " | Funds: $" + moneyAvailable + " | Member: " + hasMembership
             + " | Events Attended: " + concertsPurchased;
    }
}
