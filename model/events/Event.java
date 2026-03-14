package model.events;

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

    public String getEventName() {
        return name;
    }

    public void setEventName(String name) {
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

    public double getVipPrice() {
        return vipPrice;
    }

    public double getGoldPrice() {
        return goldPrice;
    }

    public double getSilverPrice() {
        return silverPrice;
    }

    public double getBronzePrice() {
        return bronzePrice;
    }

    public double getGeneralAdmissionPrice() {
        return generalAdmissionPrice;
    }

    @Override
    public String toString() {
        return "ID: " + eventID + " | Name: " + name + " | Date: " + date
             + " | VIP: $" + vipPrice + " | Gold: $" + goldPrice + " | Silver: $" + silverPrice
             + " | Bronze: $" + bronzePrice + " | GA: $" + generalAdmissionPrice;
    }
}
