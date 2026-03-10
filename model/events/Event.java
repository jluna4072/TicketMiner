package model.events;
import model.venues.Venue;

public abstract class Event {

    private int eventID;
    private String type;
    private String name;
    private String date;
    private String time;
    private String vipPrice;
    private String goldPrice;
    private String silverPrice;
    private String bronzePrice;
    private String generalAdmissionPrice;
    private Venue venue;


    public Event() {
    }

    public Event(int eventID, String type, String name, String date, String time, String vipPrice, String goldPrice, String silverPrice, String bronzePrice, String generalAdmissionPrice) {
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

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}
