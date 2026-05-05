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

    /**
     * Constructs a Special event with full details including venue, capacity, and seat allocation.
     *
     * @param id the unique numeric identifier
     * @param type the event type
     * @param name the name of the special event
     * @param date the scheduled date (MM/DD/YYYY format)
     * @param time the scheduled time (hh:mm AM/PM format)
     * @param venue the venue or location
     * @param capacity the total seat capacity
     * @param vipPrice the VIP ticket price
     * @param goldPrice the Gold ticket price
     * @param silverPrice the Silver ticket price
     * @param bronzePrice the Bronze ticket price
     * @param generalAdmissionPrice the General Admission ticket price
     * @param vipSeats the number of VIP seats
     * @param goldSeats the number of Gold seats
     * @param silverSeats the number of Silver seats
     * @param bronzeSeats the number of Bronze seats
     * @param generalAdmissionSeats the number of General Admission seats
     */
    public Special(int id, String type, String name, String date, String time,
                   String venue, int capacity,
                   double vipPrice, double goldPrice, double silverPrice,
                   double bronzePrice, double generalAdmissionPrice,
                   int vipSeats, int goldSeats, int silverSeats,
                   int bronzeSeats, int generalAdmissionSeats) {
        super(id, type, name, date, time, venue, capacity, vipPrice, goldPrice,
              silverPrice, bronzePrice, generalAdmissionPrice,
              vipSeats, goldSeats, silverSeats, bronzeSeats, generalAdmissionSeats);
    }
}
