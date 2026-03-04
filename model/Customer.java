package model;

/**
 * Full Customer class matching Customer_List_PA1.csv.
 * This version includes ALL fields and the full constructor
 * required by CustomerManager.
 */
public class Customer {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String userType; // Customer, Organizer, Admin
    private double moneyAvailable;
    private boolean membership;
    private int concertsPurchased;

    public Customer(String id,
                    String firstName,
                    String lastName,
                    String username,
                    String password,
                    String userType,
                    double moneyAvailable,
                    boolean membership,
                    int concertsPurchased) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.moneyAvailable = moneyAvailable;
        this.membership = membership;
        this.concertsPurchased = concertsPurchased;
    }

    // Getters (add setters later if needed)
    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getUserType() { return userType; }
    public double getMoneyAvailable() { return moneyAvailable; }
    public boolean hasMembership() { return membership; }
    public int getConcertsPurchased() { return concertsPurchased; }
}
