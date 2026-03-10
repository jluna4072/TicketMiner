package model.users;

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
    public double getMoneyAvailable() {
        return moneyAvailable;
    }

    public void setMoneyAvailable(double moneyAvailable) {
        this.moneyAvailable = moneyAvailable;
    }

    public boolean hasMembership() {
        return hasMembership;
    }

    public void setMembership(boolean hasMembership) {
        this.hasMembership = hasMembership;
    }

    public int getConcertsPurchased() {
        return concertsPurchased;
    }

    public void setConcertsPurchased(int concertsPurchased) {
        this.concertsPurchased = concertsPurchased;
    }

    public void purchaseTicket() {
    }
}
