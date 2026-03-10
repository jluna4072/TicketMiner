import java.util.HashMap;
import model.users.User;
import utility.DataManager;
public class RunTicketMiner {

    public static void main(String[] args) {
        DataManager dataManager = new DataManager();
        HashMap<String, User> userMap = dataManager.loadUsers("data/Customer_List_PA1.csv");
        //Temporary output to show program runs
        System.out.println("System started.");
        displayMainMenu();
    }

    public static void displayMainMenu() {
        System.out.println("Main Menu Displayed");
    }
}
