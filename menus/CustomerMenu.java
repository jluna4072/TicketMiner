package menus;

/**
 * Handles the Customer menu. Currently a placeholder for future customer functionality.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
import java.util.HashMap;
import java.util.Scanner;
import model.events.Event;
import model.users.Customer;
import model.users.User;
import utility.DataManager;
import utility.Logger;

public class CustomerMenu {

    private final Scanner in;
    private final HashMap<Integer, Event> eventMap;
    private final DataManager dataManager;
    private final User loggedInUser;

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
        this.eventMap = eventMap;
        this.dataManager = dataManager;
        this.loggedInUser = loggedInUser;
    }

    /**
     * Displays the customer menu. Currently a placeholder that returns immediately.
     *
     * @return true if the user logged out
     */
    public boolean show() {
        System.out.println("\nCustomer menu is under construction. Returning to main menu...");
        return true;
    }
}
