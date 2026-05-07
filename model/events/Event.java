package model.events;

/**
 * Abstract base class representing a generic event in the TicketMiner system.
 * Stores pricing, ticket sales, and scheduling information.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public abstract class Event {

    private int eventID;
    private String type;
    private String name;
    private String date;
    private String time;
    private String venue;
    private int capacity;
    private double vipPrice;
    private double goldPrice;
    private double silverPrice;
    private double bronzePrice;
    private double generalAdmissionPrice;
    private int vipSeats;
    private int goldSeats;
    private int silverSeats;
    private int bronzeSeats;
    private int generalAdmissionSeats;
    private int vipSeatsSold;
    private int goldSeatsSold;
    private int silverSeatsSold;
    private int bronzeSeatsSold;
    private int generalAdmissionSeatsSold;
    private double taxCollected;

    /**
     * Constructs a default Event with no initial field values.
     */
    public Event() {
    }

    /**
     * Constructs an Event with the specified scheduling and pricing details.
     *
     * @param eventID the unique numeric identifier for the event
     * @param type the event type (e.g., Sport, Concert, Special)
     * @param name the name of the event
     * @param date the scheduled date of the event (MM/DD/YYYY format)
     * @param time the scheduled time of the event (hh:mm AM/PM format)
     * @param vipPrice the price for a VIP ticket
     * @param goldPrice the price for a Gold ticket
     * @param silverPrice the price for a Silver ticket
     * @param bronzePrice the price for a Bronze ticket
     * @param generalAdmissionPrice the price for a General Admission ticket
     */
    public Event(int eventID, String type, String name, String date, String time, double vipPrice, double goldPrice, double silverPrice, double bronzePrice, double generalAdmissionPrice) {
        this.eventID = eventID;
        this.type = type;
        this.name = name;
        this.date = date;
        this.time = time;
        this.vipPrice = vipPrice;
        this.goldPrice = goldPrice;
        this.silverPrice = silverPrice;
        this.bronzePrice = bronzePrice;
        this.generalAdmissionPrice = generalAdmissionPrice;
        this.venue = "";
        this.capacity = 0;
        this.vipSeats = 0;
        this.goldSeats = 0;
        this.silverSeats = 0;
        this.bronzeSeats = 0;
        this.generalAdmissionSeats = 0;
        this.vipSeatsSold = 0;
        this.goldSeatsSold = 0;
        this.silverSeatsSold = 0;
        this.bronzeSeatsSold = 0;
        this.generalAdmissionSeatsSold = 0;
        this.taxCollected = 0;
    }

    /**
     * Constructs an Event with full details including venue, capacity, and seat allocation.
     *
     * @param eventID the unique numeric identifier for the event
     * @param type the event type (e.g., Sport, Concert, Special)
     * @param name the name of the event
     * @param date the scheduled date of the event (MM/DD/YYYY format)
     * @param time the scheduled time of the event (hh:mm AM/PM format)
     * @param venue the venue or location for the event
     * @param capacity the total seat capacity
     * @param vipPrice the price for a VIP ticket
     * @param goldPrice the price for a Gold ticket
     * @param silverPrice the price for a Silver ticket
     * @param bronzePrice the price for a Bronze ticket
     * @param generalAdmissionPrice the price for a General Admission ticket
     * @param vipSeats the number of VIP seats available
     * @param goldSeats the number of Gold seats available
     * @param silverSeats the number of Silver seats available
     * @param bronzeSeats the number of Bronze seats available
     * @param generalAdmissionSeats the number of General Admission seats available
     */
    public Event(int eventID, String type, String name, String date, String time,
                 String venue, int capacity,
                 double vipPrice, double goldPrice, double silverPrice,
                 double bronzePrice, double generalAdmissionPrice,
                 int vipSeats, int goldSeats, int silverSeats,
                 int bronzeSeats, int generalAdmissionSeats) {
        this.eventID = eventID;
        this.type = type;
        this.name = name;
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.capacity = capacity;
        this.vipPrice = vipPrice;
        this.goldPrice = goldPrice;
        this.silverPrice = silverPrice;
        this.bronzePrice = bronzePrice;
        this.generalAdmissionPrice = generalAdmissionPrice;
        this.vipSeats = vipSeats;
        this.goldSeats = goldSeats;
        this.silverSeats = silverSeats;
        this.bronzeSeats = bronzeSeats;
        this.generalAdmissionSeats = generalAdmissionSeats;
        this.vipSeatsSold = 0;
        this.goldSeatsSold = 0;
        this.silverSeatsSold = 0;
        this.bronzeSeatsSold = 0;
        this.generalAdmissionSeatsSold = 0;
        this.taxCollected = 0;
    }

    /**
     * Returns the unique identifier for this event.
     *
     * @return the event ID
     */
    public int getEventID() {
        return eventID;
    }

    /**
     * Sets the unique identifier for this event.
     *
     * @param eventID the new event ID
     */
    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    /**
     * Returns the type of this event (e.g., Sport, Concert, Special).
     *
     * @return the event type string
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of this event.
     *
     * @param type the new event type string
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the name of this event.
     *
     * @return the event name
     */
    public String getEventName() {
        return name;
    }

    /**
     * Sets the name of this event.
     *
     * @param name the new event name
     */
    public void setEventName(String name) {
        this.name = name;
    }

    /**
     * Returns the scheduled date of this event.
     *
     * @return the date string in MM/DD/YYYY format
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the scheduled date of this event.
     *
     * @param date the new date string in MM/DD/YYYY format
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns the scheduled time of this event.
     *
     * @return the time string in hh:mm AM/PM format
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the scheduled time of this event.
     *
     * @param time the new time string in hh:mm AM/PM format
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Returns the price for a VIP ticket to this event.
     *
     * @return the VIP ticket price
     */
    public double getVipPrice() {
        return vipPrice;
    }

    /**
     * Returns the price for a Gold ticket to this event.
     *
     * @return the Gold ticket price
     */
    public double getGoldPrice() {
        return goldPrice;
    }

    /**
     * Returns the price for a Silver ticket to this event.
     *
     * @return the Silver ticket price
     */
    public double getSilverPrice() {
        return silverPrice;
    }

    /**
     * Returns the price for a Bronze ticket to this event.
     *
     * @return the Bronze ticket price
     */
    public double getBronzePrice() {
        return bronzePrice;
    }

    /**
     * Returns the price for a General Admission ticket to this event.
     *
     * @return the General Admission ticket price
     */
    public double getGeneralAdmissionPrice() {
        return generalAdmissionPrice;
    }

    /**
     * Returns the venue/location for this event.
     *
     * @return the venue string
     */
    public String getVenue() {
        return venue;
    }

    /**
     * Sets the venue/location for this event.
     *
     * @param venue the new venue string
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }

    /**
     * Returns the total seat capacity for this event.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the total seat capacity for this event.
     *
     * @param capacity the new capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Sets the VIP ticket price.
     *
     * @param vipPrice the new VIP price
     */
    public void setVipPrice(double vipPrice) {
        this.vipPrice = vipPrice;
    }

    /**
     * Sets the Gold ticket price.
     *
     * @param goldPrice the new Gold price
     */
    public void setGoldPrice(double goldPrice) {
        this.goldPrice = goldPrice;
    }

    /**
     * Sets the Silver ticket price.
     *
     * @param silverPrice the new Silver price
     */
    public void setSilverPrice(double silverPrice) {
        this.silverPrice = silverPrice;
    }

    /**
     * Sets the Bronze ticket price.
     *
     * @param bronzePrice the new Bronze price
     */
    public void setBronzePrice(double bronzePrice) {
        this.bronzePrice = bronzePrice;
    }

    /**
     * Sets the General Admission ticket price.
     *
     * @param generalAdmissionPrice the new General Admission price
     */
    public void setGeneralAdmissionPrice(double generalAdmissionPrice) {
        this.generalAdmissionPrice = generalAdmissionPrice;
    }

    /** @return the number of VIP seats available */
    public int getVipSeats() { 
        return vipSeats; 
    
    }
    /** @param vipSeats the number of VIP seats */
    public void setVipSeats(int vipSeats) {
        this.vipSeats = vipSeats; 
    }

    /** @return the number of Gold seats available */
    public int getGoldSeats() {
        return goldSeats;
    }

    /** @param goldSeats the number of Gold seats */
    public void setGoldSeats(int goldSeats) {
        this.goldSeats = goldSeats; 
    }

    /** @return the number of Silver seats available */
    public int getSilverSeats() {
        return silverSeats; 
    }

    /** @param silverSeats the number of Silver seats */
    public void setSilverSeats(int silverSeats) {
        this.silverSeats = silverSeats; 
    }

    /** @return the number of Bronze seats available */
    public int getBronzeSeats() {
        return bronzeSeats; 
    }

    /** @param bronzeSeats the number of Bronze seats */
    public void setBronzeSeats(int bronzeSeats) {
        this.bronzeSeats = bronzeSeats; 
    }

    /** @return the number of General Admission seats available */
    public int getGeneralAdmissionSeats() {
        return generalAdmissionSeats; 
    }

    /** @param generalAdmissionSeats the number of General Admission seats */
    public void setGeneralAdmissionSeats(int generalAdmissionSeats) {
        this.generalAdmissionSeats = generalAdmissionSeats; 
    }

    /** @return the number of VIP seats sold */
    public int getVipSeatsSold() {
        return vipSeatsSold; 
    }

    /** @param vipSeatsSold the number of VIP seats sold */
    public void setVipSeatsSold(int vipSeatsSold) {
        this.vipSeatsSold = vipSeatsSold; 
    }

    /** @return the number of Gold seats sold */
    public int getGoldSeatsSold() {
        return goldSeatsSold; 
    }
    
    /** @param goldSeatsSold the number of Gold seats sold */
    public void setGoldSeatsSold(int goldSeatsSold) {
        this.goldSeatsSold = goldSeatsSold; 
    }

    /** @return the number of Silver seats sold */
    public int getSilverSeatsSold() {
        return silverSeatsSold; 
    }

    /** @param silverSeatsSold the number of Silver seats sold */
    public void setSilverSeatsSold(int silverSeatsSold) {
        this.silverSeatsSold = silverSeatsSold; 
    }

    /** @return the number of Bronze seats sold */
    public int getBronzeSeatsSold() {
        return bronzeSeatsSold; 
    }

    /** @param bronzeSeatsSold the number of Bronze seats sold */
    public void setBronzeSeatsSold(int bronzeSeatsSold) {
        this.bronzeSeatsSold = bronzeSeatsSold; 
    }

    /** @return the number of General Admission seats sold */
    public int getGeneralAdmissionSeatsSold() {
        return generalAdmissionSeatsSold; 
    }

    /** @param generalAdmissionSeatsSold the number of General Admission seats sold */
    public void setGeneralAdmissionSeatsSold(int generalAdmissionSeatsSold) {
        this.generalAdmissionSeatsSold = generalAdmissionSeatsSold; 
    }

    /**
     * Returns the total tax collected from ticket sales for this event.
     *
     * @return the total tax collected
     */
    public double getTaxCollected() {
        return taxCollected;
    }

    /**
     * Sets the total tax collected for this event.
     *
     * @param taxCollected the new tax collected amount
     */
    public void setTaxCollected(double taxCollected) {
        this.taxCollected = taxCollected;
    }

    /**
     * Adds a tax amount to the running total of tax collected for this event.
     *
     * @param tax the tax amount to add
     */
    public void addTaxCollected(double tax) {
        this.taxCollected += tax;
    }

    /**
     * Calculates the total number of seats sold across all ticket types.
     *
     * @return the total number of seats sold
     */
    public int getTotalSeatsSold() {
        return vipSeatsSold + goldSeatsSold + silverSeatsSold + bronzeSeatsSold + generalAdmissionSeatsSold;
    }

    /**
     * Calculates the total actual revenue from tickets sold.
     *
     * @return the total revenue from all sold tickets
     */
    public double getTotalRevenue() {
        return (vipSeatsSold * vipPrice) + (goldSeatsSold * goldPrice)
             + (silverSeatsSold * silverPrice) + (bronzeSeatsSold * bronzePrice)
             + (generalAdmissionSeatsSold * generalAdmissionPrice);
    }

    /**
     * Calculates the expected profit if all seats were sold at face value.
     *
     * @return the expected profit based on full capacity
     */
    public double getExpectedProfit() {
        return (vipSeats * vipPrice) + (goldSeats * goldPrice)
             + (silverSeats * silverPrice) + (bronzeSeats * bronzePrice)
             + (generalAdmissionSeats * generalAdmissionPrice);
    }

    /**
     * Returns a formatted string representation of this event including ID, name, date, and all ticket prices.
     *
     * @return a human-readable summary of the event
     */
    @Override
    public String toString() {
        return "ID: " + eventID + " | Type: " + type + " | Name: " + name
             + " | Date: " + date + " | Time: " + time
             + " | Venue: " + venue + " | Capacity: " + capacity
             + " | VIP: $" + vipPrice + " (" + vipSeats + " seats)"
             + " | Gold: $" + goldPrice + " (" + goldSeats + " seats)"
             + " | Silver: $" + silverPrice + " (" + silverSeats + " seats)"
             + " | Bronze: $" + bronzePrice + " (" + bronzeSeats + " seats)"
             + " | GA: $" + generalAdmissionPrice + " (" + generalAdmissionSeats + " seats)";
    }
}
