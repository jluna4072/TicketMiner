package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import model.users.Admin;
import model.users.Customer;
import model.users.Organizer;
import model.users.User;

public class DataManager {
    public HashMap<String, User> loadUsers(String filePath) {
        HashMap<String, User> userMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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

                userMap.put(username, user);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return userMap;
    }

    public void loadEvents(String fileName) {
        
    }

    public void writeEvent(String fileName) {
    }

    public void writeUsers(String fileName) {
    }

}
