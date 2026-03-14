import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.InputMismatchException;
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
import utility.DataManager;
import utility.Logger;

public class RunTicketMiner {

    private final DataManager dataManager = new DataManager();
    private final HashMap<String, User> userMap = dataManager.loadUsers("data/Customer_List_PA1.csv");
    private final HashMap<Integer, Venue> venueMap = dataManager.loadVenues("data/Venue_List_PA1.csv");
    private final HashMap<Integer, Event> eventMap = dataManager.loadEvents("data/Event_List_PA1.csv");
    private final Scanner in = new Scanner(System.in);
    private User loggedInUser;

    public static void main(String[] args) {
        RunTicketMiner app = new RunTicketMiner();
        System.out.println("System started.");
        app.displayMainMenu();
        app.saveUpdatedData();
    }

    public void displayMainMenu() {
        boolean exit = true;
        while (exit) {
            System.out.println("=== Welcome to TicketMiner ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Please select an option (1-3): ");
            try {
                int choice = in.nextInt();
                in.nextLine();
                switch (choice) {
                    case 1:
                        String userType;
                        while (true) {
                            System.out.print("Would you like to register as a Customer or Organizer? (C/O): ");
                            userType = in.nextLine().trim().toUpperCase();
                            if (userType.equals("C") || userType.equals("O")) {
                                break;
                            } else {
                                System.out.println("Invalid input. Please enter 'C' for Customer or 'O' for Organizer.");
                            }
                        }
                        if (userType.equals("C")) {
                            registerCustomer();
                        } else {
                            registerOrganizer();
                        }
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        System.out.println("Thank you for using TicketMiner. Goodbye!");
                        System.out.println("Exiting...");
                        exit = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                in.nextLine();
            }
        }
    }

    public void registerCustomer() {
        String firstName = readNonBlank("Enter first name: ");
        String lastName = readNonBlank("Enter last name: ");
        String username = "";
        String password = "";
        while (true) {
            System.out.print("Enter desired username: ");
            username = in.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("This field cannot be blank. Please try again.");
                continue;
            }
            if (isUsernameUnique(username)) {
                password = readNonBlank("Enter password: ");
                break;
            } else {
                System.out.println("Username already exists, please input different username.");
            }
        }
        boolean becomeMember = false;
        System.out.print("Register as a member? (Y/N): ");
        while (true) {
            String memberChoice = in.nextLine().trim().toUpperCase();
            if (memberChoice.equals("Y") || memberChoice.equals("N")) {
                if (memberChoice.equals("Y")) {
                    becomeMember = true;
                }
                break;
            } else {
                System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
            }
        }
        double initialAmount = 0;
        while (true) {
            try {
                System.out.print("What is the initial amount of money available for the user? ");
                initialAmount = in.nextDouble();
                in.nextLine();
                if (initialAmount >= 0) break;
                System.out.println("Amount cannot be negative. Please try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                in.nextLine();
            }
        }
        int userId = dataManager.generateUniqueUserId();
        Customer newCustomer = new Customer(userId, firstName, lastName, username, password, "Customer", initialAmount, becomeMember, 0);
        userMap.put(newCustomer.getUsername(), newCustomer);
        //Logging action to file
        String actionDetail = "User " + userId + " has been registered as a Customer.";
        Logger.logAction(actionDetail);
        System.out.println("Customer registered successfully!");
    }

    public void registerOrganizer() {
        String firstName = readNonBlank("Enter first name: ");
        String lastName = readNonBlank("Enter last name: ");
        String username = "";
        String password = "";
        while (true) {
            System.out.print("Enter desired username: ");
            username = in.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("This field cannot be blank. Please try again.");
                continue;
            }
            if (isUsernameUnique(username)) {
                password = readNonBlank("Enter password: ");
                break;
            } else {
                System.out.println("Username already exists, please input different username.");
            }
        }
        int userId = dataManager.generateUniqueUserId();
        User newOrganizer = new Organizer(userId, firstName, lastName, username, password, "Organizer");
        userMap.put(newOrganizer.getUsername(), newOrganizer);
        //Logging action to file
        String actionDetail = "User " + userId + " has registered as a Organizer to the system."; 
        Logger.logAction(actionDetail);

        System.out.println("Organizer registered successfully!");

    }

