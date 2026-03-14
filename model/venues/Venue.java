package model.venues;

/**
 * Abstract base class representing a generic venue in the TicketMiner system.
 * Stores capacity, cost, and seating section percentage information.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public abstract class Venue {
    private int venueID;
    private String name;
    private String type;
    private int capacity;
    private int concertCapacity;
    private double cost;
    private int vipPercent;
    private int goldPercent;
    private int silverPercent;
    private int bronzePercent;
    private int generalAdmissionPercent;
    private int reservedPercent;

    /**
     * Constructs a default Venue with no initial field values.
     */
    public Venue() {
    }

    /**
     * Constructs a Venue with the specified identity, capacity, cost, and seating section percentages.
     *
     * @param venueID the unique numeric identifier for the venue
     * @param name the name of the venue
     * @param type the venue type (e.g., Arena, Stadium, Open Air, Auditorium)
     * @param capacity the general seating capacity of the venue
     * @param concertCapacity the concert-specific seating capacity
     * @param cost the rental cost of the venue
     * @param vipPercent the percentage of seats allocated to the VIP section
     * @param goldPercent the percentage of seats allocated to the Gold section
     * @param silverPercent the percentage of seats allocated to the Silver section
     * @param bronzePercent the percentage of seats allocated to the Bronze section
     * @param generalAdmissionPercent the percentage of seats allocated to the General Admission section
     * @param reservedPercent the percentage of seats allocated to the Reserved section
     */
    public Venue(int venueID, String name, String type, int capacity, int concertCapacity, double cost, int vipPercent, int goldPercent, int silverPercent, int bronzePercent, int generalAdmissionPercent, int reservedPercent) {
        this.venueID = venueID;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.concertCapacity = concertCapacity;
        this.cost = cost;
        this.vipPercent = vipPercent;
        this.goldPercent = goldPercent;
        this.silverPercent = silverPercent;
        this.bronzePercent = bronzePercent;
        this.generalAdmissionPercent = generalAdmissionPercent;
        this.reservedPercent = reservedPercent;
    }

    /**
     * Returns the unique identifier for this venue.
     *
     * @return the venue ID
     */
    public int getVenueID() {
        return venueID;
    }

    /**
     * Returns the name of this venue.
     *
     * @return the venue name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the type of this venue (e.g., Arena, Stadium, Open Air, Auditorium).
     *
     * @return the venue type string
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the general seating capacity of this venue.
     *
     * @return the total capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns the concert-specific seating capacity of this venue.
     *
     * @return the concert capacity
     */
    public int getConcertCapacity() {
        return concertCapacity;
    }

    /**
     * Returns the rental cost for this venue.
     *
     * @return the cost to rent the venue
     */
    public double getCost() {
        return cost;
    }

    /**
     * Returns the percentage of seats allocated to the VIP section.
     *
     * @return the VIP seat percentage
     */
    public int getVipPercent() {
        return vipPercent;
    }

    /**
     * Returns the percentage of seats allocated to the Gold section.
     *
     * @return the Gold seat percentage
     */
    public int getGoldPercent() {
        return goldPercent;
    }

    /**
     * Returns the percentage of seats allocated to the Silver section.
     *
     * @return the Silver seat percentage
     */
    public int getSilverPercent() {
        return silverPercent;
    }

    /**
     * Returns the percentage of seats allocated to the Bronze section.
     *
     * @return the Bronze seat percentage
     */
    public int getBronzePercent() {
        return bronzePercent;
    }

    /**
     * Returns the percentage of seats allocated to the General Admission section.
     *
     * @return the General Admission seat percentage
     */
    public int getGeneralAdmissionPercent() {
        return generalAdmissionPercent;
    }

    /**
     * Returns the percentage of seats allocated to the Reserved section.
     *
     * @return the Reserved seat percentage
     */
    public int getReservedPercent() {
        return reservedPercent;
    }

    /**
     * Sets the name of this venue.
     *
     * @param name the new venue name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the rental cost for this venue.
     *
     * @param cost the new rental cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Sets the general seating capacity of this venue.
     *
     * @param capacity the new total capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Sets the type of this venue.
     *
     * @param type the new venue type string
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the concert-specific seating capacity of this venue.
     *
     * @param concertCapacity the new concert capacity
     */
    public void setConcertCapacity(int concertCapacity) {
        this.concertCapacity = concertCapacity;
    }

    /**
     * Sets the percentage of seats allocated to the VIP section.
     *
     * @param vipPercent the new VIP seat percentage
     */
    public void setVipPercent(int vipPercent) {
        this.vipPercent = vipPercent;
    }

    /**
     * Sets the percentage of seats allocated to the Gold section.
     *
     * @param goldPercent the new Gold seat percentage
     */
    public void setGoldPercent(int goldPercent) {
        this.goldPercent = goldPercent;
    }

    /**
     * Sets the percentage of seats allocated to the Silver section.
     *
     * @param silverPercent the new Silver seat percentage
     */
    public void setSilverPercent(int silverPercent) {
        this.silverPercent = silverPercent;
    }

    /**
     * Sets the percentage of seats allocated to the Bronze section.
     *
     * @param bronzePercent the new Bronze seat percentage
     */
    public void setBronzePercent(int bronzePercent) {
        this.bronzePercent = bronzePercent;
    }

    /**
     * Sets the percentage of seats allocated to the General Admission section.
     *
     * @param generalAdmissionPercent the new General Admission seat percentage
     */
    public void setGeneralAdmissionPercent(int generalAdmissionPercent) {
        this.generalAdmissionPercent = generalAdmissionPercent;
    }

    /**
     * Sets the percentage of seats allocated to the Reserved section.
     *
     * @param reservedPercent the new Reserved seat percentage
     */
    public void setReservedPercent(int reservedPercent) {
        this.reservedPercent = reservedPercent;
    }

    /**
     * Returns a formatted string representation of this venue including ID, name, type,
     * capacities, cost, and all seating section percentages.
     *
     * @return a readable summary of the venue
     */
    @Override
    public String toString() {
        return "ID: " + venueID + " | Name: " + name + " | Type: " + type
             + " | Cap: " + capacity + " | C.Cap: " + concertCapacity + " | Cost: $" + cost
             + " | VIP: " + vipPercent + "% | Gold: " + goldPercent + "% | Silver: " + silverPercent
             + "% | Bronze: " + bronzePercent + "% | GA: " + generalAdmissionPercent + "% | Reserved: " + reservedPercent + "%";
    }
}
