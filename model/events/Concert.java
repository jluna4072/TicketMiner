package model.events;

public class Concert extends Event {
    public Concert() {
    }

    public Concert(int id, String type, String name, String date, String time, double vipPrice, double goldPrice, double silverPrice, double bronzePrice, double generalAdmissionPrice) {
        super(id, type, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice);
    }
}
