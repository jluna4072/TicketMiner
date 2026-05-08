package menus;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import model.events.Event;
import model.users.Customer;
import model.users.User;
import utility.DataManager;
import utility.EventNotFoundException;
import utility.InputReader;
import utility.Logger;

/**
 * Handles the Customer menu including ticket purchasing, order history, and event viewing.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public class CustomerMenu {

    private final Scanner in;
    private final InputReader reader;
    private final HashMap<Integer, Event> eventMap;
    private final DataManager dataManager;
    private final User loggedInUser;
    private final ArrayList<String[]> orderHistory;
    public final double TEXAS_SALES_TAX = .0825;
    public final double DISCOUNT = .10;

    /**
     * Constructs a CustomerMenu with the required shared state.
     *
     * @param in the shared Scanner for console input
     * @param eventMap the shared map of event ID to Event
     * @param dataManager the shared DataManager
     * @param loggedInUser the currently logged-in Customer user
     */
    public CustomerMenu(Scanner in, HashMap<Integer, Event> eventMap,
                        DataManager dataManager, User loggedInUser) {
        this.in = in;
        this.reader = new InputReader(in);
        this.eventMap = eventMap;
        this.dataManager = dataManager;
        this.loggedInUser = loggedInUser;
        this.orderHistory = new ArrayList<>();
    }

    /**
     * Displays the customer menu and handles user choices.
     *
     * @return true if the user logged out
     */
    public boolean show() {
        boolean logout = false;
        while(!logout){
            System.out.println("\n=== Customer Menu ===");
            System.out.println("1. Purchase Tickets");
            System.out.println("2. Print Order Summary");
            System.out.println("3. Preview Tickets");
            System.out.println("4. Logout");
            
            int choice = reader.readPositiveInt("Please select an option (1-4): ");
            String actionDefined;
            switch (choice) {
                case 1:
                    purchaseTickets();
                    break;
                case 2:
                    printOrderSummary();

                    actionDefined = "User " + loggedInUser.getUserID() + " has printed their order summary";
                    Logger.logAction(actionDefined);

                    break;
                case 3:
                    viewEvent();

                    actionDefined = "User " + loggedInUser.getUserID() + " has viewed all events";
                    Logger.logAction(actionDefined);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    actionDefined = "User " + loggedInUser.getUserID() + " had logged out.";
                    Logger.logAction(actionDefined);
                    logout = true;
                    break;
                default:
                    System.out.println("Please select a valid option.");
                    break;
            }
        }
        return true;
    }

    /**
     * Handles ticket purchasing for the logged-in customer.
     * Allows purchasing tickets for multiple events in a single transaction
     * before finalizing. The customer can keep adding events or finalize at any time.
     */
    public void purchaseTickets(){
        Customer loggedInCustomer = (Customer) loggedInUser;
        boolean shopping = true;

        while (shopping) {
            System.out.println("\n--- Purchase Tickets ---");
            System.out.println("Current balance: $" + String.format("%.2f", loggedInCustomer.getMoneyAvailable()));
            String choice = reader.readNonBlank("Enter the ID, name, or date of the event (or type 'done' to finish): ");

            if (choice.equalsIgnoreCase("done")) {
                shopping = false;
                System.out.println("Purchase session ended.");
                continue;
            }
            Event foundEvent;
            try {
                foundEvent = dataManager.resolveEvent(eventMap, choice, in);
            } catch (EventNotFoundException ex) {
                System.out.println(ex.getMessage());
                continue;
            }
            System.out.println("Event Found: " + foundEvent.getEventName() + " (ID: " + foundEvent.getEventID() + ")");

            String ticketType = selectTicketType(foundEvent);
            if (ticketType == null) {
                continue;
            }

            double ticketPrice = getTicketPrice(foundEvent, ticketType);
            int availableSeats = getAvailableSeats(foundEvent, ticketType);
            System.out.println("Available " + ticketType + " seats: " + availableSeats);

            if (availableSeats <= 0) {
                System.out.println("No " + ticketType + " tickets available for this event.");
                continue;
            }
            int numTickets = reader.readPositiveInt("How many " + ticketType + " tickets would you like to purchase? ");
            if (numTickets <= 0) {
                System.out.println("Number of tickets must be greater than 0.");
                continue;
            }

            if (numTickets > availableSeats) {
                System.out.println("Not enough " + ticketType + " tickets available. Only " + availableSeats + " remaining.");
                continue;
            }

            double totalCost = calculateTotalCost(numTickets, ticketPrice);
            double taxAmount = calculateTax(numTickets, ticketPrice);

            if (loggedInCustomer.getMoneyAvailable() >= totalCost) {
                double newBalance = loggedInCustomer.getMoneyAvailable() - totalCost;
                loggedInCustomer.setMoneyAvailable(newBalance);
                updateSeatsSold(foundEvent, ticketType, numTickets);
                foundEvent.addTaxCollected(taxAmount);
                int confirmationNumber = dataManager.generateConfirmationNumber();

                System.out.println("\n--- Order Summary ---");
                System.out.println("Event Type: " + foundEvent.getType());
                System.out.println("Event Name: " + foundEvent.getEventName());
                System.out.println("Event Date: " + foundEvent.getDate());
                System.out.println("Ticket Type: " + ticketType);
                System.out.println("Quantity: " + numTickets);
                System.out.println("Total Charged: $" + String.format("%.2f", totalCost));
                System.out.println("Confirmation Number: " + confirmationNumber);
                System.out.println("Remaining Balance: $" + String.format("%.2f", newBalance));
                System.out.println("---------------------");

                String actionDetail = "User " + loggedInCustomer.getUserID() + " has bought " + numTickets + " " + ticketType + " ticket(s) for event " + foundEvent.getEventID();
                Logger.logAction(actionDetail);
                actionDetail = "User " + loggedInCustomer.getUserID() + " new balance is: $" + newBalance;
                Logger.logAction(actionDetail);

                orderHistory.add(new String[]{
                    foundEvent.getType(),
                    foundEvent.getEventName(),
                    foundEvent.getDate(),
                    ticketType,
                    String.valueOf(numTickets),
                    String.format("%.2f", totalCost),
                    String.valueOf(confirmationNumber)
                });

                dataManager.writeCustomerHistory("data/Customer_Order_History.csv",
                loggedInCustomer.getUserID(), foundEvent.getType(), foundEvent.getEventName(),
                foundEvent.getDate(), ticketType,
                numTickets, totalCost, confirmationNumber);

                String again = reader.readNonBlank("\nWould you like to purchase tickets for another event? (Y/N): ").toUpperCase();
                if (again.equals("N")) {
                    shopping = false;
                }
            } else {
                System.out.println("Insufficient funds. Current balance: $" + String.format("%.2f", loggedInCustomer.getMoneyAvailable())
                    + " | Cost: $" + String.format("%.2f", totalCost));
            }
        }
    }

    /**
     * Writes all orders from this session to a .txt file named after the customer.
     * Each order entry includes event type, event name, date, ticket type,
     * quantity, total price, and confirmation number.
     */
    private void printOrderSummary() {
        if (orderHistory.isEmpty()) {
            System.out.println("No purchases have been made this session.");
            return;
        }

        String fileName = "data/OrderSummary_" + loggedInUser.getFirstName()
            + loggedInUser.getLastName() + ".txt";

        System.out.println("\n--- On-Screen Order Summary ---");
        System.out.println("Customer: " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
        System.out.println("Customer ID: " + loggedInUser.getUserID());
        System.out.println("----------------------------------------");

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("========================================");
            writer.println("       Electronic Order Summary");
            writer.println("========================================");
            writer.println("Customer: " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
            writer.println("Customer ID: " + loggedInUser.getUserID());
            writer.println("========================================\n");

            int orderNum = 1;
            for (String[] order : orderHistory) {
                String summaryLine = "Order #" + orderNum + "\n" +
                                     "Event Type:          " + order[0] + "\n" +
                                     "Event Name:          " + order[1] + "\n" +
                                     "Event Date:          " + order[2] + "\n" +
                                     "Ticket Type:         " + order[3] + "\n" +
                                     "Number of Tickets:   " + order[4] + "\n" +
                                     "Total Price:         $" + order[5] + "\n" +
                                     "Confirmation Number: " + order[6] + "\n" +
                                     "----------------------------------------";
                System.out.println(summaryLine);

                writer.println("Order #" + orderNum);
                writer.println("----------------------------------------");
                writer.println("Event Type:          " + order[0]);
                writer.println("Event Name:          " + order[1]);
                writer.println("Event Date:          " + order[2]);
                writer.println("Ticket Type:         " + order[3]);
                writer.println("Number of Tickets:   " + order[4]);
                writer.println("Total Price:         $" + order[5]);
                writer.println("Confirmation Number: " + order[6]);
                writer.println("----------------------------------------\n");
                orderNum++;
            }

            writer.println("========================================");
            writer.println("         End of Order Summary");
            writer.println("========================================");

            System.out.println("Order summary also written to: " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing order summary: " + e.getMessage());
        }
    }

    /**
     * Prompts the customer to select a ticket type for an event.
     *
     * @param event the event to display ticket options for
     * @return the selected ticket type string, or null if selection was cancelled
     */
    private String selectTicketType(Event event) {
        while (true) {
            System.out.println("\nSelect Ticket Type:");
            System.out.println("1. VIP - $" + event.getVipPrice());
            System.out.println("2. Gold - $" + event.getGoldPrice());
            System.out.println("3. Silver - $" + event.getSilverPrice());
            System.out.println("4. Bronze - $" + event.getBronzePrice());
            System.out.println("5. General Admission - $" + event.getGeneralAdmissionPrice());
            System.out.println("6. Cancel");
            String typeChoice = reader.readNonBlank("Enter choice (1-6): ");
            switch (typeChoice) {
                case "1":
                    return "VIP";
                case "2":
                    return "Gold";
                case "3":
                    return "Silver";
                case "4":
                    return "Bronze";
                case "5":
                    return "General Admission";
                case "6":
                    return null;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }

    /**
     * Returns the ticket price for a given ticket type on an event.
     *
     * @param event the event
     * @param ticketType the ticket type string
     * @return the price for that ticket type
     */
    private double getTicketPrice(Event event, String ticketType) {
        switch (ticketType) {
            case "VIP":
                return event.getVipPrice();
            case "Gold":
                return event.getGoldPrice();
            case "Silver":
                return event.getSilverPrice();
            case "Bronze":
                return event.getBronzePrice();
            case "General Admission":
                return event.getGeneralAdmissionPrice();
            default:
                return 0;
        }
    }

    /**
     * Calculates the total cost of tickets including tax and membership discount.
     *
     * @param numOfTickets number of tickets the customer wants to buy
     * @param admissionPrice the price per ticket
     * @return The total cost of the tickets with the Texas sales tax.
     */
    public double calculateTotalCost(int numOfTickets, double admissionPrice){
        Customer loggedInCustomer = (Customer) loggedInUser;
        double subtotal = numOfTickets * admissionPrice;
        if (loggedInCustomer.hasMembership()) {
            subtotal = subtotal - (subtotal * DISCOUNT);
        }
        double tax = subtotal * TEXAS_SALES_TAX;
        double total = subtotal + tax;
        total = Math.round(total * 100.0) / 100.0;
        return total;
    }

    /**
     * Calculates the tax portion of a purchase based on the number of tickets,
     * price per ticket, and whether the customer has a membership discount.
     *
     * @param numOfTickets number of tickets
     * @param admissionPrice the price per ticket
     * @return the tax amount rounded to the nearest cent
     */
    public double calculateTax(int numOfTickets, double admissionPrice) {
        Customer loggedInCustomer = (Customer) loggedInUser;
        double subtotal = numOfTickets * admissionPrice;
        if (loggedInCustomer.hasMembership()) {
            subtotal = subtotal - (subtotal * DISCOUNT);
        }
        double tax = subtotal * TEXAS_SALES_TAX;
        return Math.round(tax * 100.0) / 100.0;
    }

    /**
     * Displays a sub-menu allowing the customer to list all events or search for a specific event.
     */
    public void viewEvent() {
        String actionDetail;
        while (true) {
            System.out.println("\n--- View Events ---");
            System.out.println("1. Display all events");
            System.out.println("2. Search for an event");
            String choice = reader.readNonBlank("Choice: ");

            if (choice.equals("1")) {
                for (Event e : eventMap.values()) {
                    System.out.println(e);
                }
                actionDetail = "User " + loggedInUser.getUserID() + " has printed all events to the console";
                Logger.logAction(actionDetail);
                break;
            } else if (choice.equals("2")) {
                String query = reader.readNonBlank("Enter Event ID, Name, or Date: ");
                try {
                    Event found = dataManager.resolveEvent(eventMap, query, in);
                    System.out.println(found);
                    actionDetail = "User " + loggedInUser.getUserID() + " searched for " + found.getEventID();
                    Logger.logAction(actionDetail);
                } catch (EventNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            } else {
                System.out.println("Invalid option. Please enter 1 or 2.");
            }
        }
    }

    /**
     * Updates the number of seats sold for the given ticket type on an event.
     *
     * @param event the event to update
     * @param ticketType the ticket type (VIP, Gold, Silver, Bronze, General Admission)
     * @param quantity the number of tickets sold
     */
    private void updateSeatsSold(Event event, String ticketType, int quantity) {
        switch (ticketType) {
            case "VIP":
                event.setVipSeatsSold(event.getVipSeatsSold() + quantity);
                break;
            case "Gold":
                event.setGoldSeatsSold(event.getGoldSeatsSold() + quantity);
                break;
            case "Silver":
                event.setSilverSeatsSold(event.getSilverSeatsSold() + quantity);
                break;
            case "Bronze":
                event.setBronzeSeatsSold(event.getBronzeSeatsSold() + quantity);
                break;
            case "General Admission":
                event.setGeneralAdmissionSeatsSold(event.getGeneralAdmissionSeatsSold() + quantity);
                break;
        }
    }

    /**
     * Returns the number of available (unsold) seats for a given ticket type.
     *
     * @param event the event to check
     * @param ticketType the ticket type string (VIP, Gold, Silver, Bronze, General Admission)
     * @return the number of available seats for that type
     */
    private int getAvailableSeats(Event event, String ticketType) {
        switch (ticketType) {
            case "VIP":
                return event.getVipSeats() - event.getVipSeatsSold();
            case "Gold":
                return event.getGoldSeats() - event.getGoldSeatsSold();
            case "Silver":
                return event.getSilverSeats() - event.getSilverSeatsSold();
            case "Bronze":
                return event.getBronzeSeats() - event.getBronzeSeatsSold();
            case "General Admission":
                return event.getGeneralAdmissionSeats() - event.getGeneralAdmissionSeatsSold();
            default:
                return 0;
        }
    }

}
