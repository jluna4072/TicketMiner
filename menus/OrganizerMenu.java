package menus;

import java.util.HashMap;
import java.util.Scanner;
import model.events.Concert;
import model.events.Event;
import model.events.Special;
import model.events.Sport;
import model.users.User;
import model.venues.Venue;
import utility.DataManager;
import utility.EventManageable;
import utility.EventNotFoundException;
import utility.InputReader;
import utility.Logger;

/**
 * Handles the Organizer menu and all organizer-specific event management
 * operations. Implements the EventManageable interface to provide add, view,
 * update, and delete functionality for events.
 *
 * Design Pattern: Strategy Pattern (Concrete Strategy) This class serves as a
 * concrete strategy in the Strategy pattern, providing the Organizer-specific
 * implementation of event management operations defined by the
 * {@code EventManageable} interface.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public class OrganizerMenu implements EventManageable {

    private final Scanner in;
    private final InputReader reader;
    private final HashMap<Integer, Event> eventMap;
    private final HashMap<Integer, Venue> venueMap;
    private final DataManager dataManager;
    private final User loggedInUser;

    /**
     * Constructs an OrganizerMenu with the required shared state.
     *
     * @param in the shared Scanner for console input
     * @param eventMap the shared map of event ID to Event
     * @param venueMap the shared map of venue ID to Venue
     * @param dataManager the shared DataManager for ID generation and search
     * @param loggedInUser the currently logged-in Organizer user
     */
    public OrganizerMenu(Scanner in, HashMap<Integer, Event> eventMap,
            HashMap<Integer, Venue> venueMap,
            DataManager dataManager, User loggedInUser) {
        this.in = in;
        this.reader = new InputReader(in);
        this.eventMap = eventMap;
        this.venueMap = venueMap;
        this.dataManager = dataManager;
        this.loggedInUser = loggedInUser;
    }

    /**
     * Displays the Organizer Main Menu loop, providing options to manage
     * events, generate an event report, or log out.
     *
     * @return true if the user logged out
     */
    public boolean show() {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n===== Organizer Menu =====");
            System.out.println("1. Manage Event");
            System.out.println("2. Generate Event Report");
            System.out.println("3. Log Out");
            System.out.println("==========================");
            System.out.print("Please select an option (1-3): ");
            try {
                int choice = Integer.parseInt(in.nextLine().trim());
                switch (choice) {
                    case 1:
                        manageEvent();
                        break;
                    case 2:
                        generateEventReport();
                        break;
                    case 3:
                        System.out.println("Logging out...");
                        String actionDetail = "Organizer " + loggedInUser.getUserID() + " has logged out.";
                        Logger.logAction(actionDetail);
                        logout = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please enter 1, 2, or 3.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return true;
    }

    /**
     * Displays the Manage Event sub-menu for organizers, providing options to
     * add, view, update, or delete events.
     */
    private void manageEvent() {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== Manage Event =====");
            System.out.println("1. Add");
            System.out.println("2. View");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Back");
            System.out.println("========================");
            System.out.print("Please select an option (1-5): ");
            try {
                int choice = Integer.parseInt(in.nextLine().trim());
                switch (choice) {
                    case 1:
                        addEvent();
                        break;
                    case 2:
                        viewEvent();
                        break;
                    case 3:
                        updateEvent();
                        break;
                    case 4:
                        deleteEvent();
                        break;
                    case 5:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please enter a number between 1 and 5.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    /**
     * Prompts the Organizer for all required event fields and adds a new event
     * to the system. Validates all inputs before creating the event.
     */
    @Override
    public void addEvent() {
        System.out.println("\n--- Add New Event ---");
        String type;
        while (true) {
            System.out.print("Enter Event Type (Sport, Concert, Special): ");
            type = in.nextLine().trim();
            if (type.equalsIgnoreCase("Sport") || type.equalsIgnoreCase("Concert") || type.equalsIgnoreCase("Special")) {
                break;
            }
            System.out.println("Invalid type. Please enter Sport, Concert, or Special.");
        }

        String name = reader.readNonBlank("Enter Event Name: ");
        String date = reader.readDate("Enter Event Date (MM/DD/YYYY): ");
        String time = reader.readTime("Enter Event Time (hh:mm AM/PM): ");

        Venue selectedVenue = null;
        while (selectedVenue == null) {
            System.out.print("Enter Venue (ID or Name): ");
            String venueQuery = in.nextLine().trim();
            selectedVenue = dataManager.resolveVenue(venueMap, venueQuery, in);
            if (selectedVenue == null) {
                System.out.println("Venue not found. Please try again.");
            }
        }

        int capacity = selectedVenue.getCapacity();
        int vipSeats = (int) (capacity * selectedVenue.getVipPercent() / 100.0);
        int goldSeats = (int) (capacity * selectedVenue.getGoldPercent() / 100.0);
        int silverSeats = (int) (capacity * selectedVenue.getSilverPercent() / 100.0);
        int bronzeSeats = (int) (capacity * selectedVenue.getBronzePercent() / 100.0);
        int gaSeats = (int) (capacity * selectedVenue.getGeneralAdmissionPercent() / 100.0);

        double vipPrice = reader.readPositiveDouble("Enter VIP Ticket Price: $");
        double goldPrice = reader.readPositiveDouble("Enter Gold Ticket Price: $");
        double silverPrice = reader.readPositiveDouble("Enter Silver Ticket Price: $");
        double bronzePrice = reader.readPositiveDouble("Enter Bronze Ticket Price: $");
        double gaPrice = reader.readPositiveDouble("Enter General Admission Ticket Price: $");

        int id = dataManager.generateUniqueEventId();
        Event newEvent;
        switch (type.toLowerCase()) {
            case "sport":
                newEvent = new Sport(id, type, name, date, time, selectedVenue.getName(), capacity,
                        vipPrice, goldPrice, silverPrice, bronzePrice, gaPrice,
                        vipSeats, goldSeats, silverSeats, bronzeSeats, gaSeats);
                break;
            case "concert":
                newEvent = new Concert(id, type, name, date, time, selectedVenue.getName(), capacity,
                        vipPrice, goldPrice, silverPrice, bronzePrice, gaPrice,
                        vipSeats, goldSeats, silverSeats, bronzeSeats, gaSeats);
                break;
            default:
                newEvent = new Special(id, type, name, date, time, selectedVenue.getName(), capacity,
                        vipPrice, goldPrice, silverPrice, bronzePrice, gaPrice,
                        vipSeats, goldSeats, silverSeats, bronzeSeats, gaSeats);
                break;
        }

        eventMap.put(id, newEvent);
        System.out.println("Event added successfully! Event ID: " + id);
        String actionDetail = "Organizer " + loggedInUser.getUserID() + " added event " + id + " (" + name + ").";
        Logger.logAction(actionDetail);
    }

    /**
     * Displays the View Events sub-menu for organizers, allowing them to
     * display all events or search for a specific event by ID, name, or date.
     */
    @Override
    public void viewEvent() {
        System.out.println("\n===== View Events =====");
        System.out.println("a. Display all events");
        System.out.println("b. Search for an event");
        System.out.println("=======================");
        System.out.print("Please select an option (a or b): ");
        String choice = in.nextLine().trim().toLowerCase();

        if (choice.equals("a")) {
            if (eventMap.isEmpty()) {
                System.out.println("No events in the system.");
            } else {
                for (Event e : eventMap.values()) {
                    System.out.println(e);
                }
            }
            String actionDetail = "Organizer " + loggedInUser.getUserID() + " viewed all events.";
            Logger.logAction(actionDetail);
        } else if (choice.equals("b")) {
            System.out.print("Enter search term (Event ID, Name, or Date): ");
            String query = in.nextLine().trim();
            try {
                Event found = dataManager.resolveEvent(eventMap, query, in);
                System.out.println(found);
                String actionDetail = "Organizer " + loggedInUser.getUserID() + " searched and viewed event " + found.getEventID() + ".";
                Logger.logAction(actionDetail);
            } catch (EventNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("Invalid option. Please enter 'a' or 'b'.");
        }
    }

    /**
     * Prompts the Organizer to search for an event and update its name or
     * date/time.
     */
    @Override
    public void updateEvent() {
        System.out.print("\nEnter search term to find event (Event ID, Name, or Date): ");
        String query = in.nextLine().trim();
        try {
            Event found = dataManager.resolveEvent(eventMap, query, in);
            System.out.println("\nFound: " + found.getEventName());
            System.out.println("\n===== Update Event =====");
            System.out.println("1. Change Name");
            System.out.println("2. Change Date and Time");
            System.out.println("========================");
            System.out.print("Please select an option (1 or 2): ");
            String choice = in.nextLine().trim();

            if (choice.equals("1")) {
                String newName = reader.readNonBlank("Enter new event name: ");
                found.setEventName(newName);
                System.out.println("Event name updated successfully.");
                String actionDetail = "Organizer " + loggedInUser.getUserID() + " updated event " + found.getEventID() + " name to \"" + newName + "\".";
                Logger.logAction(actionDetail);
            } else if (choice.equals("2")) {
                String newDate = reader.readDate("Enter new date (MM/DD/YYYY): ");
                String newTime = reader.readTime("Enter new time (hh:mm AM/PM): ");
                found.setDate(newDate);
                found.setTime(newTime);
                System.out.println("Event date and time updated successfully.");
                String actionDetail = "Organizer " + loggedInUser.getUserID() + " updated event " + found.getEventID() + " date/time.";
                Logger.logAction(actionDetail);
            } else {
                System.out.println("Invalid option. No changes made.");
            }
        } catch (EventNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.out.println("No changes made.");
        }
    }

    /**
     * Prompts the Organizer to search for an event and delete it after
     * confirmation.
     */
    @Override
    public void deleteEvent() {
        System.out.print("\nEnter search term to find event (Event ID, Name, or Date): ");
        String query = in.nextLine().trim();
        try {
            Event found = dataManager.resolveEvent(eventMap, query, in);
            System.out.println("\nEvent found:");
            System.out.println(found);
            System.out.print("Are you sure you want to delete this event? (yes/no): ");
            String confirm = in.nextLine().trim().toLowerCase();
            if (confirm.equals("yes")) {
                eventMap.remove(found.getEventID());
                System.out.println("Event deleted successfully.");
                String actionDetail = "Organizer " + loggedInUser.getUserID() + " deleted event " + found.getEventID() + ".";
                Logger.logAction(actionDetail);
            } else {
                System.out.println("Deletion cancelled.");
            }
        } catch (EventNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Nothing was deleted.");
        }
    }

    /**
     * Generates and displays a detailed event report on the console, including
     * ticket sales, revenue per type, total revenue, expected profit, and
     * actual profit.
     */
    private void generateEventReport() {
        System.out.print("\nEnter search term to find event (Event ID, Name, or Date): ");
        String query = in.nextLine().trim();
        try {
            Event found = dataManager.resolveEvent(eventMap, query, in);

            double revenueVip = found.getVipSeatsSold() * found.getVipPrice();
            double revenueGold = found.getGoldSeatsSold() * found.getGoldPrice();
            double revenueSilver = found.getSilverSeatsSold() * found.getSilverPrice();
            double revenueBronze = found.getBronzeSeatsSold() * found.getBronzePrice();
            double revenueGA = found.getGeneralAdmissionSeatsSold() * found.getGeneralAdmissionPrice();
            double totalRevenue = revenueVip + revenueGold + revenueSilver + revenueBronze + revenueGA;
            double expectedProfit = found.getExpectedProfit();
            double actualProfit = totalRevenue - found.getTaxCollected();

            System.out.println("\n===== Event Report =====");
            System.out.printf("Event ID: %d%n", found.getEventID());
            System.out.printf("Event Type: %s%n", found.getType());
            System.out.printf("Event Name: %s%n", found.getEventName());
            System.out.printf("Date: %s %s%n", found.getDate(), found.getTime());
            System.out.printf("Event Capacity: %d%n", found.getCapacity());
            System.out.printf("Total Seats Sold: %d%n", found.getTotalSeatsSold());
            System.out.printf("Total VIP Seats Sold: %d%n", found.getVipSeatsSold());
            System.out.printf("Total Gold Seats Sold: %d%n", found.getGoldSeatsSold());
            System.out.printf("Total Silver Seats Sold: %d%n", found.getSilverSeatsSold());
            System.out.printf("Total Bronze Seats Sold: %d%n", found.getBronzeSeatsSold());
            System.out.printf("Total Gen Adm Seats Sold: %d%n", found.getGeneralAdmissionSeatsSold());
            System.out.printf("Revenue - VIP: $%.2f%n", revenueVip);
            System.out.printf("Revenue - Gold: $%.2f%n", revenueGold);
            System.out.printf("Revenue - Silver: $%.2f%n", revenueSilver);
            System.out.printf("Revenue - Bronze: $%.2f%n", revenueBronze);
            System.out.printf("Revenue - General Adm: $%.2f%n", revenueGA);
            System.out.printf("Total Revenue (all): $%.2f%n", totalRevenue);
            System.out.printf("Expected Profit: $%.2f%n", expectedProfit);
            System.out.printf("Actual Profit: $%.2f%n", actualProfit);
            System.out.println("========================");

            String actionDetail = "Organizer " + loggedInUser.getUserID() + " generated report for event " + found.getEventID() + ".";
            Logger.logAction(actionDetail);
        } catch (EventNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Unable to generate report.");
        }
    }

}
