/**
 * Represents a Special event in the TicketMiner system.
 * Extends the abstract Event class.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
package model.events;

public class Special extends Event {

    /**
     * Constructs a Special event with the specified scheduling and pricing details.
     *
     * @param id the unique numeric identifier for the special event
     * @param type the event type
     * @param name the name of the special event
     * @param date the scheduled date (MM/DD/YYYY format)
     * @param time the scheduled time (hh:mm AM/PM format)
     * @param vipPrice the price for a VIP ticket
     * @param goldPrice the price for a Gold ticket
     * @param silverPrice the price for a Silver ticket
     * @param bronzePrice the price for a Bronze ticket
     * @param generalAdmissionPrice the price for a General Admission ticket
     */
    public Special(int id, String type, String name, String date, String time, double vipPrice, double goldPrice, double silverPrice, double bronzePrice, double generalAdmissionPrice) {
        super(id, type, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice);
    }
}
