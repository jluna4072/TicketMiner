package model;

/**
 * Full Event class matching the Event_List_PA1.csv structure.
 * This version includes ALL fields and the full constructor
 * required by EventManager.
 */
public class Event {

    private String id;
    private String type;
    private String name;
    private String date;
    private String time;

    private double vipPrice;
    private double goldPrice;
    private double silverPrice;
    private double bronzePrice;
    private double generalAdmissionPrice;

    /**
     * Full constructor used when loading events from CSV.
     */
    public Event(String id,
                 String type,
                 String name,
                 String date,
                 String time,
                 double vipPrice,
                 double goldPrice,
                 double silverPrice,
                 double bronzePrice,
                 double generalAdmissionPrice) {

        this.id = id;
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

    // Getters (you can add setters later if needed)
    public String getId() { return id; }
    public String getType() { return type; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getTime() { return time; }

    public double getVipPrice() { return vipPrice; }
    public double getGoldPrice() { return goldPrice; }
    public double getSilverPrice() { return silverPrice; }
    public double getBronzePrice() { return bronzePrice; }
    public double getGeneralAdmissionPrice() { return generalAdmissionPrice; }
}