    public void registerAdmin() {
        String firstName = readNonBlank("Enter first name: ");
        String lastName = readNonBlank("Enter last name: ");
        String username = "";
        String password = "";
        while (true) {
            System.out.print("Enter desired username: ");
            username = in.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("This field cannot be blank. Please try again.");
                continue;
            }
            if (isUsernameUnique(username)) {
                password = readNonBlank("Enter password: ");
                break;
            } else {
                System.out.println("Username already exists, please input a different username.");
            }
        }
        int userId = dataManager.generateUniqueUserId();
        Admin newAdmin = new Admin(userId, firstName, lastName, username, password, "Admin");
        userMap.put(newAdmin.getUsername(), newAdmin);
        
        String actionDetail = "User " + userId + " has been registered as a Admin to the system.";
        Logger.logAction(actionDetail);

        System.out.println("Admin registered successfully!");
        
    }

    public void login() {
        while (true) {
            System.out.println("\n--- Login ---");
            System.out.println("Please enter your username (or type 'back' to return):");
            String username = in.nextLine().trim();

            if (username.equalsIgnoreCase("back")) {
                System.out.println("Returning to main menu...");
                return;
            }

            System.out.println("Please enter your password:");
            String password = in.nextLine().trim();

            if (userMap.containsKey(username)) {
                User user = userMap.get(username);

                if (user.getPassword().equals(password)) {
                    loggedInUser = user;
                    System.out.println("Login successful! Welcome, " + loggedInUser.getFirstName() + "!");
                    
                    String actionDetail = "User " + loggedInUser.getUserID() + " has logged into the system.";
                    Logger.logAction(actionDetail);
                    
                    if (user instanceof Customer) {
                        customerMenu();
                    } else if (user instanceof Organizer) {
                        organizerMenu();
                    } else if (user instanceof Admin) {
                        adminMenu();
                    }
                    break;
                } else {
                    System.out.println("Incorrect password. Please try again.");
                }
            } else {
                System.out.println("Username not found. Please try again.");
            }
        }
    }
    public void customerMenu() {
        System.out.println("\nCustomer menu is under construction. Returning to main menu...");
    }
    public void organizerMenu() {
        System.out.println("\nOrganizer menu is under construction. Returning to main menu...");
    }

    public void adminMenu() {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Manage Users");
            System.out.println("2. Manage Venues");
            System.out.println("3. Manage Events");
            System.out.println("4. Logout");
            System.out.print("Please select an option (1-4): ");
            try {
                int choice = in.nextInt();
                in.nextLine();
                switch (choice) {
                    case 1:
                        manageUsers();
                        break;
                    case 2:
                        manageVenues();
                        break;
                    case 3:
                        manageEvents();
                        break;
                    case 4:
                        System.out.println("Logging out...");
                        loggedInUser = null;
                        logout = true;
                        String actionDefined = "User " + loggedInUser.getUserID() + " had logged out.";
                    Logger.logAction(actionDefined);
                    break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                in.nextLine();
            }
        }
    }

    public void manageUsers() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== Manage Users ===");
            System.out.println("1. Add");
            System.out.println("2. View");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Back");
            System.out.print("Please select an option (1-5): ");
            try {
                int choice = in.nextInt();
                in.nextLine();
                switch (choice) {
                    case 1:
                        addUser();
                        break;
                    case 2:
                        viewUser();
                        break;
                    case 3:
                        updateUser();
                        break;
                    case 4:
                        deleteUser();
                        break;
                    case 5:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                in.nextLine();
            }
        }
        
    }

    public void addUser() {
        String userType;
        while (true) {
            System.out.print("Add as Customer, Organizer, or Admin? (C/O/A): ");
            userType = in.nextLine().trim().toUpperCase();
            if (userType.equals("C") || userType.equals("O") || userType.equals("A")) {
                break;
            }
            System.out.println("Invalid input. Please enter C, O, or A.");
        }
        if (userType.equals("C")) {
            registerCustomer();
        } else if (userType.equals("O")) {
            registerOrganizer();
        } else {
            registerAdmin();
        }

    }

    public void viewUser() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== View Members ===");
            System.out.println("1. Display all members");
            System.out.println("2. Search for a member");
            System.out.println("3. Back");
            System.out.print("Please select an option (1-3): ");
            try {
                int choice = in.nextInt();
                in.nextLine();
                switch (choice) {
                    case 1:
                        for (User u : userMap.values()) {
                            System.out.println(u);
                        }
                        String actionDetail = "User "+ loggedInUser.getUserID() + " has viewed all users to the console";
                        Logger.logAction(actionDetail);
                        break;
                    case 2:
                        System.out.print("Enter ID, username, or full name: ");
                        String input = in.nextLine().trim();
                        User found = resolveUser(input);
                        if (found != null) {
                            System.out.println(found);
                            actionDetail = "User " + loggedInUser.getUserID() +" has viewed user " + found.getUserID() + "and was printed to the console";
                            Logger.logAction(actionDetail);
                        } else {
                            System.out.println("Member not found.");
                        }                        
                        break;
                    case 3:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                in.nextLine();
            }
        }
    }

    public void updateUser() {
        String actionDetail;
        System.out.print("Enter ID, username, or full name of member to update: ");
        String input = in.nextLine().trim();
        User user = resolveUser(input);
        if (user == null) {
            System.out.println("Member not found.");
            return;
        }
        System.out.println("\nFound: " + user.getFirstName() + " " + user.getLastName());
        boolean back = false;
        while (!back) {
            System.out.println("\n=== Update Member ===");
            System.out.println("1. Change Name");
            System.out.println("2. Change Username");
            System.out.println("3. Change Password");
            System.out.println("4. Back");
            System.out.print("Please select an option (1-4): ");
            try {
                int choice = in.nextInt();
                in.nextLine();
                switch (choice) {
                    case 1:
                        System.out.print("Enter new first name: ");
                        String newFirst = in.nextLine().trim();
                        System.out.print("Enter new last name: ");
                        String newLast = in.nextLine().trim();
                        user.setFirstName(newFirst);
                        user.setLastName(newLast);
                        System.out.println("Name updated successfully.");
                        actionDetail = "User " + loggedInUser.getUserID() + " has updated user " + user.getUserID() + " full name";
                        Logger.logAction(actionDetail);
                        break;
                    case 2:
                        String newUsername;
                        while (true) {
                            System.out.print("Enter new username: ");
                            newUsername = in.nextLine().trim();
                            if (isUsernameUnique(newUsername)) {
                                actionDetail = "User " + loggedInUser.getUserID() + "has updated user " + user.getUserID() + " username";
                                Logger.logAction(actionDetail);
                                break;
                            }
                            System.out.println("Username already exists. Please enter a different username.");
                        }
                        userMap.remove(user.getUsername());
                        user.setUsername(newUsername);
                        userMap.put(newUsername, user);
                        System.out.println("Username updated successfully.");
                        actionDetail = "User " + loggedInUser.getUserID()+" has updated user " + user.getUserID() + " username.";
                        Logger.logAction(actionDetail);
                        break;
                    case 3:
                        System.out.print("Enter new password: ");
                        String newPassword = in.nextLine().trim();
                        user.setPassword(newPassword);
                        System.out.println("Password updated successfully.");
                        actionDetail = "User " + loggedInUser.getUserID() +" has updated user " + user.getUserID() + "password.";
                        Logger.logAction(actionDetail);
                        break;
                    case 4:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                in.nextLine();
            }
        }
    }

    public void deleteUser() {
        System.out.print("Enter ID, username, or full name of member to delete: ");
        String input = in.nextLine().trim();
        User user = resolveUser(input);
        if (user == null) {
            System.out.println("Member not found.");
            return;
        }
        System.out.println("\nFound member:");
        System.out.println(user);
        System.out.print("Are you sure you want to delete this member? (Y/N): ");
        String confirm = in.nextLine().trim();
        if (confirm.equalsIgnoreCase("y")) {
            String actionDetail = "User"+ loggedInUser.getUserID() +" has deleted user " + user.getUserID() + ".";
            Logger.logAction(actionDetail);
            userMap.remove(user.getUsername());
            System.out.println("Member deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private boolean isUsernameUnique(String username) {
        return !userMap.containsKey(username);
    }

    public void manageVenues() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== Manage Venues ===");
            System.out.println("1. Add");
            System.out.println("2. View");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Back");
            System.out.print("Please select an option (1-5): ");
            try {
                int choice = in.nextInt();
                in.nextLine();
                switch (choice) {
                    case 1:
                        addVenue();
                        break;
                    case 2:
                        viewVenue();
                        break;
                    case 3:
                        updateVenue();
                        break;
                    case 4:
                        deleteVenue();
                        break;
                    case 5:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid option.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                in.nextLine();
            }
        }
    }

    public void addVenue() {
        System.out.println("\n--- Add New Venue ---");
        String type;
        while (true) {
            System.out.print("Enter Venue Type (Arena, Stadium, Open Air, Auditorium): ");
            type = in.nextLine().trim();
            if (type.equalsIgnoreCase("Arena") || type.equalsIgnoreCase("Stadium")
                    || type.equalsIgnoreCase("Open Air") || type.equalsIgnoreCase("Auditorium")) {
                break;
            }
            System.out.println("Invalid type. Please enter Arena, Stadium, Open Air, or Auditorium.");
        }
        System.out.print("Enter Venue Name: ");
        String name = in.nextLine();
        int capacity = readPositiveInt("Enter Capacity: ");
        int concertCapacity = readPositiveInt("Enter Concert Capacity: ");
        double cost = readPositiveDouble("Enter Cost to Rent: $");
        int vip = readPositiveInt("Enter VIP %: ");
        int gold = readPositiveInt("Enter Gold %: ");
        int silver = readPositiveInt("Enter Silver %: ");
        int bronze = readPositiveInt("Enter Bronze %: ");
        int ga = readPositiveInt("Enter General Admission %: ");
        int reserved = readPositiveInt("Enter Reserved %: ");

        int id = dataManager.generateUniqueVenueId();
        Venue newVenue;
        switch (type.toLowerCase()) {
            case "arena":
                newVenue = new Arena(id, name, type, capacity, concertCapacity, cost, vip, gold, silver, bronze, ga, reserved);
                break;
            case "stadium":
                newVenue = new Stadium(id, name, type, capacity, concertCapacity, cost, vip, gold, silver, bronze, ga, reserved);
                break;
            case "open air":
                newVenue = new OpenAir(id, name, type, capacity, concertCapacity, cost, vip, gold, silver, bronze, ga, reserved);
                break;
            default:
                newVenue = new Auditorium(id, name, type, capacity, concertCapacity, cost, vip, gold, silver, bronze, ga, reserved);
                break;
        }
        
        venueMap.put(id, newVenue);
        System.out.println("Venue added successfully with ID: " + id);

        String actionDetail = "User " + loggedInUser.getUserID() + " has added the venue " + id; 
        Logger.logAction(actionDetail);
    }

    public void viewVenue() {
        while (true) {
            String actionDetail;
            System.out.println("\n--- View Venues ---");
            System.out.println("1. Display all venues");
            System.out.println("2. Search for a venue");
            System.out.print("Choice: ");
            String choice = in.nextLine().trim();

            if (choice.equals("1")) {
                for (Venue v : venueMap.values()) {
                    System.out.println(v);
                }
                actionDetail = "User " + loggedInUser.getUserID() + " has printed all venues to the console";
                Logger.logAction(actionDetail);
                break;
        } else if (choice.equals("2")) {
                System.out.print("Enter Venue ID, Name, or Type: ");
                String query = in.nextLine();
                Venue found = resolveVenue(query);
                if (found != null) {
                    System.out.println(found);
                } else {
                    System.out.println("Venue not found.");
                }
                break;
            } else {
                System.out.println("Invalid option. Please enter 1 or 2.");
            }
        }
    }

    public void updateVenue() {
        System.out.print("\nEnter Venue ID, Name, or Type to update: ");
        String query = in.nextLine();
        Venue found = resolveVenue(query);

        if (found != null) {
            System.out.println("\nEditing: " + found.getName());
            System.out.println("1. Name  2. Cost  3. Capacity  4. Cancel");
            System.out.print("Select field to update (1-4): ");

            try {
                int choice = in.nextInt();
                in.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("New Name: ");
                        found.setName(in.nextLine());
                        break;
                    case 2:
                        found.setCost(readPositiveDouble("New Cost: $"));
                        break;
                    case 3:
                        found.setCapacity(readPositiveInt("New Capacity: "));
                        break;
                    case 4:
                        System.out.println("Update cancelled.");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                        return;
                }

                String actionDetail = "User " + loggedInUser.getUserID() + " has updated venue " + found.getVenueID();
                Logger.logAction(actionDetail);

                System.out.println("Venue updated successfully!");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Update failed.");
                in.nextLine();
            }
        } else {
            System.out.println("Venue not found.");
        }
    }

    public void deleteVenue() {
        System.out.print("\nEnter Venue ID, Name, or Type to delete: ");
        String query = in.nextLine();
        Venue found = resolveVenue(query);

        if (found != null) {
            System.out.println("Found: " + found.getName());
            System.out.print("Are you sure you want to delete this venue? (y/n): ");
            String confirm = in.nextLine();
            if (confirm.equalsIgnoreCase("y")) {

                String actionDetails = "User " + " has deleted venue ID:"  + found.getVenueID();
                Logger.logAction(actionDetails);
                venueMap.remove(found.getVenueID());
                System.out.println("Venue deleted successfully.");
            } else {
                System.out.println("Delete cancelled.");
            }
        } else {
            System.out.println("Venue not found.");
        }
 
    }

    public void manageEvents() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== Manage Events ===");
            System.out.println("1. Add");
            System.out.println("2. View");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Back");
            System.out.print("Please select an option (1-5): ");
            try {
                int choice = in.nextInt();
                in.nextLine();
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
                        System.out.println("Invalid option.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                in.nextLine();
            }
        }
    }

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
        System.out.print("Enter Event Name: ");
        String name = in.nextLine();
        String date = readDate("Enter Date (MM/DD/YYYY): ");
        String time = readTime("Enter Time (hh:mm AM/PM): ");
        double vip    = readPositiveDouble("Enter VIP Price: $");
        double gold   = readPositiveDouble("Enter Gold Price: $");
        double silver = readPositiveDouble("Enter Silver Price: $");
        double bronze = readPositiveDouble("Enter Bronze Price: $");
        double ga     = readPositiveDouble("Enter General Admission Price: $");

        int id = dataManager.generateUniqueEventId();
        Event newEvent;
        switch (type.toLowerCase()) {
            case "sport":
                newEvent = new Sport(id, type, name, date, time, vip, gold, silver, bronze, ga);
                break;
            case "concert":
                newEvent = new Concert(id, type, name, date, time, vip, gold, silver, bronze, ga);
                break;
            default:
                newEvent = new Special(id, type, name, date, time, vip, gold, silver, bronze, ga);
                break;
        }

        eventMap.put(id, newEvent);
        System.out.println("Event added successfully with ID: " + id);
        String actionDetail = "User " + loggedInUser.getUserID() + " has added a new event " + id;
        Logger.logAction(actionDetail);
    }

    public void viewEvent() {
        String actionDetail;
        while (true) {
            System.out.println("\n--- View Events ---");
            System.out.println("1. Display all events");
            System.out.println("2. Search for an event");
            System.out.print("Choice: ");
            String choice = in.nextLine().trim();

            if (choice.equals("1")) {
                for (Event e : eventMap.values()) {
                    System.out.println(e);
                }
                actionDetail = "User " + loggedInUser.getUserID() + " has printed all events to the console";
                Logger.logAction(actionDetail);
                break;
            } else if (choice.equals("2")) {
                System.out.print("Enter Event ID, Name, or Date: ");
                String query = in.nextLine();
                Event found = resolveEvent(query);
                if (found != null) {
                    System.out.println(found);
                    actionDetail = "User " + loggedInUser.getUserID() + " searched for " + found.getEventID();
                    Logger.logAction(actionDetail);
                } else {
                    System.out.println("Event not found.");
                }
                break;
            } else {
                System.out.println("Invalid option. Please enter 1 or 2.");
            }
        }
    }

    public void updateEvent() {
        String actionDetail;
        System.out.print("\nEnter Event ID, Name, or Date to update: ");
        String query = in.nextLine();
        Event found = resolveEvent(query);

        if (found != null) {
            System.out.println("Updating: " + found.getEventName());
            System.out.println("1. Change Name\n2. Change Date\n3. Change Time\n4. Cancel");
            try {
                int choice = in.nextInt();
                in.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("New Name: ");
                        found.setEventName(in.nextLine());
                        actionDetail = "User " + loggedInUser.getUserID() + " has updated event " + found.getEventID() + "'s' name";
                        Logger.logAction(actionDetail);
                        break;
                    case 2:
                        found.setDate(readDate("New Date (MM/DD/YYYY): "));
                        System.out.print("New Date: ");
                        found.setDate(in.nextLine());
                        actionDetail = "User " + loggedInUser.getUserID() + " has updated event " + found.getEventID() + "'s date";
                        Logger.logAction(actionDetail);
                        break;
                    case 3:
                        found.setTime(readTime("New Time (hh:mm AM/PM): "));
                        System.out.print("New Time: ");
                        found.setTime(in.nextLine());
                        actionDetail = "User " + loggedInUser.getUserID() + " has updated event " + found.getEventID() + "'s time";
                        Logger.logAction(actionDetail);
                        break;
                    case 4:
                        System.out.println("Update cancelled.");
                        return;
                    default:
                        System.out.println("Invalid option.");
                        return;
                }
                System.out.println("Event updated.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                in.nextLine();
            }
        } else {
            System.out.println("Event not found.");
        }
    }

    public void deleteEvent() {
        System.out.print("\nEnter Event ID, Name, or Date to delete: ");
        String query = in.nextLine();
        Event found = resolveEvent(query);

        if (found != null) {
            System.out.println("Found: " + found.getEventName());
            System.out.print("Are you sure you want to delete this event? (y/n): ");
            if (in.nextLine().equalsIgnoreCase("y")) {
                String actionDetail = "User " + loggedInUser.getUserID() + " has deleted the event " + found.getEventID();
                Logger.logAction(actionDetail);
                eventMap.remove(found.getEventID());
                System.out.println("Event deleted.");
            } else {
                System.out.println("Delete cancelled.");
            }
        } else {
            System.out.println("Event not found.");
        }
    }

    private String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = in.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("This field cannot be blank. Please try again.");
        }
    }

    private int readPositiveInt(String input) {
        while (true) {
            System.out.print(input);
            try {
                int value = in.nextInt();
                in.nextLine();
                if (value >= 0) {
                    return value;
                }
                System.out.println("Value cannot be negative. Please try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a whole number.");
                in.nextLine();
            }
        }
    }

    private double readPositiveDouble(String input) {
        while (true) {
            System.out.print(input);
            try {
                double value = in.nextDouble();
                in.nextLine();
                if (value >= 0) {
                    return value;
                }
                System.out.println("Price cannot be negative. Please try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                in.nextLine();
            }
        }
    }

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

    private User resolveUser(String input) {
        List<User> matches = dataManager.findUsers(userMap, input);

        if (matches.isEmpty()) {
            return null;
        }
        if (matches.size() == 1) {
            return matches.get(0);
        }

        System.out.println("Multiple members found with that name:");
        for (User u : matches) {
            System.out.println("  ID: " + u.getUserID() + " | Username: " + u.getUsername()
                    + " | " + u.getFirstName() + " " + u.getLastName());
        }
        while (true) {
            System.out.print("Please enter the ID or username to select a specific member: ");
            String refined = in.nextLine().trim();
            for (User u : matches) {
                if (u.getUsername().equals(refined)) {
                    return u;
                }
                try {
                    if (u.getUserID() == Integer.parseInt(refined)) {
                        return u;
                    }
                } catch (NumberFormatException ignored) {}
            }
            System.out.println("Selection does not match any of the found members. Please try again.");
        }
    }

    private Venue resolveVenue(String input) {
        List<Venue> matches = dataManager.findVenues(venueMap, input);

        if (matches.isEmpty()) {
            return null;
        }
        if (matches.size() == 1) {
            return matches.get(0);
        }

        System.out.println("Multiple venues found:");
        for (Venue v : matches) {
            System.out.println("  ID: " + v.getVenueID() + " | Name: " + v.getName()
                    + " | Type: " + v.getType());
        }
        while (true) {
            System.out.print("Please enter the ID or name to select a specific venue: ");
            String refined = in.nextLine().trim();
            for (Venue v : matches) {
                if (v.getName().equalsIgnoreCase(refined)) {
                    return v;
                }
                try {
                    if (v.getVenueID() == Integer.parseInt(refined)) {
                        return v;
                    }
                } catch (NumberFormatException ignored) {}
            }
            System.out.println("Selection does not match any of the found venues. Please try again.");
        }
    }

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

    public void saveUpdatedData(){
        dataManager.writeEvent("data/Updated_Event_List.csv", eventMap);
        dataManager.writeUsers("data/Updated_Customer_List.csv", userMap);
        dataManager.writeVenues("data/Updated_Venue_List.csv", venueMap);
    }
}
