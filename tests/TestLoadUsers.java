package tests;

import java.util.HashMap;
import model.users.Customer;
import model.users.User;
import utility.DataManager;

public class TestLoadUsers {
    public static void main(String[] args) {
        DataManager dm = new DataManager();
        HashMap<String, User> users = dm.loadUsers("data/Customer_List_PA1.csv");

        System.out.println("=== Customer CSV Load Test ===");
        System.out.println("Total users loaded: " + users.size());

        User adam = users.get("adambass");
        if (adam != null) {
            System.out.println("\n-- Customer Test (adambass) --");
            System.out.println("Name: " + adam.getFirstName() + " " + adam.getLastName());
            System.out.println("Username: " + adam.getUsername());
            System.out.println("ID: " + adam.getUserID());
            if (adam instanceof Customer) {
                Customer c = (Customer) adam;
                System.out.println("Money: " + c.getMoneyAvailable());
                System.out.println("Membership: " + c.hasMembership());
            }
        } else {
            System.out.println("FAIL: adambass not found");
        }

        User allison = users.get("allisonchang");
        if (allison != null) {
            System.out.println("\n-- Organizer Test (allisonchang) --");
            System.out.println("Name: " + allison.getFirstName() + " " + allison.getLastName());
            System.out.println("Type: " + allison.getClass().getSimpleName());
        } else {
            System.out.println("FAIL: allisonchang not found");
        }

        User angela = users.get("angelacarney");
        if (angela != null) {
            System.out.println("\n-- Admin Test (angelacarney) --");
            System.out.println("Name: " + angela.getFirstName() + " " + angela.getLastName());
            System.out.println("Type: " + angela.getClass().getSimpleName());
        } else {
            System.out.println("FAIL: angelacarney not found");
        }

        System.out.println("\n=== Test Complete ===");
    }
    
}
