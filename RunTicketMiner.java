import java.util.ArrayList;
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
        System.out.print("Enter first name: ");
        String firstName = in.nextLine().trim();
        System.out.print("Enter last name: ");
        String lastName = in.nextLine().trim();
        System.out.print("Enter desired username: ");
        String username = "";
        String password = "";
        while (true) {
            username = in.nextLine().trim();
            if (isUsernameUnique(username)) {
                System.out.print("Enter password: ");
                password = in.nextLine().trim();
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
        double initialAmount;
        while (true) {
            try {
                System.out.print("What is the initial amount of money available for the user? ");
                initialAmount = in.nextDouble();
                in.nextLine();
                break;
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
        System.out.print("Enter first name: ");
        String firstName = in.nextLine().trim();
        System.out.print("Enter last name: ");
        String lastName = in.nextLine().trim();
        System.out.print("Enter desired username: ");
        String username = "";
        String password = "";
        while (true) {
            username = in.nextLine().trim();
            if (isUsernameUnique(username)) {
                System.out.print("Enter password: ");
                password = in.nextLine().trim();
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
        System.out.print("Enter first name: ");
        String firstName = in.nextLine().trim();
        System.out.print("Enter last name: ");
        String lastName = in.nextLine().trim();
        System.out.print("Enter desired username: ");
        String username = "";
        String password = "";
        while (true) {
            username = in.nextLine().trim();
            if (isUsernameUnique(username)) {
                System.out.print("Enter password: ");
                password = in.nextLine().trim();
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
        boolean loggedOut = false;
        while (!loggedOut) {
            Customer c = (Customer) loggedInUser;
            
            System.out.println("\n=== CUSTOMER MENU ===");
            System.out.println("Welcome, " + c.getFirstName() + " " + c.getLastName());
            System.out.printf("Current Balance: $%.2f\n", c.getMoneyAvailable());
            System.out.println("---------------------");
            System.out.println("1. View/Search Events");
            System.out.println("2. Buy Tickets");
            System.out.println("3. View My Stats");
            System.out.println("4. Add Funds");
            System.out.println("5. Logout");
            System.out.print("Choice: ");

            try {
                int choice = in.nextInt();
                in.nextLine(); 

                switch (choice) {
                    case 1: viewEvent(); 
                        break;

                    case 2: buyTickets(); 
                        break;

                    case 3: viewMyTickets(); 
                        break;

                    case 4: addFunds(); 
                        break;

                    case 5:
                        System.out.println("Logging out...");
                        String actionDefined = "User " + loggedInUser.getUserID() + "has logged out.";
                        Logger.logAction(actionDefined);
                        loggedInUser = null;
                        loggedOut = true;

                        
                        break;
                    default:
                        System.out.println("Invalid option.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                in.nextLine();
            }
        }
    }

    public void addFunds() {
        Customer c = (Customer) loggedInUser;
        System.out.print("\nEnter amount to add to your balance: $");
        try {
            double amount = in.nextDouble();
            in.nextLine(); 
            
            if (amount > 0) {
                double newBalance = c.getMoneyAvailable() + amount;
                c.setMoneyAvailable(newBalance);
                System.out.printf("Successfully added $%.2f. New balance: $%.2f\n", amount, newBalance);
                
                String actionDetail = "User " + loggedInUser.getUsername() + "has added " + amount + "to their funds";
                Logger.logAction(actionDetail);
            } else {
                System.out.println("Error: Amount must be greater than 0.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid decimal format.");
            in.nextLine();
        }
    }

    public void viewMyTickets() {
        Customer c = (Customer) loggedInUser;
        System.out.println("\n--- Your Account Status ---");
        System.out.println("User ID: " + c.getUserID());
        System.out.println("Membership: " + (c.hasMembership() ? "Premium Member" : "Standard"));
        System.out.println("Total Events Attended: " + c.getConcertsPurchased());
        System.out.printf("Available Funds: $%.2f\n", c.getMoneyAvailable());

        String actionDetail = "User " + c.getUsername() + " has viewed their tickets.";
        Logger.logAction(actionDetail); 
    }

    public void buyTickets() {
        System.out.print("\nEnter Event ID or Name to purchase: ");
        String query = in.nextLine();
        Event e = dataManager.findEvent(eventMap, query);
        
        if (e != null) {
            Customer c = (Customer) loggedInUser;
            System.out.println("\n--- Ticket Options for " + e.getEventName() + " ---");
            System.out.println("1. VIP:               $" + e.getVipPrice());
            System.out.println("2. Gold:              $" + e.getGoldPrice());
            System.out.println("3. Silver:            $" + e.getSilverPrice());
            System.out.println("4. Bronze:            $" + e.getBronzePrice()); 
            System.out.println("5. General Admission: $" + e.getGeneralAdmissionPrice()); 
            System.out.println("6. Cancel");
            System.out.print("Select an option: ");

            int choice = in.nextInt();
            in.nextLine();
             
            String selectedPriceStr = "0";
            switch (choice) {
                case 1: selectedPriceStr = e.getVipPrice(); 
                    break;
                case 2: selectedPriceStr = e.getGoldPrice(); 
                    break;
                case 3: selectedPriceStr = e.getSilverPrice(); 
                    break;
                case 4: selectedPriceStr = e.getBronzePrice(); 
                    break; 
                case 5: selectedPriceStr = e.getGeneralAdmissionPrice(); 
                    break; 
                default: 
                    System.out.println("Purchase cancelled."); 
                    return;
            }

            try {
                double price = Double.parseDouble(selectedPriceStr.replace("$", "").trim());

                if (c.getMoneyAvailable() >= price) {
                    c.setMoneyAvailable(c.getMoneyAvailable() - price);
                    c.setConcertsPurchased(c.getConcertsPurchased() + 1);
                    
                    if (choice == 1){ 
                        e.incrementVip();
                    }else if (choice == 2){
                        e.incrementGold();
                    }else if (choice == 3){ 
                        e.incrementSilver();
                    }else if (choice == 4){ 
                        e.incrementBronze();
                    }else if (choice == 5){ 
                        e.incrementGa();
                    }
                    System.out.println("Purchase successful! Enjoy the show!");
                    System.out.printf("Remaining Balance: $%.2f\n", c.getMoneyAvailable());
                    //Could do query but lets see what happens
                    String actionDetail = "User " + loggedInUser.getUserID() + " has bought tickets for " + e.getEventName();
                    Logger.logAction(actionDetail);
                } else {
                    System.out.println("Insufficient funds! Please add money to your account.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Error: Ticket price is not valid. Please contact an Admin.");
            }
        } else {
            System.out.println("Event not found.");
        }
    }
    public void organizerMenu() {
        boolean loggedOut = false;
        while (!loggedOut) {
            Organizer o = (Organizer) loggedInUser;
            
            System.out.println("\n=== ORGANIZER MENU ===");
            System.out.println("Welcome, " + o.getFirstName() + " " + o.getLastName());
            System.out.println("---------------------");
            System.out.println("1. Create New Event");
            System.out.println("2. View/Search Events");
            System.out.println("3. Update Event Details");
            System.out.println("4. View Sales/Attendance Report");
            System.out.println("5. Logout");
            System.out.print("Choice: ");

            try {
                int choice = in.nextInt();
                in.nextLine(); 
                switch (choice) {
                    case 1: addEvent(); 
                        break;
                    case 2: viewEventsWithPrices(); 
                        break;
                    case 3: updateEvent(); 
                        break;
                    case 4: viewSales(); 
                        break;
                    case 5:
                        System.out.println("Logging out...");
                        String actionDefined = "User " + loggedInUser.getUserID() + " has logged out.";
                        Logger.logAction(actionDefined);
                        loggedInUser = null;
                        loggedOut = true;
                        
                        break;
                    default:
                        System.out.println("Invalid option.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                in.nextLine();
            }
        }
    }

    public void viewSales() {
        //Call to Organizer??
        System.out.println("\n--- Event Sales Report ---");
        System.out.print("Enter Event ID or Name: ");
        String query = in.nextLine();
        Event e = dataManager.findEvent(eventMap, query);

        if (e != null) {
            System.out.println("--------------------------------------------------");
            System.out.println("Financial Summary for: " + e.getEventName());
            System.out.println("Date: " + e.getDate());
            System.out.println("--------------------------------------------------");
            System.out.printf("%-15s | %-10s | %-5s\n", "Category", "Price", "Sold");
            System.out.println("--------------------------------------------------");
            System.out.printf("%-15s | %-10s | %-5d\n", "VIP", e.getVipPrice(), e.getVipSold());
            System.out.printf("%-15s | %-10s | %-5d\n", "Gold", e.getGoldPrice(), e.getGoldSold());
            System.out.printf("%-15s | %-10s | %-5d\n", "Silver", e.getSilverPrice(), e.getSilverSold());
            System.out.printf("%-15s | %-10s | %-5d\n", "Bronze", e.getBronzePrice(), e.getBronzeSold());
            System.out.printf("%-15s | %-10s | %-5d\n", "GA", e.getGeneralAdmissionPrice(), e.getGaSold());
            System.out.println("--------------------------------------------------");
            
            double total = (e.getVipSold() * Double.parseDouble(e.getVipPrice().replace("$",""))) +(e.getGoldSold() * Double.parseDouble(e.getGoldPrice().replace("$",""))); 
            System.out.printf("Estimated Total Revenue: $%.2f\n", total);
            String actionDetail = "User " + loggedInUser.getUserID() + "has viewed sales for " + e.getEventName() + ", " + e.getEventID();
            Logger.logAction(actionDetail);
        } else {
            System.out.println("Event not found.");
        }
    }
    public void viewEventsWithPrices() {
        System.out.println("\n--- View/Search Events ---");
        System.out.println("a. Display All Events");
        System.out.println("b. Search for Specific Event (ID, Name, or Date)");
        System.out.print("Selection: ");

        try {
            String choice = in.nextLine().trim().toLowerCase(); 

            if (choice.equals("a")) {
                displayAllEvents();
            } else if (choice.equals("b")) {
                System.out.print("Enter search term: ");
                String query = in.nextLine(); 
                
                Event found = dataManager.findEvent(eventMap, query);
                
                if (found != null) {
                    printEventTableHeader(); 
                    System.out.println(found.toString());

                    String actionDetail = "User " + loggedInUser.getUserID() + " has found Event " + found.getEventID();
                    Logger.logAction(actionDetail);
                } else {
                    System.out.println("No event matching '" + query + "' was found.");
                }
            } else {
                System.out.println("Invalid choice. Please select a or b.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
    }

    private void displayAllEvents() {
        if (eventMap.isEmpty()) {
            System.out.println("The event list is currently empty.");
            return;
        }
        
        printEventTableHeader();
        for (Event e : eventMap.values()) {
            System.out.println(e.toString());
        }

        String actionDetail = "User " + loggedInUser.getUserID() +" has printed all events to the console";
        Logger.logAction(actionDetail);
    }

    private void printEventTableHeader() {
        System.out.println("\n" + String.format("%-4s | %-18s | %-10s | %-6s | %-6s | %-6s | %-6s | %-6s", "ID", "NAME", "DATE", "VIP", "GOLD", "SILV", "BRON", "GA"));
        System.out.println("---------------------------------------------------------------------------------------");
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
                        System.out.println("\n=== All Members ===");
                        for (User u : userMap.values()) {
                            System.out.println("--------------------");
                            printUserInfo(u);
                        }
                        
                        System.out.println("--------------------");
                        String actionDetail = "User "+ loggedInUser.getUserID() + " has viewed all users to the console";
                        Logger.logAction(actionDetail);
                        break;
                    case 2:
                        System.out.print("Enter ID, username, or full name: ");
                        String input = in.nextLine().trim();
                        User found = findUserByIdentifier(input);
                        if (found != null) {
                            System.out.println("\n=== Member Found ===");
                            printUserInfo(found);
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
        User user = findUserByIdentifier(input);
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
        User user = findUserByIdentifier(input);
        if (user == null) {
            System.out.println("Member not found.");
            return;
        }
        System.out.println("\nFound member:");
        printUserInfo(user);
        System.out.print("Are you sure you want to delete this member? (Y/N): ");
        String confirm = in.nextLine().trim().toUpperCase();
        if (confirm.equals("Y")) {
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
                in.nextLine(); // consume newline
                switch (choice) {
                    case 1: addVenue(); break;
                    case 2: viewVenue(); break;
                    case 3: updateVenue(); break;
                    case 4: deleteVenue(); break;
                    case 5: back = true; break;
                    default: System.out.println("Invalid option."); break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                in.nextLine();
            }
        }
    }

    public void addVenue() {
        System.out.println("\n--- Add New Venue ---");
        System.out.print("Enter Venue Type (Arena, Stadium, Open Air, Auditorium): ");
        String type = in.nextLine();
        System.out.print("Enter Venue Name: ");
        String name = in.nextLine();
        System.out.print("Enter Capacity: ");
        int capacity = in.nextInt();
        System.out.print("Enter Concert Capacity: ");
        int concertCapacity = in.nextInt();
        System.out.print("Enter Cost to Rent: ");
        double cost = in.nextDouble();
        
        System.out.print("Enter VIP %: "); int vip = in.nextInt();
        System.out.print("Enter Gold %: "); int gold = in.nextInt();
        System.out.print("Enter Silver %: "); int silver = in.nextInt();
        System.out.print("Enter Bronze %: "); int bronze = in.nextInt();
        System.out.print("Enter General Admission %: "); int ga = in.nextInt();
        System.out.print("Enter Reserved %: "); int reserved = in.nextInt();
        in.nextLine(); 

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
        String actionDetail;
        System.out.println("\n--- View Venues ---");
        System.out.println("a. Display all venues");
        System.out.println("b. Search for a venue");
        System.out.print("Choice: ");
        String choice = in.nextLine().toLowerCase();

        if (choice.equals("a")) {
            for (Venue v : venueMap.values()) {
                System.out.println(v.toString());
            }

            actionDetail = "User " + loggedInUser.getUserID() + " has printed all venues to the console";
            Logger.logAction(actionDetail);
            
        } else {
            System.out.print("Enter Venue ID, Name, or Type: ");
            String query = in.nextLine();
            Venue found = dataManager.findVenue(venueMap, query);
            if (found != null) {
                System.out.println(found.toString());
            } else {
                System.out.println("Venue not found.");
            }
        }
    }
    public void updateVenue() {
        System.out.print("\nEnter Venue ID, Name, or Type to update: ");
        String query = in.nextLine();
        Venue found = dataManager.findVenue(venueMap, query);

        if (found != null) {
            System.out.println("\nEditing: " + found.getName());
            System.out.println("1. Name              2. Type              3. Capacity");
            System.out.println("4. Concert Capacity  5. Cost              6. VIP %");
            System.out.println("7. Gold %            8. Silver %          9. Bronze %");
            System.out.println("10. GA %             11. Reserved %       12. Cancel");
            System.out.print("Select field to update (1-12): ");

            try {
                int choice = in.nextInt();
                in.nextLine(); 

                switch (choice) {
                    case 1:
                        System.out.print("New Name: ");
                        found.setName(in.nextLine());
                        break;
                    case 2:
                        System.out.print("New Type: ");
                        found.setType(in.nextLine());
                        break;
                    case 3:
                        System.out.print("New Capacity: ");
                        found.setCapacity(in.nextInt());
                        in.nextLine();
                        break;
                    case 4:
                        System.out.print("New Concert Capacity: ");
                        found.setConcertCapacity(in.nextInt());
                        in.nextLine();
                        break;
                    case 5:
                        System.out.print("New Cost: ");
                        found.setCost(in.nextDouble());
                        in.nextLine();
                        break;
                    case 6:
                        System.out.print("New VIP %: ");
                        found.setVipPercent(in.nextInt());
                        in.nextLine();
                        break;
                    case 7:
                        System.out.print("New Gold %: ");
                        found.setGoldPercent(in.nextInt());
                        in.nextLine();
                        break;
                    case 8:
                        System.out.print("New Silver %: ");
                        found.setSilverPercent(in.nextInt());
                        in.nextLine();
                        break;
                    case 9:
                        System.out.print("New Bronze %: ");
                        found.setBronzePercent(in.nextInt());
                        in.nextLine();
                        break;
                    case 10:
                        System.out.print("New GA %: ");
                        found.setGeneralAdmissionPercent(in.nextInt());
                        in.nextLine();
                        break;
                    case 11:
                        System.out.print("New Reserved %: ");
                        found.setReservedPercent(in.nextInt());
                        in.nextLine();
                        break;
                    case 12:
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
        Venue found = dataManager.findVenue(venueMap, query);

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
                    case 1: addEvent(); break;
                    case 2: viewEvent(); break;
                    case 3: updateEvent(); break;
                    case 4: deleteEvent(); break;
                    case 5: back = true; break;
                    default: System.out.println("Invalid option."); break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                in.nextLine();
            }
        }
    }

    public void addEvent() {
        System.out.println("\n--- Add New Event ---");
        System.out.print("Enter Event Type (Sport, Concert, Special): ");
        String type = in.nextLine();
        System.out.print("Enter Event Name: ");
        String name = in.nextLine();
        System.out.print("Enter Date (MM/DD/YYYY): ");
        String date = in.nextLine();
        System.out.print("Enter Time: ");
        String time = in.nextLine();
        System.out.print("Enter VIP Price: ");
        String vip = in.nextLine();
        System.out.print("Enter Gold Price: ");
        String gold = in.nextLine();
        System.out.print("Enter Silver Price: ");
        String silver = in.nextLine();
        System.out.print("Enter Bronze Price: ");
        String bronze = in.nextLine();
        System.out.print("Enter General Admission Price: ");
        String ga = in.nextLine();

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
        System.out.println("\n--- View Events ---");
        System.out.println("a. Display all events");
        System.out.println("b. Search for an event");
        System.out.print("Choice: ");
        String choice = in.nextLine().toLowerCase();

        if (choice.equals("a")) {
            for (Event e : eventMap.values()) {
                System.out.println(e); 
            }
            actionDetail = "User " + loggedInUser.getUserID() + " has printed all events to the console";
            Logger.logAction(actionDetail);
        } else {
            System.out.print("Enter Event ID, Name, or Date: ");
            String query = in.nextLine();
            Event found = dataManager.findEvent(eventMap, query);
            if (found != null) {
                System.out.println(found);
                actionDetail = "User " + loggedInUser.getUserID() + " searched for " + found.getEventID();
                Logger.logAction(actionDetail);
            } else {
                System.out.println("Event not found.");
            }
        }
    }
    public void updateEvent() {
        String actionDetail;
        System.out.print("\nEnter Event ID, Name, or Date to update: ");
        String query = in.nextLine();
        Event found = dataManager.findEvent(eventMap, query);

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
                        System.out.print("New Date: ");
                        found.setDate(in.nextLine());
                        actionDetail = "User " + loggedInUser.getUserID() + " has updated event " + found.getEventID() + "'s date";
                        Logger.logAction(actionDetail);
                        break;
                    case 3:
                        System.out.print("New Time: ");
                        found.setTime(in.nextLine());
                        actionDetail = "User " + loggedInUser.getUserID() + " has updated event " + found.getEventID() + "'s time";
                        Logger.logAction(actionDetail);
                        break;
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
        Event found = dataManager.findEvent(eventMap, query);

        if (found != null) {
            System.out.println("Found: " + found.getEventName());
            System.out.print("Are you sure you want to delete this event? (y/n): ");
            if (in.nextLine().equalsIgnoreCase("y")) {
                String actionDetail = "User " + loggedInUser.getUserID() + " has deleted the event " + found.getEventID();
                Logger.logAction(actionDetail);
                eventMap.remove(found.getEventID());
                System.out.println("Event deleted.");
            }
        } else {
            System.out.println("Event not found.");
        }
    }
    private User findUserByIdentifier(String input) {
        try {
            int id = Integer.parseInt(input);
            for (User u : userMap.values()) {
                if (u.getUserID() == id) {
                    return u;
                }
            }
        } catch (NumberFormatException ignored) {}

        if (userMap.containsKey(input)) {
            return userMap.get(input);
        }

        List<User> matches = new ArrayList<>();
        for (User u : userMap.values()) {
            String fullName = u.getFirstName() + " " + u.getLastName();
            if (fullName.equalsIgnoreCase(input)) {
                matches.add(u);
            }
        }

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

    private void printUserInfo(User u) {
        System.out.println("ID: " + u.getUserID());
        System.out.println("Name: " + u.getFirstName() + " " + u.getLastName());
        System.out.println("Username: " + u.getUsername());
        System.out.println("Type: " + u.getUserType());
        if (u instanceof Customer c) {
            System.out.printf("Money Available: $%.2f%n", c.getMoneyAvailable());
            System.out.println("Membership: " + c.hasMembership());
            System.out.println("Concerts Purchased: " + c.getConcertsPurchased());
        }

        String actionDetail = "User " + loggedInUser.getUserID() + " has printed " + u.getUserID() + " info to the console.";
        Logger.logAction(actionDetail);
    }

    public void saveUpdatedData(){
        dataManager.writeEvent("data/Updated_Event_List.csv", eventMap);
        dataManager.writeUsers("data/Updated_Customer_List.csv", userMap);
        dataManager.writeVenues("data/Updated_Venue_List.csv", venueMap);
    }
}
