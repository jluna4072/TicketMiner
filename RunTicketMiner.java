
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import model.events.Event;
import model.users.Admin;
import model.users.Customer;
import model.users.Organizer;
import model.users.User;
import model.venues.Venue;
import utility.DataManager;

public class RunTicketMiner {

    private final DataManager dataManager = new DataManager();
    private final HashMap<String, User> userMap = dataManager.loadUsers("data/Customer_List_PA1.csv");
    private final HashMap<Integer, Venue> venueMap = dataManager.loadVenues("data/Venue_List_PA1.csv");
    private final HashMap<Integer, Event> eventMap = dataManager.loadEvents("data/Event_List_PA1.csv");
    private final Scanner in = new Scanner(System.in);
    private final Admin admin = new Admin();

    public static void main(String[] args) {
        RunTicketMiner app = new RunTicketMiner();
        //Temporary output to show program runs
        System.out.println("System started.");
        app.displayMainMenu();
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
                        String username;
                        String password;
                        while (true) {
                            System.out.println("Please enter your username.");
                            username = in.nextLine().trim();
                            System.out.println("Please enter your password.");
                            password = in.nextLine().trim();

                            if (userMap.containsKey(username)) {
                                User user = userMap.get(username);
                                if (user.getPassword().equals(password)) {
                                    System.out.println("Login successful! Welcome, " + user.getFirstName() + "!");
                                    if (user instanceof Customer) {
                                        customerLogin();
                                    } else if (user instanceof Organizer) {
                                        organizerLogin();
                                    } else {
                                        adminLogin();
                                    }
                                    break;
                                } else {
                                    System.out.println("Incorrect username or password. Please try again.");
                                }
                            } else {
                                System.out.println("Username not found. Please try again.");
                            }
                        }
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
            if (!userMap.containsKey(username)) {
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
        admin.addMember(newCustomer, userMap);
        System.out.println("Customer registered successfully!");
        return;
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
            if (!userMap.containsKey(username)) {
                System.out.print("Enter password: ");
                password = in.nextLine().trim();
                break;
            } else {
                System.out.println("Username already exists, please input different username.");
            }
        }

        int userId = dataManager.generateUniqueUserId();
        User newOrganizer = new Organizer(userId, firstName, lastName, username, password, "Organizer");
        admin.addMember(newOrganizer, userMap);
        System.out.println("Organizer registered successfully!");
        return;
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
            if (admin.isUsernameUnique(username, userMap)) {
                System.out.print("Enter password: ");
                password = in.nextLine().trim();
                break;
            } else {
                System.out.println("Username already exists, please input a different username.");
            }
        }
        int userId = dataManager.generateUniqueUserId();
        Admin newAdmin = new Admin(userId, firstName, lastName, username, password, "Admin");
        admin.addMember(newAdmin, userMap);
        System.out.println("Admin registered successfully!");
    }

    public void customerLogin() {
        System.out.println("Customer menu will be implemented here.");
        return;
    }

    public void organizerLogin() {
        System.out.println("Organizer menu will be implemented here.");
        return;

    }

    private User findUserByIdentifier(String input) {
        //try id
        try {
            int id = Integer.parseInt(input);
            for (User u : userMap.values()) {
                if (u.getUserID() == id) {
                    return u;
                }
            }
            return null;
        } catch (NumberFormatException e) {
        }
        //try username
        if (userMap.containsKey(input)) {
            return userMap.get(input);
        }
        //full name search — collect all matches
        List<User> nameMatches = new ArrayList<>();
        for (User u : userMap.values()) {
            String fullName = u.getFirstName() + " " + u.getLastName();
            if (fullName.equalsIgnoreCase(input)) {
                nameMatches.add(u);
            }
        }
        if (nameMatches.size() == 1) {
            return nameMatches.get(0);
        } else if (nameMatches.size() > 1) {
            System.out.println("Multiple members found with that name:");
            for (User u : nameMatches) {
                System.out.println("  ID: " + u.getUserID() + " | Username: " + u.getUsername()
                        + " | " + u.getFirstName() + " " + u.getLastName());
            }
            while (true) {
                System.out.print("Please enter the ID or username to select a specific member: ");
                String refined = in.nextLine().trim();
                for (User u : nameMatches) {
                    if (u.getUsername().equals(refined)) {
                        return u;
                    }
                    try {
                        if (u.getUserID() == Integer.parseInt(refined)) {
                            return u;
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
                System.out.println("Selection does not match any of the found members. Please try again.");
            }
        }
        return null;
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
                        break;
                    case 2:
                        viewUsers();
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

    public void viewUsers() {
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
                        break;
                    case 2:
                        System.out.print("Enter ID, username, or full name: ");
                        String input = in.nextLine().trim();
                        User found = findUserByIdentifier(input);
                        if (found != null) {
                            System.out.println("\n=== Member Found ===");
                            printUserInfo(found);
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
                        break;
                    case 2:
                        String newUsername;
                        while (true) {
                            System.out.print("Enter new username: ");
                            newUsername = in.nextLine().trim();
                            if (admin.isUsernameUnique(newUsername, userMap)) {
                                break;
                            }
                            System.out.println("Username already exists. Please enter a different username.");
                        }
                        String oldUsername = user.getUsername();
                        userMap.remove(oldUsername);
                        user.setUsername(newUsername);
                        admin.addMember(user, userMap);
                        System.out.println("Username updated successfully.");
                        break;
                    case 3:
                        System.out.print("Enter new password: ");
                        String newPassword = in.nextLine().trim();
                        user.setPassword(newPassword);
                        System.out.println("Password updated successfully.");
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
            admin.deleteMember(user.getUsername(), userMap);
            System.out.println("Member deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    public void adminLogin() {

        boolean logout = false;

        while (!logout) {
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
                        System.out.println("Venue management will be implemented here.");
                        break;
                    case 3:
                        System.out.println("Event management will be implemented here.");
                        break;
                    case 4:
                        System.out.println("Logging out...");
                        logout = true;
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

}
