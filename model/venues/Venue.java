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
    public Venue(int id, String name, String type, int capacity, int concertCapacity, double cost, int vipPercent, int goldPercent, int silverPercent, int bronzePercent, int generalAdmissionPercent, int reservedPercent, String organizer) {
        this.venueID = id;
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

    public int  getVenueID() {
        return venueID;
    }

    public void setVenueID(int venueID) {
        this.venueID = venueID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    public int getVipPercent() {
        return vipPercent;
    }

    public void setVipPercent(int vipPercent) {
        this.vipPercent = vipPercent;
    }

    public int getGoldPercent() {
        return goldPercent;
    }

    public void setGoldPercent(int goldPercent) {
        this.goldPercent = goldPercent;
    }

    public int getSilverPercent() {
        return silverPercent;
    }

    public void setSilverPercent(int silverPercent) {
        this.silverPercent = silverPercent;
    }

    public int getBronzePercent() {
        return bronzePercent;
    }

    public void setBronzePercent(int bronzePercent) {
        this.bronzePercent = bronzePercent;
    }

    public int getGeneralAdmissionPercent() {
        return generalAdmissionPercent;
    }

    public void setGeneralAdmissionPercent(int generalAdmissionPercent) {
        this.generalAdmissionPercent = generalAdmissionPercent;
    }

    public int getReservedPercent() {
        return reservedPercent;
    }

    public void setReservedPercent(int reservedPercent) {
        this.reservedPercent = reservedPercent;
    }

    public void setConcertCapacity(int concertCapacity) {
        this.concertCapacity = concertCapacity;
    }

    public int getConcertCapacity() {
        return concertCapacity;
    }

}
