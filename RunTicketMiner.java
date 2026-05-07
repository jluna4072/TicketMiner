import java.util.HashMap;
import java.util.Scanner;
import menus.AdminMenu;
import menus.CustomerMenu;
import menus.OrganizerMenu;
import model.events.Event;
import model.users.Admin;
import model.users.Customer;
import model.users.Organizer;
import model.users.User;
import model.venues.Venue;
import utility.DataManager;
import utility.InputReader;
import utility.Logger;

/**
 * Main class for the TicketMiner application.
 * Provides a menu-driven console interface for login, registration, and routing
 * users to their role-specific menus.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public class RunTicketMiner {

    private final DataManager dataManager = new DataManager();
    private final HashMap<String, User> userMap = dataManager.loadUsers("data/PA2CSVs/Customer_List_PA2.csv");
    private final HashMap<Integer, Venue> venueMap = dataManager.loadVenues("data/PA2CSVs/Venue_List_PA2.csv");
    private final HashMap<Integer, Event> eventMap = dataManager.loadEvents("data/PA2CSVs/Event_List_PA2.csv", venueMap);
    private final Scanner in = new Scanner(System.in);
    private final InputReader reader = new InputReader(in);
    private User loggedInUser;

    public static void main(String[] args) {
        RunTicketMiner app = new RunTicketMiner();
        System.out.println("System started.");
        app.displayMainMenu();
        app.saveUpdatedData();
    }

    /**
     * Displays the top-level menu and routes the user to registration or login until they choose to exit.
     */
    public void displayMainMenu() {
        boolean exit = true;
        while (exit) {
            System.out.println("=== Welcome to TicketMiner ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("Type EXIT to exit");
            System.out.print("Please select an option: ");
            String input = in.nextLine().trim();

            if (input.equalsIgnoreCase("EXIT")) {
                System.out.println("Thank you for using TicketMiner. Goodbye!");
                System.out.println("Exiting...");
                exit = false;
                continue;
            }

            try {
                int choice = Integer.parseInt(input);
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
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter 1, 2, or EXIT.");
            }
        }
    }

    /**
     * Prompts the user for all required fields and registers a new Customer account.
     */
    public void registerCustomer() {
        String firstName = reader.readNonBlank("Enter first name: ");
        String lastName = reader.readNonBlank("Enter last name: ");
        String username = "";
        String password = "";
        while (true) {
            System.out.print("Enter desired username: ");
            username = in.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("This field cannot be blank. Please try again.");
                continue;
            }
            if (!userMap.containsKey(username)) {
                password = reader.readNonBlank("Enter password: ");
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
                initialAmount = Double.parseDouble(in.nextLine().trim());
                if (initialAmount >= 0) break;
                System.out.println("Amount cannot be negative. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        int userId = dataManager.generateUniqueUserId();
        Customer newCustomer = new Customer(userId, firstName, lastName, username, password, "Customer", initialAmount, becomeMember, 0);
        userMap.put(newCustomer.getUsername(), newCustomer);
        String actionDetail = "User " + userId + " has been registered as a Customer.";
        Logger.logAction(actionDetail);
        System.out.println("Customer registered successfully!");
    }

    /**
     * Prompts the user for all required fields and registers a new Organizer account.
     */
    public void registerOrganizer() {
        String firstName = reader.readNonBlank("Enter first name: ");
        String lastName = reader.readNonBlank("Enter last name: ");
        String username = "";
        String password = "";
        while (true) {
            System.out.print("Enter desired username: ");
            username = in.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("This field cannot be blank. Please try again.");
                continue;
            }
            if (!userMap.containsKey(username)) {
                password = reader.readNonBlank("Enter password: ");
                break;
            } else {
                System.out.println("Username already exists, please input different username.");
            }
        }
        int userId = dataManager.generateUniqueUserId();
        User newOrganizer = new Organizer(userId, firstName, lastName, username, password, "Organizer");
        userMap.put(newOrganizer.getUsername(), newOrganizer);
        String actionDetail = "User " + userId + " has registered as a Organizer to the system.";
        Logger.logAction(actionDetail);
        System.out.println("Organizer registered successfully!");
    }

    /**
     * Handles the login flow by prompting for username and password, validating credentials,
     * and routing the authenticated user to the appropriate role menu.
     */
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
                        CustomerMenu menu = new CustomerMenu(in, eventMap, dataManager, loggedInUser);
                        menu.show();
                    } else if (user instanceof Organizer) {
                        OrganizerMenu menu = new OrganizerMenu(in, eventMap, dataManager, loggedInUser);
                        menu.show();
                    } else if (user instanceof Admin) {
                        AdminMenu menu = new AdminMenu(in, userMap, venueMap, eventMap, dataManager, loggedInUser);
                        menu.show();
                    }
                    loggedInUser = null;
                    break;
                } else {
                    System.out.println("Incorrect password. Please try again.");
                }
            } else {
                System.out.println("Username not found. Please try again.");
            }
        }
    }


    /**
     * Writes the current state of all maps (events, users, venues) to new CSV output files.
     * Called automatically on program exit.
     */
    public void saveUpdatedData() {
        dataManager.writeEvent("data/PA2CSVs/Event_List_PA2.csv", eventMap);
        dataManager.writeUsers("data/PA2CSVs/Customer_List_PA2.csv", userMap);
        dataManager.writeVenues("data/PA2CSVs/Venue_List_PA2.csv", venueMap);
    }
}
