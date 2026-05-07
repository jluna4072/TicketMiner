package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import model.events.Concert;
import model.events.Event;
import model.events.Special;
import model.events.Sport;
import model.users.Admin;
import model.users.Customer;
import model.users.Organizer;
import model.users.User;
import model.venues.Arena;
import model.venues.Auditorium;
import model.venues.OpenAir;
import model.venues.Stadium;
import model.venues.Venue;

/**
 * Handles loading and searching of user, venue, and event data.
 * Reads CSV files and stores records in HashMaps for efficient lookup.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public class DataManager {

    private int lastUserIDSeen = 0;
    private int lastVenueIDSeen = 0;
    private int lastEventIDSeen = 0;
    private int confirmationNumber = 0;
    private String userHeader = "";
    private String eventHeader = "";
    private String venueHeader = "";
    private final HashMap<String, String> venueNameToId = new HashMap<>();

    /**
     * Searches the user map for users matching the given query. Lookup priority is:
     * numeric ID first, then exact username match, then case-insensitive full name match.
     *
     * @param userMap the map of username to {@link User} to search
     * @param query   the search term (numeric ID, username, or "First Last")
     * @return a list of matching {@link User} objects; empty if none found
     */
    public List<User> findUsers(HashMap<String, User> userMap, String query) {
        List<User> matches = new ArrayList<>();

        try {
            int id = Integer.parseInt(query);
            for (User u : userMap.values()) {
                if (u.getUserID() == id) {
                    matches.add(u);
                    return matches;
                }
            }
        } catch (NumberFormatException ignored) {
        }

        if (userMap.containsKey(query)) {
            matches.add(userMap.get(query));
            return matches;
        }

        for (User u : userMap.values()) {
            String fullName = u.getFirstName() + " " + u.getLastName();
            if (fullName.equalsIgnoreCase(query)) {
                matches.add(u);
            }
        }
        return matches;
    }

    /**
     * Searches the venue map for venues matching the given query. Lookup priority is:
     * numeric ID first, then case-insensitive name match, then case-insensitive type match.
     *
     * @param venueMap the map of venue ID to {@link Venue} to search
     * @param query    the search term (numeric ID, venue name, or venue type)
     * @return a list of matching {@link Venue} objects; empty if none found
     */
    public List<Venue> findVenues(HashMap<Integer, Venue> venueMap, String query) {
        List<Venue> matches = new ArrayList<>();

        try {
            int id = Integer.parseInt(query);
            if (venueMap.containsKey(id)) {
                matches.add(venueMap.get(id));
                return matches;
            }
        } catch (NumberFormatException ignored) {
        }

        for (Venue v : venueMap.values()) {
            if (v.getName().equalsIgnoreCase(query)) {
                matches.add(v);
            }
        }
        if (!matches.isEmpty()) {
            return matches;
        }

        for (Venue v : venueMap.values()) {
            if (v.getType().equalsIgnoreCase(query)) {
                matches.add(v);
            }
        }
        return matches;
    }

    /**
     * Searches the event map for events matching the given query. Lookup priority is:
     * numeric ID first, then case-insensitive name match, then case-insensitive date match.
     *
     * @param eventMap the map of event ID to {@link Event} to search
     * @param query    the search term (numeric ID, event name, or date string)
     * @return a list of matching {@link Event} objects; empty if none found
     */
    public List<Event> findEvents(HashMap<Integer, Event> eventMap, String query) {
        List<Event> matches = new ArrayList<>();

        try {
            int id = Integer.parseInt(query);
            if (eventMap.containsKey(id)) {
                matches.add(eventMap.get(id));
                return matches;
            }
        } catch (NumberFormatException ignored) {
        }

        for (Event e : eventMap.values()) {
            if (e.getEventName().equalsIgnoreCase(query)) {
                matches.add(e);
            }
        }
        if (!matches.isEmpty()) {
            return matches;
        }

        for (Event e : eventMap.values()) {
            if (e.getDate().equalsIgnoreCase(query)) {
                matches.add(e);
            }
        }
        return matches;
    }

    /**
     * Builds a mapping of normalized (lowercase, trimmed) column names to their index positions
     * from a CSV header row. This allows the system to handle input files regardless of column order.
     *
     * @param headerLine the first line of the CSV containing column names
     * @return a {@link HashMap} mapping lowercase column name to column index
     */
    private HashMap<String, Integer> buildColumnMap(String headerLine) {
        HashMap<String, Integer> columnMap = new HashMap<>();
        String[] headers = headerLine.split(",");
        for (int i = 0; i < headers.length; i++) {
            columnMap.put(headers[i].trim().toLowerCase(), i);
        }
        return columnMap;
    }

    /**
     * Reads a CSV file of users and populates a map keyed by username.
     * Parses the header row dynamically to determine column positions, so the CSV
     * columns can be in any order.
     * Also tracks the highest user ID seen to support unique ID generation.
     *
     * @param fileName path to the CSV file containing user records
     * @return a {@link HashMap} mapping username to the corresponding {@link User} object
     */
    public HashMap<String, User> loadUsers(String fileName) {
        HashMap<String, User> userMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String headerLine = br.readLine();
            if (headerLine == null) return userMap;
            this.userHeader = headerLine;
            HashMap<String, Integer> col = buildColumnMap(headerLine);
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                int id = Integer.parseInt(fields[col.get("id")].trim());
                String firstName = fields[col.get("first name")].trim();
                String lastName = fields[col.get("last name")].trim();
                String username = fields[col.get("username")].trim();
                String password = fields[col.get("password")].trim();
                String userType = fields[col.get("user type")].trim();

                User user;
                switch (userType) {
                    case "Customer" -> {
                        double moneyAvailable = Double.parseDouble(fields[col.get("money available")].trim());
                        boolean isMember = Boolean.parseBoolean(fields[col.get("ticketminer membership")].trim());
                        int concertsPurchased = Integer.parseInt(fields[col.get("concerts purchased")].trim());
                        user = new Customer(id, firstName, lastName, username, password, userType, moneyAvailable, isMember, concertsPurchased);
                    }
                    case "Organizer" -> {
                        user = new Organizer(id, firstName, lastName, username, password, userType);
                    }
                    default -> {
                        user = new Admin(id, firstName, lastName, username, password, userType);
                    }
                }
                lastUserIDSeen = Math.max(lastUserIDSeen, id);
                userMap.put(username, user);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return userMap;
    }

    /**
     * Reads a CSV file of events and populates a map keyed by event ID.
     * Parses the header row dynamically to determine column positions, so the CSV
     * columns can be in any order. If a "Venue ID" column is present, seat counts
     * are computed automatically from the linked venue's capacity and seat percentages.
     * Also tracks the highest event ID seen to support unique ID generation.
     *
     * @param fileName  path to the CSV file containing event records
     * @param venueMap  the loaded venue map used to compute seat counts from venue data
     * @return a {@link HashMap} mapping event ID to the corresponding {@link Event} object
     */
    public HashMap<Integer, Event> loadEvents(String fileName, HashMap<Integer, Venue> venueMap) {
        HashMap<Integer, Event> eventMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String headerLine = br.readLine();
            if (headerLine == null) return eventMap;
            this.eventHeader = headerLine;
            HashMap<String, Integer> col = buildColumnMap(headerLine);
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                int id = Integer.parseInt(fields[col.get("id")].trim());
                String type = fields[col.get("type")].trim();
                String name = fields[col.get("name")].trim();
                String date = fields[col.get("date")].trim();
                String time = fields[col.get("time")].trim();
                double vipPrice = Double.parseDouble(fields[col.get("vip price")].trim());
                double goldPrice = Double.parseDouble(fields[col.get("gold price")].trim());
                double silverPrice = Double.parseDouble(fields[col.get("silver price")].trim());
                double bronzePrice = Double.parseDouble(fields[col.get("bronze price")].trim());
                double generalAdmissionPrice = Double.parseDouble(fields[col.get("general admission price")].trim());
                Event event;

                switch (type) {
                    case "Sport" -> {
                        event = new Sport(id, type, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice);
                    }
                    case "Concert" -> {
                        event = new Concert(id, type, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice);
                    }
                    default -> {
                        event = new Special(id, type, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice);
                    }
                }

                if (col.containsKey("venue id")) {
                    int venueId = Integer.parseInt(fields[col.get("venue id")].trim());
                    Venue venue = venueMap.get(venueId);
                    if (venue != null) {
                        int capacity = venue.getCapacity();
                        event.setVenue(venue.getName());
                        event.setCapacity(capacity);
                        event.setVipSeats((int) (capacity * venue.getVipPercent() / 100.0));
                        event.setGoldSeats((int) (capacity * venue.getGoldPercent() / 100.0));
                        event.setSilverSeats((int) (capacity * venue.getSilverPercent() / 100.0));
                        event.setBronzeSeats((int) (capacity * venue.getBronzePercent() / 100.0));
                        event.setGeneralAdmissionSeats((int) (capacity * venue.getGeneralAdmissionPercent() / 100.0));
                    }
                }

                lastEventIDSeen = Math.max(lastEventIDSeen, id);
                eventMap.put(id, event);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return eventMap;
    }

    /**
     * Reads a CSV file of venues and populates a map keyed by venue ID.
     * Parses the header row dynamically to determine column positions, so the CSV
     * columns can be in any order.
     * Also tracks the highest venue ID seen to support unique ID generation.
     *
     * @param fileName path to the CSV file containing venue records
     * @return a {@link HashMap} mapping venue ID to the corresponding {@link Venue} object
     */
    public HashMap<Integer, Venue> loadVenues(String fileName) {
        HashMap<Integer, Venue> venueMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String headerLine = br.readLine();
            if (headerLine == null) return venueMap;
            this.venueHeader = headerLine;
            HashMap<String, Integer> col = buildColumnMap(headerLine);
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                int id = Integer.parseInt(fields[col.get("id")].trim());
                String name = fields[col.get("name")].trim();
                String type = fields[col.get("type")].trim();
                int capacity = Integer.parseInt(fields[col.get("capacity")].trim());
                int concertCapacity = Integer.parseInt(fields[col.get("concert capacity")].trim());
                double cost = Double.parseDouble(fields[col.get("cost")].trim());
                int vipPercent = Integer.parseInt(fields[col.get("vip percent")].trim());
                int goldPercent = Integer.parseInt(fields[col.get("gold percent")].trim());
                int silverPercent = Integer.parseInt(fields[col.get("silver percent")].trim());
                int bronzePercent = Integer.parseInt(fields[col.get("bronze percent")].trim());
                int generalAdmissionPercent = Integer.parseInt(fields[col.get("general admission percent")].trim());
                int reservedPercent = Integer.parseInt(fields[col.get("reserved extra percent")].trim());
                Venue venue;

                switch (type) {
                    case "Arena" -> {
                        venue = new Arena(id, name, type, capacity, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedPercent);
                    }
                    case "Stadium" -> {
                        venue = new Stadium(id, name, type, capacity, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedPercent);
                    }
                    case "Open Air" -> {
                        venue = new OpenAir(id, name, type, capacity, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedPercent);
                    }
                    default -> {
                        venue = new Auditorium(id, name, type, capacity, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedPercent);
                    }
                }
                lastVenueIDSeen = Math.max(lastVenueIDSeen, id);
                venueMap.put(id, venue);
                venueNameToId.put(name, String.valueOf(id));
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return venueMap;
    }

    /**
     * Returns the value for a given column name from an Event object.
     *
     * @param colName the lowercase column name
     * @param event the Event to extract the value from
     * @return the string representation of the value for that column
     */
    private String getEventField(String colName, Event event) {
        switch (colName) {
            case "id": return String.valueOf(event.getEventID());
            case "type": return event.getType();
            case "name": return event.getEventName();
            case "date": return event.getDate();
            case "time": return event.getTime();
            case "vip price": return String.valueOf(event.getVipPrice());
            case "gold price": return String.valueOf(event.getGoldPrice());
            case "silver price": return String.valueOf(event.getSilverPrice());
            case "bronze price": return String.valueOf(event.getBronzePrice());
            case "general admission price": return String.valueOf(event.getGeneralAdmissionPrice());
            case "venue id": return venueNameToId.getOrDefault(event.getVenue(), "");
            default: return "";
        }
    }

    /**
     * Returns the value for a given column name from a User object.
     *
     * @param colName the lowercase column name
     * @param user the User to extract the value from
     * @return the string representation of the value for that column
     */
    private String getUserField(String colName, User user) {
        switch (colName) {
            case "id": return String.valueOf(user.getUserID());
            case "first name": return user.getFirstName();
            case "last name": return user.getLastName();
            case "username": return user.getUsername();
            case "password": return user.getPassword();
            case "user type": return user.getUserType();
            case "money available":
                if (user instanceof Customer) {
                    return String.valueOf(((Customer) user).getMoneyAvailable());
                } else {
                    return "";
                }
            case "ticketminer membership":
                if (user instanceof Customer){
                    return String.valueOf(((Customer) user).hasMembership());
                } else {
                    return "false";
                }
            case "concerts purchased":
                if (user instanceof Customer){
                    return String.valueOf(((Customer) user).getConcertsPurchased());
                }
                return "0";
            default: return "";
        }
    }

    /**
     * Returns the value for a given column name from a Venue object.
     *
     * @param colName the lowercase column name
     * @param venue the Venue to extract the value from
     * @return the string representation of the value for that column
     */
    private String getVenueField(String colName, Venue venue) {
        switch (colName) {
            case "id":
                return String.valueOf(venue.getVenueID());
            case "name":
                return venue.getName();
            case "type":
                return venue.getType();
            case "capacity":
                return String.valueOf(venue.getCapacity());
            case "concert capacity":
                return String.valueOf(venue.getConcertCapacity());
            case "cost":
                return String.valueOf(venue.getCost());
            case "vip percent":
                return String.valueOf(venue.getVipPercent());
            case "gold percent":
                return String.valueOf(venue.getGoldPercent());
            case "silver percent":
                return String.valueOf(venue.getSilverPercent());
            case "bronze percent":
                return String.valueOf(venue.getBronzePercent());
            case "general admission percent":
                return String.valueOf(venue.getGeneralAdmissionPercent());
            case "reserved extra percent":
                return String.valueOf(venue.getReservedPercent());
            default: return "";
        }
    }

    /**
     * Writes all events in the provided map to a CSV file, preserving the original column order.
     *
     * @param fileName path to the output CSV file
     * @param eventMap the map of event ID to {@link Event} to write
     */
    public void writeEvent(String fileName, HashMap<Integer, Event> eventMap) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println(eventHeader);
            String[] columns = eventHeader.split(",");
            for (Event event : eventMap.values()) {
                StringBuilder line = new StringBuilder();
                for (int i = 0; i < columns.length; i++) {
                    if (i > 0) line.append(",");
                    line.append(getEventField(columns[i].trim().toLowerCase(), event));
                }
                writer.println(line.toString());
            }
        } catch (IOException e) {
            System.out.println("Error writing events file: " + e.getMessage());
        }
    }

    /**
     * Writes all users in the provided map to a CSV file, preserving the original column order.
     *
     * @param fileName path to the output CSV file
     * @param userMap  the map of username to {@link User} to write
     */
    public void writeUsers(String fileName, HashMap<String, User> userMap) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println(userHeader);
            String[] columns = userHeader.split(",");
            for (User user : userMap.values()) {
                StringBuilder line = new StringBuilder();
                for (int i = 0; i < columns.length; i++) {
                    if (i > 0) line.append(",");
                    line.append(getUserField(columns[i].trim().toLowerCase(), user));
                }
                writer.println(line.toString());
            }
        } catch (IOException e) {
            System.out.println("Error writing users file: " + e.getMessage());
        }
    }

    /**
     * Writes all venues in the provided map to a CSV file, preserving the original column order.
     *
     * @param fileName path to the output CSV file
     * @param venueMap the map of venue ID to {@link Venue} to write
     */
    public void writeVenues(String fileName, HashMap<Integer, Venue> venueMap) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println(venueHeader);
            String[] columns = venueHeader.split(",");
            for (Venue venue : venueMap.values()) {
                StringBuilder line = new StringBuilder();
                for (int i = 0; i < columns.length; i++) {
                    if (i > 0) line.append(",");
                    line.append(getVenueField(columns[i].trim().toLowerCase(), venue));
                }
                writer.println(line.toString());
            }
        } catch (IOException e) {
            System.out.println("Error writing venues file: " + e.getMessage());
        }
    }
    /**
     * Searches for an event matching the query and prompts the user to disambiguate
     * if multiple matches are found.
     *
     * @param eventMap the map of event ID to Event
     * @param query the search term (numeric ID, event name, or date)
     * @param in the Scanner for user input when disambiguation is needed
     * @return the matched Event
     * @throws EventNotFoundException if no event matches the query
     */
    public Event resolveEvent(HashMap<Integer, Event> eventMap, String query, Scanner in) throws EventNotFoundException {
        List<Event> matches = findEvents(eventMap, query);

        if (matches.isEmpty()) {
            throw new EventNotFoundException("Event not found for query: " + query);
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
     * Searches for a user matching the query and prompts to disambiguate
     * if multiple matches are found.
     *
     * @param userMap the map of username to User
     * @param query the search term (numeric ID, username, or "First Last")
     * @param in the Scanner for user input when disambiguation is needed
     * @return the matched User, or null if no match is found
     */
    public User resolveUser(HashMap<String, User> userMap, String query, Scanner in) {
        List<User> matches = findUsers(userMap, query);

        if (matches.isEmpty()) return null;
        if (matches.size() == 1) return matches.get(0);

        System.out.println("Multiple members found with that name:");
        for (User u : matches) {
            System.out.println("  ID: " + u.getUserID() + " | Username: " + u.getUsername()
                    + " | " + u.getFirstName() + " " + u.getLastName());
        }
        while (true) {
            System.out.print("Please enter the ID or username to select a specific member: ");
            String refined = in.nextLine().trim();
            for (User u : matches) {
                if (u.getUsername().equals(refined)) return u;
                try {
                    if (u.getUserID() == Integer.parseInt(refined)) return u;
                } catch (NumberFormatException ignored) {}
            }
            System.out.println("Selection does not match any of the found members. Please try again.");
        }
    }

    /**
     * Searches for a venue matching the query and prompts to disambiguate
     * if multiple matches are found.
     *
     * @param venueMap the map of venue ID to Venue
     * @param query the search term (numeric ID, venue name, or venue type)
     * @param in the Scanner for user input when disambiguation is needed
     * @return the matched Venue, or null if no match is found
     */
    public Venue resolveVenue(HashMap<Integer, Venue> venueMap, String query, Scanner in) {
        List<Venue> matches = findVenues(venueMap, query);

        if (matches.isEmpty()) return null;
        if (matches.size() == 1) return matches.get(0);

        System.out.println("Multiple venues found:");
        for (Venue v : matches) {
            System.out.println("  ID: " + v.getVenueID() + " | Name: " + v.getName()
                    + " | Type: " + v.getType());
        }
        while (true) {
            System.out.print("Please enter the ID or name to select a specific venue: ");
            String refined = in.nextLine().trim();
            for (Venue v : matches) {
                if (v.getName().equalsIgnoreCase(refined)) return v;
                try {
                    if (v.getVenueID() == Integer.parseInt(refined)) return v;
                } catch (NumberFormatException ignored) {}
            }
            System.out.println("Selection does not match any of the found venues. Please try again.");
        }
    }

    /**
     * Prints all order history entries for a given customer from the history CSV file.
     *
     * @param fileName the path to the customer order history CSV
     * @param userID the customer's ID to filter by
     */
   public void printCustomerHistory(String fileName, int userID) {
    File file = new File(fileName);
    if (!file.exists()) {
        return;
    }
    
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 1) {
                try {
                    int customerID = Integer.parseInt(parts[0].trim());
                    if (customerID == userID) {
                        System.out.println(line);
                    }
                } catch (NumberFormatException e) {
                    // Skip malformed lines silently
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading customer history: " + e.getMessage());
    }
}
    /**
     * Writes a customer's purchase transaction to the order history CSV file.
     *
     * @param fileName the path to the CSV file
     * @param userID the customer's ID
     * @param eventType the type of event (Sport, Concert, Special)
     * @param eventName the name of the event
     * @param eventDate the date of the event
     * @param ticketType the type of ticket purchased (VIP, Gold, Silver, Bronze, General Admission)
     * @param numOfTickets the number of tickets purchased
     * @param totalCost the total cost charged to the customer
     * @param confirmNum the confirmation number for this transaction
     */
    public void writeCustomerHistory(String fileName, int userID, String eventType, String eventName, String eventDate, String ticketType, int numOfTickets, double totalCost, int confirmNum) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.printf("%d,%s,%s,%s,%s,%d,%.2f,%d%n",
                userID, eventType, eventName, eventDate, ticketType, numOfTickets, totalCost, confirmNum);
        } catch (IOException e) {
            System.out.println("Error writing customer history file: " + e.getMessage());
        }
    }
    
    /**
     * Increments and returns the next unique user ID based on the highest ID seen during load.
     *
     * @return a new unique user ID
     */
    public int generateUniqueUserId() {
        lastUserIDSeen++;
        return lastUserIDSeen;
    }

    /**
     * Increments and returns the next unique venue ID based on the highest ID seen during load.
     *
     * @return a new unique venue ID
     */
    public int generateUniqueVenueId() {
        lastVenueIDSeen++;
        return lastVenueIDSeen;
    }

    /**
     * Increments and returns the next unique event ID based on the highest ID seen during load.
     *
     * @return a new unique event ID
     */
    public int generateUniqueEventId() {
        lastEventIDSeen++;
        return lastEventIDSeen;
    }

    /**
     * Increments and returns the next unique confirmation number for a purchase transaction.
     *
     * @return a new unique confirmation number
     */
    public int generateConfirmationNumber(){
        confirmationNumber++;
        return confirmationNumber;
    }

}
