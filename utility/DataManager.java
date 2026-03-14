package utility;

/**
 * Handles loading and searching of user, venue, and event data.
 * Reads CSV files and stores records in HashMaps for efficient lookup.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

//Assumed input files will not change and hardcoded their names for standard initialization
public class DataManager {

    private int lastUserIDSeen = 0;
    private int lastVenueIDSeen = 0;
    private int lastEventIDSeen = 0;

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
     * Reads a CSV file of users and populates a map keyed by username.
     * Also tracks the highest user ID seen to support unique ID generation.
     *
     * @param fileName path to the CSV file containing user records
     * @return a {@link HashMap} mapping username to the corresponding {@link User} object
     */
    public HashMap<String, User> loadUsers(String fileName) {
        HashMap<String, User> userMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                int id = Integer.parseInt(fields[0].trim());
                String firstName = fields[1].trim();
                String lastName = fields[2].trim();
                String username = fields[3].trim();
                String password = fields[4].trim();
                String userType = fields[5].trim();

                User user;
                switch (userType) {
                    case "Customer" -> {
                        double moneyAvailable = Double.parseDouble(fields[6].trim());
                        boolean isMember = Boolean.parseBoolean(fields[7].trim());
                        int concertsPurchased = Integer.parseInt(fields[8].trim());
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
     * Also tracks the highest event ID seen to support unique ID generation.
     *
     * @param fileName path to the CSV file containing event records
     * @return a {@link HashMap} mapping event ID to the corresponding {@link Event} object
     */
    public HashMap<Integer, Event> loadEvents(String fileName) {
        HashMap<Integer, Event> eventMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                int id = Integer.parseInt(fields[0].trim());
                String type = fields[1].trim();
                String name = fields[2].trim();
                String date = fields[3].trim();
                String time = fields[4].trim();
                double vipPrice = Double.parseDouble(fields[5].trim());
                double goldPrice = Double.parseDouble(fields[6].trim());
                double silverPrice = Double.parseDouble(fields[7].trim());
                double bronzePrice = Double.parseDouble(fields[8].trim());
                double generalAdmissionPrice = Double.parseDouble(fields[9].trim());
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
     * Also tracks the highest venue ID seen to support unique ID generation.
     *
     * @param fileName path to the CSV file containing venue records
     * @return a {@link HashMap} mapping venue ID to the corresponding {@link Venue} object
     */
    public HashMap<Integer, Venue> loadVenues(String fileName) {
        HashMap<Integer, Venue> venueMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                int id = Integer.parseInt(fields[0].trim());
                String name = fields[1].trim();
                String type = fields[2].trim();
                int capacity = Integer.parseInt(fields[3].trim());
                int concertCapacity = Integer.parseInt(fields[4].trim());
                double cost = Double.parseDouble(fields[5].trim());
                int vipPercent = Integer.parseInt(fields[6].trim());
                int goldPercent = Integer.parseInt(fields[7].trim());
                int silverPercent = Integer.parseInt(fields[8].trim());
                int bronzePercent = Integer.parseInt(fields[9].trim());
                int generalAdmissionPercent = Integer.parseInt(fields[10].trim());
                int reservedPercent = Integer.parseInt(fields[11].trim());
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
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return venueMap;
    }

    /**
     * Writes all events in the provided map to a new CSV file.
     * Does not overwrite the original input file.
     *
     * @param fileName path to the output CSV file
     * @param eventMap the map of event ID to {@link Event} to write
     */
    public void writeEvent(String fileName, HashMap<Integer, Event> eventMap) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("ID,Type,Name,Date,Time,VIP Price,Gold Price,Silver Price,Bronze Price,General Admission Price");
            for (Event event : eventMap.values()) {
                writer.printf("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                    event.getEventID(), event.getType(), event.getEventName(),
                    event.getDate(), event.getTime(),
                    event.getVipPrice(), event.getGoldPrice(), event.getSilverPrice(),
                    event.getBronzePrice(), event.getGeneralAdmissionPrice());
            }
        } catch (IOException e) {
            System.out.println("Error writing events file: " + e.getMessage());
        }
    }

    /**
     * Writes all users in the provided map to a new CSV file.
     *
     * @param fileName path to the output CSV file
     * @param userMap  the map of username to {@link User} to write
     */
    public void writeUsers(String fileName, HashMap<String, User> userMap) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("ID,First Name,Last Name,Username,Password,User Type,Money Available,TicketMiner Membership,Concerts Purchased");
            for (User user : userMap.values()) {
                StringBuilder line = new StringBuilder();
                line.append(user.getUserID()).append(",");
                line.append(user.getFirstName()).append(",");
                line.append(user.getLastName()).append(",");
                line.append(user.getUsername()).append(",");
                line.append(user.getPassword()).append(",");
                line.append(user.getUserType()).append(",");

                if (user instanceof Customer) {
                    Customer c = (Customer) user;
                    line.append(c.getMoneyAvailable()).append(",");
                    line.append(c.hasMembership()).append(",");
                    line.append(c.getConcertsPurchased());
                } else {
                    line.append("0.00,false,0");
                }
                writer.println(line.toString());
            }
        } catch (IOException e) {
            System.out.println("Error writing users file: " + e.getMessage());
        }
    }

    /**
     * Writes all venues in the provided map to a new CSV file.
     *
     * @param fileName path to the output CSV file
     * @param venueMap the map of venue ID to {@link Venue} to write
     */
    public void writeVenues(String fileName, HashMap<Integer, Venue> venueMap) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Venue venue : venueMap.values()) {
                writer.printf("%d,%s,%s,%d,%d,%.2f,%d,%d,%d,%d,%d,%d%n",
                    venue.getVenueID(), venue.getName(), venue.getType(), venue.getCapacity(),
                    venue.getConcertCapacity(), venue.getCost(), venue.getVipPercent(),
                    venue.getGoldPercent(), venue.getSilverPercent(), venue.getBronzePercent(),
                    venue.getGeneralAdmissionPercent(), venue.getReservedPercent());
            }
        } catch (IOException e) {
            System.out.println("Error writing venues file: " + e.getMessage());
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

}
