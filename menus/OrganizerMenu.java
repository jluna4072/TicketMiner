package menus;

/**
 * Handles the Organizer menu and all organizer-specific event management operations.
 * Implements the EventManageable interface to provide add, view, update, and delete
 * functionality for events.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import model.events.Concert;
import model.events.Event;
import model.events.Special;
import model.events.Sport;
import model.users.User;
import utility.DataManager;
import utility.EventManageable;
import utility.EventNotFoundException;
import utility.Logger;

public class OrganizerMenu implements EventManageable {

    private final Scanner in;
    private final HashMap<Integer, Event> eventMap;
    private final DataManager dataManager;
    private final User loggedInUser;

    /**
     * Constructs an OrganizerMenu with the required shared state.
     *
     * @param in the shared Scanner for console input
     * @param eventMap the shared map of event ID to Event
     * @param dataManager the shared DataManager for ID generation and search
     * @param loggedInUser the currently logged-in Organizer user
     */
    public OrganizerMenu(Scanner in, HashMap<Integer, Event> eventMap,
                         DataManager dataManager, User loggedInUser) {
        this.in = in;
        this.eventMap = eventMap;
        this.dataManager = dataManager;
        this.loggedInUser = loggedInUser;
    }

    /**
     * Displays the Organizer Main Menu loop, providing options to manage events,
     * generate an event report, or log out.
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
     * Prompts the Organizer for all required event fields and adds a new event to the system.
     * Validates all inputs before creating the event.
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
        
        String name = readNonBlank("Enter Event Name: ");
        String date = readDate("Enter Event Date (MM/DD/YYYY): ");
        String time = readTime("Enter Event Time (hh:mm AM/PM): ");
        String venue = readNonBlank("Enter Venue / Location: ");
        int capacity = readPositiveInt("Enter Total Capacity (total seats): ");
        double vipPrice = readPositiveDouble("Enter VIP Ticket Price: $");
        double goldPrice = readPositiveDouble("Enter Gold Ticket Price: $");
        double silverPrice = readPositiveDouble("Enter Silver Ticket Price: $");
        double bronzePrice = readPositiveDouble("Enter Bronze Ticket Price: $");
        double gaPrice = readPositiveDouble("Enter General Admission Ticket Price: $");
        int vipSeats = readPositiveInt("Enter number of VIP seats: ");
        int goldSeats = readPositiveInt("Enter number of Gold seats: ");
        int silverSeats = readPositiveInt("Enter number of Silver seats: ");
        int bronzeSeats = readPositiveInt("Enter number of Bronze seats: ");
        int gaSeats = readPositiveInt("Enter number of General Admission seats: ");

        int id = dataManager.generateUniqueEventId();
        Event newEvent;
        switch (type.toLowerCase()) {
            case "sport":
                newEvent = new Sport(id, type, name, date, time, venue, capacity,
                    vipPrice, goldPrice, silverPrice, bronzePrice, gaPrice,
                    vipSeats, goldSeats, silverSeats, bronzeSeats, gaSeats);
                break;
            case "concert":
                newEvent = new Concert(id, type, name, date, time, venue, capacity,
                    vipPrice, goldPrice, silverPrice, bronzePrice, gaPrice,
                    vipSeats, goldSeats, silverSeats, bronzeSeats, gaSeats);
                break;
            default:
                newEvent = new Special(id, type, name, date, time, venue, capacity,
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
     * Displays the View Events sub-menu for organizers, allowing them to display all events
     * or search for a specific event by ID, name, or date.
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
                Event found = searchEvent(query);
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
     * Prompts the Organizer to search for an event and update its name or date/time.
     */
    @Override
    public void updateEvent() {
        System.out.print("\nEnter search term to find event (Event ID, Name, or Date): ");
        String query = in.nextLine().trim();
        try {
            Event found = searchEvent(query);
            System.out.println("\nFound: " + found.getEventName());
            System.out.println("\n===== Update Event =====");
            System.out.println("1. Change Name");
            System.out.println("2. Change Date and Time");
            System.out.println("========================");
            System.out.print("Please select an option (1 or 2): ");
            String choice = in.nextLine().trim();

            if (choice.equals("1")) {
                String newName = readNonBlank("Enter new event name: ");
                found.setEventName(newName);
                System.out.println("Event name updated successfully.");
                String actionDetail = "Organizer " + loggedInUser.getUserID() + " updated event " + found.getEventID() + " name to \"" + newName + "\".";
                Logger.logAction(actionDetail);
            } else if (choice.equals("2")) {
                String newDate = readDate("Enter new date (MM/DD/YYYY): ");
                String newTime = readTime("Enter new time (hh:mm AM/PM): ");
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
     * Prompts the Organizer to search for an event and delete it after confirmation.
     */
    @Override
    public void deleteEvent() {
        System.out.print("\nEnter search term to find event (Event ID, Name, or Date): ");
        String query = in.nextLine().trim();
        try {
            Event found = searchEvent(query);
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
     * Searches for an event by ID, name, or date and returns it.
     * Uses the custom EventNotFoundException for not-found cases.
     *
     * @param query the search term (Event ID, Name, or Date)
     * @return the found Event
     * @throws EventNotFoundException if no event matches the query
     */
    private Event searchEvent(String query) throws EventNotFoundException {
        Event found = resolveEvent(query);
        if (found == null) {
            throw new EventNotFoundException("Event not found. Please try again.");
        }
        return found;
    }

    /**
     * Generates and displays a detailed event report on the console, including
     * ticket sales, revenue per type, total revenue, expected profit, and actual profit.
     */
    private void generateEventReport() {
        System.out.print("\nEnter search term to find event (Event ID, Name, or Date): ");
        String query = in.nextLine().trim();
        try {
            Event found = searchEvent(query);

            double revenueVip = found.getVipSeatsSold() * found.getVipPrice();
            double revenueGold = found.getGoldSeatsSold() * found.getGoldPrice();
            double revenueSilver = found.getSilverSeatsSold() * found.getSilverPrice();
            double revenueBronze = found.getBronzeSeatsSold() * found.getBronzePrice();
            double revenueGA = found.getGeneralAdmissionSeatsSold() * found.getGeneralAdmissionPrice();
            double totalRevenue = revenueVip + revenueGold + revenueSilver + revenueBronze + revenueGA;
            double expectedProfit = found.getExpectedProfit();
            double actualProfit = totalRevenue;

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


    /**
     * Searches the event map for events matching the given query (by ID, name, or date).
     * If multiple matches are found, prompts the user to disambiguate by ID.
     *
     * @param input the search query (numeric ID, event name, or date string)
     * @return the matched Event, or null if no match is found
     */
    private Event resolveEvent(String input) {
        List<Event> matches = dataManager.findEvents(eventMap, input);

        if (matches.isEmpty()) {
            return null;
        }
        if (matches.size() == 1) {
            return matches.get(0);
        }

        System.out.println("Multiple events found:");
        for (Event e : matches) {
            System.out.println("  ID: " + e.getEventID() + " | Name: " + e.getEventName()
                    + " | Date: " + e.getDate());
        }
        while (true) {
            System.out.print("Please enter the ID to select a specific event: ");
            String refined = in.nextLine().trim();
            try {
                int id = Integer.parseInt(refined);
                for (Event e : matches) {
                    if (e.getEventID() == id) {
                        return e;
                    }
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("Selection does not match any of the found events. Please try again.");
        }
    }

    /**
     * Repeatedly prompts the user until a non-blank string is entered.
     *
     * @param prompt the message to display before reading input
     * @return the non-blank string entered by the user
     */
    private String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = in.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("This field cannot be blank. Please try again.");
        }
    }

    /**
     * Repeatedly prompts the user until a non-negative integer is entered.
     *
     * @param prompt the prompt message to display
     * @return the non-negative integer value entered by the user
     */
    private int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(in.nextLine().trim());
                if (value >= 0) return value;
                System.out.println("Value cannot be negative. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    /**
     * Repeatedly prompts the user until a non-negative double is entered.
     *
     * @param prompt the prompt message to display
     * @return the non-negative double value entered by the user
     */
    private double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(in.nextLine().trim());
                if (value >= 0) return value;
                System.out.println("Price cannot be negative. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Repeatedly prompts the user until a valid date string in MM/DD/YYYY format is entered.
     *
     * @param prompt the message to display before reading input
     * @return a valid date string in MM/DD/YYYY format
     */
    private String readDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        while (true) {
            System.out.print(prompt);
            String input = in.nextLine().trim();
            try {
                LocalDate.parse(input, formatter);
                return input;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Please enter a valid date in MM/DD/YYYY format.");
            }
        }
    }

    /**
     * Repeatedly prompts the user until a valid time string in hh:mm AM/PM format is entered.
     *
     * @param prompt the message to display before reading input
     * @return a valid time string in hh:mm AM/PM format
     */
    private String readTime(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        while (true) {
            System.out.print(prompt);
            String input = in.nextLine().trim().toUpperCase();
            try {
                LocalTime.parse(input, formatter);
                return input;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time. Please enter a valid time in hh:mm AM/PM format (e.g. 07:30 PM).");
            }
        }
    }
}
