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
    private double vipPrice;
    private double goldPrice;
    private double silverPrice;
    private double bronzePrice;
    private double generalAdmissionPrice;

    public Event() {
    }

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
     * Returns a formatted string representation of this event including ID, name, date, and all ticket prices.
     *
     * @return a human-readable summary of the event
     */
    @Override
    public String toString() {
        return "ID: " + eventID + " | Name: " + name + " | Date: " + date
             + " | VIP: $" + vipPrice + " | Gold: $" + goldPrice + " | Silver: $" + silverPrice
             + " | Bronze: $" + bronzePrice + " | GA: $" + generalAdmissionPrice;
    }
}
