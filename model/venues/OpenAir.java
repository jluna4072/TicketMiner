package model.venues;

public class OpenAir extends Venue {

    public OpenAir() {
    }
    public OpenAir(int id, String name, String type, int capacity, int concertCapacity, double cost, int vipPercent, int goldPercent, int silverPercent, int bronzePercent, int generalAdmissionPercent, int reservedPercent) {
        super(id, name, type, capacity, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedPercent);
    }
}
