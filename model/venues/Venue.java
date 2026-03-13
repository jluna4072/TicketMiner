package model.venues;

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

    public Venue() {
    }

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

    public int getVenueID() { 
        return venueID; 
    }
    public String getName() { 
        return name; 
    }
    public String getType() { 
        return type; 
    }
    public int getCapacity() { 
        return capacity; 
    }
    public int getConcertCapacity() { 
        return concertCapacity; 
    }
    public double getCost() { 
        return cost; 
    }
    public int getVipPercent() { 
        return vipPercent; 
    }
    public int getGoldPercent() { 
        return goldPercent; 
    }
    public int getSilverPercent() { 
        return silverPercent; 
    }
    public int getBronzePercent() { 
        return bronzePercent; 
    }
    public int getGeneralAdmissionPercent() { 
        return generalAdmissionPercent; 
    }
    public int getReservedPercent() { 
        return reservedPercent; 
    }

    public void setName(String name) { 
        this.name = name; 
    }
    public void setCost(double cost) { 
        this.cost = cost; 
    }
    public void setCapacity(int capacity) { 
        this.capacity = capacity; 
    }
    public void setType(String type) { 
        this.type = type; 
    }
    public void setConcertCapacity(int concertCapacity) { 
        this.concertCapacity = concertCapacity; 
    }
    public void setVipPercent(int vipPercent) { 
        this.vipPercent = vipPercent; 
    }
    public void setGoldPercent(int goldPercent) {
        this.goldPercent = goldPercent; 
    }
    public void setSilverPercent(int silverPercent) {
        this.silverPercent = silverPercent; 
    }
    public void setBronzePercent(int bronzePercent) {
        this.bronzePercent = bronzePercent; 
    }
    public void setGeneralAdmissionPercent(int generalAdmissionPercent) {
        this.generalAdmissionPercent = generalAdmissionPercent; 
    }
    public void setReservedPercent(int reservedPercent) {
        this.reservedPercent = reservedPercent; 
    }
    @Override
public String toString() {
    return String.format("ID: %-4d | Name: %-18s | Type: %-10s | Cap: %-6d | C.Cap: %-6d | Cost: $%-8.2f | " +"VIP: %d%% | Gold: %d%% | Silv: %d%% | Bron: %d%% | GA: %d%% | Res: %d%%",
        venueID, 
        name, 
        type, 
        capacity, 
        concertCapacity, 
        cost, 
        vipPercent, 
        goldPercent, 
        silverPercent, 
        bronzePercent, 
        generalAdmissionPercent, 
        reservedPercent);
    }
}
