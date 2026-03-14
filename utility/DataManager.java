package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

public class DataManager {

    private int lastUserIDSeen = 0;
    private int lastVenueIDSeen = 0;
    private int lastEventIDSeen = 0;

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

    public void writeVenues(String fileName, HashMap<Integer, Venue> venueMap){
          try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
        for (Venue venue : venueMap.values()) {
            writer.printf("%d,%s,%s,%d,%d,%.2f,%d,%d,%d,%d,%d,%d%n",
                venue.getVenueID(), venue.getName(), venue.getType(),venue.getCapacity(),
                venue.getConcertCapacity(), venue.getCost(), venue.getVipPercent(),
                venue.getGoldPercent(), venue.getSilverPercent(), venue.getBronzePercent(),
                venue.getGeneralAdmissionPercent(), venue.getReservedPercent());
        }
    } catch (IOException e) {
        System.out.println("Error writing venues file: " + e.getMessage());
    }
}

    public int generateUniqueUserId() {
        lastUserIDSeen++;
        return lastUserIDSeen;
    }

    public int generateUniqueVenueId() {
        lastVenueIDSeen++;
        return lastVenueIDSeen;
    }

    public int generateUniqueEventId() {
        lastEventIDSeen++;
        return lastEventIDSeen;
    }

}
