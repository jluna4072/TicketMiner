package model.venues;

/**
 * Represents an Open Air venue in the TicketMiner system.
 * Extends the abstract Venue class.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public class OpenAir extends Venue {

    /**
     * Constructs a default OpenAir venue with no initial field values.
     */
    public OpenAir() {
    }

    /**
     * Constructs an OpenAir venue with the specified identity, capacity, cost, and seating section percentages.
     *
     * @param id the unique numeric identifier for the open air venue
     * @param name the name of the open air venue
     * @param type the venue type
     * @param capacity the general seating capacity
     * @param concertCapacity the concert-specific seating capacity
     * @param cost the rental cost
     * @param vipPercent the percentage of seats allocated to the VIP section
     * @param goldPercent the percentage of seats allocated to the Gold section
     * @param silverPercent the percentage of seats allocated to the Silver section
     * @param bronzePercent the percentage of seats allocated to the Bronze section
     * @param generalAdmissionPercent the percentage of seats allocated to the General Admission section
     * @param reservedPercent the percentage of seats allocated to the Reserved section
     */
    public OpenAir(int id, String name, String type, int capacity, int concertCapacity, double cost, int vipPercent, int goldPercent, int silverPercent, int bronzePercent, int generalAdmissionPercent, int reservedPercent) {
        super(id, name, type, capacity, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedPercent);
    }
}
