package model.events;

public class Concert extends Event {
    public Concert() {
    }
    public Concert(int id, String type, String name, String date, String time, String vipPrice, String goldPrice, String silverPrice, String bronzePrice, String generalAdmissionPrice) {
        super(id, type, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice);
    }
}
