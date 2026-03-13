package model.venues;

public class Stadium extends Venue {

    public Stadium() {
        
    }
    public Stadium(int id, String name, String type, int capacity, int concertCapacity, double cost, int vipPercent, int goldPercent, int silverPercent, int bronzePercent, int generalAdmissionPercent, int reservedPercent) {
        super(id, name, type, capacity, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedPercent);
    }
}
