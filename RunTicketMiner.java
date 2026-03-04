import manager.EventManager;
import manager.CustomerManager;

/**
 * Main entry point of the program.
 * Creates managers and prepares the system.
 * This file MUST exist because Java requires a main() method.
 */
public class RunTicketMiner {

    public static void main(String[] args) {

        // Create managers (they will later load CSV data)
        EventManager eventManager = new EventManager();
        CustomerManager customerManager = new CustomerManager();

        // Temporary output to show program runs
        System.out.println("System started.");
        System.out.println("EventManager loaded with " + eventManager.getAllEvents().size() + " events.");
        System.out.println("CustomerManager loaded with " + customerManager.getAllCustomers().size() + " customers.");
    }
}
