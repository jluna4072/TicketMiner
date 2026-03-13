package model.events;

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
    private int vipSold = 0;
    private int goldSold = 0;
    private int silverSold = 0;
    private int bronzeSold = 0;
    private int gaSold = 0;

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
    public String getVipPrice() {
        return vipPrice;
    }

    public String getGoldPrice() {
        return goldPrice;
    }

    public String getSilverPrice() {
        return silverPrice;
    }

    public String getBronzePrice() {
        return bronzePrice;
    }

    public String getGeneralAdmissionPrice() {
        return generalAdmissionPrice;
    }
    public void incrementVip() { 
        vipSold++; 
    }
    public void incrementGold() { 
        goldSold++; 
    }
    public void incrementSilver() { 
        silverSold++; 
    }
    public void incrementBronze() { 
        bronzeSold++; 
    }
    public void incrementGa() { 
        gaSold++; 
    }
    public int getVipSold() { 
        return vipSold; 
    }
    public int getGoldSold() { 
        return goldSold; 
    }
    public int getSilverSold() { 
        return silverSold; 
    }
    public int getBronzeSold() { 
        return bronzeSold; 
    }
    public int getGaSold() { 
        return gaSold; 
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %-4d | Name: %-18s | Date: %-10s | VIP: %-6s | Gold: %-6s | Silv: %-6s | Bron: %-6s | GA: %-6s",
            eventID, 
            name, 
            date, 
            vipPrice, 
            goldPrice, 
            silverPrice, 
            bronzePrice, 
            generalAdmissionPrice);
    }
}